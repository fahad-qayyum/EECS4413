package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class loc extends Thread{

	public static PrintStream log = System.out;
	private Socket client;
	
	public loc(Socket client) {
		this.client = client;
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int port = 0;
		InetAddress ip = InetAddress.getLocalHost();
		ServerSocket server = new ServerSocket(port, 0, ip);
		log.printf("Loc Server listening @ %s %d\n",server.getInetAddress(), server.getLocalPort() );
		while(true) {
			Socket client = server.accept();
			new loc(client).start();
		}
	}
	public void run() {
		try {
			log.printf("Client Added with info @ %s %d\n",client.getInetAddress(), client.getLocalPort());
			Scanner in = new Scanner(client.getInputStream());
			PrintStream out = new PrintStream(client.getOutputStream());
			out.println("Enter the address: ");
			String address = in.nextLine();
			String payload = "";
			address = address.replace(' ', '+');
			payload = getAddress(address);
			JsonParser parser = new JsonParser();
			payload = parser.parse(payload).getAsJsonObject().get("results").toString();
			
			out.println(payload);
			client.close();
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getAddress(String response) throws Exception {
	    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ response +"&key=AIzaSyBxwV7M-rbBVqKBJBY5fTMYGtAow-Jrz0w");
	    Scanner http = new Scanner(url.openStream());
	    String payload = "";
	    while(http.hasNext()) {
	    	payload += http.nextLine();
	    }
	    return payload;
	    
	  }
}
	
	  
