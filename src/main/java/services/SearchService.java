package services;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.Part;

import algorithm.SearchComponent;
import database.DBAuthentification;
import utils.Persist;
import utils.Tools;

public class SearchService {
	
		
	public static int verifyParameters(String sessionkey, String searchword) {
		if(Tools.isNullParameter(sessionkey) || Tools.isNullParameter(searchword))
			return Persist.ERROR_NULL_PARAMETER;

		//		if(DBSessionKey.isSessionKeyExpired(userId))		
		return Persist.SUCCESS;
	}
	
	
	public static String searchResults(String searchword) {
		ArrayList<String> users=DBAuthentification.getUserNames();
		String results="";
		
		for (String user:users) {
			if (user.contains(searchword)) {
				results.concat(user);
				results.concat(",");
			}
		}
		return results;
		
	}
	

	
}
