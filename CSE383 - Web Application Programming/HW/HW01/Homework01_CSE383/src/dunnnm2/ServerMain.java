package dunnnm2;
/*
Noah Dunn
8/28/2019
CSE 383 
dunnnm2_ServerMain

This program will generate a server that will be connected to by campbest_ClientMain.java.
After input is sent over from campbest, the server will either 
1. Filter out incorrect input and display an error message or
2. Return the sum of the 2-6 numbers sent from the client

Several of the code pieces used in this program come directly from Dr. Campbell's provided client,
Further socket programming understanding was provided by: 
https://www.geeksforgeeks.org/socket-programming-in-java/,

*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ServerMain {

	/* This code was taken from Dr. Campbell */
	int port;
	Socket sock = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	private static Logger LOGGER = Logger.getLogger("info");
	FileHandler fh = null;

	public static void main(String[] args) {
		/* This code was modified from Dr. Campbell's */
		int port = -1;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception err) {
			System.err.println("Invalid usage - first arg must be port which is an integer");
			System.exit(-1);
		}

		ServerMain server = new ServerMain(port);
	}
	/* This builds our File handler and formatter for our logger and executes the startup of our server  
	 *  
	 */
	private ServerMain(int port) {

		try {
			/* This code was taken from Dr. Campbell */
			fh = new FileHandler("server.log");
			LOGGER.addHandler(fh);
			LOGGER.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (IOException err) {
			System.err.println("Error - can't open log file");
			// continue here since this is not a blocking error
		}

		LOGGER.info("Attempting to start server on port: " + port);
		startServer(port);

	}
	/*  The body of this method, which takes @param p as the port number for the server
	 *  is longer than a normal method to avoid redundant try-catches that
	 *  would occur with splicing up the method.
	 *  The server accepts clients on port p, processes their input, and either writes
	 *  errors to the log and output stream, or returns the sum of the inputs to the output stream.
	 */
	private void startServer(int p) {
		/*
		 * Credit to https://www.geeksforgeeks.org/socket-programming-in-java/ for this
		 * portion + Dr. Campbell's code
		 */
		Socket socket = null;
		ServerSocket server = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		LOGGER.info("Server Started");
		LOGGER.info("Waiting for a client ...");
		try {
			// We attempt to create two stand in sockets, one representing the server and
			// the other for IO to/from the server
			socket = new Socket();
			server = new ServerSocket(p);
		} catch (IOException e1) {
			LOGGER.info("System not able to be created on port " + p);
			System.exit(-1);
		}

		// Server should never die
		while (true) {
			try {
				// Wait until we get a connection from the client
				socket = server.accept();
				socket.setSoTimeout(10000); //10 Second timeout 
				LOGGER.info("Client accepted");

				// Create our data streams from the connection to the client socket
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF("Hello! This is server dunnnm2");
				
				//Our logic operations 
				boolean finished = false;
				double sum = 0;
				int count = 0;

				// This is the loop that will calculate the sum of between 2 and 6 numbers
				// inclusive
				for (int i = 0; i < 6 && !finished; i++) {
						try {
							int dataInt = in.readInt();
							if (dataInt == 1) {
								try {
									int addedVal = in.readInt();
									LOGGER.info("Added " + addedVal + " to the list "); //  Log values that are correctly added for context
									count++;
									sum = sum + addedVal;
								} catch (EOFException eof) {
									LOGGER.info("Error - The item identified as an int is not an int " + eof.toString());  //  A "1" flag was set for a non-int
									out.writeUTF("Error - The item identified as an int is not an int");
									out.writeDouble(sum);
								} catch (IOException io) {
									LOGGER.info("Error - Stream has been closed early or has timed out " + io.toString());  //  Pre-mature disconnect
									out.writeDouble(sum);
								}
							} else if (dataInt == 2) {
								try {
									double addedVal = in.readDouble();
									count++;
									sum = sum + addedVal;
								} catch (EOFException eof) {
									LOGGER.info("Error - The item identified as a double is not a double " + eof.toString());  //A "2" flag was set for a non-double
									out.writeUTF("Error - The item identified as a double is not a double");
									out.writeDouble(sum);
									break;
								} catch (IOException io) {
									LOGGER.info("Error - Stream has been closed early or has timed out " + io.toString());//  Pre-mature disconnect
									out.writeUTF("Error - Stream has been closed early or has timed out");
									break;
								}

							} else if (dataInt == 0) {  //  It is finished
									finished = true;
									break;
							}

							else {
								LOGGER.info("Error - Input type identified was not 1 or 2 or 0, potential type mismatch, ending connection");  //  Input type was declared off
								out.writeUTF("Error - Input type identified was not 1 or 2 or 0, potential type mismatch, ending connection");
								out.writeDouble(sum);
								in.close();
								out.flush();
								out.close();
								socket.close();
								break;
							}
						}
						catch (EOFException eof) {
								LOGGER.info("Error - User has provided incorrect input(no end terminator 0) or Socket Timeout" + eof.toString());  //A "2" flag was set for a non-double
								out.writeUTF("Error - User has provided incorrect input or Socket Timeout");
								out.writeDouble(sum);
								break;
						}
						 catch (IOException io) {
							LOGGER.info("Error - Stream has been closed early or timed out " + io.toString()); //Early timeout
							out.writeUTF("Error - User has provided incorrect input"); 
							out.writeDouble(sum);
							break;
						}

					}
				if (finished && (count > 1 && count < 6)) {  //  This validates we actually got what we need logically 
					out.writeUTF("OK");
					out.writeDouble(sum);
					LOGGER.info("OK");
					LOGGER.info("Sucessfully Calculated Sum: " + sum);
					try {
						in.close();
						out.flush();
						out.close();
						socket.close();
					} catch (IOException e) {
						LOGGER.info("Connection not able to successfully close");
					}
				} else { // These ones are pretty self explanatory
					out.writeUTF("Error - # of values inputted not in range {2,5}, no end termination (0) provided, or premature disconnection");
					LOGGER.info("Error - # of values inputted not in range {2,5}, no end termination (0) provided, or premature disconnection");
					out.writeDouble(sum);
					try {
						in.close();
						out.flush();
						out.close();
						socket.close();
					} catch (IOException e) {
						LOGGER.info("Error - Connection not able to successfully close");
						out.writeUTF("Error - Connection not able to successfully close ");
					}
				}

			} catch (SocketTimeoutException ste) { // If the user's client takes too long it will time out.
				LOGGER.info("Error - Socket Connection timed out");
				try {
					out.writeUTF("Error - Socket Connection timed out");
					socket.close();
					out.flush();
					out.close();
					in.close();
				} 
				catch (IOException e) {
					LOGGER.info("Error - Socket closed after timeout before message could send");
				}
			}
			catch (IOException ioe) {
				LOGGER.info("Error - Client offered input invalid to the protocol, or the connection was severed prematurely");
			}
		}
	}
}