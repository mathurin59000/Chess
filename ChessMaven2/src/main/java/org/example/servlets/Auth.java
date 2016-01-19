package org.example.servlets;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Auth
 */
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auth() {
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
		String username;
		String email;
		String password;
		String confirmedPassword;
		if(request.getParameter("username")!=null&&request.getParameter("email")!=null&&request.getParameter("password")!=null&&request.getParameter("confirmedPassword")!=null){
			//Récupération des paramètres
			username = request.getParameter("username");
			email = request.getParameter("email");
			password = request.getParameter("password");
			confirmedPassword = request.getParameter("confirmedPassword");
			System.out.println("test");
			//Connexion à la BDD
			String url = "jdbc:mysql://localhost:3306/plugdj";
			String utilisateur = "";
			String motDePasse = "";
			Connection connexion = null;
			try {
				System.out.println("test try");
			    connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
			    System.out.println("test try 2");
			    System.out.println(connexion);

			    /* Création de l'objet gérant les requêtes */
			    Statement statement = (Statement) connexion.createStatement();
			    ResultSet resultat = ((java.sql.Statement) statement).executeQuery( "SELECT id, email FROM users;" );
			    System.out.println(resultat);
			    
			   /* while ( resultat.next() ) {
			        int idUtilisateur = resultat.getInt( "id" );
			        String emailUtilisateur = resultat.getString( "email" );
			        String motDePasseUtilisateur = resultat.getString( "mot_de_passe" );
			        String nomUtilisateur = resultat.getString( "nom" );

			    }*/

			} catch ( SQLException e ) {
				System.out.println(e.getMessage());
			    /* Gérer les éventuelles erreurs ici */
			} finally {
			    if ( connexion != null )
			        try {
			            /* Fermeture de la connexion */
			            connexion.close();
			        } catch ( SQLException ignore ) {
			            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
			        }
			}
			
			//Vérification si email valide
				//vérification mdp
				//Ecriture dans la BDD
		}
		else{
			response.setContentType("application/json");   
			PrintWriter out = response.getWriter();
			String jsonObject = "{\"error\":\"All fiels are required, complete it !\"}";
			out.print(jsonObject);
			out.flush();
		}
	}

}
