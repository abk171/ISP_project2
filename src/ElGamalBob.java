import java.io.*;
import java.net.*;
import java.security.*;
import java.math.BigInteger;
import java.security.MessageDigest;

public class ElGamalBob
{
	private static boolean verifySignature(	BigInteger y, BigInteger g, BigInteger p, BigInteger a, BigInteger b, String message)
	{
		// IMPLEMENT THIS FUNCTION;
		try {
			BigInteger v1 = y.modPow(a, p).multiply(a.modPow(b, p)).mod(p);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes());
			BigInteger hashInt = new BigInteger(1, hash);
			BigInteger v2 = g.modPow(hashInt, p);

			return v1.equals(v2);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		ObjectInputStream is = new ObjectInputStream(client.getInputStream());

		// read public key
		BigInteger y = (BigInteger)is.readObject();
		BigInteger g = (BigInteger)is.readObject();
		BigInteger p = (BigInteger)is.readObject();

		// read message
		String message = (String)is.readObject();

		// read signature
		BigInteger a = (BigInteger)is.readObject();
		BigInteger b = (BigInteger)is.readObject();

		boolean result = verifySignature(y, g, p, a, b, message);

		System.out.println(message);

		if (result == true)
			System.out.println("Signature verified.");
		else
			System.out.println("Signature verification failed.");

		s.close();
	}
}