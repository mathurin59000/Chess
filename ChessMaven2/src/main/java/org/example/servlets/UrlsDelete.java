package org.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.db.DatabaseConnection;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class UrlsDelete
 */
public class UrlsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SERVER="localhost", BD="plugdj", LOGIN="root", PASSWORD="";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrlsDelete() {
        super();
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
		String id;
		System.out.println("dopost servlet");
		System.out.println(request.getParameter("id"));

		//System.out.println(request.getParameter("id"));
		if(request.getParameter("id")!=null){
			//Récupération des paramètres
			id = request.getParameter("id");
			System.out.println(id);
			DatabaseConnection jdbc;
			try {
				jdbc = new DatabaseConnection();
				try {
					jdbc.connect(SERVER, BD, LOGIN, PASSWORD);
					List<Map<String, String>> urls = jdbc.deleteUrl(id);
					if(urls.size()==1){
						String key1 = "id";
						String key2 = "username";
						String key3 = "url";
						String key4 = "urlMinify";
						JSONObject myJson = new JSONObject();
						myJson.put("id", urls.get(0).get(key1));
						myJson.put("username", urls.get(0).get(key2));
						myJson.put("url", urls.get(0).get(key3));
						myJson.put("urlMinify", urls.get(0).get(key4));
						PrintWriter out = response.getWriter();
						out.print(myJson);
						out.flush();
					}
					else{
						response.setContentType("application/json");   
						PrintWriter out = response.getWriter();
						String jsonObject = "{\"error\":\"None url deleted !\"}";
						out.print(jsonObject);
						out.flush();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			response.setContentType("application/json");   
			PrintWriter out = response.getWriter();
			String jsonObject = "{\"error\":\"All fields are required, complete it !\"}";
			out.print(jsonObject);
			out.flush();
		}	
	}

}
