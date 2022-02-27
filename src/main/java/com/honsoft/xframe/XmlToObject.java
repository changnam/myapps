package com.honsoft.xframe;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;

import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class XmlToObject {

	// private static CommonMap commonMap = new CommonMap();

	private static JAXBContext jaxbContext;
	private static Unmarshaller unmarshaller;
	private static HanaMapList mapList;
	private static String file_path;
	private static String file_name;

	private static Connection conn;
	private static PreparedStatement pstmt, pstmtAttrs, pstmtElements, pstmtIndividualElements;
	private static Statement stmt;
	private static ResultSet rs, rsTemp;

	private static String db = "jdbc:mysql://localhost:3306/hanacard";
	private static String user = "hanacard";
	private static String password = "hanacard";

	private static String sqlAttrs = "select file_path,element_name,element_id,parent_id,attr_name,attr_value From elements where file_path = ? and element_name = ? and element_id = ? order by file_path,element_id,attr_name";
	private static String sqlElements = "select distinct element_name from elements order by element_name";
	private static String sqlIndividualElements = "select file_path,file_name,element_name,element_id,parent_id from elements where attr_name <> '' group by file_path,file_name,element_name,element_id,parent_id order by file_path,file_name,element_name,element_id,parent_id";

	// private static String sql

	public static void prepareXML(File file) {
		try {
			jaxbContext = JAXBContext.newInstance(HanaMapList.class);
			unmarshaller = jaxbContext.createUnmarshaller();
			mapList = (HanaMapList) unmarshaller.unmarshal(file);
			mapList.setId(file.getName());
			file_path = file.getAbsolutePath();
			file_name = file.getName();

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(db, user, password);
			pstmtAttrs = conn.prepareStatement(sqlAttrs);
			pstmtElements = conn.prepareStatement(sqlElements);
			pstmtIndividualElements = conn.prepareStatement(sqlIndividualElements);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

	public static void createTables() {
		String sqlStr = null;
		boolean bResult = false;
		try {
			rs = pstmtElements.executeQuery();
			while (rs.next()) {
				sqlStr = makeCreateSql(rs.getString("element_name"));
				bResult = createPersonTable(sqlStr);
				if (!bResult)
					System.out.println(rs.getString("element_name") + " not created.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	public static void insertTables() {
		String sqlStr = null;
		boolean bResult = false;
		List<String> attrs = new ArrayList<String>();
		try {
			rs = pstmtIndividualElements.executeQuery();
			while (rs.next()) {

				if ("map".equals(rs.getString("element_name"))||"map_list".equals(rs.getString("element_name"))) {
					continue;
				} else {
					sqlStr = makeInsertSql(rs.getString("file_path"), rs.getString("file_name"),
							rs.getString("element_name"), rs.getInt("element_id"), rs.getInt("parent_id"), attrs);
					System.out.println(sqlStr);
					pstmt = conn.prepareStatement(sqlStr);
					bResult = insertTable(rs.getString("file_path"), rs.getString("file_name"),
							rs.getNString("element_name"), rs.getInt("element_id"), rs.getInt("parent_id"), attrs);
					if (!bResult)
						System.out.println(rs.getString("element_name") + " not inserted.");
					pstmt.close();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	public static boolean insertTable(String file_path, String file_name, String element_name, int element_id,
			int parent_id, List<String> attrs) {

		try {
			pstmt.setString(1, file_path);
			pstmt.setString(2, file_name);
			pstmt.setString(3, element_name);
			pstmt.setInt(4, element_id);
			pstmt.setInt(5, parent_id);

			pstmtAttrs.setString(1, file_path);
			pstmtAttrs.setString(2, element_name);
			pstmtAttrs.setInt(3, element_id);

			rsTemp = pstmtAttrs.executeQuery();
			int i = 6;

			//System.out.println(file_path);
			//System.out.println(file_name);
			//System.out.println(element_name);
			//System.out.println(element_id);
			//System.out.println(parent_id);

			while (rsTemp.next()) {
				System.out.println(rsTemp.getString("attr_name") + " : " + rsTemp.getString("attr_value"));
				pstmt.setString(i++, rsTemp.getString("attr_value"));
			}
			pstmt.executeUpdate();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.exit(1);
			return false;
		} finally {
			try {
				rsTemp.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static String makeCreateSql(String element_name) {
		String tempSql = null;
		tempSql = RegexApp.makeSqlForCreateTable(element_name);
		return tempSql;
	}

	public static String makeInsertSql(String file_path, String file_name, String element_name, int element_id,
			int parent_id, List<String> attrs) {
		String tempSql = null;
		tempSql = RegexApp.makeSqlForInsertTable(file_path, element_name, element_id, attrs);
		return tempSql;
	}

	public static boolean createPersonTable(String sqlStr) {

		String query = ("CREATE TABLE Person (" + "personID INT AUTO_INCREMENT NOT NULL, "
				+ "name VARCHAR(50) NOT NULL, " + "PRIMARY KEY(personId) " + ");");

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStr);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void printMap(HanaMap map) {
		// System.out.println("map:---------- ");
		// System.out.println(file_path + ",map id: " + map.getId());
	}

	public static void main(String[] args) {
		File f = new File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\07_GRID\\01_BASIC\\grid_basic_02.xml");

		prepareXML(f);
		createTables();
		insertTables();
	}
}
