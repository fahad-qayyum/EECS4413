package service;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class stateful extends Thread{

	private Socket client;
	private static String lat1;
	private static String lon1;
	private static String lat2;
	private static String lon2;
	private static String token;
	
	public static PrintStream log = System.out;
	public stateful(Socket client) {
		this.client = client;
	}
	public static void main(String[] args) throws Exception {
		int port = 0;
		InetAddress ip = InetAddress.getLocalHost();
		ServerSocket server = new ServerSocket(port, 0 , ip);
		log.printf("Connected to Stateful Server @ %s %d", server.getInetAddress(),server.getLocalPort());
		while(true) {
			Socket client = server.accept();
			new stateful(client).start();

		}
	}
	public void run() {
		try {
			Scanner in = new Scanner(client.getInputStream());
			PrintStream out = new PrintStream(client.getOutputStream());
			String request = "";
			String response ="";
			out.println("Format: Latitude Longitude Token:");
			String p1 = in.nextLine();	
			Random rand = new Random();
			String[] in1 = p1.split("\\s+");
			if(in1[2].equals("0")) {
				int rand_int1 = rand.nextInt(1000);
				lat1 = in1[0];
				lon1 = in1[1];
				token = String.valueOf(rand_int1);
				out.println(rand_int1);
				client.close();
			}else if(in1[2].equals(token)) {
				lat2 = in1[0];
				lon2 = in1[1];
				request = lat1 + " " + lon1 + " " + lat2 + " " + lon2;
				Socket stateSocket = new Socket(InetAddress.getLocalHost(), 50573);
				new PrintStream(stateSocket.getOutputStream(), true).println(request);
				response = new Scanner(stateSocket.getInputStream()).nextLine();
				out.println(response);
				stateSocket.close();
				client.close();

			}else {
				out.println(token);

				out.println("OHH! SNAP");
				client.close();
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
