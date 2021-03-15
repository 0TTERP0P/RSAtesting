import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Data {

	/*
	 * This will simply return a string of the contents found 
	 * in the given file
	 */
	public static String load(String filePath) {
		String str = "";
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			String temp = br.readLine();
			while(temp!=null) {
				str+=temp+"\n";
				temp = br.readLine();
			}
			br.close();
			fr.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/*
	 * This will create a file in the given path who's contents will be that of the given string.
	 */
	public static void save(String filePath, String contents) {
		try {
			FileWriter fw = new FileWriter(filePath);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contents);
			bw.close();
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
