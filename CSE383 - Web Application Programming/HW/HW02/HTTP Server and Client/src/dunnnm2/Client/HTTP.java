
package dunnnm2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
/*
Noah Dunn
9/05/2019
CSE 383 
HTTPClient

This program will connect to a server socket and read the status code, MIME type and all HTTP headers


Several of the code pieces used in this program come directly from Dr. Campbell's provided client,
Further socket programming understanding was provided by: 
https://www.geeksforgeeks.org/socket-programming-in-java/,

Additional response from http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm

*/


public class HTTP {

		/*
		 * Very similarly to our previous assignment, with the addition of the uid and URL
		 */
		int port;
		ServerSocket server = null;
		FileHandler fh = null;
		static String uid ="";
		static URL url = null;
		
		public static void main(String[] args) { /* Parse our arguments and create our client */ 
			try {
				url = new URL(args[0]);
			} catch (MalformedURLException e) {
				System.err.println("URL is not well formed!");
			}
			HTTP client = new HTTP(url); // Begin the server
		}
		
		private HTTP(URL url) {
			try {
				String host = "";
				String path = "";
				host = url.getHost();  //  Grab our host from the url
				path = url.getPath();  //  Grab our path from the url

				Socket socket = new Socket(host, 80);  // Create the socket on the parsed host
				BufferedWriter wr =
				new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8")); //  Ensure our BufferedWriter is writing UTF8 encoded strings
				wr.write("GET "+path+" HTTP/1.1\r\n"); // Our very simple GET request, with the path and protocol
				wr.write("HOST: " + host + "\r\n");
				wr.write("\r\n");
				wr.flush(); // Send our request 
				BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Begin reading reponse 
	            String line = "startLine";
	            while (!(line = rd.readLine()).isEmpty()) {  // Read until we hit the end of the header
	                System.out.println(line);
	            }
	            
	            wr.close();
	            rd.close();
	            socket.close(); //  Close er up
				
				
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
		}
}
