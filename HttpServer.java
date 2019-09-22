import java.net.*;
import java.io.*;
import java.util.*;

public class HttpServer{

	private ServerSocket server;

	public HttpServer(){
		try{
		server = new ServerSocket(8080);
		System.out.println("Server started at port 8080");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void start(){
		try{
			Socket s = server.accept();
			Client client = new Client(s);
			client.start();	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private class Client{
		private Socket client;
		private PrintWriter out;
		private BufferedReader in;

		public Client(Socket s){
			client = s;
		}

		public void start() throws IOException{
			
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            BufferedOutputStream os = new BufferedOutputStream(client.getOutputStream());

            
        	String inputLine = in.readLine();
           
           
            in.close();
            out.close();
            client.close();
		}
	}

	public static void main(String[] args){
			HttpServer http = new HttpServer();
			http.start();
	}

}