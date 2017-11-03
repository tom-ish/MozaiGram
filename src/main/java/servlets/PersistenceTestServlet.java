package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Persist;

/**
 * Servlet implementation class PersistenceTestServlet
 */
@WebServlet("/PersistenceTestServlet")
public class PersistenceTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int count = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersistenceTestServlet() {
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
				
		JSONObject json = new JSONObject();

		try {
			if(count <= 10)
				json.put("PersistenceTestServlet", ""+Persist.ERROR);
			else
				json.put("PersistenceTestServlet", ""+Persist.SUCCESS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		count++;
		// Ecriture des donnees
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		writer.println(json.toString());
		System.out.println("Since loading, this servlet has been accessed " +
				count + " times.");
	}

}
