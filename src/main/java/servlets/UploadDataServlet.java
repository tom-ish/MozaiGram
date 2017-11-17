package servlets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatic;
import mozaik_process.ImageResizer;
import services.ServicesMozaikProcessingCompletableFuture;
import utils.FileProcess;
import utils.Persist;
import utils.Tools;


/**
 * Servlet implementation class UploadDataServlet
 */
@WebServlet("/UploadDataServlet")
@MultipartConfig
public class UploadDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static int NB_IMAGES = 149;

	public static HashMap<String, SimpleEntry<Integer, String>> userTasksMapper;
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadDataServlet() {
		// TODO Auto-generated constructor stub
		if(userTasksMapper == null)
			userTasksMapper = new HashMap<String, SimpleEntry<Integer, String>>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String sessionkey = request.getParameter("sessionkey");
		String keyword = request.getParameter("userKeyword");
		Part imageFilePart = request.getPart("imageFile");
		
		String originalFileName = Tools.getFileName(imageFilePart);
		
		BufferedImage originalImage = FileProcess.getBufferedImageFromPart(imageFilePart);
		BufferedImage image = ImageResizer.resizeValidDimensions(originalImage, originalImage.getType(), originalImage.getWidth(), originalImage.getHeight());
		
		
		JSONObject json = new JSONObject();

		int rslt = ServicesMozaikProcessingCompletableFuture.verifyParameters(sessionkey, keyword, imageFilePart);
		if(rslt != Persist.SUCCESS) {
			try {
				json.put("UploadDataServlet", "" + rslt);				
				PrintWriter writer = response.getWriter();
				response.setContentType("text/plain");
				writer.println(json.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		System.out.println("CompletableFuture call started...");
		rslt = generateMozaik(sessionkey, keyword, image, originalFileName);
		System.out.println("... CompletableFuture call ended");		

		try {
			json.put("UploadDataServlet", "" + rslt);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Ecriture des donnees
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		writer.println(json.toString());
	}
	
	
	private CompletableFuture<ArrayList<String>> getURLs(String sessionkey, String keyword) {
		System.out.println("SESSIONKEY : " + sessionkey +" KEYWORD : " + keyword);
		userTasksMapper.put(sessionkey, new SimpleEntry<Integer, String>(Persist.PROCESS_COMPLETABLE_FUTURE_TASKS_STARTED, ""));
		getServletContext().setAttribute(Persist.USERS_TASKS, userTasksMapper);
		return CompletableFuture.supplyAsync( () -> ServicesMozaikProcessingCompletableFuture.loadAPIImagesFromKeyword(keyword),
				Executors.newSingleThreadExecutor());
	}

	private CompletableFuture<List<BufferedImage>> saveAndResizeImagesFromURLs(String sessionkey, String keyword) {
		return getURLs(sessionkey, keyword).
			thenCompose(urls -> ServicesMozaikProcessingCompletableFuture.saveImagesFromURLs(urls));
	}

	private int generateMozaik(String sessionkey, String keyword, Image image, String originalFileName) {
		long startTime = System.currentTimeMillis();
		saveAndResizeImagesFromURLs(sessionkey, keyword).
			thenCompose(savedImages -> ServicesMozaikProcessingCompletableFuture.generateMozaik(savedImages, image, originalFileName)).
			thenCompose(status -> ServicesMozaikProcessingCompletableFuture.storeMozaik(status, sessionkey, originalFileName)).
			thenAccept( (statusImgIdSimpleEntry) -> {					
				userTasksMapper.put(sessionkey, statusImgIdSimpleEntry);
				getServletContext().setAttribute(Persist.USERS_TASKS, userTasksMapper);
				System.out.println(System.currentTimeMillis() - startTime);
				System.out.println("COMPLETED FUTURE - STATUS : " + statusImgIdSimpleEntry.getKey() + " imgPath : " + statusImgIdSimpleEntry.getValue());
		});
		return Persist.PROCESS_COMPLETABLE_FUTURE_TASKS_STARTED;
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		Persist.OPENED_SESSION = DBStatic.getHibernateSession();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Persist.OPENED_SESSION.close();
		super.destroy();
	}
}
