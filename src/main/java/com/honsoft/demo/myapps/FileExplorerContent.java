package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsoft.xframe.StaxExample;
import com.honsoft.xframe.StaxReadXml;

public class FileExplorerContent {
	private static Logger logger = LoggerFactory.getLogger(FileExplorerContent.class);
	Date startDate, endDate = null;
	int dirCnt, fileCnt, totalLines, skipLines, totalMatchCnt = 0;
	int xmlCnt, jsCnt = 0;
	int fileNotFoundCnt, ioExceptionCnt = 0;
	
	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private final Charset EUCKR_CHARSET = Charset.forName("EUC-KR");
	private final Charset WINDOWS_CHARSET = Charset.forName("WINDOWS-1252");
	
	Path file;
	String mysqlUrl = "jdbc:mysql://localhost:3306/apitest";
	String mysqlId = "shoppingnt", mysqlPw = "Shoppingnt2021!@";

	String oracleUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	String oracleId = "cddba1", oraclePw = "cn0012";

	String h2Url = "jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE";
	String h2Id = "sa", h2Pw = "";

	String hsqldbUrl = "jdbc:hsqldb:file:d:\\dbfiles\\samplehsqldb;set schema public";
	String hsqldbId = "sa", hsqldbPw = "";
	
	String mariadbUrl = "jdbc:mariadb://localhost:3306/hanacard";
	String mariadbId = "hanacard", mariadbPw = "hanacard";

	Connection conn;
	PreparedStatement pstmt, pstmtLine, pstmtSymbol, pstmtHeader, pstmtEncoding;
	PreparedStatement pstmtScreen, pstmtTranMap, pstmtPushButton;
	
	Statement stmt;
	ResultSet rs;

	String oracleSqlStr = "insert into filecontents (id, file_path, file_name, file_ext, line_num, line_text) values (files_seq.nextval,?,?,?,?,?)";
	String oracleSqlLine = "insert into filecontents(id,file_path, file_name,file_ext, line_num, line_text,work_timestamp) values(files_seq.nextval,?,?,?,?,?,?)";
	String oracleEncoding = "insert into fileencodings (file_path, file_name, line_num) values (?,?,?)";
	
	String mysqlSqlStr = "insert into filecontents (file_path, file_name, file_ext, line_num, line_text) values (?,?,?,?,?)";
	String h2SqlStr = "insert into filecontents (file_path, file_name, file_ext, line_num, line_text) values (?,?,?,?,?)";
	String hsqldbSqlStr = "insert into filecontents (file_path, file_name, file_ext, line_num, line_text) values (,?,?,?,?,?)";
	String mysqlSqlLine = "insert into filecontents(file_path, file_name,line_num, line_text,work_timestamp) values(?,?,?,?,?)";
	String mysqlEncoding = "insert into fileencodings (file_path, file_name, line_num) values (?,?,?)";
	
	String filePath, fileName, fileExt;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	Pattern pattern;
	Matcher matcher;
	
	BufferedWriter bw;

	//StaxExample staxExample = new StaxExample();
	StaxReadXml staxReadXml = new StaxReadXml();
	
	public void dbOracle() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("oracle.jdbc.driver.OracleDriver"); // oracle
			conn = DriverManager.getConnection(oracleUrl, oracleId, oraclePw);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(oracleSqlStr);
			pstmtLine = conn.prepareStatement(oracleSqlLine);
			pstmtEncoding = conn.prepareStatement(oracleEncoding);

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
	
