package dunnnm2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
Noah Dunn
9/05/2019
CSE 383 
HTTPServer

This program will connect to a client socket, and send an HTTP
reponse regardless of client request

Several of the code pieces used in this program come directly from Dr. Campbell's provided client,
Further socket programming understanding was provided by: 
https://www.geeksforgeeks.org/socket-programming-in-java/,

Additional HTTP References on https://javarevisited.blogspot.com
/2015/06/how-to-create-http-server-in-java-serversocket-example.html

Date/Time referencing from https://stackoverflow.com/questions/6842245/converting-date-time-to-24-hour-format

*/

public class HTTP {
	int port;
	//Socket sock = null;
	ServerSocket server = null;
	FileHandler fh = null;
	static String uid ="dunnnm2";
	
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		HTTP server = new HTTP(port);
	}
	
	private HTTP(int port) {
		try {
			// We attempt to create two stand in sockets, one representing the server and
			// the other for IO to/from the server
			server = new ServerSocket(port);
			
			
			while(true) {
				System.out.println("Waiting on client!");
				try {
					Socket sock = server.accept();
					
				//Taken from HTTP reference listed above
				//Date formatter ripped from https://stackoverflow.com/questions/6842245/converting-date-time-to-24-hour-format
		        Date today = new Date();
		        DateFormat writeFormat = new SimpleDateFormat("hh:mm:ss");
		        String timeFormatted = writeFormat.format(today);
		        		
				String httpResponse = "HTTP/1.1 200 OK\r\n" + 
				     "Content-Type: text/plain\r\n" + 
    					"X-id: " + uid + "\r\n" +
						"Connection: Close" + "\r\n\r\n" +
    					"Hello from " + uid + " " + "- the time is " 
						+ timeFormatted;
                sock.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                System.out.println("Request processed!");
                sock.close();
				}

			 catch (IOException e1) {
				System.out.println("Something went wrong!");
				
			}
			}

		} catch (IOException e1) {
			System.out.println("Server could not be created on port " + port);
			System.exit(-1);
		}
		
	}

}
