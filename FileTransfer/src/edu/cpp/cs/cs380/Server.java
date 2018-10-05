package edu.cpp.cs.cs380;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	private ServerSocket server;
	private Socket sock;
	private DataInputStream netIn;
	private DataOutputStream netOut;
	
	//Server object for using server socket
	public Server(int port)
	{
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(5000);
		} catch (IOException e) {
			
		}
	}
	
	public void connect() throws IOException
	{
		sock = server.accept();
		netIn = new DataInputStream(sock.getInputStream());
		netOut = new DataOutputStream(sock.getOutputStream());
	}
	
	public byte[] receive(int x) throws IOException
	{
		byte[] bytes = new byte[x];
		netIn.readFully(bytes);
		return bytes;
	}
	
	public int receive() throws IOException
	{
		return netIn.readInt();
	}
	
	public void confirm() throws IOException
	{
		netOut.writeInt(999);;
	}
	
	public void tryAgain() throws IOException
	{
		netOut.writeInt(-1);
	}
	
	public void deny() throws IOException
	{
		netOut.writeInt(-999);;
	}
	
	public void terminate()
	{
		try {
			server.close();
			sock.close();
			netIn.close();
			netOut.close();
		}catch(NullPointerException e){
		
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
