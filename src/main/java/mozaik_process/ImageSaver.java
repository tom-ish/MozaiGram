package mozaik_process;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import utils.FileProcess;

public class ImageSaver implements Callable<Integer> {
	
	ArrayList<String> urls;
	
	public ImageSaver(ArrayList<String> urls) {
		this.urls = urls;
	}

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return FileProcess.saveImagesFromURLs(urls);
	}

}
