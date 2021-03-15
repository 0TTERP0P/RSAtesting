
public class Main {
	public static void main(String[] args){
		Data.save("Message.txt", "Hello World! :)");
		RSA.setDelimiter("#");
		String[] keyCombo = RSA.createKeys(13,17);
		String publicKey = keyCombo[0];
		String privateKey = keyCombo[1];
		String f1 = Data.load("message.txt");
		String cipher = RSA.encrypt(f1,publicKey);
		System.out.println(cipher);
		Data.save("cipher.txt",cipher);
		String message = RSA.decrypt(cipher,privateKey);
		System.out.println(message);
		
	}
}
