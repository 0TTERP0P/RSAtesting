import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
	public static String delimiter = ":";
	
	public static void setDelimiter(String s) {
		delimiter = s;
	}
	
	/*
	 * Will return a set of public and private keys the index 0 will contain
	 * the private key pair as privateKey:n and index 1 will contain the public
	 * key pair as publicKey:n
	 */
	public static String[] createKeys(int p, int q) {
		int n = p*q;
		int phiN = (p-1)*(q-1);
		int e = getPublicKey(n,phiN);
		return new String[]{getPrivateKey(e,phiN)+":"+n,e+":"+n};
	}

	
	/*
	 * This method was not created by myself I found it here:
	 * https://introcs.cs.princeton.edu/java/99crypto/ExtendedEuclid.java.html
	 * 
	 * It will take two number and find the GCD as well as the linear combination of
	 * p and q required to get the GCD
	 */
	private static int[] gcd(int p, int q) {
	      if (q == 0)
	         return new int[] { p, 1, 0 };
	      int[] vals = gcd(q, p % q);
	      int d = vals[0];
	      int a = vals[2];
	      int b = vals[1] - (p / q) * vals[2];
	      return new int[] { d, a, b };
	}
	
	/*
	 * This method will take n and the phi of n and return a number that
	 * is co-prime with both
	 */
	public static int getPublicKey(int n,int phiN) {
		while(true) {
			int result = (int) (Math.random()*phiN+1);
			if(result%2!=0&&gcd(n,result)[0]==1&&gcd(phiN,result)[0]==1) {
				return result;
			}
		}
	}
	
	/*
	 * Using the public key and the phi of N this method will find the
	 * private key
	 */
	public static int getPrivateKey(int e,int phiN) {
		int d = gcd(phiN,e)[2];
		if(d<0) {
			return ((d%phiN)+phiN)%phiN;
		}
		return d;
	}
	
	/*
	 * This will go through each character of the message and use the formula to create a cipher
	 *  c = (m^e)%n where m is any given character and c is the resulting cipher 
	 */
	public static String encrypt(String message,String publicKey) {
		List<String> key = parse(publicKey,":");
		int e = Integer.parseInt(key.get(0));
		BigInteger n = new BigInteger(key.get(1));
		BigInteger result;
		String cipher = "";
		for(char c:message.toCharArray()) {
			result = new BigInteger((int)c+"");
			result = result.pow(e);
			result = result.mod(n);
			cipher += result.toString()+delimiter;
		}
		return cipher.substring(0, cipher.length()-delimiter.length());
	}
	
	/*
	 * This will go through each character of the cipher and use the formula to reconstruct the message
	 * using the formula
	 *  m = (c^d)%n where c is the cipher and m is the corresponding character 
	 */
	public static String decrypt(String cipher,String privateKey) {
		List<String> key = parse(privateKey,":");
		int d = Integer.parseInt(key.get(0));
		BigInteger n = new BigInteger(key.get(1));
		BigInteger result;
		String message = "";
		List<String> c = parse(cipher,delimiter);
		for(String s:c) {
			result = new BigInteger(s);
			result = result.pow(d);
			result = result.mod(n);
			message +=(char)Integer.parseInt(result.toString());
		}
		return message;
	}
	
	/*
	 * This will return a list of strings that have been parsed from the given message using the delimiter
	 */
	protected static List<String> parse(String message, String delimiter) {
		int index = 0;
		int delimIndex = -1;
		ArrayList<String> list = new ArrayList<String>();
		do {
			delimIndex = message.indexOf(delimiter, index);
			if(delimIndex!=-1)
				list.add(message.substring(index, delimIndex));
			else
				list.add(message.substring(index, message.length()));
			index = delimIndex+delimiter.length();
		}while(delimIndex>=0);
		return list;
	}
}
