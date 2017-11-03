package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import services.ServicesMozaikProcessingCompletableFuture;

// C:\Program Files\Java\jdk1.8.0_91/bin

public class FileProcess {
	public static int saveCpt = 0;

	public static int saveImagesFromURLs(ArrayList<String> urls) {
		//long startTime = System.currentTimeMillis();
		int nbImgSaved = 0;
		for(String url : urls) {			
			if(saveImage(url))
				nbImgSaved++;
		}
		//System.out.println("Number of saved files : "+ GetFile.saveCpt + " , exec time : "+((System.currentTimeMillis() - startTime)/1000.)+" s");
		System.out.println("Number of saved files : "+ nbImgSaved);
		return nbImgSaved;
	}

	public static boolean saveImage(String host) {
		URL url = null;
		BufferedImage img = null;
		try {
			url = new URL(host);
			img = ImageIO.read(url.openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("IOException on ImageIO.read() from saveImage() : " + url + "\nprobably Error 403 on URL opening...");
			return false;
		}

		String fileName = url.getFile().substring(url.getFile().lastIndexOf('/')+1);
		//fileName = fileName.replaceAll("[^A-Za-z0-9.]", "");

		if(fileName.contains("?"))
			fileName = fileName.substring(0, fileName.lastIndexOf('?'));		
		if(!Tools.verifyImageExtension(fileName)) {
			System.out.println("Corrupted fileName from saveImage() : " + url);
			return false;
		}

		if(!ServicesMozaikProcessingCompletableFuture.FROM_REPOSITORY.exists())
			ServicesMozaikProcessingCompletableFuture.FROM_REPOSITORY.mkdir();
		
		File file = new File(ServicesMozaikProcessingCompletableFuture.FROM_REPOSITORY.getName()+File.separator+fileName);
		
		if(img != null) {
			try {
				ImageIO.write(img, "jpg", file);
				//System.out.println(file.getAbsolutePath());
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.err.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	public static BufferedImage getBufferedImageFromPart(Part imageFilePart) {
		InputStream imageFileContent = null;
		try {
			imageFileContent = imageFilePart.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("IOException e1 on getInputStream() from UploadDataServlet");
			e1.printStackTrace();
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException : ImageIO.read(imageFileContent)");
		}
		return image;
	}

	public static int getFile(String host)
	{
		InputStream input = null;
		FileOutputStream writeFile = null;

		try
		{
			URL url = new URL(host);
			//Image image = ImageIO.read(url); 
			URLConnection connection = url.openConnection();
			int fileLength = connection.getContentLength();

			if (fileLength == -1)
			{
				//System.out.println("Invalide URL or file.");
				return -1;
			}

			input = connection.getInputStream();
			String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
			writeFile = new FileOutputStream("images_save/"+fileName);
			byte[] buffer = new byte[1024];
			int read;

			while ((read = input.read(buffer)) > 0)
				writeFile.write(buffer, 0, read);
			writeFile.flush();
		}
		catch (IOException e)
		{
			System.out.println("Error while trying to download the file.");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(writeFile != null)
					writeFile.close();
				if(input != null)
					input.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public static void emptyLibrary(){
		File dir_from = new File("images_save");
		for (File f : dir_from.listFiles()){
			f.delete();
		}
	}

	//public static void main(String[] args){
	//	long startTime = System.currentTimeMillis();
	//
	//	String url = "http://ecx.images-amazon.com/images/I/71QunIzKtaL._SL1500_.jpg";
	//
	//	try {
	//		GetFile.saveImage(url);
	//	} catch (IOException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//
	//	System.out.println(System.currentTimeMillis() - startTime);
	//}

}



