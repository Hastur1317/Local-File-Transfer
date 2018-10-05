package edu.cpp.cs.cs380;

public class ASCIIArmorer
{
	public ASCIIArmorer()
	{
		//doesnt matter because its a tool
	}
	
	//Takes bytes and returns ASCII string array with each element a four letter word
	public String[] donASCIIArmor(byte[] bytes)
	{
		int n = bytes.length;
		int padding = 0;
		String[] armor;
		if(n%3 == 0)
		{
			armor = new String[n/3];
		}
		else if((n+1)%3 == 0)
		{
			armor = new String[(n+1)/3];
			padding = 1;
		}
		else
		{
			armor = new String[(n+2)/3];
			padding = 2;
		}
		
		for(int x=0; x<armor.length-1; x++)
		{
			armor[x] = asciiPlate(bytes[x*3], bytes[x*3+1], bytes[x*3+2], 0);
		}
		
		if(padding == 1)
		{
			armor[armor.length-1] = asciiPlate(bytes[bytes.length-2], bytes[bytes.length-1], (byte) 0, 1);
		}
		else if(padding == 2)
		{
			armor[armor.length-1] = asciiPlate(bytes[bytes.length-1], (byte) 0, (byte) 0, 2);
		}
		else
		{
			armor[armor.length-1] = asciiPlate(bytes[bytes.length-3], bytes[bytes.length-2], bytes[bytes.length-1], 0);
		}
		
		return armor;
	}
	
	//used in ASCII armor to make a 4 letter word from 3 bytes
	private String asciiPlate(byte b1, byte b2, byte b3, int pad)
	{
		byte a1 = (byte) ((b1 & 0xfc) >> 2);
		byte a2 = (byte) (((b1 & 0x3) << 4) | ((b2 & 0xf0) >> 4));
		byte a3 = (byte) (((b2 & 0xf) << 2) | ((b3 & 0xc0) >> 6));
		byte a4 = (byte) (b3 & 0x3F);
		if(pad == 1)
		{
			return ascii(a1) + ascii(a2) + ascii(a3) + "=";
		}
		if(pad == 2)
		{
			return ascii(a1) + ascii(a2) + "=" + "=";
		}
		return ascii(a1) + ascii(a2) + ascii(a3) + ascii(a4);
	}
	
	//byte to ASCII alphabet
	private String ascii(byte b)
	{
		String c = "=";
		switch(b)
		{
		case 0:
			c = "A";
			break;
		case 1:
			c = "B";
			break;
		case 2:
			c = "C";
			break;
		case 3:
			c = "D";
			break;
		case 4:
			c = "E";
			break;
		case 5:
			c = "F";
			break;
		case 6:
			c = "G";
			break;
		case 7:
			c = "H";
			break;
		case 8:
			c = "I";
			break;
		case 9:
			c = "J";
			break;
		case 10:
			c = "K";
			break;
		case 11:
			c = "L";
			break;
		case 12:
			c = "M";
			break;
		case 13:
			c = "N";
			break;
		case 14:
			c = "O";
			break;
		case 15:
			c = "P";
			break;
		case 16:
			c = "Q";
			break;
		case 17:
			c = "R";
			break;
		case 18:
			c = "S";
			break;
		case 19:
			c = "T";
			break;
		case 20:
			c = "U";
			break;
		case 21:
			c = "V";
			break;
		case 22:
			c = "W";
			break;
		case 23:
			c = "X";
			break;
		case 24:
			c = "Y";
			break;
		case 25:
			c = "Z";
			break;
		case 26:
			c = "a";
			break;
		case 27:
			c = "b";
			break;
		case 28:
			c = "c";
			break;
		case 29:
			c = "d";
			break;
		case 30:
			c = "e";
			break;
		case 31:
			c = "f";
			break;
		case 32:
			c = "g";
			break;
		case 33:
			c = "h";
			break;
		case 34:
			c = "i";
			break;
		case 35:
			c = "j";
			break;
		case 36:
			c = "k";
			break;
		case 37:
			c = "l";
			break;
		case 38:
			c = "m";
			break;
		case 39:
			c = "n";
			break;
		case 40:
			c = "o";
			break;
		case 41:
			c = "p";
			break;
		case 42:
			c = "q";
			break;
		case 43:
			c = "r";
			break;
		case 44:
			c = "s";
			break;
		case 45:
			c = "t";
			break;
		case 46:
			c = "u";
			break;
		case 47:
			c = "v";
			break;
		case 48:
			c = "w";
			break;
		case 49:
			c = "x";
			break;
		case 50:
			c = "y";
			break;
		case 51:
			c = "z";
			break;
		case 52:
			c = "0";
			break;
		case 53:
			c = "1";
			break;
		case 54:
			c = "2";
			break;
		case 55:
			c = "3";
			break;
		case 56:
			c = "4";
			break;
		case 57:
			c = "5";
			break;
		case 58:
			c = "6";
			break;
		case 59:
			c = "7";
			break;
		case 60:
			c = "8";
			break;
		case 61:
			c = "9";
			break;
		case 62:
			c = "+";
			break;
		case 63:
			c = "/";
			break;
		}
		return c;
	}
	
