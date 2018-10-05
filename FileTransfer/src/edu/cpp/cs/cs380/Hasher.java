package edu.cpp.cs.cs380;

public class Hasher
{
	
	//unneeded because this is just a tool
	public Hasher()
	{
		
	}
	
	//takes a bytes sum and hashes a byte that is added to the end of the array as a check sum
	public byte[] addCheckSum(byte[] chunk)
	{
		byte checkSum = 0;
		byte[] newchunk = new byte[chunk.length + 1];
		for(int x = 0; x <chunk.length; x++)
		{
			newchunk[x] = chunk[x];
			checkSum = (byte) (checkSum + (chunk[x] >> (x%8)));
		}
		newchunk[chunk.length] = checkSum;
		System.out.println("RealSum: " + checkSum);
		
		return newchunk;
	}
	
	//checks the check sum is accurate and removes it from the array
	//returns null if not accurate
	public byte[] checkCheckSum(byte[] chunk)
	{
		byte checkSum = 0;
		byte[] newchunk = new byte[chunk.length - 1];
		for(int x = 0; x <chunk.length -1; x++)
		{
			newchunk[x] = chunk[x];
			checkSum = (byte) (checkSum + (chunk[x] >> (x%8)));
		}
		
		System.out.println("Calculated sum: " + checkSum + "\tRealSum: " + chunk[chunk.length-1]);
		if(checkSum == chunk[chunk.length-1])
		{
			return newchunk;
		}
		
		return null;
	}
}
