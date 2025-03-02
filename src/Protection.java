import java.io.*;
import java.security.*;
import java.util.Random;

public class Protection
{
	public static byte[] makeBytes(long t, double q) 
	{    
		try 
		{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(byteOut);
			dataOut.writeLong(t);
			dataOut.writeDouble(q);
			return byteOut.toByteArray();
		}
		catch (IOException e) 
		{
			return new byte[0];
		}
	}	

	public static byte[] makeDigest(byte[] mush, long t2, double q2) throws NoSuchAlgorithmException 
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(mush);
		md.update(makeBytes(t2, q2));
		return md.digest();
	}
	

	public static byte[] makeDigest(String user, String password,
									long t1, double q1)
									throws NoSuchAlgorithmException
	{
		// IMPLEMENT THIS FUNCTION.
		MessageDigest md = MessageDigest.getInstance("MD5");
		String input = user + ":" + password;
		md.update(input.getBytes());
		md.update(makeBytes(t1, q1));
		return md.digest();
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("You must provide the message and the choice for the hashing algorithm:\n1. SHA-1\n2. MD5");
			return;
		}
		Random random = new Random();
		String message = args[0];
		int algo_choice = Integer.parseInt(args[1]);
		long t = random.nextLong();
		double q = random.nextDouble();
		try {
			if (algo_choice == 1) {
				System.out.println(makeDigest(message.getBytes(),t, q));
			}
			else {
				System.out.println(makeDigest("Abhigyan", message, t, q));
			}
		} catch(NoSuchAlgorithmException e) {
			System.err.println("Error: Algorithm not found! " + e.getMessage());
		}		
	}
}