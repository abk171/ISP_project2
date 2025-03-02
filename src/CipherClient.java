import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "127.0.0.1";
		int port = 7999;
		// Socket s = new Socket(host, port);

		// YOU NEED TO DO THESE STEPS:
		// -Generate a DES key.
		// -Store it in a file.
		// -Use the key to encrypt the message above and send it over socket s to the server.		
		
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("DES");
			keygen.init(56);
			SecretKey deskey = keygen.generateKey();

			byte key[] = deskey.getEncoded();
			try (FileOutputStream out_key = new FileOutputStream("deskey.bin")) {
                out_key.write(key);
            }
			
			
			Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, deskey);

			try (FileOutputStream out_iv = new FileOutputStream("iv.bin")) {
                out_iv.write(c.getIV());
            }
			

			byte encrypted_text[] = c.doFinal(message.getBytes());
			
			try (Socket s = new Socket(host, port);
				OutputStream out  = s.getOutputStream()) {
				System.out.printf("Connected to server at %s, %d\n", host, port);
				out.write(encrypted_text);
				System.out.println("Sent message to server");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}