	public void dbMariaDb() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("org.mariadb.jdbc.Driver"); // mysql
			conn = DriverManager.getConnection(mariadbUrl, mariadbId, mariadbPw);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(mysqlSqlStr);
			pstmtLine = conn.prepareStatement(mysqlSqlLine);
			pstmtEncoding = conn.prepareStatement(mysqlEncoding);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public void walk(String path, Charset charset) throws Exception {

		File root = new File(path);
		File[] list = root.listFiles();
		String encoding = null;

		if (list == null) {
			if (root != null) {
				filePath = root.getAbsolutePath();
				fileName = root.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
				else
					fileExt = "";
				
				file = Paths.get(filePath);
				//if ("INI".equals(fileExt.toUpperCase())) {
				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())
						|| "INI".equals(fileExt.toUpperCase())
						|| "SQL".equals(fileExt.toUpperCase())
						|| "LST".equals(fileExt.toUpperCase())
						|| "DFM".equals(fileExt.toUpperCase())
						|| "PAS".equals(fileExt.toUpperCase())
						|| "INI".equals(fileExt.toUpperCase())
						|| "XFDL".equals(fileExt.toUpperCase())
						|| "XJS".equals(fileExt.toUpperCase())
						|| "REPORT".equals(fileExt.toUpperCase())
						|| "CSV".equals(fileExt.toUpperCase())
						|| "LOG".equals(fileExt.toUpperCase())
						|| "JSON".equals(fileExt.toUpperCase())
						|| "CSS".equals(fileExt.toUpperCase())
						|| "TS".equals(fileExt.toUpperCase())
						|| "TSX".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(root);
					if("UTF-8".equals(encoding)) {
						insertSource(root, Charset.forName("UTF-8"));
					}else if ("EUC-KR".equals(encoding)) {
						insertSource(root, Charset.forName("EUC-KR"));
					}else if ("UTF-16LE".equals(encoding)) {
						insertSource(root, Charset.forName("UTF-16LE"));
					}else if ("WINDOWS-1252".equals(encoding)) {
						insertSource(root,  Charset.forName("EUC-KR"));
					}else {
						insertSource(root, Charset.forName("EUC-KR"));
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
					}
				}
			}
			return;
		}
		
		for (File f : list) {
			if(".svn".equals(f.getName()))
				continue;
			if (f.isDirectory()) {
				if (!f.getAbsolutePath().contains("C:\\HANA\\CTS\\xf5"))
					walk(f.getAbsolutePath(), charset);
				dirCnt++;
				//walk(f.getAbsolutePath());
				// logger.debug("Dir:" + f.getAbsoluteFile());
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

				//if ("INI".equals(fileExt.toUpperCase())) {
				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())
						|| "INI".equals(fileExt.toUpperCase())
						|| "SQL".equals(fileExt.toUpperCase())
						|| "LST".equals(fileExt.toUpperCase())
						|| "DFM".equals(fileExt.toUpperCase())
						|| "PAS".equals(fileExt.toUpperCase())
						|| "INI".equals(fileExt.toUpperCase())
						|| "XFDL".equals(fileExt.toUpperCase())
						|| "XJS".equals(fileExt.toUpperCase())
						|| "REPORT".equals(fileExt.toUpperCase())
						|| "CSV".equals(fileExt.toUpperCase())
						|| "LOG".equals(fileExt.toUpperCase())
						|| "JSON".equals(fileExt.toUpperCase())
						|| "CSS".equals(fileExt.toUpperCase())
						|| "TS".equals(fileExt.toUpperCase())
						|| "TSX".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(f);
					if("UTF-8".equals(encoding)) {
						insertSource(f, Charset.forName("UTF-8"));
					}else if ("EUC-KR".equals(encoding)) {
						insertSource(f, Charset.forName("EUC-KR"));
					}else if ("UTF-16LE".equals(encoding)) {
						insertSource(f, Charset.forName("UTF-16LE"));
					}else if ("WINDOWS-1252".equals(encoding)) {
						insertSource(f,  Charset.forName("EUC-KR"));
					}else {
						insertSource(f,  Charset.forName("EUC-KR"));
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
					}
				}
			}
		}
	}
	
	private void insertSource(File f, Charset charset) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
			String sLine = null;
			int line_num = 0;
			Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			while ((sLine = br.readLine()) != null) {
				try {
					line_num++;
					pstmtLine.setString(1,  filePath);
					pstmtLine.setString(2,  fileName);
					pstmtLine.setString(3,  fileExt);
					pstmtLine.setLong(4,  line_num);
					pstmtLine.setString(5,  sLine);
					pstmtLine.setTimestamp(6,  currentTimestamp);
					pstmtLine.executeUpdate();
				}catch (SQLException e) {
					e.printStackTrace();
					try {
						pstmtEncoding.setString(1, f.getAbsolutePath());
						pstmtEncoding.setString(2, f.getName());
						pstmtEncoding.setInt(3, line_num);
						pstmtEncoding.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			conn.commit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void walkStax(String path) throws Exception{
		File root = new File(path);
		File[] list = root.listFiles();
		String encoding = null;
		
		if(list == null) {
			if (root != null) {
				filePath = root.getAbsolutePath();
				fileName = root.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
				else
					fileExt = "";
				
				if ("BDM103U.xml".equals(fileName)) {
					System.out.println("break point");
				}
				file = Paths.get(filePath);
				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())
						|| "XFDL".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(root);
					try {
					if("UTF-8".equals(encoding)) {
						staxReadXml.parse(root, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding)) {
						staxReadXml.parse(root, EUCKR_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding)) {
						staxReadXml.parse(root, WINDOWS_CHARSET);
					}else {
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
						staxReadXml.parse(root, UTF8_CHARSET);
					}
				}catch(Exception e) {
					System.out.println("--------------------------------------");
					System.out.println(file.toAbsolutePath());
					e.printStackTrace();
					System.out.println("---------------------------------------");
				}
			}
		}
			return;
	}
		
	for(File f : list) {
		if(".svn".equals(f.getName()))
			continue;
		if (f.isDirectory()) {
			dirCnt++;
			if (!f.getAbsolutePath().contains("C:\\HANA\\CTS\\xf5"))
				walkStax(f.getAbsolutePath());
			//walk(f.getAbsolutePath());
			// logger.debug("Dir:" + f.getAbsoluteFile());
			if (dirCnt % 1000 == 0)
				logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
		} else {
			fileCnt++;
			filePath = f.getAbsolutePath();
			fileName = f.getName();

			if ("BDM103U.xml".equals(fileName)) {
				System.out.println("break point");
			}
			
			if (fileName.lastIndexOf(".") >= 0)
				fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			else
				fileExt = "";

			file = Paths.get(filePath);
			// logger.debug(fileCnt + " , "+ f.getAbsolutePath());

			if ("XML".equals(fileExt.toUpperCase())|| "XFDL".equals(fileExt.toUpperCase())) {
				xmlCnt++;
				encoding = findFileEncoding(f);
				try {
				if("UTF-8".equals(encoding)) {
					staxReadXml.parse(f, UTF8_CHARSET);
				}else if ("EUC-KR".equals(encoding)) {
					staxReadXml.parse(f, EUCKR_CHARSET);
				}else if ("WINDOWS-1252".equals(encoding)) {
					staxReadXml.parse(f, WINDOWS_CHARSET);
				}else {
					System.out.println(f.getAbsolutePath()+ " : "+ encoding);
					staxReadXml.parse(f, UTF8_CHARSET);
				}
			} catch(Exception e) {
				System.out.println("--------------------------------------");
				System.out.println(file.toAbsolutePath());
				e.printStackTrace();
				System.out.println("---------------------------------------");
			}
		}
			
		if (fileCnt % 100 == 0) {
			System.out.println(fileCnt + " files processed. file: "+f.getAbsolutePath());
		}
	}
	}
	}

	public void saveScript() throws Exception{
		
	
	}
	
	
	public void walkScript(String path) throws Exception{
		File root = new File(path);
		File[] list = root.listFiles();
		String encoding = null;
		
		if(list == null) {
			if (root != null) {
				filePath = root.getAbsolutePath();
				fileName = root.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
				else
					fileExt = "";
				
				if ("BDM031P.xfdl".equals(fileName)) {
					System.out.println("break point");
				}
				file = Paths.get(filePath);
				if ("XJS".equals(fileExt.toUpperCase())
						|| "XFDL".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(root);
					try {
					if("UTF-8".equals(encoding)) {
						staxReadXml.parseScript(root, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding)) {
						staxReadXml.parseScript(root, EUCKR_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding)) {
						staxReadXml.parseScript(root, WINDOWS_CHARSET);
					}else {
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
						staxReadXml.parseScript(root, UTF8_CHARSET);
					}
				}catch(Exception e) {
					System.out.println("--------------------------------------");
					System.out.println(file.toAbsolutePath());
					e.printStackTrace();
					System.out.println("---------------------------------------");
				}
			}
		}
			return;
	}
		
	for(File f : list) {
		if(".svn".equals(f.getName()))
			continue;
		if (f.isDirectory()) {
			dirCnt++;
			if (!f.getAbsolutePath().contains("C:\\HANA\\CTS\\xf5"))
				walkScript(f.getAbsolutePath());
			//walk(f.getAbsolutePath());
			// logger.debug("Dir:" + f.getAbsoluteFile());
			if (dirCnt % 1000 == 0)
				logger.info(dirCnt + " , Dir:" + f.getAbsoluteFile());
		} else {
			fileCnt++;
			filePath = f.getAbsolutePath();
			fileName = f.getName();

			if ("commLib.xjs".equals(fileName)) {
				System.out.println("break point");
			}
			
			if (fileName.lastIndexOf(".") >= 0)
				fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			else
				fileExt = "";

			file = Paths.get(filePath);
			// logger.debug(fileCnt + " , "+ f.getAbsolutePath());

			if ("XJS".equals(fileExt.toUpperCase()) || "XFDL".equals(fileExt.toUpperCase())) {
				xmlCnt++;
				encoding = findFileEncoding(f);
				try {
				if("UTF-8".equals(encoding)) {
					staxReadXml.parseScript(f, UTF8_CHARSET);
				}else if ("EUC-KR".equals(encoding)) {
					staxReadXml.parseScript(f, EUCKR_CHARSET);
				}else if ("WINDOWS-1252".equals(encoding)) {
					staxReadXml.parseScript(f, WINDOWS_CHARSET);
				}else {
					System.out.println(f.getAbsolutePath()+ " : "+ encoding);
					staxReadXml.parseScript(f, UTF8_CHARSET);
				}
			} catch(Exception e) {
				System.out.println("--------------------------------------");
				System.out.println(file.toAbsolutePath());
				e.printStackTrace();
				System.out.println("---------------------------------------");
			}
		}
			
		if (fileCnt % 100 == 0) {
			System.out.println(fileCnt + " files processed. file: "+f.getAbsolutePath());
		}
	}
	}
	}
	
	public static String findFileEncoding(File file) throws Exception {
		byte[] buf = new byte[4096];
		FileInputStream fis = new FileInputStream(file);
		UniversalDetector detector = new UniversalDetector(null);
		
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		if(encoding != null) {
			
		} else {
			encoding = "No encoding";
		}
		
		detector.reset();
		
		return encoding.toUpperCase();
	}
	
	public void walkHash(String path) throws Exception{
		File root = new File(path);
		File[] list = root.listFiles();
		String encoding = null;
		
		if(list == null) {
			if (root != null) {
				filePath = root.getAbsolutePath();
				fileName = root.getName();
				if (fileName.lastIndexOf(".") >= 0)
					fileExt = fileName.substring(fileName.lastIndexOf(".") +1, fileName.length());
				else
					fileExt = "";
				
				file = Paths.get(filePath);
				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(root);
					try {
					if("UTF-8".equals(encoding)) {
						findFileHash(root, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding)) {
						findFileHash(root, EUCKR_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding)) {
						findFileHash(root, WINDOWS_CHARSET);
					}else {
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
					}
				}catch(Exception e) {
					System.out.println("--------------------------------------");
					System.out.println(file.toAbsolutePath());
					e.printStackTrace();
					System.out.println("---------------------------------------");
				}
			}
		}
			return;
	}
		for(File f : list) {
			if(".svn".equals(f.getName()))
				continue;
			if (f.isDirectory()) {
				dirCnt++;
				if (!f.getAbsolutePath().contains("C:\\HANA\\CTS\\xf5"))
					walkHash(f.getAbsolutePath());
				//walk(f.getAbsolutePath());
				// logger.debug("Dir:" + f.getAbsoluteFile());
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

				if ("XML".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(f);
					try {
					if("UTF-8".equals(encoding)) {
						findFileHash(f, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding)) {
						findFileHash(f, EUCKR_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding)) {
						findFileHash(f, WINDOWS_CHARSET);
					}else {
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
					}
				} catch(Exception e) {
					System.out.println("--------------------------------------");
					System.out.println(file.toAbsolutePath());
					e.printStackTrace();
					System.out.println("---------------------------------------");
				}
			}
				
			if (fileCnt % 100 == 0) {
				System.out.println(fileCnt + " files processed. file: "+f.getAbsolutePath());
			}
		}
		}
		
	}
	
	private void findFileHash(File f, Charset charset) throws Exception{
		//String hashValue = FileHash.getMD5CheckSumFile(f);
		
		try {
		//	if (hashValue != null) {
		//		pstmtHash.setString(1, f.getAbsolutePath());
		//		pstmtHash.setString(2, f.getName());
		//		pstmtHash.setString(3, hashValue);
		//		pstmtHash.executeUpdate();
		//	}else {
		//		System.out.println(f.getAbsolutePath() + " hash is null");
		//	}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) throws Exception {
		logger.info("FileExplorer started. : " + new Date());
		FileExplorerContent fe = new FileExplorerContent();
		fe.dbOracle();
		//fe.dbMysql();
		//fe.dbHsqldb();
		//fe.walk("D:\\DBS\\project\\src", Charset.forName("UTF-8"));
//		fe.walkStax("C:\\xFrameSJ\\project\\SJRX\\screen\\SYSTEM");
		//fe.walkScript("C:\\nTree\\workspace\\DBSB.nTreeWorks\\Web\\xui");
		//fe.walk("C:\\반입파일\\next-sample\\package.json", Charset.forName("UTF-8"));
		System.out.println("args length : "+args.length);
		//if (args.length > 0)
//			fe.walkStax("C:\\xFrame\\project\\asb\\screen");
		//else
//		fe.walkStax("C:\\xFrame\\project\\asb\\screen\\SBT\\SAMPLE");
		//fe.walkHash("C:\\ASIS");
		//fe.walkScriptSave();
		fe.walkTranMap();
		logger.info("FileExplorer ended. : " + new Date());
		logger.info("Total directories: " + fe.dirCnt);
		logger.info("Total files: " + fe.fileCnt);
	}

	private void walkTranMap() {
	    String sqlTranMapDrop = "DROP TABLE elements_tranmap";
	    String sqlMapInfoDetailTruncate = "Truncate table elements_mapinfo_detail";
	    String sqlTranMapCreate = "CREATE TABLE elements_tranmap as "
	            + "SELECT file_path,element_id,max(fcode) fcode,to_number(max(defaultmap)) defaultmap,max(maptype) maptype FROM ( "
	            + "SELECT file_path,element_id,fcode,'' defaultmap,'' maptype FROM (SELECT file_path,element_id,attr_value fcode FROM elements WHERE attr_name = 'fcode') t1 "
	            + "UNION ALL "
	            + "SELECT file_path,element_id,'' fcode,defaultmap,'' maptype FROM (SELECT file_path,element_id,attr_value defaultmap FROM elements WHERE attr_name = 'defaultmap') t2 "
	            + "UNION ALL "
	            + "SELECT file_path,element_id,'' fcode,'' defaultmap,maptype FROM (SELECT file_path,element_id,attr_value maptype FROM elements WHERE attr_name = 'type') t3 "
	            + ") GROUP BY file_path,element_id";
	    String sql = "SELECT a.file_path,a.element_id,a.fcode,a.defaultmap,a.maptype,b.attr_value mapinfo "
	            + " FROM elements_tranmap a LEFT OUTER JOIN elements_mapinfo b "
	            + " ON a.FILE_PATH = b.file_path AND a.element_id = b.element_id  ORDER BY a.file_path,a.fcode";
	    
	    String sqlInsert = "insert into elements_mapinfo_detail (file_path,element_id,fcode,defaultmap, control_id,header_horzcount, attr_value,input_order,output_order,mapinfo_order) values (?,?,?,?,?,?,?,?,?,?)";

	    try {
	        stmt = conn.createStatement();
	    } catch (SQLException e) {
	        System.out.println("Connection error: " + e.getMessage());
	        System.exit(1);
	    }

	    try {
	        stmt.executeUpdate(sqlTranMapDrop);
	        System.out.println("elements_tranmap drop success ");
	    } catch (SQLException e) {
	        System.out.println("drop error: " + e.getMessage());
	    }

	    try {
	        stmt.executeUpdate(sqlTranMapCreate);
	        System.out.println("elements_tranmap create success ");
	    } catch (SQLException e) {
	        System.out.println("create error: " + e.getMessage());
	        System.exit(1);
	    }

	    try {
	        stmt.executeUpdate(sqlMapInfoDetailTruncate);
	        System.out.println("elements_mapinfo_detail truncate success ");
	    } catch (SQLException e) {
	        System.out.println("truncte error: " + e.getMessage());
	        System.exit(1);
	    }
	    
	    try {
	        rs = stmt.executeQuery(sql);
	        pstmt = conn.prepareStatement(sqlInsert);
	    } catch (SQLException e) {
	        System.out.println("Connection error: " + e.getMessage());
	        System.exit(1);
	    }

	    try {
	        while (rs.next()) {
	            int mapinfoOrder = 1;
	            String filePath = rs.getString("file_path");
	            int elementId = rs.getInt("element_id");
	            String fcode = rs.getString("fcode");
	            int defaultmap = rs.getInt("defaultmap");
	            String mapinfo = rs.getString("mapinfo");
	            String maptype = rs.getString("maptype");
//	            mapinfo="8::1:1:/11::2:2:/21:::3:/5::4:4:;O:0:0::1:1::2:2::3:3::4:4::5:5::6:6::7:7::8:8::9:9::10:10::11:11::12:12::13:13::14:14::15:15::16:16::17:17::18:18::19:19:/22:::45:/1::::/2::::/6::::/7::::/12::::/13::::/14::::/15::::/16::::/17::::/18::::/19::::/20::::/"
//	            mapinfo="8::1:1:/11::2:2:/21::3:3:/5:::4:;:::::1:::2:::3:::4:::5:::6:::7:::8:::9:::10:::11:::12:::13:::14:::15:::16:::17:::18:::19:/22:::45:/1::::/2::::/6::::/7::::/12::::/13::::/14::::/15::::/16::::/17::::/18::::/19::::/20::::/"
	            if ("check".equals(maptype)) {
	            	continue;
	            }
	            
	            if (mapinfo == null || mapinfo.trim().isEmpty()) {
	                continue;
	            }

	            String[] mapInfos = mapinfo.split("/");

	            for (String info : mapInfos) {
	                if (info == null || info.trim().isEmpty()) {
	                    continue;
	                }

	                // Split base mapping and grid mapping at ';'
	                int semiIndex = info.indexOf(';');
	                String basePart = (semiIndex >= 0) ? info.substring(0, semiIndex) : info;
	                String gridPart = (semiIndex >= 0) ? info.substring(semiIndex + 1) : null;

	                // Parse base parameters using split
	                String[] baseTokens = basePart.split(":", -1);
	                if (baseTokens.length == 0 || baseTokens[0].isEmpty()) {
	                    continue;
	                }
	                Integer controlId = -1;
	                String headerHorzCount = "";
	                String attrValue = "";
	                String inputOrder = "";
	                String outputOrder = "";
	                if (baseTokens.length == 5) {
	                	controlId = Integer.parseInt(baseTokens[0]);
	                	attrValue = (baseTokens.length > 1 && !baseTokens[1].isEmpty()) ? baseTokens[1] : null;
	                	inputOrder = (baseTokens.length > 2 && !baseTokens[2].isEmpty()) ? baseTokens[2] : null;
	                	outputOrder = (baseTokens.length > 3 && !baseTokens[3].isEmpty()) ? baseTokens[3] : null;
	                } else if (baseTokens.length == 6) {
	                	controlId = Integer.parseInt(baseTokens[0]);
	                	headerHorzCount = (baseTokens.length > 1 && !baseTokens[1].isEmpty()) ? baseTokens[1] : null;
	                	attrValue = (baseTokens.length > 2 && !baseTokens[1].isEmpty()) ? baseTokens[2] : null;
	                	inputOrder = (baseTokens.length > 3 && !baseTokens[2].isEmpty()) ? baseTokens[3] : null;
	                	outputOrder = (baseTokens.length > 4 && !baseTokens[3].isEmpty()) ? baseTokens[4] : null;
	                } else {
	                	System.out.println("@@@@@@@@@@ Cannot parse grid component @@@@@@@@@@@@@@@@");
	                	System.out.println("@@@@"+ filePath+",fcode: "+fcode+",basePart: "+basePart);
	    	            
	                	continue;
	                	//System.exit(1);
	                }
	                // 1. Insert Base Mapping Row
	                try {
		                pstmt.setString(1, filePath);
		                pstmt.setInt(2, elementId);
		                pstmt.setString(3, fcode);
		                pstmt.setInt(4, defaultmap);
		                pstmt.setInt(5, controlId);
		                pstmt.setString(6, headerHorzCount);
		                pstmt.setString(7, attrValue);
		                pstmt.setString(8, inputOrder);
		                pstmt.setString(9, outputOrder);
		                pstmt.setInt(10, mapinfoOrder++);
		                pstmt.executeUpdate();
	                }catch(SQLException e) {
	                	System.out.println("pstmt error 1: " + filePath+",fcode: "+fcode+","+e.getMessage());
	                }
	             // Parse Grid Mapping
	                if (gridPart != null && !gridPart.trim().isEmpty()) {

	                    // keep trailing empty fields
	                    String[] gridTokens = gridPart.split(":", -1);

	                    // every grid row consists of
	                    // attribute : input : output
	                    for (int i = 0; i + 2 < gridTokens.length; i += 3) {

	                        String gridAttr =
	                                gridTokens[i].isEmpty() ? null : gridTokens[i];

	                        String gridInput =
	                                gridTokens[i + 1].isEmpty() ? null : gridTokens[i + 1];

	                        String gridOutput =
	                                gridTokens[i + 2].isEmpty() ? null : gridTokens[i + 2];
	                        try {
	                        pstmt.setString(1, filePath);
	                        pstmt.setInt(2, elementId);
	                        pstmt.setString(3, fcode);
	                        pstmt.setInt(4, defaultmap);

	                        // Grid inherits the base control id
	                        pstmt.setInt(5, controlId);
	                        pstmt.setString(6, null);
	                        pstmt.setString(7, gridAttr);
	                        pstmt.setString(8, gridInput);
	                        pstmt.setString(9, gridOutput);

	                        pstmt.setInt(10, mapinfoOrder++);
	                        pstmt.executeUpdate();
	                        }catch(SQLException e) {
	                        	System.out.println("pstmt error 2: " + filePath+",fcode: "+fcode+","+e.getMessage());
	                        }
	                    }
	                }
	            }
	            conn.commit();
	        }
	    } catch (SQLException e) {
	        System.out.println("pstmt error: " + filePath+","+e.getMessage());
	    }
	}

	/**
	 * Helper method to parse grid tokens safely without relying on a full string split.
	 */
	private void parseAndInsertGridItems(
	        PreparedStatement pstmt, String filePath, int elementId, String fcode, int defaultmap,
	        Integer controlId, String baseAttr, String baseInput, String baseOutput,
	        String gridStr, int startOrder) throws SQLException {

	    String[] rawTokens = gridStr.split(":", -1);
	    
	    // Trim initial leading empty placeholders if present
	    int startIdx = 0;
	    while (startIdx < rawTokens.length && rawTokens[startIdx].isEmpty()) {
	        startIdx++;
	    }

	    // Determine grid dialect/structure
	    // If first item contains explicit attribute (e.g. 'O:0:0') vs standard tokens
	    for (int i = startIdx; i < rawTokens.length; ) {
	        String subAttr = null;
	        String inVal = null;
	        String outVal = null;

	        // Pattern Check: [Attr:Input:Output] vs [Input:Output] vs [Output]
	        // Default mapping logic:
	        if (i + 2 < rawTokens.length && !rawTokens[i].matches("\\d+") && !rawTokens[i].isEmpty()) {
	            // Type 1: attr : input : output
	            subAttr = rawTokens[i];
	            inVal = rawTokens[i + 1];
	            outVal = rawTokens[i + 2];
	            i += 3;
	        } else if (i + 1 < rawTokens.length) {
	            // Type 2: input : output
	            inVal = rawTokens[i];
	            outVal = rawTokens[i + 1];
	            i += 2;
	        } else {
	            // Type 3: output only
	            outVal = rawTokens[i];
	            i += 1;
	        }

	        if ((inVal == null || inVal.isEmpty()) && (outVal == null || outVal.isEmpty())) {
	            continue;
	        }

	        String newInput = (inVal != null && !inVal.isEmpty()) 
	                ? (baseInput != null ? baseInput + "." + inVal : inVal) 
	                : null;

	        String newOutput = (outVal != null && !outVal.isEmpty()) 
	                ? (baseOutput != null ? baseOutput + "." + outVal : outVal) 
	                : null;

	        pstmt.setString(1, filePath);
	        pstmt.setInt(2, elementId);
	        pstmt.setString(3, fcode);
	        pstmt.setInt(4, defaultmap);
	        pstmt.setInt(5, controlId);
	        pstmt.setString(6, (subAttr != null && !subAttr.isEmpty()) ? subAttr : baseAttr);
	        pstmt.setString(7, newInput);
	        pstmt.setString(8, newOutput);
	        pstmt.setInt(9, startOrder++);
	        pstmt.executeUpdate();
	    }
	}

	/**
	 * Counts parsed grid items to keep mapinfo_order aligned.
	 */
	private int countGridItems(String gridStr) {
	    if (gridStr == null || gridStr.isEmpty()) return 0;
	    String[] tokens = gridStr.split(":", -1);
	    int count = 0;
	    for (String token : tokens) {
	        if (!token.isEmpty()) count++;
	    }
	    return Math.max(1, count / 2);
	}

	private List<GridItem> parseGrid(String grid) {

	    List<GridItem> list = new ArrayList<>();

	    if (grid == null || grid.isEmpty())
	        return list;

	    GridItem item = new GridItem();

	    StringBuilder token = new StringBuilder();

	    int field = 0;

	    for (int i = 0; i <= grid.length(); i++) {

	        char ch = (i == grid.length()) ? '\0' : grid.charAt(i);

	        if (ch == ':' || ch == '\0') {

	            String value = token.length() == 0 ? null : token.toString();
	            token.setLength(0);

	            switch (field) {
	            case 0:
	                item.attr = value;
	                break;
	            case 1:
	                item.input = value;
	                break;
	            case 2:
	                item.output = value;
	                break;
	            }

	            field++;

	            boolean endRecord = false;

	            if (ch == '\0') {
	                endRecord = true;
	            }
	            else if (i + 1 < grid.length() && grid.charAt(i + 1) == ':') {
	                endRecord = true;
	                i++;        // consume second ':'
	            }

	            if (endRecord) {

	                list.add(item);

	                item = new GridItem();
	                field = 0;
	            }

	        } else {
	            token.append(ch);
	        }
	    }

	    return list;
	}
	
	private void walkScriptSave() throws XMLStreamException, SQLException, IOException {
		staxReadXml.parseScriptSave();
		
	}

}

