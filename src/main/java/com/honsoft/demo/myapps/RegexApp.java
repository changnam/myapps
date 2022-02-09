package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexApp {
	
	public static void main(String[] args) throws Exception {
		File f = new File("C:\\xFrame5\\project\\sample\\screen\\DEMO\\grd_column_move.xml");
		// 공백문자가 여러개 있거나 없고, < 가 나오고 , 하나이상의 문자열 (group) 이 나오고, 공백이 하나이상 또는 > 가 나오고, 문자열이 있거나 없거나
		Pattern pattern = Pattern.compile("\\s*<(\\w+).*");
		Matcher m = null;
		String sLine;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		while((sLine = br.readLine()) != null) {
			m = pattern.matcher(sLine);
			if(m.find()) {
				System.out.println(m.group(1)+" || "+m.group());
			}
		}
	}
}
