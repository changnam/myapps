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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileExplorerContentUpdate {
	private static Logger logger = LoggerFactory.getLogger(FileExplorerContentUpdate.class);
	Date startDate, endDate = null;
	int dirCnt, fileCnt = 0;
	Path file;
	String mysqlUrl = "jdbc:mysql://localhost:3306/quickguide";
	String mysqlId = "shoppingnt", mysqlPw = "Shoppingnt2021!@";
	
	String oracleUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	String oracleId = "cddba1", oraclePw = "cn0012";
	
	String h2Url = "jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE";
	String h2Id = "sa", h2Pw = "";

	String hsqldbUrl = "jdbc:hsqldb:file:d:\\dbfiles\\samplehsqldb;set schema public";
	String hsqldbId = "sa", hsqldbPw = "";
	
	
	Connection conn ;
	PreparedStatement pstmt;
	Statement stmt;
	
	String oracleSqlStr = "insert into files (id, file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (files_seq.nextval,?,?,?,?,?,?,?,?,?)";
	String mysqlSqlStr = "insert into files (file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (?,?,?,?,?,?,?,?,?)";
	String h2SqlStr = "insert into files (file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (?,?,?,?,?,?,?,?,?)";
	String hsqldbSqlStr = "insert into files (file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (?,?,?,?,?,?,?,?,?)";

<<<<<<< HEAD
	String oracleUpdateStr = "update elements where file_path = ? and element_id = ?";
=======
	String oracleUpdateStr = "update elements where file_path = ? and element_id = ?"
>>>>>>> 8732615b2b4ecf5e2efd5ca175763264c8c1c630
	String filePath, fileName, fileExt;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public void dbOracle() {
		   try{	            
	        	//드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
	            Class.forName("oracle.jdbc.driver.OracleDriver");    //oracle
	            conn = DriverManager.getConnection(oracleUrl, oracleId, oraclePw);
	            
	            pstmt = conn.prepareStatement(oracleSqlStr);	        	

	        }catch (Exception e){
	            e.printStackTrace();
	        }
	}
	
	public void dbMysql() {
		   try{	            
	        	//드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
	            Class.forName("com.mysql.cj.jdbc.Driver");    //mysql
	            conn = DriverManager.getConnection(mysqlUrl, mysqlId, mysqlPw);
	            conn.setAutoCommit(false);
	            
	            pstmt = conn.prepareStatement(mysqlSqlStr);	        	

	        }catch (Exception e){
	            e.printStackTrace();
	        }
	}

	public void dbH2() {
		   try{	            
	        	//드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
	            Class.forName("org.h2.Driver");    //mysql
	            conn = DriverManager.getConnection(h2Url, h2Id, h2Pw);
	            conn.setAutoCommit(false);
	            
	            pstmt = conn.prepareStatement(h2SqlStr);	        	

	        }catch (Exception e){
	            e.printStackTrace();
	        }
	}
	
	public void dbHsqldb() {
		   try{	            
	        	//드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
	            Class.forName("org.hsqldb.jdbcDriver");    //mysql
	            conn = DriverManager.getConnection(hsqldbUrl, hsqldbId, hsqldbPw);
	            conn.setAutoCommit(false);
	            
	            pstmt = conn.prepareStatement(hsqldbSqlStr);	        	

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
				//logger.debug("Dir:" + f.getAbsoluteFile());
				if(dirCnt++ % 1000 == 0)
					logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
			} else {
				filePath = f.getAbsolutePath();
				fileName = f.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				else
					fileExt = "";
				
				file = Paths.get(filePath);
				//logger.debug(fileCnt + " , "+ f.getAbsolutePath());
				
	            try {
					BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
					
					pstmt.setString(1,filePath);
					pstmt.setString(2, fileName);
					pstmt.setLong(3, attr.size());
					pstmt.setString(4, fileExt);
					pstmt.setTimestamp(5,new java.sql.Timestamp(attr.lastModifiedTime().toMillis()));
					pstmt.setTimestamp(6,new java.sql.Timestamp(attr.lastAccessTime().toMillis()));
					pstmt.setTimestamp(7,new java.sql.Timestamp(attr.creationTime().toMillis()));
					pstmt.setTimestamp(8,new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
					pstmt.setInt(9, 1);
					pstmt.executeUpdate();
					
					//logger.debug(sdf.format(attr.lastModifiedTime().toMillis()));
					//startDate = new Date();
					if(fileCnt++ % 1000 == 0) {
						logger.info(fileCnt + " , "+ attr.lastModifiedTime()+ " , " + attr.lastAccessTime()+ " , "+ attr.creationTime() + " , "+ f.getAbsolutePath());
						conn.commit();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info(filePath);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info(filePath);
				}
				
			}
		}
	}

	public static void main(String[] args) {
		logger.info("FileExplorer started. : "+new Date());
		FileExplorerContentUpdate fe = new FileExplorerContentUpdate();
		fe.dbOracle();
		//fe.dbMysql();
		//fe.dbHsqldb();
		fe.walk("C:\\xFrame\\project\\DSI\\screen\\NTREE\\bd\\");
		logger.info("FileExplorer ended. : "+new Date());
		logger.info("Total directories: "+ fe.dirCnt);
		logger.info("Total files: "+ fe.fileCnt);
	}

}
