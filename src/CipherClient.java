import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "0.0.0.0";
		int port = 7999;
		// Socket s = new Socket(host, port);

		// YOU NEED TO DO THESE STEPS:
		// -Generate a DES key.
		// -Store it in a file.
		// -Use the key to encrypt the message above and send it over socket s to the server.		
		
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("DES");
			SecretKey deskey = keygen.generateKey();
			
			Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, deskey);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
}