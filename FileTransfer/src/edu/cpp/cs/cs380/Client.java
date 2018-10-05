package edu.cpp.cs.cs380;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
	private Socket sock;
	private DataInputStream netIn;
	private DataOutputStream netOut;
	
	//Client interface for operating client socket
	public Client(String ip, int port) throws UnknownHostException, IOException
	{
		sock = new Socket(ip, port);
		netIn = new DataInputStream(sock.getInputStream());
		netOut = new DataOutputStream(sock.getOutputStream());
	}
	
	public int send(byte[] packet) throws IOException
	{
		netOut.write(packet);
		return netIn.readInt();
	}
	
	public int send(int b) throws IOException
	{
		netOut.writeInt(b);
		return netIn.readInt();
	}
	
	public void terminate() throws IOException
	{
		netIn.close();
		netOut.close();
		sock.close();
	}
	
}
