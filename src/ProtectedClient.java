import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import java.util.Date;

public class ProtectedClient
{
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException 
	{
		DataOutputStream out = new DataOutputStream(outStream);

		// IMPLEMENT THIS FUNCTION.
		try (FileInputStream keyIn = new FileInputStream("serverPublicKey.bin");){
			byte[] serverPublicKeyBytes = keyIn.readAllBytes();
			

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(serverPublicKeyBytes);
			PublicKey serverPublicKey = keyFactory.generatePublic(publicKeySpec);

			String userPassPair = user + ":" + password;

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);
			byte[] encryptedUserPassPair = cipher.doFinal(userPassPair.getBytes());
			
			out.write(encryptedUserPassPair);

			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception 
	{
		String host = "127.0.0.1";
		int port = 7999;
		String user = "George";
		String password = "abc123";
		Socket s = new Socket(host, port);

		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());

		s.close();
	}
}