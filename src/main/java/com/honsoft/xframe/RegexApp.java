package com.honsoft.xframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;

public class RegexApp {
	private static Pattern patternElement = Pattern.compile("\\s*<(\\w+?)\\s");
	private static Pattern patternKeyValue = Pattern.compile("(\\w+)=\"(.*?)\""); // .*만 하면 안되고 ? 를 줘야 일치하는 가장 최소값을 찾음
	// private static Pattern pattern =
	// Pattern.compile("(\\w+)=\"*((?<=\")[^\"]+(?=\")|([^\\s]+))\"*");
	private static Matcher m;

	private static Connection conn;
	private static PreparedStatement pstmt, pstmtKeys, pstmtChild;
	private static ResultSet rs;

	private static String sqlStr = "insert into element_attr (element,idx,key_name,key_value) values(?,?,?,?)";

	private static String db = "jdbc:mysql://localhost:3306/hanacard";
	private static String user = "hanacard";
	private static String password = "hanacard";

	private static int seq = 0;

	private static String sqlKeys = "select element,key_name from element_attr where element = ? group by element,key_name order by element,key_name";

	private static String sqlChild = "select element_name from element_hier where parent = ? group by element_name order by element_name";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(db, user, password);
			pstmt = conn.prepareStatement(sqlStr);
			pstmtKeys = conn.prepareStatement(sqlKeys);
			pstmtChild = conn.prepareStatement(sqlChild);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, Map<String, String>> getMap(String input) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		String element = null;

