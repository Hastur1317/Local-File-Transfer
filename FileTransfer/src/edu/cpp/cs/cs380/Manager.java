package edu.cpp.cs.cs380;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.swing.JTextArea;

public class Manager {

	private File file;
	private File key;
	String identify;
	int chunkSize;
	boolean armor;
	
	//Client side manager set up
	public Manager(String username, String password, File file, File key, int chunkSize, boolean armor)
	{
		this.file = file;
		this.key = key;
		this.identify = username + "@" + password;
		this.chunkSize = chunkSize * 1024;
		this.armor = armor;
	}

	//server side manager Set up
	public Manager(String username, String password, File key, boolean armor, String file)
	{
		this.key = key;
		this.identify = username+ "@" +password;
		this.armor = armor;
		this.file = new File(file);
	}

	//client main function (currently being used for testing tools)
	public void SendFile(String ip, int port, JTextArea txtOutput)
	{
		//Variables
		Client client;
		FileInterface file;
		FileInterface key;
		Hasher slingBlade = new Hasher();
		ASCIIArmorer blacksmith = new ASCIIArmorer();
		Encryptor locksmith = new Encryptor();
		byte[] packet;
		byte[] keySpace;
		int store;
		int packetSize;
		
		//getConnection
		try {
			client = new Client(ip, port);
			txtOutput.append("Connecting...\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		//handShake
			//get packet
			packet = identify.getBytes();
			//hash it
			packet = slingBlade.addCheckSum(packet);
			//send Size
			store = client.send(packet.length);
			//check if its accepted
			if(store != 999)
			{
				client.terminate();
				txtOutput.append("Handshake Size not Accepted\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				return;
			}
			//send packet
			store = client.send(packet);
			if(store != 999)
			{
				txtOutput.append("Handshake not Accepted\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				client.terminate();
				return;
			}
			txtOutput.append("Connection Accepted\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
	//Transfer File
		//prepare files
			//file
			file = new FileInterface(this.file);
			file.openRead();
			key = new FileInterface(this.key);
			key.openRead();
		while(true)
		{
			if(file.leftToRead() == 0)
			{
				client.send(-999);
				txtOutput.append("File Transfer Complete\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				client.terminate();
				return;
			}
			packetSize = chunkSize;
			if(file.leftToRead() < packetSize-1)
			{
				packetSize = file.leftToRead();
			}
			//read bytes
			packet = file.readBytes(packetSize);
			//checksum
			packet = slingBlade.addCheckSum(packet);
			txtOutput.append("Packet Checksum: " + packet[packet.length-1] +"\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			//read key
			keySpace = key.readBytesForce(packet.length);
			//encrypt
			packet = locksmith.encrypt(packet, keySpace);
			//ASCII Armor
			if(armor)
				packet = blacksmith.combine(blacksmith.donASCIIArmor(packet));
			//send Packet
			while(true)
			{
				//send PacketSize
				txtOutput.append("Sending Packet Size: " + packet.length +"\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				store = client.send(packet.length);///////////////////////////////////////////////////////////////////
				if(store == -999)
				{
					txtOutput.append("Packet size failed\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					client.terminate();
					return;
				}
				if(store == 999)
				{
					txtOutput.append("Packet Size accepted\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					//send Packet
					txtOutput.append("Sending Packet\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					store = client.send(packet);////////////////////////////////////////////////////////////
					if(store == -1)
					{
						
						while(store != 999)
						{
							txtOutput.append("Packet Failed to pass hashing\nTrying Again!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
							txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
							store = client.send(packet);
							if(store == -999)
							{
								//fail
								txtOutput.append("Packet wasn't accepted due to bad hashing\n" + "PacketSize sent = " + packet.length);
								txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
								client.terminate();
								return;
							}
						}
					}
					
					txtOutput.append("Packet Sent\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					break;
				}
			}
		}
		}catch (ConnectException e)
		{
			txtOutput.append("Something went Wrong: Could Not Connect\n" + e.getMessage() + "\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		}
		catch (IOException e) {
			txtOutput.append("Something went Wrong: IO\n" + e.getMessage() + "\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		}
	}

	//Server main function
	public void RecieveFile(int port, JTextArea txtOutput)
	{
		//Variables
		Server server = new Server(port);;
		FileInterface file;
		FileInterface key;
		FileInterface profile;
		Hasher slingBlade = new Hasher();
		ASCIIArmorer blacksmith = new ASCIIArmorer();
		Encryptor locksmith = new Encryptor();
		byte[] packet;
		byte[] keySpace;
		int packetSize;
		String receivedText;
		
		try {
		//get Connectionserver
			txtOutput.append("Waiting for Connection\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			server.connect();
		//Handshake
			//Get packet size
			txtOutput.append("Connecting...\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			packetSize = server.receive();
			txtOutput.append("Packet size Receive: " +packetSize + "\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			if(packetSize<=0)
			{
				txtOutput.append("Handshake Size not accepted\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				txtOutput.append("Connection Terminated\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				server.deny();
				server.terminate();
				return;
			}
			packet = new byte[packetSize];
			//Receive Packet
			server.confirm();
			packet = server.receive(packetSize);
			txtOutput.append("Checking Username@Password\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			//check checksum
			packet = slingBlade.checkCheckSum(packet);
			if(packet == null)
			{
				server.deny();
				txtOutput.append("Username@password not accepted\n");
				txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
				server.terminate();
				return;
			}
			//check username
			receivedText = new String(packet);
			if(!(receivedText.equals(identify)))
			{
				profile = new FileInterface(new File("profiles.txt"));
				boolean accepted =profile.checkProfile(receivedText);
				if(!accepted)
				{
					txtOutput.append("Username@password not accepted\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					server.deny();
					server.terminate();
					return;
				}
			}
			txtOutput.append("Username@password Accepted\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			server.confirm();
	//Receive File
		//Prepare Files
			//file
			file = new FileInterface(this.file);
			file.openWrite();
			//key
			key = new FileInterface(this.key);
			key.openRead();
			txtOutput.append("Receiving file...\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			int counter = 0;
			while(true)
			{
				//receive size / eof code
				packetSize = server.receive();
				//test end of file
				if(packetSize == -999)
				{
					//complete
					txtOutput.append("Transfer Complete\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					txtOutput.append("File save under: " + this.file.getName() + "\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					server.confirm();
					server.terminate();
					file.closeWrite();
					key.closeRead();
					return;
				}
				if(packetSize <=0)
				{
					//Size denied
					counter++;
					if(counter >=3)
					{
						//fail
						txtOutput.append("Transfer failure: Packet Size not Accepted\n");
						txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
						server.deny();
						server.terminate();
						file.closeWrite();
						key.closeRead();
						return;
					}
					else
					{
						server.tryAgain();
					}
				}
				else
				{
					//size Accepted
					txtOutput.append("Getting Packet of size " + packetSize + "\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					counter = 0;
					packet = new byte[packetSize];
					server.confirm();
					packet = server.receive(packetSize);
					
					//ASCII Armor
					if(armor)
						packet = blacksmith.removeASCIIArmor(blacksmith.divide(packet));
					keySpace = key.readBytesForce(packet.length);
					packet = locksmith.decrypt(packet, keySpace);
					txtOutput.append("Recieved Checksum" + packet[packet.length-1] +"\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					packet = slingBlade.checkCheckSum(packet);
					if(packet == null)
					{
						//try again
						while(true)
						{
							packet = new byte[packetSize];
							counter++;
							if(counter >=3)
							{
								//fail
								txtOutput.append("Transfer Failure: Packet not accepted because of Poor Hashing\n");
								txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
								server.deny();
								server.terminate();
								file.closeWrite();
								key.closeRead();
								return;
							}
							txtOutput.append("Packet Failed to pass hashing\nTrying Again!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
							txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
							server.tryAgain();
							packet = server.receive(packetSize);
							if(armor)
								packet = blacksmith.removeASCIIArmor(blacksmith.divide(packet));
							packet = locksmith.decrypt(packet, keySpace);
							txtOutput.append("Recieved Checksum" + packet[packet.length-1] +"\n");
							txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
							packet = slingBlade.checkCheckSum(packet);
							if(packet != null)
							{
								counter =0;
								break;
							}
						}
					}
					txtOutput.append("Packet Accepted\n");
					txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
					server.confirm();
					file.writeBytes(packet);
				}
			}
		}
		catch (SocketTimeoutException e)
		{
			txtOutput.append("Something went Wrong: " + e.getMessage() + "\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
			server.terminate();
		}
		catch (ConnectException e)
		{
			txtOutput.append("Something went Wrong: Could Not Connect\n" + e.getMessage() + "\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		}
		catch (IOException e) {
			txtOutput.append("Something went Wrong: IO\n" + e.getMessage() + "\n");
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		}
	}

}
