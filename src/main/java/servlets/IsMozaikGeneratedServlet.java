package servlets;

import java.awt.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatic;
import services.ServicesImage;
import utils.Persist;

/**
 * Servlet implementation class IsMozaikGeneratedServlet
 */
@WebServlet("/IsMozaikGeneratedServlet")
public class IsMozaikGeneratedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IsMozaikGeneratedServlet() {
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
		HashMap<String, SimpleEntry<Integer, Integer>> userTasksMapper = (HashMap<String, SimpleEntry<Integer, Integer>>) getServletContext().getAttribute(Persist.USERS_TASKS);
		
		JSONObject json = new JSONObject();

		if(userTasksMapper.containsKey(sessionkey)) {
			if(userTasksMapper.get(sessionkey).getKey() == Persist.SUCCESS) {
				Integer imgId = userTasksMapper.get(sessionkey).getValue();
				String imgPath = ServicesImage.getPathFromImgId(imgId);
				//Image mozaik = ServicesImage.getImageFromPath(imgPath);
				
				try {
					json.put("IsMozaikGeneratedServlet", ""+Persist.SUCCESS);
					json.put("imgId", ""+imgId);
					json.put("imgPath", ""+imgPath);
					//json.put()
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			try {
				json.put("IsMozaikGeneratedServlet", Persist.ERROR_SESSION_KEY_NOT_FOUND);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Ecriture des donnees
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		writer.println(json.toString());
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
