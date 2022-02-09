package com.honsoft.demo.myapps;

import java.io.File;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;

public class CharsetDetect {
	
	public static void main(String[] args) throws IOException {
		
		 //File f = new File("C:\\xFrame5\\project\\sample\\screen\\DEMO\\grd_column_move.xml");
	        File f = new File("C:\\Users\\chang\\git\\myapps\\src\\main\\java\\com\\honsoft\\demo\\myapps\\test.html");

		 System.out.println(findFileEncoding(f));
	}
	
	
	public static String findFileEncoding(File file) throws IOException {
	    byte[] buf = new byte[4096];
	    java.io.FileInputStream fis = new java.io.FileInputStream(file);
	 
	    // (1)
	    UniversalDetector detector = new UniversalDetector(null);
	 
	    // (2)
	    int nread;
	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
	      detector.handleData(buf, 0, nread);
	    }
	    // (3)
	    detector.dataEnd();
	 
	    // (4)
	    String encoding = detector.getDetectedCharset();
	    if (encoding != null) {
	      System.out.println("Detected encoding = " + encoding);
	    } else {
	      System.out.println("No encoding detected.");
	    }
	 
	    // (5)
	    detector.reset();
	    
	    return encoding;
	}
}
