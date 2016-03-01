package org.example.servlets;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatEndpoint {
	
	private static Set<Session> clients = 
		    Collections.synchronizedSet(new HashSet<Session>());
		  
		  @OnMessage
		  public void onMessage(String message, Session session) 
		    throws IOException {
			 
		    synchronized(clients){
		      // Iterate over the connected sessions
		      // and broadcast the received message
		      for(Session client : clients){
		        if (!client.equals(session)){
		          client.getBasicRemote().sendText(message);
		        }
		      }
		    }
		    
		  }
		  
		  @OnOpen
		  public void onOpen (Session session) {
		  // Add session to the connected sessions set
		    clients.add(session);
		   
		  }

		  @OnClose
		  public void onClose (Session session) {
		    // Remove session from the connected sessions set
		    clients.remove(session);
		  }
	

}
