package com.honsoft.xframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxReadXml {
	private Connection conn;
	private PreparedStatement pstmt, pstmtKeys, pstmtScript, pstmtScriptSave, pstmtDataset;
	private ResultSet rs;

	//private String sqlStr = "insert into elements (file_path,file_name,depth,element_name,element_id,parent_name,parent_id,attr_name, attr_value) values(?,?,?,?,?,?,?,?,?)";

	private String db = "jdbc:mysql://localhost:3306/apitest";
	private String user = "shoppingnt";
	private String password = "Shoppingnt2021!@";
	
	private String sqlStr = "insert into elements (file_path,file_name,el_depth,element_name,element_id,parent_name,parent_id,attr_name, attr_value,control_id) values(?,?,?,?,?,?,?,?,?,?)";
	private String sqlStrScript = "insert into scripts (file_path,file_name,script_text) values(?,?,?)";
	private String sqlStrScriptSave = "SELECT CASE substr(file_name,INSTR(file_name,'.',-1)+1) WHEN 'xfdl' THEN REPLACE (file_path,'.xfdl','.js') WHEN 'xjs' THEN REPLACE (file_path,'.xjs','.js') END AS file_path_js,script_text  FROM scripts where script_text is not null ORDER BY file_name";
	private String sqlStrDataset = "insert into xdatasets(file_path,file_name,ds_name,col_name,col_desc,col_length,col_data,col_callback,col_order) values (?,?,?,?,?,?,?,?,?)";

	// String db = "jdbc:oracle:thin:@localhost:1521:xe";
	//private String user = "cddba1";
	//private String password = "cn0012";
	

	File f;

	XMLInputFactory factory;
	XMLStreamReader reader;
	XMLEventReader eventReader;
	int depth = 0;

	public StaxReadXml() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection(db, user, password);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sqlStr);
			pstmtScript = conn.prepareStatement(sqlStrScript);
			pstmtScriptSave = conn.prepareStatement(sqlStrScriptSave);
			pstmtDataset = conn.prepareStatement(sqlStrDataset);
			
			factory = XMLInputFactory.newInstance();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parse(File file, Charset charset) throws FileNotFoundException, XMLStreamException, SQLException {
		this.f = file;
		eventReader = factory.createXMLEventReader(new FileInputStream(file), charset.toString());
		depth = 0;
		int cnt = 0;
		ParentElement[] open_elements = new ParentElement[100];
		open_elements[0] = new ParentElement("", 0);
		ParentElement parent = null;
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				depth++;
				cnt++;
				StartElement element = (StartElement) event;
				parent = open_elements[depth - 1];
				open_elements[depth] = new ParentElement(element.getName().getLocalPart(), cnt);
				// System.out.println("-------------- tag : "+element.getName());
				Iterator<Attribute> iterator = element.getAttributes();
				//System.out.println("tag: "+element.getName().getLocalPart());
				if("Script".equals(element.getName().getLocalPart())){
					iterator = Collections.emptyIterator();
				}
				String xDatasetName = "";
				if (iterator.hasNext()) {
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						if  (!("xlinkdataset".equals(element.getName().getLocalPart()) && "columns".equals(attribute.getName().getLocalPart()))) {
							if ("xlinkdataset".equals(element.getName().getLocalPart()) && "id".equals(attribute.getName().getLocalPart())) {
								xDatasetName =  attribute.getValue();
							}
							QName name = attribute.getName();
							String value = attribute.getValue();
							// System.out.println(f.getAbsolutePath()+","+f.getName() +
							// ","+element.getName()+","+name+","+value);
							// System.out.println(element.getName() + " , " + depth + " , " + name + " , " +
							// value);
							pstmt.setString(1, f.getAbsolutePath());
							pstmt.setString(2, f.getName());
							pstmt.setInt(3, depth);
							pstmt.setString(4, element.getName().getLocalPart());
							pstmt.setInt(5, cnt);
							pstmt.setString(6, parent.getParent_name());
							pstmt.setInt(7, parent.getParent_id());
							pstmt.setString(8, name.getLocalPart());
							pstmt.setString(9, value);
							pstmt.setInt(10, -1);
							pstmt.executeUpdate();
						} else if ("xlinkdataset".equals(element.getName().getLocalPart()) && "columns".equals(attribute.getName().getLocalPart())){
							parseDataSetColumns(element,attribute,parent);
						}
					}
				} else {
					// System.out.println(element.getName() + " , " + depth);
					pstmt.setString(1, f.getAbsolutePath());
					pstmt.setString(2, f.getName());
					pstmt.setInt(3, depth);
					pstmt.setString(4, element.getName().getLocalPart());
					pstmt.setInt(5, cnt);
					pstmt.setString(6, parent.getParent_name());
					pstmt.setInt(7, parent.getParent_id());
					pstmt.setString(8, "");
					pstmt.setString(9, "");
					pstmt.setInt(10, -1);
					pstmt.executeUpdate();
				}

			} else if (event.isEndElement()) {
				depth--;
			}
		}

		conn.commit();

	}
	
	public void parseDataSetColumns(StartElement element,Attribute attribute, ParentElement parent) throws SQLException {
		String xDataSetName = "";
		Iterator<Attribute> iterator = element.getAttributes();
		if (iterator.hasNext()) {
			while(iterator.hasNext()) {
				Attribute tempAttribute = iterator.next();
				if ("id".equals(tempAttribute.getName().getLocalPart())) {
					xDataSetName = tempAttribute.getValue();
					break;
				}
			}
		}
		
		QName name = attribute.getName();
		String value = attribute.getValue().replace("\n", "");
		value = value.replaceAll("&#x0A;", ""); //데이터 영역에 ; 가 있는 경우 정상으로 split 되지 않으므로 replace 처리
		//Pattern pattern = Pattern.compile("([a-zA-Z]+:\".*\":[1-9].*:\".*\":)");
		//Matcher matcher = pattern.matcher(value);
		//while (matcher.find()) {
		//	System.out.println("start: "+matcher.start()+",end: "+matcher.end()+",value: "+value.substring(matcher.start(), matcher.end()));
		//}
		try {
		 if (!"".equals(value)) {         
			String[] columns = value.split(";");
			int cnt = 1;
			//System.out.println("column 갯수 : "+columns.length);
			for (String column : columns) {
				//System.out.println(xDataSetName+"<->"+cnt++ + "<->"+column);
				String[] attrs = column.split(":");
				
				pstmtDataset.setString(1, f.getAbsolutePath());
				pstmtDataset.setString(2, f.getName());
				pstmtDataset.setString(3,xDataSetName);
				pstmtDataset.setString(4, attrs[0]);
				pstmtDataset.setString(5, attrs[1].replace("\"", ""));
				if (!"".equals(attrs[2]))
					pstmtDataset.setInt(6, Integer.parseInt(attrs[2]));
				else
					pstmtDataset.setNull(6, Types.INTEGER);
				
				if (!"".equals(attrs[3].replace("\"", "")))
					pstmtDataset.setString(7,"Y");
				else
					pstmtDataset.setString(7,"N");
				if(attrs.length > 4)	pstmtDataset.setString(8, attrs[4].replace("\"", ""));
				else	pstmtDataset.setNull(8, Types.VARCHAR);
				pstmtDataset.setInt(9, cnt++);
				pstmtDataset.executeUpdate();
	
				int cntAttr = 1;
				//for (String attr : attrs) {
				//	System.out.println(cntAttr++ +"<->"+attr);
	
				//}
			}
		 }
		//conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseScript(File file, Charset charset) throws FileNotFoundException, XMLStreamException, SQLException {
		this.f = file;
		eventReader = factory.createXMLEventReader(new FileInputStream(file), charset.toString());
		depth = 0;
		int cnt = 0;
		ParentElement[] open_elements = new ParentElement[20];
		open_elements[0] = new ParentElement("", 0);
		ParentElement parent = null;
		boolean valueFlag = false;
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				depth++;
				cnt++;
				StartElement element = (StartElement) event;
				if("Script".equals(element.getName().getLocalPart())){
					//System.out.println("break point ");
					valueFlag = true;
				}
				parent = open_elements[depth - 1];
				open_elements[depth] = new ParentElement(element.getName().getLocalPart(), cnt);
				// System.out.println("-------------- tag : "+element.getName());
				//Iterator<Attribute> iterator = element.getAttributes();
				//System.out.println("tag: "+element.getName().getLocalPart());
				//if(!"Script".equals(element.getName().getLocalPart())){
				//	iterator = Collections.emptyIterator();
				//}
				//if (iterator.hasNext()) {
					//System.out.println();
				//} else {
					//System.out.println();
				//}

			} else if (event.isEndElement()) {
				depth--;
				valueFlag = false;
			} else if (event.isCharacters()) {
				if(valueFlag) {
					pstmtScript.setString(1, f.getAbsolutePath());
					pstmtScript.setString(2, f.getName());
					pstmtScript.setString(3, event.asCharacters().getData());
					pstmtScript.executeUpdate();
					valueFlag = false;
				}
			}
		}

		conn.commit();

	}
	public void parseScriptSave() throws XMLStreamException, SQLException, IOException {
		
		ResultSet rs = pstmtScriptSave.executeQuery();
		File f ;
		FileInputStream fio;
		FileWriter fw ;
		String result = null;
		Reader reader = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		while(rs.next()) {
			System.out.println(rs.getString("file_path_js"));
			reader = (rs.getClob("script_text")).getCharacterStream();
			/*
			if(reader != null) {
				br = new BufferedReader(reader);
				result = br.lines().collect(Collectors.joining());
				f = new File(rs.getString("file_path_js"));
				if(f.createNewFile()) {
					System.out.println("newly created.");
				}else {
					System.out.println("already existed.");
				}
				//fio = new FileInputStream(f);
				fw = new FileWriter(f);
				fw.write(result); //개행문자가 포함되지않아 한줄로 write 됨
				fw.close();
			}*/
			
			
			if(reader != null) {
				br = new BufferedReader(reader);
				while(true) {
					result = br.readLine();
					if(result == null) {
						break;
					}
					sb.append(result);
					sb.append("\n");
				}
				
				
				result = sb.toString();
				sb.setLength(0);
				
				f = new File(rs.getString("file_path_js"));
				//fio = new FileInputStream(f);
				fw = new FileWriter(f);
				fw.write(result); 
				fw.close();
			}
			
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, SQLException {
		StaxReadXml app = new StaxReadXml();
		File f = new File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\07_GRID\\01_BASIC\\grid_basic_02.xml");
		app.parse(f,Charset.forName("UTF-8"));

	}

}
