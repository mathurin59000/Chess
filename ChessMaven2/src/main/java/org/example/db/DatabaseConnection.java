package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DatabaseConnection {

	private static final Integer port = 3306;
	private Connection connexion;

	  
	 public DatabaseConnection() throws ClassNotFoundException {
	        /* On commence par "charger" le pilote MySQL */
	        Class.forName("com.mysql.jdbc.Driver");
	  }
     
    public Connection getConnection(){
        return this.connexion;
    }
    
    public void connect(String server, String bd, String u, String p) throws SQLException {
	    String url = "jdbc:mysql://" + server + ":" + port + "/" + bd;
	    this.connexion = DriverManager.getConnection(url, u, p);
	}
     
    public void closeConnection(){
    	try {
			this.connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //close DB connection here
    }
    
    public List<Map<String, String>> createUser(String username, String email, String password){
    	List<Map<String, String>> resultat = new ArrayList<Map<String, String>>();
    	Statement statement;
		try {
			statement = (Statement) connexion.createStatement();
			ResultSet users = statement.executeQuery( "SELECT * FROM users");
			boolean uniqueEmail = false;
			while (users.next()) {
                if(users.getString("email").equals(email)){
                	uniqueEmail = true;
                }  
			}
			if(!uniqueEmail){
				try {
					String sql = "INSERT INTO users"
							+ "(id, username, email, password, token) VALUES"
							+ "(?,?,?,?,?)";
					PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
					String id = UUID.randomUUID().toString();
					preparedStatement.setString(1, id);
					preparedStatement.setString(2, username);
					preparedStatement.setString(3, email);
					preparedStatement.setString(4, password);
					SessionIdentifierGenerator webToken = new SessionIdentifierGenerator();
					String token = webToken.nextSessionId();
					preparedStatement.setString(5, token);
					preparedStatement.executeUpdate();
					users = statement.executeQuery( "SELECT * FROM users");
					while (users.next()) {
	                    Map<String, String> user = new HashMap<String, String> ();
	                    if(users.getString("email").equals(email)){
	                    	user.put("id", users.getString("id"));
	                        user.put("username", users.getString("username"));
	                        user.put("email", users.getString("email"));
	                        user.put("token", users.getString("token"));
	                        resultat.add(user);
	                    }  
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return resultat;
    }
    
    public List<Map<String, String>> getUrls(){
    	List<Map<String, String>> resultat = new ArrayList<Map<String, String>>();
    	Statement statement = null;
    	ResultSet urls;
    	
    	try {
			statement = (Statement) connexion.createStatement();
			try {
				urls = statement.executeQuery( "SELECT * FROM urls");
				String mess = "";
				while (urls.next()) {
	                    Map<String, String> url = new HashMap<String, String> ();
	                    	url.put("id", urls.getString("id"));
	                        url.put("username", urls.getString("username"));
	                        url.put("url", urls.getString("url"));
	                        url.put("urlMinify", urls.getString("urlMinify"));
	                        resultat.add(url); 
									
		        }
				return resultat;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resultat;
    }
    
    
    public List<Map<String, String>> auth(String email, String password){
    	List<Map<String, String>> resultat = new ArrayList<Map<String, String>>();
    	Statement statement = null;
    	ResultSet users;
		try {
			statement = (Statement) connexion.createStatement();
			try {
				users = statement.executeQuery( "SELECT * FROM users");
				while (users.next()) {
					if(users.getString("email").equals(email)&&users.getString("password").equals(password)){
						Map<String, String> user = new HashMap<String, String> ();
			            user.put("id", users.getString("id"));
			            user.put("username", users.getString("username"));
			            user.put("email", users.getString("email"));
			            user.put("token", users.getString("token"));
			            resultat.add(user);
					}
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultat;
    }
    
    public List<Map<String, String>> getUsers() throws SQLException
    {
            List<Map<String, String>> resultat = new ArrayList<Map<String, String>>();

            Statement statement = (Statement) connexion.createStatement();
            ResultSet users = statement.executeQuery( "SELECT * FROM users");
            while (users.next()) {
                    Map<String, String> user = new HashMap<String, String> ();
                    user.put("id", users.getString("id"));
                    user.put("username", users.getString("username"));
                    user.put("email", users.getString("email"));
                    resultat.add(user);
            }
            // Et on renvoie
            return resultat;
    }
    
    public void deleteUser(String email) throws SQLException
    {
    	try {
			String sql = "DELETE FROM users "
					+ "WHERE email ='" + email + "'";
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public List<Map<String, String>> createUrl(String username, String id, String url, String urlMinify) {
		// TODO Auto-generated method stub
		List<Map<String, String>> resultat = new ArrayList<Map<String, String>>();
    	Statement statement = null;
    	ResultSet users;
    	ResultSet urls;
		try {
			statement = (Statement) connexion.createStatement();
			try {
				users = statement.executeQuery( "SELECT * FROM users");
				while (users.next()) {
					if(users.getString("id").equals(id)){
						List<Map<String, String>> resultat2 = new ArrayList<Map<String, String>>();
				    	//Statement statement2;
								try {
									String sql = "INSERT INTO urls"
											+ "(username, url, urlMinify) VALUES"
											+ "(?,?,?)";
									PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
									preparedStatement.setString(1, username);
									preparedStatement.setString(2, url);
									preparedStatement.setString(3, urlMinify);
									preparedStatement.executeUpdate();
									
									
					                    Map<String, String> url2 = new HashMap<String, String> ();
					                    try {
					        				urls = statement.executeQuery( "SELECT * FROM urls");
					        				while (urls.next()) {
					        					boolean just_one =false;
					        					if(urls.getString("url").equals(url)&&urls.getString("urlMinify").equals(urlMinify)&&urls.getString("username").equals(username)&&just_one==false){
					        						url2.put("id", urls.getString("id"));
							                        url2.put("username", urls.getString("username"));
							                        url2.put("url", urls.getString("url"));
							                        url2.put("urlMinify", urls.getString("urlMinify"));
							                        resultat.add(url2);
							                        just_one=true;
					        					}
					        				}
					                    } catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						return resultat;
					}
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultat;
	}

	public List<Map<String, String>> deleteUrl(String id) {
		System.out.println("Dans delete !");
		// TODO Auto-generated method stub
		List<Map<String, String>> resultat = new ArrayList<Map<String, String>>();
    	Statement statement = null;
    	ResultSet urls;
		try {
			statement = (Statement) connexion.createStatement();
			try {
				urls = statement.executeQuery( "SELECT * FROM urls");
				while (urls.next()) {
					System.out.println(urls);
					if(urls.getString("id").equals(id)){
				    	//Statement statement2;
						System.out.println(id);
						System.out.println(urls.getString("id"));
						try {
									String sql = "DELETE FROM urls "
											+ "WHERE id IN ("+urls.getString("id")+")";
									System.out.println(sql);
									PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
									preparedStatement.executeUpdate();
									
									
					                    Map<String, String> url = new HashMap<String, String> ();
					                  
					                    	url.put("id", urls.getString("id"));
					                        url.put("username", urls.getString("username"));
					                        url.put("url", urls.getString("url"));
					                        url.put("urlMinify", urls.getString("urlMinify"));
					                        resultat.add(url);
					                     
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						return resultat;
					}
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultat;
	}
}
