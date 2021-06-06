package com.honsoft.demo.myapps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFileReader {
	Date startDate, endDate = null;
	int dirCnt, fileCnt = 0;
	Path file;
	String url = "jdbc:oracle:thin:@localhost:1521:jerry";
	String id = "cddba1", pw = "cn0012";
	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	String sqlStr = "insert into springlog (seq,linenum,line) values (?,?,?)";
	String fileName;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	int currentSeq;
	String[] words;
	Pattern pattern;
	Matcher matcher;

	public void db() {
		try {
			// 드라이버 로딩 (Mysql 또는 Oracle 중에 선택하시면 됩니다.)
			Class.forName("oracle.jdbc.driver.OracleDriver"); // oracle
			conn = DriverManager.getConnection(url, id, pw);

			pstmt = conn.prepareStatement(sqlStr);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(seq) from springlog");
			if (rs.next()) {
				currentSeq = rs.getInt(1);
			} else {
				currentSeq = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read(String path) {

		String s = "123456789";
		StringBuilder sb = new StringBuilder();
		char[] buff = s.toCharArray();
		sb.append(buff, 0, 3).append("foobar");
		sb.append(buff, 3 + 3, buff.length - (3 + 3));
		// System.out.println(sb.toString());
		sb.delete(0, sb.length());
		sb.append(buff, 0, buff.length);
		// System.out.println(sb.toString());
		// pattern = Pattern.compile("@"); //영문자만
		// pattern = Pattern.compile("@.+$"); //영문자만
		pattern = Pattern.compile(
				"(@.+:)|(@.+\\])|(@.+,)|(\\d\\d\\:\\d\\d\\:\\d\\d\\.\\d\\d\\d)|(\\d\\d\\d\\d \\d\\d\\:\\d\\d\\:\\d\\d \\w\\w)|(\\d\\w \\d\\d,)|(\\d\\d\\d\\d \\d\\:\\d\\d\\:\\d\\d \\w\\w)",
				Pattern.UNICODE_CHARACTER_CLASS);
		// pattern = Pattern.compile("(\\d\\d\\:\\d\\d\\:\\d\\d\\.\\d\\d\\d)");
		// pattern = Pattern.compile("(\\d\\d\\d\\d \\d\\d\\:\\d\\d\\:\\d\\d
		// \\w\\w)",Pattern.UNICODE_CHARACTER_CLASS);
		File file = new File(path);

		int cnt = 0, count = 0, totalFounded = 0;
		int nLine = 0;
		if (file.exists()) {
			BufferedReader inFile;
			try {
				inFile = new BufferedReader(new FileReader(file));

				String sLine = null;

				while ((sLine = inFile.readLine()) != null) {
					if (sLine.length() > 4000)
						continue;
					cnt++;
					// System.out.println(sLine); // 읽어들인 문자열을 출력 합니다.
					// words = sLine.trim().split("\\s+");
					// sLine = sLine.substring(words[0].length())
					matcher = pattern.matcher(sLine);
					buff = sLine.toCharArray();
					sb.delete(0, sb.length());
					count = 0;

//					if (cnt == 70) {
//						System.out.println(cnt);
//					}
					int matchCount = 0, startPoint = 0;
					while (matcher.find()) {
						// System.out.println("match found");
						matchCount++;
						sb.append(buff, startPoint, matcher.start() - startPoint);
						startPoint = matcher.end();
					}
					if (startPoint == sLine.length() - 1)
						continue;
					else
						sb.append(buff, startPoint, buff.length - startPoint);
					// System.out.println(cnt + " : " + sb.toString());
					try {
						pstmt.setInt(1, currentSeq + 1);
						pstmt.setInt(2, cnt);
						pstmt.setString(3, sb.toString());
						pstmt.executeUpdate();

						if (cnt % 1000 == 0) {
							conn.commit();
							System.out.println(cnt + " inserted.");
						}
					} catch (SQLException e) {
						System.out.println(sLine);
						e.printStackTrace();
					}

				}
				// System.out.println(cnt + " : " + sb.toString());
				// sLine.replaceAll("(@.+:)|(@.+\\])|(@.+,)|(\\d\\d\\:\\d\\d\\:\\d\\d\\.\\d\\d\\d)",
				// "");
				// System.out.println("
				// "+sLine.replaceAll("(@.+:)|(@.+\\])|(@.+,)|(\\d\\d\\:\\d\\d\\:\\d\\d\\.\\d\\d\\d)|(\\d\\d\\d\\d
				// \\d\\d\\:\\d\\d\\:\\d\\d \\w\\w)", ""));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(cnt + " rows inserted. " + totalFounded + " matched.");
		}

	}

	public static void main(String[] args) {
		System.out.println("FileExplorer started. : " + new Date());
		MyFileReader fr = new MyFileReader();
		fr.db();
		fr.read("C:\\Users\\changnamgo\\OneDrive\\문서\\test.txt");
		System.out.println("FileReader ended. : " + new Date());
		// System.out.println("Total directories: "+ fe.dirCnt);
		// System.out.println("Total files: "+ fe.fileCnt);
	}

}
