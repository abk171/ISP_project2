import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class ProtectedServer
{
	private PrivateKey privateKey;
	public ProtectedServer() {
		try(FileOutputStream fos = new FileOutputStream("serverPublicKey.bin");) {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			keygen.initialize(2048);
			KeyPair pair = keygen.generateKeyPair();

			PublicKey pkey = pair.getPublic();
			this.privateKey = pair.getPrivate();

			fos.write(pkey.getEncoded());
			System.out.println("Generated key pair");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException 
	{
		try {
			DataInputStream in = new DataInputStream(inStream);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
			byte[] encryptedUserPassPair = in.readAllBytes();
			byte[] decryptedUserPassPair = cipher.doFinal(encryptedUserPassPair);
			
			String userPassPair = new String(decryptedUserPassPair);
			String[] parts = userPassPair.split(":", 2);

			if (parts[1].equals(lookupPassword(parts[0])))
				return true;
			else
				return false;
			// IMPLEMENT THIS FUNCTION.
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	
		
	}

	protected String lookupPassword(String user) { return "abc123"; }

	public static void main(String[] args) throws Exception 
	{
		ProtectedServer server = new ProtectedServer();
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();

		if (server.authenticate(client.getInputStream()))
		  System.out.println("Client logged in.");
		else
		  System.out.println("Client failed to log in.");

		s.close();
	}
}