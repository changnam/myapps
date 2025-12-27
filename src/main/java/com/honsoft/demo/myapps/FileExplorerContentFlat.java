package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsoft.xframe.StaxExample;
import com.honsoft.xframe.StaxReadXml;

public class FileExplorerContentFlat {
	private static Logger logger = LoggerFactory.getLogger(FileExplorerContentFlat.class);
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
	PreparedStatement pstmt, pstmtLine, pstmtSymbol, pstmtHeader, pstmtEncoding, pstmtElements, pstmtElementsUpdate;
	PreparedStatement pstmtScreen, pstmtTranMap, pstmtPushButton;
	
	Statement stmt;
	ResultSet rs;

	String oracleSqlStr = "insert into filecontents (id, file_path, file_name, file_ext, line_num, line_text) values (files_seq.nextval,?,?,?,?,?)";
	String oracleSqlLine = "insert into filecontents(id,file_path, file_name,file_ext, line_num, line_text,work_timestamp) values(files_seq.nextval,?,?,?,?,?,?)";
	String oracleEncoding = "insert into fileencodings (file_path, file_name, line_num) values (?,?,?)";
	String oracleSqlElements = "SELECT element_name,attr_name,max(LENGTHB(attr_value)) max_length FROM elements GROUP BY element_name,attr_name ORDER BY element_name,attr_name";
	//String oracleSqlElementsUpdate = "SELECT file_path,element_id,parent_id,element_name,attr_name,attr_value FROM elements where regexp_like(file_path,'cmf006u','i') ORDER BY file_path,element_id,parent_id,element_name,attr_name";
	String oracleSqlElementsUpdate = "SELECT file_path,element_id,parent_id,element_name,attr_name,attr_value FROM elements ORDER BY file_path,element_id,parent_id,element_name,attr_name";
	String mysqlSqlStr = "insert into filecontents (file_path, file_name, file_ext, line_num, line_text) values (?,?,?,?,?)";
	String h2SqlStr = "insert into filecontents (file_path, file_name, file_ext, line_num, line_text) values (?,?,?,?,?)";
	String hsqldbSqlStr = "insert into filecontents (file_path, file_name, file_ext, line_num, line_text) values (,?,?,?,?,?)";
	String mysqlSqlLine = "insert into filecontents(file_path, file_name,file_ext,line_num, line_text,work_timestamp) values(?,?,?,?,?,?)";
	String mysqlEncoding = "insert into fileencodings (file_path, file_name, line_num) values (?,?,?)";
	String mysqlSqlElements = "SELECT element_name,attr_name,max(LENGTHB(attr_value)) max_length FROM elements GROUP BY element_name,attr_name ORDER BY element_name,attr_name";
	String mysqlSqlElementsUpdate = "SELECT file_path,element_id,parent_id,element_name,attr_name,attr_value FROM elements ORDER BY file_path,element_id,parent_id,element_name,attr_name";
	
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
			pstmtElements = conn.prepareStatement(oracleSqlElements);
			pstmtElementsUpdate = conn.prepareStatement(oracleSqlElementsUpdate);	

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
			pstmtLine = conn.prepareStatement(mysqlSqlLine);
			pstmtEncoding = conn.prepareStatement(mysqlEncoding);
			pstmtElements = conn.prepareStatement(mysqlSqlElements);
			pstmtElementsUpdate = conn.prepareStatement(mysqlSqlElementsUpdate);
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
						|| "CSV".equals(fileExt.toUpperCase())) {
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
						|| "CSV".equals(fileExt.toUpperCase())) {
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
		FileExplorerContentFlat fe = new FileExplorerContentFlat();
		//fe.dbOracle();
		fe.dbMysql();
		//fe.dbHsqldb();
		fe.walk("D:\\DBS\\project\\src", Charset.forName("UTF-8"));
		//fe.walkStax("C:\\workspace\\DBSB.nTreeWorks\\Web\\xui\\bc\\ACA805L.xfdl");
		//fe.walkScript("C:\\nTree\\workspace\\DBSB.nTreeWorks\\Web\\xui");
		//fe.walk("C:\\CONV\\report_0510\\NTREE", Charset.forName("UTF-8"));
		//fe.walkStax("C:\\xFrame\\project\\DSI\\screen\\NTREE");
		//fe.walkStaxFlat();
		//fe.walkStaxFlatUpdate();
		//fe.walkHash("C:\\ASIS");
		//fe.walkScriptSave();
		logger.info("FileExplorer ended. : " + new Date());
		logger.info("Total directories: " + fe.dirCnt);
		logger.info("Total files: " + fe.fileCnt);
	}

	private void walkStaxFlatUpdate() throws SQLException {
		// TODO Auto-generated method stub
		int cnt = 0;
		Boolean programStart = true;
		rs = pstmtElementsUpdate.executeQuery();
		stmt = conn.createStatement();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbColumns = new StringBuffer();
		StringBuffer sbValues = new StringBuffer();
		
		String prev_file_path = "";
		int prev_element_id = 0;
		
		while(rs.next()) {
			//System.out.println(cnt++ + " insert "+rs.getString("file_path")+","+rs.getInt("element_id")+","+rs.getInt("parent_id")+","+rs.getString("attr_name")+","+rs.getString("attr_value"));
			if (rs.getString("attr_name") == null)
				continue;
			
			if(prev_element_id == rs.getInt("element_id")) {
					sbColumns.append(","+chaneAttrName(rs.getString("attr_name")));
					sbValues.append("','"+replaceSingleQuote(rs.getString("attr_value")));
			} else {			
				prev_element_id = rs.getInt("element_id");
				if (programStart) {
					programStart = false;
					//System.out.println("여기서 테이블 생성 "+sb.toString());
						sb.append("insert into xf_"+rs.getString("element_name")+"(");
						sbColumns.append("file_path,element_id,parent_id,"+chaneAttrName(rs.getString("attr_name")));
						sbValues.append(" values('"+rs.getString("file_path")+"',"+rs.getInt("element_id")+","+rs.getInt("parent_id")+",'"+replaceSingleQuote(rs.getString("attr_value")));
					//sb.delete(0, sb.length());
				}else {
					sbColumns.append(")");
					sbValues.append("')");
					//System.out.println(cnt++ + " 여기서 insert "+sb.toString()+sbColumns.toString()+sbValues.toString());
					//System.out.println(tableName);
					try {
						System.out.println(cnt++ + " 여기서 insert "+sb.toString()+sbColumns.toString()+sbValues.toString());
					    if (cnt == 944) 
					    	System.out.println("break point");
						//stmt.executeUpdate("drop table "+tableName);
						stmt.executeUpdate(sb.toString()+sbColumns.toString()+sbValues.toString());
						conn.commit();
					} catch(SQLException e) {
						//e.printStackTrace();
						if (e.getErrorCode() == 955) {
							e.printStackTrace();
						}
						else {
							System.out.println(e.getErrorCode());
							e.printStackTrace();
							throw e;
						}
					}
					sb.delete(0, sb.length());
					sbColumns.delete(0, sbColumns.length());
					sbValues.delete(0, sbValues.length());		
					
					sb.append("insert into xf_"+rs.getString("element_name")+"(");
					sbColumns.append("file_path,element_id,parent_id,"+chaneAttrName(rs.getString("attr_name")));
					sbValues.append(" values('"+rs.getString("file_path")+"',"+rs.getInt("element_id")+","+rs.getInt("parent_id")+",'"+replaceSingleQuote(rs.getString("attr_value")));
					
					//startFlag = true;
				}
			}
		}
		
		sbColumns.append(")");
		sbValues.append("')");
		try {
			//stmt.executeUpdate("drop table "+tableName);
			stmt.executeUpdate(sb.toString()+sbColumns.toString()+sbValues.toString());
			conn.commit();
		} catch(SQLException e) {
			//e.printStackTrace();
			if (e.getErrorCode() == 955) {
				e.printStackTrace();
			}
			else {
				System.out.println(e.getErrorCode());
				e.printStackTrace();
				throw e;
			}
		}
		
	}

	private String replaceSingleQuote(String string) {
		// TODO Auto-generated method stub
		if (string != null) {
			return string.replace("'", "''");
		} else {
			return "";
		}
	}

	private void walkStaxFlat() throws SQLException {
		// TODO Auto-generated method stub
		stmt = conn.createStatement();
		int cnt = 0;
		Boolean startFlag = true, programStart = true;
		String prev_element_name = "";
		String tableName = "";
		StringBuffer sb = new StringBuffer();
		int columnLength = 0;
		rs = pstmtElements.executeQuery();
		while(rs.next()) {
			//System.out.println(cnt++ + " element_name: "+rs.getString("element_name")+ ",attr_name: "+rs.getString("attr_name")+ ",max_length: "+rs.getInt("max_length"));
			if (rs.getString("attr_name") == null)
				continue;
			
			if(prev_element_name.equals(rs.getString("element_name"))) {
					if (rs.getInt("max_length") == 0)
						sb.append(","+chaneAttrName(rs.getString("attr_name"))+ " varchar2(512) ");
					else
						sb.append(","+chaneAttrName(rs.getString("attr_name"))+ " varchar2("+rs.getInt("max_length") +")");
			} else {			
				prev_element_name = rs.getString("element_name");
				if (programStart) {
					programStart = false;
					//System.out.println("여기서 테이블 생성 "+sb.toString());
					if (rs.getInt("max_length") == 0) {
						sb.append("create table xf_"+rs.getString("element_name")+" ( "+defaultColumns()+chaneAttrName(rs.getString("attr_name"))+ " varchar2(512) ");
						tableName = "xf_"+rs.getString("element_name");
					} else {
						sb.append("create table xf_"+rs.getString("element_name")+" ( "+defaultColumns()+chaneAttrName(rs.getString("attr_name"))+ " varchar2("+rs.getInt("max_length") +")");
						tableName = "xf_"+rs.getString("element_name");
					}
					//sb.delete(0, sb.length());
				}else {
					sb.append(")");
					//System.out.println(cnt++ + " 여기서 테이블 생성 "+sb.toString());
					System.out.println(tableName);
					try {
						stmt.executeUpdate("drop table "+tableName);
						//stmt.executeUpdate(sb.toString());
					} catch(SQLException e) {
						//e.printStackTrace();
						if (e.getErrorCode() == 955 || e.getErrorCode() == 942) {
							e.printStackTrace();
						}
						else {
							e.printStackTrace();
							throw e;
						}
					}
					
					try {
						//stmt.executeUpdate("drop table "+tableName);
						stmt.executeUpdate(sb.toString());
					} catch(SQLException e) {
						//e.printStackTrace();
						if (e.getErrorCode() == 955 || e.getErrorCode() == 942) {
							e.printStackTrace();
						}
						else {
							e.printStackTrace();
							throw e;
						}
					}
					
					sb.delete(0, sb.length());
					if (rs.getInt("max_length") == 0) {
						sb.append("create table xf_"+rs.getString("element_name")+" ( "+defaultColumns()+chaneAttrName(rs.getString("attr_name"))+ " varchar2(512) ");
						tableName = "xf_"+rs.getString("element_name");
					} else {
						sb.append("create table xf_"+rs.getString("element_name")+" ( "+defaultColumns()+chaneAttrName(rs.getString("attr_name"))+ " varchar2("+ rs.getInt("max_length") +")");
						tableName = "xf_"+rs.getString("element_name");
					}
		
					startFlag = true;
				}
			}
		}
		sb.append(")");
		//System.out.println(cnt++ + " 여기서 마지막 테이블 생성 "+sb.toString());
		System.out.println(tableName);
		try {
			stmt.executeUpdate("drop table "+tableName);
			//stmt.executeUpdate(sb.toString());
		} catch(SQLException e) {
			//e.printStackTrace();
			if (e.getErrorCode() == 955 || e.getErrorCode() == 942) {
				e.printStackTrace();
			}
			else {
				System.out.println(e.getErrorCode());
				e.printStackTrace();
				throw e;
			}
		}
		
		try {
			//stmt.executeUpdate("drop table "+tableName);
			stmt.executeUpdate(sb.toString());
		} catch(SQLException e) {
			//e.printStackTrace();
			if (e.getErrorCode() == 955 || e.getErrorCode() == 942) {
				e.printStackTrace();
			}
			else {
				System.out.println(e.getErrorCode());
				e.printStackTrace();
				throw e;
			}
		}
		rs.close();
		pstmtElements.close();
		System.out.println("break point");
	}

	private String defaultColumns() {
		// TODO Auto-generated method stub
		return "file_path varchar2(512), element_id number, parent_id number, ";
	}

	private String chaneAttrName(String string) {
		// TODO Auto-generated method stub
		if ("row".equals(string)||"desc".equals(string)||"comment".equals(string)) {
			return "xf_"+string;
		}else {
			return string;
		}
	}

	private void walkScriptSave() throws XMLStreamException, SQLException, IOException {
		staxReadXml.parseScriptSave();
		
	}

}
