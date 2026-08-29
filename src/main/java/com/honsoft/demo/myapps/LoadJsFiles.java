package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class LoadJsFiles {

	private static void loadScript() throws IOException {
		Connection conn = null;
		Statement stmt = null;
		File file = new File("D:\\vueproj\\src\\jsfiles_insert.txt");
		String db = "jdbc:oracle:thin:@localhost:1521:xe";
		String oracleId = "cddba1", oraclePw = "cn0012";
		
		String line = "", subLine = "";
		int cnt = 0, lineNum = 0;
		try {
			conn = DriverManager.getConnection(db, oracleId, oraclePw);
			stmt = conn.createStatement();
		}catch(SQLException e) {
			System.out.println("driver load 오류");
			System.exit(1);
		}
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		while((line = br.readLine()) != null) {
			lineNum++;
			try {
				if (line.startsWith("insert")) {
					//System.out.println(line);
					subLine = line.substring(0,line.length()-1);
					//System.out.println(subLine);
					cnt++;
					stmt.executeUpdate(subLine);
					
					if(cnt % 1000 == 0) {
						System.out.println(cnt + " processed.");
						conn.commit();
					}
				}
			} catch(SQLException e) {
				System.out.println("insert error : "+subLine+", "+e.getMessage());
			}
		} // end of while
		
		try {
			stmt.close();
		}catch(SQLException e) {
			System.out.println("stmt close error: "+e.getMessage());
		}
	}
	public static void main(String[] args) throws IOException {
		System.out.println("LoadJsFiles started. : "+new Date());
		loadScript();
		System.out.println("LoadJsFiles ended. : "+new Date());
	}
}
