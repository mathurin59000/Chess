package org.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.db.DatabaseConnection;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class Urls
 */
public class Urls extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SERVER="localhost", BD="plugdj", LOGIN="root", PASSWORD="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Urls() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
			DatabaseConnection jdbc;
			try {
				jdbc = new DatabaseConnection();
				try {
					jdbc.connect(SERVER, BD, LOGIN, PASSWORD);
					List<Map<String, String>> urls = jdbc.getUrls();
					if(urls.size()>0){
						String key1 = "id";
						String key2 = "username";
						String key3 = "url";
						String key4 = "urlMinify";
						JSONObject myJson = new JSONObject();
						String mess="";
						for (int i =0;i<urls.size();i++){
							myJson.put("id", urls.get(i).get(key1));
							myJson.put("username", urls.get(i).get(key2));
							myJson.put("url", urls.get(i).get(key3));
							myJson.put("urlMinify", urls.get(i).get(key4));
							if(i==0){
								mess = myJson.toString();
							}
							else{
								mess = mess+"|"+myJson.toString();
							}
						}
						PrintWriter out = response.getWriter();
						out.print(mess);
						out.flush();
					}
					else{
						response.setContentType("application/json");   
						PrintWriter out = response.getWriter();
						String jsonObject = "{\"error\":\"Retrieve failed !\"}";
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
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id;
		String username;
		String url;
		String urlMinify;
		if(request.getParameter("username")!=null&&request.getParameter("id")!=null&&request.getParameter("url")!=null&&request.getParameter("urlMinify")!=null){
			//Récupération des paramètres
			username = request.getParameter("username");
			id = request.getParameter("id");
			url = request.getParameter("url");
			urlMinify = request.getParameter("urlMinify");
			DatabaseConnection jdbc;
			try {
				jdbc = new DatabaseConnection();
				try {
					jdbc.connect(SERVER, BD, LOGIN, PASSWORD);
					List<Map<String, String>> urls = jdbc.createUrl(username, id, url, urlMinify);
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
						String jsonObject = "{\"error\":\"None url created !\"}";
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
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id;
		System.out.println("dodelete servlet");
		System.out.println(request.getParameterNames().toString());
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("url"));
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
		}	}

}