	//removes ASCII armor
	public byte[] removeASCIIArmor(String[] strings)
	{
		int padding = 0;
		byte[] bytesPad = new byte[strings.length * 3];
		for(int x=0; x<strings.length; x++)
		{
			String[] letters = strings[x].split("");
			byte[] coded = new byte[4];
			for(int y = 0; y<4;y++)
			{
				String test = letters[y];
				if(test.equals("="))
				{
					padding++;
				}
				coded[y] = ascii(letters[y]);
			}
			coded = deplate(coded[0], coded[1], coded[2], coded[3]);
			bytesPad[x*3] = coded[0];
			bytesPad[x*3+1] = coded[1];
			bytesPad[x*3+2] = coded[2];
		
		}
		byte[] bytes = new byte[bytesPad.length - padding];
		
		for(int x=0; x< bytes.length; x++)
		{
			bytes[x] = bytesPad[x];
		}
		
		return bytes;
	}
	
	//takes 4 bytes and puts them back into 3 bytes
	private byte[] deplate(byte a1, byte a2, byte a3, byte a4)
	{
		byte[] bits = new byte[3];
		
		bits[0] = (byte) (((a1 << 2) & 0xfc) | ((a2 >> 4) & 0x3));
		bits[1] = (byte) (((a2<<4) & 0xf0) | ((a3>>2) & 0xf));
		bits[2] = (byte) (((a3<<6) & 0xc0) | (a4));
		
		return bits;
	}

	//ASCII to byte alphabet
	private byte ascii(String c)
	{
		byte b = 0;
		
		switch(c)
		{
		case "A":
			b = 0;
			break;
		case "B":
			b = 1;
			break;
		case "C":
			b = 2;
			break;
		case "D":
			b = 3;
			break;
		case "E":
			b = 4;
			break;
		case "F":
			b = 5;
			break;
		case "G":
			b = 6;
			break;
		case "H":
			b = 7;
			break;
		case "I":
			b = 8;
			break;
		case "J":
			b = 9;
			break;
		case "K":
			b = 10;
			break;
		case "L":
			b = 11;
			break;
		case "M":
			b = 12;
			break;
		case "N":
			b = 13;
			break;
		case "O":
			b = 14;
			break;
		case "P":
			b = 15;
			break;
		case "Q":
			b = 16;
			break;
		case "R":
			b = 17;
			break;
		case "S":
			b = 18;
			break;
		case "T":
			b = 19;
			break;
		case "U":
			b = 20;
			break;
		case "V":
			b = 21;
			break;
		case "W":
			b = 22;
			break;
		case "X":
			b = 23;
			break;
		case "Y":
			b = 24;
			break;
		case "Z":
			b = 25;
			break;
		case "a":
			b = 26;
			break;
		case "b":
			b = 27;
			break;
		case "c":
			b = 28;
			break;
		case "d":
			b = 29;
			break;
		case "e":
			b = 30;
			break;
		case "f":
			b = 31;
			break;
		case "g":
			b = 32;
			break;
		case "h":
			b = 33;
			break;
		case "i":
			b = 34;
			break;
		case "j":
			b = 35;
			break;
		case "k":
			b = 36;
			break;
		case "l":
			b = 37;
			break;
		case "m":
			b = 38;
			break;
		case "n":
			b = 39;
			break;
		case "o":
			b = 40;
			break;
		case "p":
			b = 41;
			break;
		case "q":
			b = 42;
			break;
		case "r":
			b = 43;
			break;
		case "s":
			b = 44;
			break;
		case "t":
			b = 45;
			break;
		case "u":
			b = 46;
			break;
		case "v":
			b = 47;
			break;
		case "w":
			b = 48;
			break;
		case "x":
			b = 49;
			break;
		case "y":
			b = 50;
			break;
		case "z":
			b = 51;
			break;
		case "0":
			b = 52;
			break;
		case "1":
			b = 53;
			break;
		case "2":
			b = 54;
			break;
		case "3":
			b = 55;
			break;
		case "4":
			b = 56;
			break;
		case "5":
			b = 57;
			break;
		case "6":
			b = 58;
			break;
		case "7":
			b = 59;
			break;
		case "8":
			b = 60;
			break;
		case "9":
			b = 61;
			break;
		case "+":
			b = 62;
			break;
		case "/":
			b = 63;
			break;
		}
		return b;
	}

	public byte[] combine(String[] strings)
	{
		String all = "";
		for(int x=0; x<strings.length; x++)
		{
			all = all+strings[x];
		}
		return all.getBytes();
	}
	
	public String[] divide(byte[] bytes)
	{
		String[] strings = new String[bytes.length/4];
		byte[] word = new byte[4];
		for(int x=0; x<strings.length; x++)
		{
			for(int y=0; y<word.length; y++)
			{
				word[y] = bytes[x*4 + y];
			}
			strings[x] = new String(word);
		}
		
		return strings;
	}
}
