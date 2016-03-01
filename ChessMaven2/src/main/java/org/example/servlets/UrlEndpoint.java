package org.example.servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/url")
public class UrlEndpoint {

	private static Set<Session> clients = 
		    Collections.synchronizedSet(new HashSet<Session>());
		  
		  @OnMessage
		  public void onMessage(String message, Session session) 
		    throws IOException {
			  
			  
				System.out.println(message);

			  if (message.indexOf("delete")>=0){
				  String url ="http://localhost:8080/ChessMaven2/urlsDelete";
				  URL obj = new URL(url);
				  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				  System.out.println("delete detected");
				  String urlParameters =  message.split(":")[1];
				  con.setRequestMethod("POST");
				  System.out.println(urlParameters);
				  con.setDoOutput(true);
				  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				  wr.writeBytes(urlParameters);
				  wr.flush();
				  wr.close();
				  
				  BufferedReader in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
					String inputLine;
					String tab="";
					//StringBuffer response = new StringBuffer();
					
					while ((inputLine = in.readLine()) != null) {
						if (tab.isEmpty()){
							tab = "delete!"+inputLine;
						}else {
							tab = tab + "|" + inputLine;
						}
					}
					in.close();
					
					synchronized(clients){
					      // Iterate over the connected sessions
					      // and broadcast the received message
					      for(Session client : clients){
					          client.getBasicRemote().sendText(tab);
					      }
					    }
				  
			  }else{
				  String url ="http://localhost:8080/ChessMaven2/Urls";
				  URL obj = new URL(url);
				  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			  con.setRequestMethod("POST");
			  String urlParameters = message;
			  System.out.println(urlParameters);
			  con.setDoOutput(true);
			  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			  wr.writeBytes(urlParameters);
			  wr.flush();
			  wr.close();
			  
			  BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				String tab="";
				//StringBuffer response = new StringBuffer();
				
				while ((inputLine = in.readLine()) != null) {
					if (tab.isEmpty()){
						tab = inputLine;
					}else {
						tab = tab + "|" + inputLine;
					}
				}
				in.close();
				
				synchronized(clients){
				      // Iterate over the connected sessions
				      // and broadcast the received message
				      for(Session client : clients){
				        
				          client.getBasicRemote().sendText(tab);
				        
				      }
				    }
			  
			  }
				
				
			 
		    
		    
		    
		  }
		  
		  @OnOpen
		  public void onOpen (Session session) {
		  // Add session to the connected sessions set
		    clients.add(session);
		    String url ="http://localhost:8080/ChessMaven2/Urls";
		    
		    try {
				URL obj = new URL(url);
				try {
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					
					BufferedReader in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
					String inputLine;
					String tab="";
					//StringBuffer response = new StringBuffer();
					//System.out.println(in);
					while ((inputLine = in.readLine()) != null) {
						//System.out.println(inputLine);
						tab = inputLine;
					}
					in.close();
					session.getBasicRemote().sendText(tab);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		  }

		  @OnClose
		  public void onClose (Session session) {
		    // Remove session from the connected sessions set
		    clients.remove(session);
		  }
}
