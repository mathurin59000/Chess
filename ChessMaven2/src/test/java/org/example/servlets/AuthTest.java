package org.example.servlets;

import org.example.db.DatabaseConnection;
import org.json.simple.JSONObject;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthTest {
	
	private static final String SERVER="localhost", BD="plugdj", LOGIN="root", PASSWORD="";
	private DatabaseConnection jdbc;
	private String username = "test";
	private String emailTest = "test@test.fr";
	private String passwordTest = "1234";
	private List<Map<String, String>> userTest;
	
	@Before
	public void setUp() throws Exception {
		jdbc = new DatabaseConnection();
		jdbc.connect(SERVER, BD, LOGIN, PASSWORD);		
		userTest = jdbc.createUser(username, emailTest, passwordTest);
	}

	@After
	public void tearDown() throws Exception {
		jdbc.deleteUser(emailTest);
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

}
