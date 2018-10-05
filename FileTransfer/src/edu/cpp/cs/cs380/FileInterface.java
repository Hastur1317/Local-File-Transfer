package edu.cpp.cs.cs380;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileInterface
{
	private File f;
	private BufferedInputStream fileIn;
	private BufferedOutputStream fileOut;
	
	//Interface for using files
	public FileInterface(File f) throws FileNotFoundException
	{
		this.f = f;
		fileIn = null;
		fileOut = null;
	}
	
	//makes a profile file for adding usernames and passwords
	@SuppressWarnings("deprecation")
	public void addProfile(String username, String password) throws IOException
	{
		FileUtils.writeStringToFile(f, username + password + "/n", true);
	}
	
	public void openWrite() throws FileNotFoundException
	{
		fileOut = new BufferedOutputStream(new FileOutputStream(f));
	}
	
	public void writeBytes(byte[] bytes) throws IOException
	{
		fileOut.write(bytes);
	}
	
	public void closeWrite() throws IOException
	{
		fileOut.close();
	}
	
	public void openRead() throws FileNotFoundException
	{
		fileIn = new BufferedInputStream(new FileInputStream(f));
	}
	
	public byte[] readBytes(int x) throws IOException
	{
		byte[] bytes = new byte[x];
		fileIn.read(bytes);
		return bytes;
	}
	
	public byte[] readBytesForce(int x) throws IOException
	{
		byte[] bytes = new byte[x];
		if(f.length() < x)
		{
			for(int y = 0;y<bytes.length;y++)
			{
				if(fileIn.available() <= 0)
				{
					fileIn.close();
					fileIn = new BufferedInputStream(new FileInputStream(f));
				}
				bytes[y] = (byte) fileIn.read();
			}
		}
		else
		{
			if(fileIn.available()>=bytes.length)
			{
				fileIn.read(bytes);
			}
			else
			{
				
				fileIn.close();
				fileIn = new BufferedInputStream(new FileInputStream(f));
				fileIn.read(bytes);
			}
		}
		return bytes;
	}
	
	public int leftToRead() throws IOException
	{
		return fileIn.available();
	}
	
	public void closeRead() throws IOException
	{
		fileIn.close();
	}
	
	
	//checks for username and passwords in a profile
	public boolean checkProfile(String username) throws FileNotFoundException
	{
		boolean prof = false;
		
		FileReader filer = new FileReader(f);
		BufferedReader read = new BufferedReader(filer);
		try {
			while(read.ready())
			{
				String test = read.readLine();
				if(test.equals(username))
				{
					read.close();
					filer.close();
					return true;
				}
			}
			read.close();
			filer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prof;
	}
	
	
	
}
