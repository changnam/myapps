package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetTest {
	public static void main(String[] args) throws IOException {
		 //File f = new File("C:\\xFrame5\\project\\sample\\screen\\DEMO\\grd_column_move.xml");
	        File f = new File("C:\\Users\\chang\\git\\myapps\\src\\main\\java\\com\\honsoft\\demo\\myapps\\test.html");

	     BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	     
		 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),Charset.forName("EUC-KR")));
		 String sLine = null;
		 
		 byte[] bFile = new byte[(int) f.length()];
		 FileInputStream fis = new FileInputStream(f);
		 fis.read(bFile);
		 fis.close();
		 
		
		 while ((sLine = br.readLine()) != null) {
			 bFile = sLine.getBytes(StandardCharsets.UTF_8);
			 System.out.println(new String(bFile));
		 }
		 System.out.println("--------------------------------------");
		 
		 while ((sLine = br1.readLine()) != null) {
			 bFile = sLine.getBytes(StandardCharsets.UTF_8);
			 System.out.println(new String(bFile));
		 }
		 
		 //String allString = new String(bFile,Charset.forName("UTF-8"));
		 
		 //System.out.println(allString);
		 
		 //while ((sLine = br.readLine()) != null) {
			// System.out.println(sLine);
		// }
	}

}
