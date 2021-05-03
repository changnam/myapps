package com.honsoft.demo.myapps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileExplorer {
	Date startDate, endDate = null;
	int dirCnt, fileCnt = 0;
	Path file;
	String url = "jdbc:oracle:thin:@localhost:1521:jerry";
	String id = "cddba1", pw = "cn0012";
	Connection conn ;
	PreparedStatement pstmt;
	String sqlStr = "insert into files (file_name,last_mod_time,last_access_time,creation_time) values (?,?,?,?)";
	String fileName;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public void db() {
		   try{	            
	        	//드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
	            Class.forName("oracle.jdbc.driver.OracleDriver");    //oracle
	            conn = DriverManager.getConnection(url, id, pw);
	            
	            pstmt = conn.prepareStatement(sqlStr);	        	

	        }catch (Exception e){
	            e.printStackTrace();
	        }
	}
	
	public void walk(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			
			if (f.isDirectory()) {
				walk(f.getAbsolutePath());
				//System.out.println("Dir:" + f.getAbsoluteFile());
				if(dirCnt++ % 1000 == 0)
					System.out.println(dirCnt + " , Dir:" + f.getAbsoluteFile());
			} else {
				fileName = f.getAbsolutePath();
				file = Paths.get(fileName);
				//System.out.println(fileCnt + " , "+ f.getAbsolutePath());
				
	            try {
					BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
					pstmt.setString(1,fileName);
					pstmt.setTimestamp(2,new java.sql.Timestamp(attr.lastModifiedTime().toMillis()));
					pstmt.setTimestamp(3,new java.sql.Timestamp(attr.lastAccessTime().toMillis()));
					pstmt.setTimestamp(4,new java.sql.Timestamp(attr.creationTime().toMillis()));
					pstmt.executeUpdate();
					
					//System.out.println(sdf.format(attr.lastModifiedTime().toMillis()));
					//startDate = new Date();
					if(fileCnt++ % 1000 == 0) {
						System.out.println(fileCnt + " , "+ attr.lastModifiedTime()+ " , " + attr.lastAccessTime()+ " , "+ attr.creationTime() + " , "+ f.getAbsolutePath());
						conn.commit();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("FileExplorer started. : "+new Date());
		FileExplorer fe = new FileExplorer();
		fe.db();
		fe.walk("D:\\");
		System.out.println("FileExplorer ended. : "+new Date());
		System.out.println("Total directories: "+ fe.dirCnt);
		System.out.println("Total files: "+ fe.fileCnt);
	}

}