		m = patternElement.matcher(input);
		if (m.find()) {
			element = m.group(1);
			m = patternKeyValue.matcher(input);
			Map<String, String> mapKeyValue = new HashMap<String, String>();
			while (m.find()) {

				mapKeyValue.put(m.group(1), m.group(2));
				// System.out.println(i++ +" , "+ m.group(1)+" || "+m.group(2));
			}
			map.put(element, mapKeyValue);
		}
		return map;
	}

	public static void printMap(Map<String, Map<String, String>> map) {
		int i = 0;
		Map<String, String> keyValueMap = null;
		for (String element : map.keySet()) {
			System.out.println(i++ + " , " + element);
			keyValueMap = map.get(element);
			for (String key : keyValueMap.keySet()) {
				System.out.println(i++ + " , " + key + " , " + keyValueMap.get(key));
			}
		}
	}

	public static void printMapForElement(String element) {
		// int i = 0;
		StringBuilder sb = new StringBuilder();
		StringBuilder sbGet = new StringBuilder();
		StringBuilder sbCreate = new StringBuilder();
		StringBuilder sbInsert = new StringBuilder();
		StringBuilder sbPstmt = new StringBuilder();

		sb.append("private String id;\n");
		sb.append("private String parent;\n");

		sbGet.append("   public String getId(){\n");
		sbGet.append("     return id;\n");
		sbGet.append("   }\n");

		sbGet.append("   public void setId(String id){\n");
		sbGet.append("     this.id = id;\n");
		sbGet.append("   }\n");

		sbGet.append("   public String getParent(){\n");
		sbGet.append("     return parent;\n");
		sbGet.append("   }\n");

		sbGet.append("   public void setParent(String parent){\n");
		sbGet.append("     this.parent = parent;\n");
		sbGet.append("   }\n");

		sbCreate.append("create table hana" + element + "(file_path varchar(128),file_name varchar(32),id varchar(32), parent varchar(32),");
		sbInsert.append("private static PreparedStatement pstmt"+element+";\n");
		sbInsert.append("private static String sql"+element+" = \"insert into hana"+element+" (file_path,file_name,id,parent,");
		
		sbPstmt.append("try{\n");
		sbPstmt.append("pstmt"+element+".setString(1,file_path);\n");
		sbPstmt.append("pstmt"+element+".setString(2,file_name);\n");
		sbPstmt.append("pstmt"+element+".setString(3,"+element+".getId());\n");
		sbPstmt.append("pstmt"+element+".setString(4,"+element+".getParent());\n");
		try {
			pstmtKeys.setString(1, element);
			rs = pstmtKeys.executeQuery();
			int i=0;
			while (rs.next()) {
				i++;
				// System.out.println("private String "+rs.getString("key_name")+";");
				String oriKey = rs.getString("key_name");
				if ("row".equals(oriKey))
					oriKey = "row_data";
				String capKey = oriKey.substring(0, 1).toUpperCase() + oriKey.substring(1);
				sb.append("private String " + oriKey + ";\n");
				if ("row_data".equals(oriKey))
					sbGet.append("	@XmlAttribute(name = \"" + "row" + "\")\n");
				else
					sbGet.append("	@XmlAttribute(name = \"" + oriKey + "\")\n");
				
				sbGet.append("	public String get" + capKey + "() {\n" + "		return " + oriKey + ";\n" + "	}\n");
				sbGet.append("	public void set" + capKey + "(String " + oriKey + ") {\n" + "		this." + oriKey
						+ " = " + oriKey + ";\n" + "	}\n");
				sbCreate.append(oriKey + " varchar(128),");
				sbInsert.append(oriKey+",");
				
				sbPstmt.append("pstmt"+element+".setString("+(i+4)+","+element+".get"+capKey+"());\n");
			}
			// System.out.println(sb.deleteCharAt(sb.length()-1).toString());

			pstmtChild.setString(1, element);
			rs = pstmtChild.executeQuery();
			while (rs.next()) {
				String childName = rs.getString("element_name");
				String capChildName = childName.substring(0, 1).toUpperCase() + childName.substring(1);
				sb.append("private List<Hana" + capChildName + "> " + childName + "List;\n");

				sbGet.append("	@XmlElement(name = \"" + childName + "\")\n" + "	public List<Hana" + capChildName
						+ "> get" + capChildName + "List() {\n" + "		return " + childName + "List;\n" + "	}\n");
				sbGet.append("	public void set" + capChildName + "List(List<Hana" + capChildName + ">" + childName
						+ "List) {\n" + "		this." + childName + "List = " + childName + "List;\n" + "	}\n");
			}
			System.out.println(sb.toString());
			System.out.println(sbGet.toString());
			System.out.println("------------------ db creation -----------------");
			sbCreate.deleteCharAt(sbCreate.length() - 1);
			System.out.println(sbCreate.append(");").toString());
			sbInsert.deleteCharAt(sbInsert.length() -1);
			sbInsert.append(") values(");
			for (int j=0;j<i+4;j++)
				sbInsert.append("?,");
			sbInsert.deleteCharAt(sbInsert.length() -1);
			sbInsert.append(")\";");
			System.out.println(sbInsert.toString());
			System.out.println("-------------------");
			
			sbPstmt.append("pstmt"+element+".executeUpdate();\n");
			sbPstmt.append("} catch (SQLException e) {\n");
			sbPstmt.append("e.printStackTrace();\n");
		    sbPstmt.append("}\n");
		    
			System.out.println(sbPstmt.toString());
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertMap(Map<String, Map<String, String>> map) {
		int i = 0;
		Map<String, String> keyValueMap = null;
		for (String element : map.keySet()) {
			// System.out.println(i++ + " , " + element);
			keyValueMap = map.get(element);
			for (String key : keyValueMap.keySet()) {
				// System.out.println(i++ + " , "+key + " , " + keyValueMap.get(key));
				try {
					// pstmt.setSInt(1, seq++);
					pstmt.setString(1, element);
					pstmt.setInt(2, i++);
					pstmt.setString(3, key);
					pstmt.setString(4, keyValueMap.get(key));

					pstmt.executeLargeUpdate();

					// conn.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		// File f = new
		// File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\01_NORMAL_FIELD\\01_BASIC\\field_basic.xml");
		File f = new File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\07_GRID\\01_BASIC\\grid_basic_02.xml");
		String sLine = null;
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(f), Charset.forName("euc-kr")));
			while ((sLine = br.readLine()) != null) {
				map = getMap(sLine);
				// printMap(map);
				// insertMap(map);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// printMapForElement("map");
		 printMapForElement("screen");
		// printMapForElement("activex");
		// printMapForElement("grid");
		//printMapForElement("column");
		// printMapForElement("data");
		// printMapForElement("header");
		//printMapForElement("pushbutton");
		//printMapForElement("image");
		//printMapForElement("panel");
		//printMapForElement("text");
		//printMapForElement("multiline");
		//printMapForElement("numericex_field");
		//printMapForElement("tab_order");
		//printMapForElement("table");
	}

}
