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
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
			HttpServer http = new HttpServer();
			http.start();
	}

}