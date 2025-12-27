package com.honsoft.xframe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxReadXml {
	private Connection conn;
	private PreparedStatement pstmt, pstmtKeys;
	private ResultSet rs;

	private String sqlStr = "insert into elements (file_path,file_name,depth,element_name,element_id,parent_name,parent_id,attr_name, attr_value,element_text) values(?,?,?,?,?,?,?,?,?,?)";

	private String db = "jdbc:mysql://localhost:3306/apitest";
	private String user = "shoppingnt";
	private String password = "Shoppingnt2021!@";

	File f;

	XMLInputFactory factory;
	XMLStreamReader reader;
	XMLEventReader eventReader;
	int depth = 0;

	public StaxReadXml() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(db, user, password);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sqlStr);

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
		ParentElement[] open_elements = new ParentElement[20];
		//int[] start_elements = new int[20];
		Map<String,Integer> startMap = new HashMap<String, Integer>();
		open_elements[0] = new ParentElement("", 0);
		//start_elements[0] = 0;
		startMap.put("root", 0);
		ParentElement parent = null;
		String elementText = "";
		StartElement startElement ;
		EndElement endElement;
		int startElementId = 0;
		
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				depth++;
				cnt++;
				//startElementId = start_elements[depth-1];
				startElement = (StartElement)event;
				//startElementId = cnt;
				parent = open_elements[depth - 1];
				open_elements[depth] = new ParentElement(startElement.getName().getLocalPart(), cnt);
				startMap.put(startElement.getName().getLocalPart(), cnt);
				//start_elements[depth] = cnt;
				// System.out.println("-------------- tag : "+element.getName());
				Iterator<Attribute> iterator = startElement.getAttributes();
				if (iterator.hasNext()) {
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						QName name = attribute.getName();
						String value = attribute.getValue();
						// System.out.println(f.getAbsolutePath()+","+f.getName() +
						// ","+element.getName()+","+name+","+value);
						// System.out.println(element.getName() + " , " + depth + " , " + name + " , " +
						// value);
						pstmt.setString(1, f.getAbsolutePath());
						pstmt.setString(2, f.getName());
						pstmt.setInt(3, depth);
						pstmt.setString(4, startElement.getName().getLocalPart());
						pstmt.setInt(5, cnt);
						pstmt.setString(6, parent.getParent_name());
						pstmt.setInt(7, parent.getParent_id());
						pstmt.setString(8, name.getLocalPart());
						pstmt.setString(9, value);
						pstmt.setString(10, "");
						pstmt.executeUpdate();
						conn.commit();
					}
				} else {
					// System.out.println(element.getName() + " , " + depth);
					pstmt.setString(1, f.getAbsolutePath());
					pstmt.setString(2, f.getName());
					pstmt.setInt(3, depth);
					pstmt.setString(4, startElement.getName().getLocalPart());
					pstmt.setInt(5, cnt);
					pstmt.setString(6, parent.getParent_name());
					pstmt.setInt(7, parent.getParent_id());
					pstmt.setString(8, "");
					pstmt.setString(9, "");
					pstmt.setString(10, "");
					pstmt.executeUpdate();
					conn.commit();
				}

			} else if (event.isEndElement()) {
				if(!"".equals(elementText)) {
					endElement = (EndElement) event;
					parent = open_elements[depth - 1];
					if("xml".equals(endElement.getName().getLocalPart()))
							System.out.println("break point");
					else
						System.out.println("end element name: "+endElement.getName().getLocalPart());
					pstmt.setString(1, f.getAbsolutePath());
					pstmt.setString(2, f.getName());
					pstmt.setInt(3, depth);
					pstmt.setString(4, endElement.getName().getLocalPart());
					pstmt.setInt(5,startMap.get(endElement.getName().getLocalPart()));
					pstmt.setString(6, parent.getParent_name());
					pstmt.setInt(7, parent.getParent_id());
					pstmt.setString(8, "");
					pstmt.setString(9, "");
					pstmt.setString(10, elementText);
					pstmt.executeUpdate();
					conn.commit();
					elementText = "";
				}
				depth--;
				
			} else if (event.isCharacters()){
				Characters characters = event.asCharacters();
				elementText = characters.getData();
               // if (!characters.isWhiteSpace()) {
               //     command.addResponse(characters.getData());
               // }
				//parent = open_elements[depth - 1];
				//pstmt.setString(1, f.getAbsolutePath());
				//pstmt.setString(2, f.getName());
				//pstmt.setInt(3, depth-1);
				//pstmt.setString(4, parent.getParent_name());
				//pstmt.setInt(5, cnt);
				//pstmt.setString(6, parent.getParent_name());
				//pstmt.setInt(7, parent.getParent_id());
				//pstmt.setString(8, "");
				//pstmt.setString(9, "");
				//pstmt.setString(10, characters.getData());
				//pstmt.executeUpdate();
			}
		}

		conn.commit();

	}

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, SQLException {
		StaxReadXml app = new StaxReadXml();
		File f = new File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\07_GRID\\01_BASIC\\grid_basic_02.xml");
		app.parse(f,Charset.forName("UTF-8"));

	}

}
