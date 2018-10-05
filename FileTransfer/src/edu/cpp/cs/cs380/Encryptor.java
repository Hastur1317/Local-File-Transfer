package edu.cpp.cs.cs380;

public class Encryptor
{
	//unneeded since this is a tool
	public Encryptor()
	{
		
	}
	
	//takes a file and xors it with a key file
	public byte[] encrypt(byte[] unencrypted, byte[] key)
	{
		int n = unencrypted.length;
		byte[] encrypted = new byte[n];
		for(int x=0; x<n; x++)
		{
			encrypted[x] = (byte) ((key[x])^(unencrypted[x]));
		}
		return encrypted;
	}
	
	//decrypts a file by xoring it with a key; pretty sure it is the same as encrypt but haven't tested it
	public byte[] decrypt(byte[] encrypted, byte[] key)
	{
		int n = encrypted.length;
		byte[] unencrypted = new byte[n];
		for(int x=0; x<n; x++)
		{
			unencrypted[x] = (byte) ((key[x])^(encrypted[x]));
		}
		
		return unencrypted;
	}
	
}
