
import java.net.*;
import java.io.*;
import java.util.logging.*;

/* Solution #1: This code was largely taken from
 * Dr. Campbell's solution to the first sockets homework.
 * 
 * Noah Dunn
 * 
 * 
 */
public class ClientMain {
	int port;
	String fqdn;
	Socket sock = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	private static Logger LOGGER = Logger.getLogger("info");
	FileHandler fh = null;



	//Main method - DO NO WORK IN PSVM, just invoke other classes
	//invocation java <PGM> serverName serverPort Values...
	public static void main(String[] args) {
		int port = -1;
		String fqdn= "";
		try {
			fqdn= args[0];
			port = Integer.parseInt(args[1]);
		}
		catch (Exception err) {
			System.err.println("Invalid usage - first arg must be IP and second argument is port which is an integer, then value list");
			System.exit(-1);
		}
		if (args.length < 6)
		{
			System.err.println("Invalid usage - FQDN port VALUES (at least 2 values required)");
		}
		//copy over values
		java.util.ArrayList<String> values = new java.util.ArrayList<String>();
		ClientMain client = new ClientMain(port,fqdn);
		int intSent = 0;
		double doubleSent = 0.0; 
		
		client.Main(intSent, doubleSent);
	}

	//Construtor
	public ClientMain(int port, String fqdn) {
		try {
			fh = new FileHandler("client.log");
			LOGGER.addHandler(fh);
			LOGGER.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  

		} catch (IOException err) {
			System.err.println("Error - can't open log file");
			//continue here since this is not a blocking error
		}

		LOGGER.info("ClientMAIN - Port = " + port + " fqdn=" + fqdn);
		this.fqdn = fqdn;
		this.port = port;
	}

	//main - takes an arraylist of arguments -> each argument is type, number. 
	public void Main(int integerVal, double doubleVal) {
		boolean result = false;
		String greeting = "";
		String response = "";

		for (int retry = 5; retry > 0 && !result; retry --) {
			try {
				LOGGER.info("Connecting");
				connect();
				greeting = readGreeting();
				System.err.println(greeting);
				sendValues(integerVal, doubleVal);
				response = readResponse();
				result = true;
				sock.close();
			} catch (IOException err) {
				System.err.println("Error during protocol " + err.toString());
				LOGGER.log(Level.SEVERE,"error during connection", err);
			    try {   //in error condition try to close socket
			        sock.close();
			    } catch (IOException err1) {}//can ignore error in this case since this is just best effort.
			    
			}
		}
		if (result) {
			System.out.println("Success");
			System.out.println("Greeting = " + greeting);
			System.out.println("Response => " + response);
		}
		else {
			System.out.println("Failed");
		}
	}

	//connects to server
	public void connect() throws IOException {
		sock = new Socket(fqdn,port);
		dis = new DataInputStream(sock.getInputStream());
		dos = new DataOutputStream(sock.getOutputStream());
		sock.setSoTimeout(5000); // 5 second timeout
	}

	//server sends initial greeting that should contain students uniqueid
	public String readGreeting() throws IOException {
		String greet = dis.readUTF();
		return greet;
	}

	//send values to server
	public void sendValues(int integerVal, double doubleVal) throws IOException {
			dos.writeInt(integerVal);
			dos.writeDouble(doubleVal);	
			dos.flush();
	}

	//read response - first string should be "OK" or "Error - and error message"  Then it should send back SUM of numbers as a double
	public String readResponse() throws IOException {
		String response = dis.readUTF();
		response += " sum of values = ";
		response += dis.readDouble();
		return response;
	}
}
