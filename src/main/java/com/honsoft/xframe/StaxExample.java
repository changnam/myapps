package com.honsoft.xframe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaxExample {
	private Connection conn;
	private PreparedStatement pstmt, pstmtKeys;
	private ResultSet rs;
	
	private String sqlStr = "insert into element_hier (file_path,file_name,depth,element_name,element_value,parent,attr_name, attr_value) values(?,?,?,?,?,?,?,?)";
	
	private String db = "jdbc:mysql://localhost:3306/hanacard";
    private String user = "hanacard";
    private String password = "hanacard";
    
    File file;
    
    XMLInputFactory inputFactory ;    
    XMLStreamReader reader ;
    InputStream is ;
    
    public StaxExample() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(db,user,password);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sqlStr);	
			
			inputFactory = XMLInputFactory.newInstance();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    public void getXml(File file) {
    	this.file = file;
        
        try {
            is = new FileInputStream(file);
            reader = inputFactory.createXMLStreamReader(is);
            parse(reader, 0, "maplist");
            
            conn.commit();
            
           // System.out.println("-----------------");

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch(IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }

    }

    private void parse(XMLStreamReader reader, int depth, String parent) throws XMLStreamException {
        while(true) {
            if(reader.hasNext()) {
                switch(reader.next()) {
                case XMLStreamConstants.START_ELEMENT:
                	depth++;
                    writeBeginTag(reader.getLocalName(), depth, parent);
                    parse(reader, depth,reader.getLocalName());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                	depth--;
                    writeEndTag(reader.getLocalName(), depth);
                    return;
                case XMLStreamConstants.CHARACTERS:
                    writeTagData(reader.getLocalName(), depth);
                    return;
                }
            }else {
            	break;
            }
        }
    }

    private void writeBeginTag(String tag, int depth, String parent) {
       // for(int i = 0; i < depth; i++) {
       //     System.out.print(" ");
       // }
       System.out.println(depth+" "+"<" + tag + ">"+parent);
		/*
		 * try { pstmt.setString(1, file.getAbsolutePath()); pstmt.setString(2,
		 * file.getName()); pstmt.setInt(3, depth); pstmt.setString(4,tag);
		 * pstmt.setString(5,parent);
		 * 
		 * pstmt.executeLargeUpdate();
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

    	
    }

    private void writeEndTag(String tag, int depth) {
        //for(int i = 0; i < depth; i++) {
        //    System.out.print(" ");
       // }
       // System.out.println("</" + tag + ">");
    }
    
    private void writeTagData(String tag, int depth) {
        //for(int i = 0; i < depth; i++) {
        //    System.out.print(" ");
       // }
       // System.out.println("</" + tag + ">");
    }

    public static void main(String[] args) {
        StaxExample app = new StaxExample();
        File f = new File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\07_GRID\\01_BASIC\\grid_basic_02.xml");
        app.getXml(f);
    }

}