import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.String;
import java.net.FileNameMap;

public class HttpServer{

	private ServerSocket server;
	private Map<String, Integer> results;
	private SimpleDateFormat date;

	public HttpServer(){
		try{
			server = new ServerSocket(0);
			System.out.println("Server started at " + InetAddress.getLocalHost().getCanonicalHostName() + ":" + server.getLocalPort());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void start(){

		results = new HashMap<>();
		date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		date.setTimeZone(TimeZone.getTimeZone("GMT"));

		try{
			while(true){
				Client c = new Client(server.accept());
				Thread t = new Thread(c);
				t.start();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private class Client implements Runnable{
		private Socket client;
		private PrintWriter out;
		private BufferedReader in;
		static final String root = "www";
		String filename = null;
		

		public Client(Socket s){
			client = s;
		}

		public void run() {
			try{
			InetAddress IPAddress = client.getInetAddress(); 
			int port = client.getPort();

			//https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream());
			BufferedOutputStream os = new BufferedOutputStream(client.getOutputStream());


			String inputLine = in.readLine();
			StringTokenizer parse = new StringTokenizer(inputLine);
			String method = parse.nextToken().toUpperCase();
			filename = parse.nextToken().toLowerCase();

			File myFile = new File (root, filename);
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
    		String content_type = fileNameMap.getContentTypeFor(myFile.getName());
			byte [] mybytearray = new byte [(int)myFile.length()];

			FileInputStream fileIn = new FileInputStream(myFile);
			fileIn.read(mybytearray);
			fileIn.close();

            // header of the packet
			out.println("HTTP/1.1 200 OK");
			out.println("Date: " + date.format(new Date()));
			out.println("Server: Java HTTP Server from Sagar : 1.1");
			out.println("Last-Modified: " + date.format(myFile.lastModified()));
			out.println("Accept-Ranges: bytes");
			out.println("Content-length: " + mybytearray.length);
			out.println("Content-type: " + content_type);
			out.println();
			out.flush();
			
			// data being sent
			os.write(mybytearray,0,mybytearray.length);
			os.flush();

			int access_count = count(filename);
			System.out.println (filename + "|" + IPAddress.toString().substring(1) + "|" + port + "|" + access_count);

			}
			catch (FileNotFoundException fnfe)
		    {
		      	fileNotFound(out, filename);
		    }
			catch(IOException ioe){
				System.err.println("Server Error: " + ioe);
			}
			finally{
				try{
					in.close();
					out.close();
					client.close();
				}
				catch(Exception e){
					System.err.println("Error closing stream: " + e);
				}
			}
		}
	}

	synchronized public int count(String filename){
		if (results.containsKey(filename)) { 
 			int c = results.get(filename);
 			results.put(filename,  c + 1); 
 			return (c+1) ;
        } 
        else { 
  			results.put(filename, 1);
  			return 1;
        } 
	}

	public static void main(String[] args){
		HttpServer http = new HttpServer();
		http.start();
	}

	private void fileNotFound(PrintWriter out, String file){
	    //send file not found HTTP headers
	    out.println("HTTP/1.0 404 File Not Found");
	    out.println("Date: " + date.format(new Date()));
	    out.println("Server: Java HTTP Server from Sagar : 1.1");
	    out.println("Content-Type: text/html");
	    out.println();
	    out.println("<HTML>");
	    out.println("<HEAD><TITLE>File Not Found</TITLE>" +
	      "</HEAD>");
	    out.println("<BODY>");
	    out.println("<H2>404 File Not Found: " + file + "</H2>");
	    out.println("</BODY>");
	    out.println("</HTML>");
	    out.flush();
  	}

}