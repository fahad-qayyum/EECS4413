package service;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class geo extends Thread{

	private Socket client;
	public static PrintStream log = System.out;
	public geo(Socket client) {
		this.client = client;
	}
	public static void main(String[] args) throws Exception {
		int port = 50573;
		InetAddress ip = InetAddress.getLocalHost();
		ServerSocket server = new ServerSocket(port, 0 , ip);
		log.printf("Connected to Geo Server @ %s %d", server.getInetAddress(),server.getLocalPort());
		while(true) {
			Socket client = server.accept();
			new geo(client).start();

		}
	}
	public void run() {
		try {
			Scanner in = new Scanner(client.getInputStream());
			PrintStream out = new PrintStream(client.getOutputStream());
			String response = "";
			String p1 = in.nextLine();			
			String[] point1 = p1.split("\\s+");
			double t1 = Double.parseDouble(point1[0]) * (Math.PI/180); // Point 1 Latitude
			double n1 = Double.parseDouble(point1[1]) * (Math.PI/180); // Point 1 Longitude
			double t2 = Double.parseDouble(point1[2]) * (Math.PI/180); // Point 2 Latitude
			double n2 = Double.parseDouble(point1[3]) * (Math.PI/180); // Point 2 Longitude
			
			//calculating X and Y
			//sin^2(theta) means (sin(theta))^2
			double x,y, dist;
			y = Math.cos(t1) * Math.cos(t2);
			x = Math.sin((t2-t1)/2) * Math.sin((t2-t1)/2) + y * Math.sin((n2-n1)/2) * Math.sin((n2-n1)/2);
			dist = Math.atan2(Math.sqrt(x), Math.sqrt(y)) * 12742; 
			
			response = "The geodesic distance between the points is: " + dist;
			out.println(response);
			
			
			
			
			
			
			client.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
