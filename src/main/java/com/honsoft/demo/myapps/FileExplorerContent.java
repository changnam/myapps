package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honsoft.xframe.StaxReadXml;

public class FileExplorerContent {
	private static Logger logger = LoggerFactory.getLogger(FileExplorerContent.class);
	Date startDate, endDate = null;
	int dirCnt, fileCnt, totalLines, skipLines, totalMatchCnt = 0;
	int xmlCnt, jsCnt = 0;
	int fileNotFoundCnt, ioExceptionCnt = 0;
	
	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private final Charset EUCKR_CHARSET = Charset.forName("EUC-KR");
	private final Charset CP949_CHARSET = Charset.forName("x-windows-949");
	private final Charset WINDOWS_CHARSET = Charset.forName("WINDOWS-1252");
	
	Path file;
	String mysqlUrl = "jdbc:mysql://localhost:3306/apitest?useUnicode=true" +
			  "&characterEncoding=utf8" +
			  "&serverTimezone=Asia/Seoul";
	String mysqlId = "shoppingnt", mysqlPw = "Shoppingnt2021!@";

	String oracleUrl = "jdbc:oracle:thin:@localhost:1521:jerry";
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
				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())
						|| "JSX".equals(fileExt.toUpperCase())
						|| "INI".equals(fileExt.toUpperCase())
						|| "SQL".equals(fileExt.toUpperCase())
						|| "LST".equals(fileExt.toUpperCase())
						|| "JAVA".equals(fileExt.toUpperCase())
						|| "TS".equals(fileExt.toUpperCase())
						|| "TSX".equals(fileExt.toUpperCase())
						|| "MD".equals(fileExt.toUpperCase())
						|| "MJS".equals(fileExt.toUpperCase())
						|| "JSON".equals(fileExt.toUpperCase())
						|| "LOCAL".equals(fileExt.toUpperCase())
						|| "ENV".equals(fileExt.toUpperCase())
						|| "TXT".equals(fileExt.toUpperCase())
						|| "LOG".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(root);
					if("UTF-8".equals(encoding) || "utf-8".equals(encoding)) {
						insertSource(root, Charset.forName("UTF-8"));
					}else if ("EUC-KR".equals(encoding) || "euc-kr".equals(encoding)) {
						insertSource(root, Charset.forName("EUC-KR"));
					}else if ("UTF-16LE".equals(encoding) || "utf-16le".equals(encoding)) {
						insertSource(root, Charset.forName("UTF-16LE"));
					}else if ("X-WINDOWS-949".equals(encoding) || "x-windows-949".equals(encoding)) {
						insertSource(root, CP949_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding) || "windows-1252".equals(encoding)) {
						insertSource(root, WINDOWS_CHARSET);
					}else {
						insertSource(root, Charset.forName("UTF-8"));
						System.out.println(root.getAbsolutePath()+ " : "+ encoding);
					}
				}
			}
			return;
		}
		
		for (File f : list) {
			if(".svn".equals(f.getName())|| "node_modules".equals(f.getName())|| ".next".equals(f.getName())|| ".shopify".equals(f.getName()) || ".git".equals(f.getName()))
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

				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())
						|| "JSX".equals(fileExt.toUpperCase())
						|| "INI".equals(fileExt.toUpperCase())
						|| "SQL".equals(fileExt.toUpperCase())
						|| "LST".equals(fileExt.toUpperCase())
						|| "JAVA".equals(fileExt.toUpperCase())
						|| "TS".equals(fileExt.toUpperCase())
						|| "TSX".equals(fileExt.toUpperCase())
						|| "MD".equals(fileExt.toUpperCase())
						|| "MJS".equals(fileExt.toUpperCase())
						|| "JSON".equals(fileExt.toUpperCase())
						|| "LOCAL".equals(fileExt.toUpperCase())
						|| "ENV".equals(fileExt.toUpperCase())
						|| "TXT".equals(fileExt.toUpperCase())
						|| "LOG".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(f);
					if("UTF-8".equals(encoding) || "utf-8".equals(encoding)) {
						insertSource(f, Charset.forName("UTF-8"));
					}else if ("EUC-KR".equals(encoding) || "euc-kr".equals(encoding)) {
						insertSource(f, Charset.forName("EUC-KR"));
					}else if ("UTF-16LE".equals(encoding) || "utf-16le".equals(encoding)) {
						insertSource(f, Charset.forName("UTF-16LE"));
					}else if ("X-WINDOWS-949".equals(encoding) || "x-windows-949".equals(encoding)) {
						insertSource(f, CP949_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding) || "windows-1252".equals(encoding)) {
						insertSource(f, WINDOWS_CHARSET);
					}else {
						System.out.println(f.getAbsolutePath()+ " : "+ encoding);
						insertSource(f, Charset.forName("UTF-8"));
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
//					System.out.println("filePaht: "+filePath);
					pstmtLine.setString(1,  filePath);
					pstmtLine.setString(2,  fileName);
					pstmtLine.setLong(3,  line_num);
					pstmtLine.setString(4,  sLine);
					pstmtLine.setTimestamp(5,  currentTimestamp);
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
				
				file = Paths.get(filePath);
				if ("XML".equals(fileExt.toUpperCase())
						|| "JS".equals(fileExt.toUpperCase())) {
					xmlCnt++;
					encoding = findFileEncoding(root);
					try {
					if("UTF-8".equals(encoding) || "utf-8".equals(encoding)) {
						staxReadXml.parse(root, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding) || "euc-kr".equals(encoding)) {
						staxReadXml.parse(root, EUCKR_CHARSET);
					}else if ("X-WINDOWS-949".equals(encoding) || "x-windows-949".equals(encoding)) {
						insertSource(root, CP949_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding) || "windows-1252".equals(encoding)) {
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
				if("UTF-8".equals(encoding) || "utf-8".equals(encoding)) {
					staxReadXml.parse(f, UTF8_CHARSET);
				}else if ("EUC-KR".equals(encoding) || "euc-kr".equals(encoding)) {
					staxReadXml.parse(f, EUCKR_CHARSET);
				}else if ("X-WINDOWS-949".equals(encoding) || "x-windows-949".equals(encoding)) {
					insertSource(f, CP949_CHARSET);
				}else if ("WINDOWS-1252".equals(encoding) || "windows-1252".equals(encoding)) {
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
	
	public static String findFileEncoding(File file) throws Exception {
		// notepad++ like charset encoding detection
		byte[] data = Files.readAllBytes(file.toPath());

		// 1. BOM
		String bom = BomUtil.detectBom(data);
		if (bom != null) return bom;

		// 2. UTF-8 validity
		if (isValidUtf8(data)) return "UTF-8";

		// 3. Statistical detection
		UniversalDetector d = new UniversalDetector(null);
		d.handleData(data, 0, data.length);
		d.dataEnd();
		String detected = d.getDetectedCharset().toLowerCase();

		if (detected.equals("windows-1252") || detected.equals("iso-8859-1")) {
		   // Heuristic: check if bytes are valid CP949
		  if (looksLikeKorean(data)) {
		     return "x-windows-949";
	      }
	    }

		if (detected.equals("euc-kr")) {
		    return "x-windows-949";
		}

		// 4. Locale fallback
		return detected != null ? detected : "no encoding";
	}
	
	private static boolean looksLikeKorean(byte[] data) throws Exception{
	    int count = 0;
	    for (int i = 0; i < data.length - 1; i++) {
	        int b1 = data[i] & 0xFF;
	        int b2 = data[i + 1] & 0xFF;

	        // CP949 Hangul range
	        if (b1 >= 0xB0 && b1 <= 0xC8 &&
	            b2 >= 0xA1 && b2 <= 0xFE) {
	            count++;
	        }
	    }
	    return count >= 2; // very low threshold
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
					if("UTF-8".equals(encoding) || "utf-8".equals(encoding)) {
						findFileHash(root, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding) || "euc-kr".equals(encoding)) {
						findFileHash(root, EUCKR_CHARSET);
					}else if ("X-WINDOWS-949".equals(encoding) || "x-windows-949".equals(encoding)) {
						insertSource(root, CP949_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding) || "windows-1252".equals(encoding)) {
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
					if("UTF-8".equals(encoding) || "utf-8".equals(encoding)) {
						findFileHash(f, UTF8_CHARSET);
					}else if ("EUC-KR".equals(encoding) || "euc-kr".equals(encoding)) {
						findFileHash(f, EUCKR_CHARSET);
					}else if ("X-WINDOWS-949".equals(encoding) || "x-windows-949".equals(encoding)) {
						insertSource(f, CP949_CHARSET);
					}else if ("WINDOWS-1252".equals(encoding) || "windows-1252".equals(encoding)) {
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
	
	private static boolean isValidUtf8(byte[] data) {
	    try {
	        CharsetDecoder decoder = StandardCharsets.UTF_8
	            .newDecoder()
	            .onMalformedInput(CodingErrorAction.REPORT)
	            .onUnmappableCharacter(CodingErrorAction.REPORT);
	        decoder.decode(ByteBuffer.wrap(data));
	        return true;
	    } catch (CharacterCodingException e) {
	        return false;
	    }
	}

	public static void main(String[] args) throws Exception {
		logger.info("FileExplorerContent started. : " + new Date());
		FileExplorerContent fe = new FileExplorerContent();
		// fe.dbOracle();
		 fe.dbMysql();
		//fe.dbHsqldb();
		//fe.walk("D:\\workspace\\eclipse-202103-workspace\\myapps", Charset.forName("UTF-8"));
		//fe.walk("D:\\DBS\\project\\java", Charset.forName("UTF-8"));
		fe.walkStax("D:\\DBS\\project\\DSI\\screen\\ACIS");
		//fe.walkHash("C:\\ASIS");
		logger.info("FileExplorerContent ended. : " + new Date());
		logger.info("Total directories: " + fe.dirCnt);
		logger.info("Total files: " + fe.fileCnt);
	}

}
