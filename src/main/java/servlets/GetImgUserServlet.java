package servlets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
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

import services.ServicesImage;
import services.ServicesAuthentification;
import services.ServicesMozaikProcessingCompletableFuture;
import utils.FileProcess;
import utils.Persist;


/**
 * Servlet implementation class UploadDataServlet
 */
@WebServlet("/GetImgUserServlet")
@MultipartConfig
public class GetImgUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetImgUserServlet() {
		// TODO Auto-generated constructor stub

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
		String username = request.getParameter("username");
			
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		ArrayList<String> results=new ArrayList<String>();
		try {
			JSONObject json = new JSONObject();
			results = ServicesImage.getPathsfromUser(username);
			json.put("GetImgUserServlet", ""+Persist.SUCCESS);
			json.put("listImg", ""+results);
			System.out.println(json);
			writer.println(json.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


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
