import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		// ServerSocket server = new ServerSocket(port);
		// Socket s = server.accept();
		
		
		// YOU NEED TO DO THESE STEPS:
		// -Read the key from the file generated by the client.
		// -Use the key to decrypt the incoming message from socket s.		
		// -Print out the decrypt String to see if it matches the orignal message.

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server listening on port " + port + "...");
			while(true) {
				try (Socket socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				FileInputStream keyin = new FileInputStream("deskey.bin");
				FileInputStream ivin = new FileInputStream("iv.bin")) {
					
					byte[] keygen = keyin.readAllBytes();
					SecretKeySpec desKey = new SecretKeySpec(keygen, "DES");
					
					byte[] iv = ivin.readAllBytes();
					IvParameterSpec ivKey = new IvParameterSpec(iv);
					
					byte[] encryptedMessage = in.readAllBytes();

					Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
					cipher.init(Cipher.DECRYPT_MODE, desKey, ivKey);

					byte[] decryptedText = cipher.doFinal(encryptedMessage);
					System.out.println("Decrypted Message: " + new String(decryptedText));
				}
				
			}
		}
	}
}