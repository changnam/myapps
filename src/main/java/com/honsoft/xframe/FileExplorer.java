package com.honsoft.xframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileExplorer {
	private static Logger logger = LoggerFactory.getLogger(FileExplorer.class);
	Date startDate, endDate = null;
	int dirCnt, fileCnt = 0;
	Path file;
	String mysqlUrl = "jdbc:mysql://localhost:3306/quickguide";
	String mysqlId = "shoppingnt", mysqlPw = "Shoppingnt2021!@";

	String oracleUrl = "jdbc:oracle:thin:@localhost:1521:jerry";
	String oracleId = "cddba1", oraclePw = "cn0012";

	String h2Url = "jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE";
	String h2Id = "sa", h2Pw = "";

	String hsqldbUrl = "jdbc:hsqldb:file:d:\\dbfiles\\samplehsqldb;set schema public";
	String hsqldbId = "sa", hsqldbPw = "";

	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;

	String oracleSqlStr = "insert into files (id, file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (files_seq.nextval,?,?,?,?,?,?,?,?,?)";
	String mysqlSqlStr = "insert into files (file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (?,?,?,?,?,?,?,?,?)";
	String h2SqlStr = "insert into files (file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (?,?,?,?,?,?,?,?,?)";
	String hsqldbSqlStr = "insert into files (file_path, file_name, file_size, file_ext, last_mod_time,last_access_time,creation_time, runjob_time, runjob_id) values (?,?,?,?,?,?,?,?,?)";

	String filePath, fileName, fileExt;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	StaxExample staxExample = new StaxExample();
	StaxReadXml staxReadXml = new StaxReadXml();
	int xmlCnt = 0;
	
	public void dbOracle() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("oracle.jdbc.driver.OracleDriver"); // oracle
			conn = DriverManager.getConnection(oracleUrl, oracleId, oraclePw);

			pstmt = conn.prepareStatement(oracleSqlStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dbMysql() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysql
			conn = DriverManager.getConnection(mysqlUrl, mysqlId, mysqlPw);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(mysqlSqlStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dbH2() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("org.h2.Driver"); // mysql
			conn = DriverManager.getConnection(h2Url, h2Id, h2Pw);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(h2SqlStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dbHsqldb() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("org.hsqldb.jdbcDriver"); // mysql
			conn = DriverManager.getConnection(hsqldbUrl, hsqldbId, hsqldbPw);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(hsqldbSqlStr);

		} catch (Exception e) {
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
				// logger.debug("Dir:" + f.getAbsoluteFile());
				dirCnt++;
				if (dirCnt % 1000 == 0)
					logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
			} else {
				fileCnt++;
				filePath = f.getAbsolutePath();
				fileName = f.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				else
					fileExt = "";

				file = Paths.get(filePath);
				// logger.debug(fileCnt + " , "+ f.getAbsolutePath());

				// BufferedReader br = new BufferedReader(new InputStreamReader(new
				// FileInputStream(f),Charset.forName("euc-kr")));
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

					String sLine = null;
					Map<String, Map<String, String>> map = null;

					while ((sLine = br.readLine()) != null) {
						map = RegexApp.getMap(sLine);
						RegexApp.insertMap(map);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(1);
				}
				
				if (fileCnt % 100 == 0) {
					System.out.println(fileCnt+" files processed."+f.getAbsolutePath());
				}
			}
		}
	}

	public void walkHier(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {

			if (f.isDirectory()) {
				walkHier(f.getAbsolutePath());
				// logger.debug("Dir:" + f.getAbsoluteFile());
				dirCnt++;
				if (dirCnt % 1000 == 0)
					logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
			} else {
				fileCnt++;
				filePath = f.getAbsolutePath();
				fileName = f.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				else
					fileExt = "";

				file = Paths.get(filePath);

				if("XML".equals(fileExt.toUpperCase())){
					xmlCnt++;
					staxExample.getXml(f);
				}
				
				
				if (fileCnt % 100 == 0) {
					System.out.println(fileCnt+" files processed."+f.getAbsolutePath());
				}
			}
		}
	}


	public void walkReal(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {

			if (f.isDirectory()) {
				walkReal(f.getAbsolutePath());
				// logger.debug("Dir:" + f.getAbsoluteFile());
				dirCnt++;
				if (dirCnt % 1000 == 0)
					logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
			} else {
				fileCnt++;
				filePath = f.getAbsolutePath();
				fileName = f.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				else
					fileExt = "";

				file = Paths.get(filePath);

				if("XML".equals(fileExt.toUpperCase())){
					xmlCnt++;
					XmlToObject.prepareXML(f);
					//XmlToObject.insertXML();
				}
				
				
				if (fileCnt % 100 == 0) {
					System.out.println(fileCnt+" files processed."+f.getAbsolutePath());
				}
			}
		}
	}

	public void walkFull(String path) throws FileNotFoundException, XMLStreamException, SQLException {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {

			if (f.isDirectory()) {
				walkFull(f.getAbsolutePath());
				// logger.debug("Dir:" + f.getAbsoluteFile());
				dirCnt++;
				if (dirCnt % 1000 == 0)
					logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
			} else {
				fileCnt++;
				filePath = f.getAbsolutePath();
				fileName = f.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				else
					fileExt = "";

				file = Paths.get(filePath);

				if("XML".equals(fileExt.toUpperCase())){
					xmlCnt++;
					staxReadXml.parse(f);
				}
				
				
				if (fileCnt % 100 == 0) {
					System.out.println(fileCnt+" files processed."+f.getAbsolutePath());
				}
			}
		}
	}

	
	
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, SQLException {
		logger.info("FileExplorer started. : " + new Date());
		FileExplorer fe = new FileExplorer();
		// fe.dbOracle();
		// fe.dbMysql();
		// fe.dbHsqldb();
		//fe.walk("C:\\xFrame\\project\\screen\\XAP"); // element 별 attribute 종류
		//fe.walkHier("C:\\xFrame\\project\\screen\\XAP"); // element hierachy
		//fe.walkReal("C:\\xFrame\\project\\screen\\XAP"); // element attribute data 
		fe.walkFull("C:\\xFrame\\project\\screen\\XAP"); // element attribute data 
		logger.info("FileExplorer ended. : " + new Date());
		logger.info("Total directories: " + fe.dirCnt);
		logger.info("Total files: " + fe.fileCnt);
	}

}
