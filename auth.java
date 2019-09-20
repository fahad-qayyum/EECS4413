package service;

import java.io.IOException;
import g.Util;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class auth extends Thread{
	
	
	public static PrintStream log = System.out;
	private Socket client;
	
	public auth(Socket client) {
		this.client = client;
	}
	
	public static void main(String[] args) throws Exception {
		int port = 0;
		InetAddress ip = InetAddress.getLocalHost();
		ServerSocket server = new ServerSocket(port, 0, ip);
		log.printf("Connect to Auth Server <%s %d>", server.getInetAddress(), server.getLocalPort());
		while(true) {
			Socket client = server.accept();
			new auth(client).start();
			
		}
	}
	public void run() {
		try {
			String usernameArray[]= new String[4];
			int count =0 ;
			Scanner in = new Scanner(client.getInputStream());
			PrintStream out = new PrintStream(client.getOutputStream(), true);
			String response = "";
			String foundName = "";
			User user = new User();
			out.print("Enter the Username: ");
			String username = in.next();
			out.print("Enter the password: ");
			String password = in.next();
			String givenHash = "";
			String query = "Select * from Client where name = ?";
			Connection con = connect();
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if(rs.next() != false) {
				foundName = rs.getString("name");
				if (username.equals(foundName)){
					user.setCount(rs.getInt("count"));

					user.setSalt(rs.getString("salt"));

					user.setHash(rs.getString("hash"));

					givenHash = g.Util.hash(password, user.getSalt(), user.getCount());
					if(givenHash.equals(user.getHash())) {
						response = "OK";
					}else {
						response = "FAILURE";
					}
				}
			}else {
				response = "FAILURE";
			}
			
			out.println(response);
			st.close();
			con.close();

			log.printf("Closing <%s:%d>\n", client.getInetAddress(), client.getLocalPort());
			client.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Connection connect(){
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:Models_R_US.db");
			System.out.println("Connected!");
		} catch (ClassNotFoundException| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}
