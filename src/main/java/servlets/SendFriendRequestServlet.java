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

import database.DBStatic;
import services.ServicesFriendship;
import utils.Persist;

/**
 * Servlet implementation class AddFriendServlet
 */
@WebServlet("/SendFriendRequestServlet")
public class SendFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendFriendRequestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sessionkey =  request.getParameter("sessionkey");
		int friendId = Integer.valueOf(request.getParameter("userid"));
		
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		
		try {
			// Traitement des donnees
			JSONObject json = new JSONObject();
			int rslt = ServicesFriendship.addFriend(sessionkey, friendId, json);
			json.put("SendFriendRequestServlet", ""+rslt);
			json.put("sessionkey", ""+sessionkey);
			json.put("friendId", ""+friendId);
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
