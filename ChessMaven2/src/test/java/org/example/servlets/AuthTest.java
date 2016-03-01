package org.example.servlets;

import org.example.db.DatabaseConnection;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthTest {
	
	private static final String SERVER="localhost", BD="plugdj", LOGIN="root", PASSWORD="";
	private DatabaseConnection jdbc;
	private String username = "test";
	private String emailTest = "test@test.fr";
	private String passwordTest = "1234";
	private String idTest;
	private String urlTest = "https://www.youtube.com/watch?v=q74CbVPvnr0";
	private String urlMinifyTest = "q74CbVPvnr0";
	private List<Map<String, String>> userTest;
	private List<Map<String, String>> urlsTest;
	
	@Before
	public void setUp() throws Exception {
		jdbc = new DatabaseConnection();
		jdbc.connect(SERVER, BD, LOGIN, PASSWORD);		
		userTest = jdbc.createUser(username, emailTest, passwordTest);
		urlsTest = jdbc.createUrl(username, userTest.get(0).get("id"), urlTest, urlMinifyTest);
		idTest = jdbc.getUser(emailTest).get(0).get("id");
		
	}

	@After
	public void tearDown() throws Exception {
		jdbc.deleteUser(emailTest);
		jdbc.deleteUrl(urlsTest.get(0).get("id"));
	}

	@Test
	public void successConnection() {
		String email = "test@test.fr";
		String password = "1234";
		assertEquals(userTest, jdbc.auth(email, password));
	}
	 
	@Test
	public void failConnection(){
		String email = "test@test.fr";
		String password = "1243";
		
		assertNotEquals(userTest, jdbc.auth(email, password));
	}

	@Test
    public void cantRegisterSameUser(){
        List<Map<String, String>> no_user = new ArrayList<Map<String, String>>();
        assertEquals(jdbc.createUser(username, emailTest, passwordTest), no_user);
    }
	
	@Test
    public void successRegister(){
            assertEquals(userTest, jdbc.getUser(emailTest));       
    }
	
	@Test
	public void successUrl(){
		assertEquals(urlsTest, jdbc.getUrl(urlsTest.get(0).get("id")));
		}
	
	@Test
	public void deleteUrl(){
		jdbc.deleteUrl(urlsTest.get(0).get("id"));
		List<Map<String, String>> no_url = new ArrayList<Map<String, String>>();
		assertEquals(no_url, jdbc.getUrl(urlsTest.get(0).get("id")));
	}
}
