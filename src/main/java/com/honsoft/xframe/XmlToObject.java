package com.honsoft.xframe;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private static PreparedStatement pstmt, pstmtKeys, pstmtChild;
	private static ResultSet rs;
	private static PreparedStatement pstmtgrid;
	private static String sqlgrid = "insert into hanagrid (file_path,file_name,id,parent,accept_dropfiles,autocheckrow_editing,autozoom,back_color,border,border_color,cell_dividecolor,cell_dividerowcount,cell_height,cell_linecolor,column_moveable,column_sort,column_sort_disp,control_id,description,dragable,dynamic_fixedcols,dynamic_fixedrows,fixedcols,fixedrows,font,gradient_effect,gradient_endcolor,gradient_startcolor,header_clicksort,header_height,header_horzcount,header_style,header_vertcount,height,horzscrollbar_style,in_index,linenumber_backcolor,linenumber_forecolor,linenumber_show,linenumber_title,link_data,max_addablerows,mid_statistics,multi_checkrow,multi_select,name,on_checkrowclick,on_dropfiles,on_itemclick,on_itemdblclick,on_itemeditcomplete,on_itemselchange,on_keydown,out_index,popupmenu_show,row_addable,row_moveable,samplerows,selectcell_backcolor,selectcell_forecolor,selectrow_backcolor,selectrow_bordercolor,selectrow_style,statistics_column,statistics_row,tabstop,use_checkrow,use_selectblock,version,vertscrollbar_style,width,x,y) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static PreparedStatement pstmtcolumn;
	private static String sqlcolumn = "insert into hanacolumn (file_path,file_name,id,parent,row_data) values(?,?,?,?,?)";

	private static PreparedStatement pstmtdata;
	private static String sqldata = "insert into hanadata (file_path,file_name,id,parent,apply_rightpattern,backcolor,cellmerge,combobox_autocodevalid,combobox_editable,combobox_excelstyle,data_type,description,editable,excel_rw,forecolor,hostdata_includedot,image,image_horzalign,imemode,input_type,link_data,max_length,midstatistics_show,midstatistics_type,name,pattern,picklist,picklist_linkdata,picklist_selstyle,picklist_viewstyle,prefilldata,resizable,sorting,statistics_show,statisticsrow_type,text_horzalign,width) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static PreparedStatement pstmtpushbutton;
	private static String sqlpushbutton = "insert into hanapushbutton (file_path,file_name,id,parent,arrayindex,back_color,border,border_color,click_setfocus,control_id,description,drawfocusrect,enable,font,fore_color,gradient_effect,gradient_endcolor,gradient_startcolor,height,hidden,horz_align,image_disable,image_down,image_fillstyle,image_focus,image_hover,image_normal,mouse_cursor,name,on_mousedown,on_mousein,on_mouseup,on_rdblclick,push_state,tabstop,text,tooltip,version,vert_align,width,x,y) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static PreparedStatement pstmtimage;
	private static String sqlimage = "insert into hanaimage (file_path,file_name,id,parent,autosize,back_color,border,border_color,control_id,height,image,image_fillstyle,imagesize,name,on_click,width,x,y) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static PreparedStatement pstmtpanel;
	private static String sqlpanel = "insert into hanapanel (file_path,file_name,id,parent,back_color,border,control_id,height,name,width,x,y) values(?,?,?,?,?,?,?,?,?,?,?,?)";

	private static PreparedStatement pstmtscreen;
	private static String sqlscreen = "insert into hanascreen (file_path,file_name,id,parent,back_color,back_imagesize,CPI,directory,height,horzscrollbar_style,last_update_date,lattice_x,lattice_y,LPI,on_activate,on_destroy,on_keydown,on_load,on_submitcomplete,printmediatype,printscreen,screen_help,screen_version,screenid,scriptcode,title,trancode,version,vertscrollbar_style,width) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static String db = "jdbc:mysql://localhost:3306/hanacard";
	private static String user = "hanacard";
	private static String password = "hanacard";

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
			pstmtcolumn = conn.prepareStatement(sqlcolumn);
			pstmtdata = conn.prepareStatement(sqldata);
			pstmtgrid = conn.prepareStatement(sqlgrid);
			pstmtpushbutton = conn.prepareStatement(sqlpushbutton);
			pstmtimage = conn.prepareStatement(sqlimage);
			pstmtpanel = conn.prepareStatement(sqlpanel);
			pstmtscreen = conn.prepareStatement(sqlscreen);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

	public static void printGrid(HanaGrid grid) {
		// System.out.println("grid:----");
		try {
			pstmtgrid.setString(1, file_path);
			pstmtgrid.setString(2, file_name);
			pstmtgrid.setString(3, grid.getId());
			pstmtgrid.setString(4, grid.getParent());
			pstmtgrid.setString(5, grid.getAccept_dropfiles());
			pstmtgrid.setString(6, grid.getAutocheckrow_editing());
			pstmtgrid.setString(7, grid.getAutozoom());
			pstmtgrid.setString(8, grid.getBack_color());
			pstmtgrid.setString(9, grid.getBorder());
			pstmtgrid.setString(10, grid.getBorder_color());
			pstmtgrid.setString(11, grid.getCell_dividecolor());
			pstmtgrid.setString(12, grid.getCell_dividerowcount());
			pstmtgrid.setString(13, grid.getCell_height());
			pstmtgrid.setString(14, grid.getCell_linecolor());
			pstmtgrid.setString(15, grid.getColumn_moveable());
			pstmtgrid.setString(16, grid.getColumn_sort());
			pstmtgrid.setString(17, grid.getColumn_sort_disp());
			pstmtgrid.setString(18, grid.getControl_id());
			pstmtgrid.setString(19, grid.getDescription());
			pstmtgrid.setString(20, grid.getDragable());
			pstmtgrid.setString(21, grid.getDynamic_fixedcols());
			pstmtgrid.setString(22, grid.getDynamic_fixedrows());
			pstmtgrid.setString(23, grid.getFixedcols());
			pstmtgrid.setString(24, grid.getFixedrows());
			pstmtgrid.setString(25, grid.getFont());
			pstmtgrid.setString(26, grid.getGradient_effect());
			pstmtgrid.setString(27, grid.getGradient_endcolor());
			pstmtgrid.setString(28, grid.getGradient_startcolor());
			pstmtgrid.setString(29, grid.getHeader_clicksort());
			pstmtgrid.setString(30, grid.getHeader_height());
			pstmtgrid.setString(31, grid.getHeader_horzcount());
			pstmtgrid.setString(32, grid.getHeader_style());
			pstmtgrid.setString(33, grid.getHeader_vertcount());
			pstmtgrid.setString(34, grid.getHeight());
			pstmtgrid.setString(35, grid.getHorzscrollbar_style());
			pstmtgrid.setString(36, grid.getIn_index());
			pstmtgrid.setString(37, grid.getLinenumber_backcolor());
			pstmtgrid.setString(38, grid.getLinenumber_forecolor());
			pstmtgrid.setString(39, grid.getLinenumber_show());
			pstmtgrid.setString(40, grid.getLinenumber_title());
			pstmtgrid.setString(41, grid.getLink_data());
			pstmtgrid.setString(42, grid.getMax_addablerows());
			pstmtgrid.setString(43, grid.getMid_statistics());
			pstmtgrid.setString(44, grid.getMulti_checkrow());
			pstmtgrid.setString(45, grid.getMulti_select());
			pstmtgrid.setString(46, grid.getName());
			pstmtgrid.setString(47, grid.getOn_checkrowclick());
			pstmtgrid.setString(48, grid.getOn_dropfiles());
			pstmtgrid.setString(49, grid.getOn_itemclick());
			pstmtgrid.setString(50, grid.getOn_itemdblclick());
			pstmtgrid.setString(51, grid.getOn_itemeditcomplete());
			pstmtgrid.setString(52, grid.getOn_itemselchange());
			pstmtgrid.setString(53, grid.getOn_keydown());
			pstmtgrid.setString(54, grid.getOut_index());
			pstmtgrid.setString(55, grid.getPopupmenu_show());
			pstmtgrid.setString(56, grid.getRow_addable());
			pstmtgrid.setString(57, grid.getRow_moveable());
			pstmtgrid.setString(58, grid.getSamplerows());
			pstmtgrid.setString(59, grid.getSelectcell_backcolor());
			pstmtgrid.setString(60, grid.getSelectcell_forecolor());
			pstmtgrid.setString(61, grid.getSelectrow_backcolor());
			pstmtgrid.setString(62, grid.getSelectrow_bordercolor());
			pstmtgrid.setString(63, grid.getSelectrow_style());
			pstmtgrid.setString(64, grid.getStatistics_column());
			pstmtgrid.setString(65, grid.getStatistics_row());
			pstmtgrid.setString(66, grid.getTabstop());
			pstmtgrid.setString(67, grid.getUse_checkrow());
			pstmtgrid.setString(68, grid.getUse_selectblock());
			pstmtgrid.setString(69, grid.getVersion());
			pstmtgrid.setString(70, grid.getVertscrollbar_style());
			pstmtgrid.setString(71, grid.getWidth());
			pstmtgrid.setString(72, grid.getX());
			pstmtgrid.setString(73, grid.getY());
			pstmtgrid.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void printColumn(HanaColumn column) {
		// System.out.println("column:------ ");
		// System.out.println(file_path + ", column id: " + column.getId() + ",parent: "
		// + column.getParent());
		try {
			pstmtcolumn.setString(1, file_path);
			pstmtcolumn.setString(2, file_name);
			pstmtcolumn.setString(3, column.getId());
			pstmtcolumn.setString(4, column.getParent());
			pstmtcolumn.setString(5, column.getRow_data());

			pstmtcolumn.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printHeader(HanaHeader header) {
		// System.out.println("header:----");
		// System.out.println(file_path + ",header id: " + header.getId() + ",parent: "
		// + header.getParent() + " title: "
		// + header.getTitle());
	}

	public static void printImage(HanaImage image) {
		// System.out.println("header:----");
		try {
			pstmtimage.setString(1, file_path);
			pstmtimage.setString(2, file_name);
			pstmtimage.setString(3, image.getId());
			pstmtimage.setString(4, image.getParent());
			pstmtimage.setString(5, image.getAutosize());
			pstmtimage.setString(6, image.getBack_color());
			pstmtimage.setString(7, image.getBorder());
			pstmtimage.setString(8, image.getBorder_color());
			pstmtimage.setString(9, image.getControl_id());
			pstmtimage.setString(10, image.getHeight());
			pstmtimage.setString(11, image.getImage());
			pstmtimage.setString(12, image.getImage_fillstyle());
			pstmtimage.setString(13, image.getImagesize());
			pstmtimage.setString(14, image.getName());
			pstmtimage.setString(15, image.getOn_click());
			pstmtimage.setString(16, image.getWidth());
			pstmtimage.setString(17, image.getX());
			pstmtimage.setString(18, image.getY());
			pstmtimage.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void printData(HanaData data) {
		// System.out.println("data:----");
		try {
			pstmtdata.setString(1, file_path);
			pstmtdata.setString(2, file_name);
			pstmtdata.setString(3, data.getId());
			pstmtdata.setString(4, data.getParent());
			pstmtdata.setString(5, data.getApply_rightpattern());
			pstmtdata.setString(6, data.getBackcolor());
			pstmtdata.setString(7, data.getCellmerge());
			pstmtdata.setString(8, data.getCombobox_autocodevalid());
			pstmtdata.setString(9, data.getCombobox_editable());
			pstmtdata.setString(10, data.getCombobox_excelstyle());
			pstmtdata.setString(11, data.getData_type());
			pstmtdata.setString(12, data.getDescription());
			pstmtdata.setString(13, data.getEditable());
			pstmtdata.setString(14, data.getExcel_rw());
			pstmtdata.setString(15, data.getForecolor());
			pstmtdata.setString(16, data.getHostdata_includedot());
			pstmtdata.setString(17, data.getImage());
			pstmtdata.setString(18, data.getImage_horzalign());
			pstmtdata.setString(19, data.getImemode());
			pstmtdata.setString(20, data.getInput_type());
			pstmtdata.setString(21, data.getLink_data());
			pstmtdata.setString(22, data.getMax_length());
			pstmtdata.setString(23, data.getMidstatistics_show());
			pstmtdata.setString(24, data.getMidstatistics_type());
			pstmtdata.setString(25, data.getName());
			pstmtdata.setString(26, data.getPattern());
			pstmtdata.setString(27, data.getPicklist());
			pstmtdata.setString(28, data.getPicklist_linkdata());
			pstmtdata.setString(29, data.getPicklist_selstyle());
			pstmtdata.setString(30, data.getPicklist_viewstyle());
			pstmtdata.setString(31, data.getPrefilldata());
			pstmtdata.setString(32, data.getResizable());
			pstmtdata.setString(33, data.getSorting());
			pstmtdata.setString(34, data.getStatistics_show());
			pstmtdata.setString(35, data.getStatisticsrow_type());
			pstmtdata.setString(36, data.getText_horzalign());
			pstmtdata.setString(37, data.getWidth());
			pstmtdata.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void printPushbutton(HanaPushbutton pushbutton) {
		// System.out.println("data:----");
		try {
			pstmtpushbutton.setString(1, file_path);
			pstmtpushbutton.setString(2, file_name);
			pstmtpushbutton.setString(3, pushbutton.getId());
			pstmtpushbutton.setString(4, pushbutton.getParent());
			pstmtpushbutton.setString(5, pushbutton.getArrayindex());
			pstmtpushbutton.setString(6, pushbutton.getBack_color());
			pstmtpushbutton.setString(7, pushbutton.getBorder());
			pstmtpushbutton.setString(8, pushbutton.getBorder_color());
			pstmtpushbutton.setString(9, pushbutton.getClick_setfocus());
			pstmtpushbutton.setString(10, pushbutton.getControl_id());
			pstmtpushbutton.setString(11, pushbutton.getDescription());
			pstmtpushbutton.setString(12, pushbutton.getDrawfocusrect());
			pstmtpushbutton.setString(13, pushbutton.getEnable());
			pstmtpushbutton.setString(14, pushbutton.getFont());
			pstmtpushbutton.setString(15, pushbutton.getFore_color());
			pstmtpushbutton.setString(16, pushbutton.getGradient_effect());
			pstmtpushbutton.setString(17, pushbutton.getGradient_endcolor());
			pstmtpushbutton.setString(18, pushbutton.getGradient_startcolor());
			pstmtpushbutton.setString(19, pushbutton.getHeight());
			pstmtpushbutton.setString(20, pushbutton.getHidden());
			pstmtpushbutton.setString(21, pushbutton.getHorz_align());
			pstmtpushbutton.setString(22, pushbutton.getImage_disable());
			pstmtpushbutton.setString(23, pushbutton.getImage_down());
			pstmtpushbutton.setString(24, pushbutton.getImage_fillstyle());
			pstmtpushbutton.setString(25, pushbutton.getImage_focus());
			pstmtpushbutton.setString(26, pushbutton.getImage_hover());
			pstmtpushbutton.setString(27, pushbutton.getImage_normal());
			pstmtpushbutton.setString(28, pushbutton.getMouse_cursor());
			pstmtpushbutton.setString(29, pushbutton.getName());
			pstmtpushbutton.setString(30, pushbutton.getOn_mousedown());
			pstmtpushbutton.setString(31, pushbutton.getOn_mousein());
			pstmtpushbutton.setString(32, pushbutton.getOn_mouseup());
			pstmtpushbutton.setString(33, pushbutton.getOn_rdblclick());
			pstmtpushbutton.setString(34, pushbutton.getPush_state());
			pstmtpushbutton.setString(35, pushbutton.getTabstop());
			pstmtpushbutton.setString(36, pushbutton.getText());
			pstmtpushbutton.setString(37, pushbutton.getTooltip());
			pstmtpushbutton.setString(38, pushbutton.getVersion());
			pstmtpushbutton.setString(39, pushbutton.getVert_align());
			pstmtpushbutton.setString(40, pushbutton.getWidth());
			pstmtpushbutton.setString(41, pushbutton.getX());
			pstmtpushbutton.setString(42, pushbutton.getY());
			pstmtpushbutton.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void printPanel(HanaPanel panel) {
		try {
			pstmtpanel.setString(1, file_path);
			pstmtpanel.setString(2, file_name);
			pstmtpanel.setString(3, panel.getId());
			pstmtpanel.setString(4, panel.getParent());
			pstmtpanel.setString(5, panel.getBack_color());
			pstmtpanel.setString(6, panel.getBorder());
			pstmtpanel.setString(7, panel.getControl_id());
			pstmtpanel.setString(8, panel.getHeight());
			pstmtpanel.setString(9, panel.getName());
			pstmtpanel.setString(10, panel.getWidth());
			pstmtpanel.setString(11, panel.getX());
			pstmtpanel.setString(12, panel.getY());
			pstmtpanel.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void printScreen(HanaScreen screen) {
		try {
			pstmtscreen.setString(1, file_path);
			pstmtscreen.setString(2, file_name);
			pstmtscreen.setString(3, screen.getId());
			pstmtscreen.setString(4, screen.getParent());
			pstmtscreen.setString(5, screen.getBack_color());
			pstmtscreen.setString(6, screen.getBack_imagesize());
			pstmtscreen.setString(7, screen.getCPI());
			pstmtscreen.setString(8, screen.getDirectory());
			pstmtscreen.setString(9, screen.getHeight());
			pstmtscreen.setString(10, screen.getHorzscrollbar_style());
			pstmtscreen.setString(11, screen.getLast_update_date());
			pstmtscreen.setString(12, screen.getLattice_x());
			pstmtscreen.setString(13, screen.getLattice_y());
			pstmtscreen.setString(14, screen.getLPI());
			pstmtscreen.setString(15, screen.getOn_activate());
			pstmtscreen.setString(16, screen.getOn_destroy());
			pstmtscreen.setString(17, screen.getOn_keydown());
			pstmtscreen.setString(18, screen.getOn_load());
			pstmtscreen.setString(19, screen.getOn_submitcomplete());
			pstmtscreen.setString(20, screen.getPrintmediatype());
			pstmtscreen.setString(21, screen.getPrintscreen());
			pstmtscreen.setString(22, screen.getScreen_help());
			pstmtscreen.setString(23, screen.getScreen_version());
			pstmtscreen.setString(24, screen.getScreenid());
			pstmtscreen.setString(25, screen.getScriptcode());
			pstmtscreen.setString(26, screen.getTitle());
			pstmtscreen.setString(27, screen.getTrancode());
			pstmtscreen.setString(28, screen.getVersion());
			pstmtscreen.setString(29, screen.getVertscrollbar_style());
			pstmtscreen.setString(30, screen.getWidth());
			pstmtscreen.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void printMap(HanaMap map) {
		// System.out.println("map:---------- ");
		// System.out.println(file_path + ",map id: " + map.getId());
	}

	public static void insertXML() {
		List<HanaMap> maps = mapList.getHanaMapList();
		int i = 0;
		// String idx = "i";
		for (HanaMap map : maps) {
			i++;
			map.setParent(mapList.getId());
			map.setId("m" + i);
			printMap(map);
			int j = 0;
			List<HanaGrid> grids = map.getGridList();
			List<HanaPushbutton> pushbuttons = map.getPushbuttonList();
			List<HanaImage> images = map.getImageList();
			List<HanaPanel> panels = map.getPanelList();
			List<HanaScreen> screens = map.getScreenList();

			// -- grid
			if (grids != null) {
				for (HanaGrid grid : grids) {
					j++;
					grid.setParent(map.getId());
					grid.setId(map.getId() + j);
					printGrid(grid);
					int k = 0;
					List<HanaColumn> columns = grid.getColumnList();

					// - column
					if (columns != null) {
						for (HanaColumn column : columns) {
							k++;
							column.setParent(grid.getId());
							column.setId(grid.getId() + k);
							int l = 0;
							printColumn(column);
							List<HanaHeader> headers = column.getHeaderList();
							List<HanaData> datas = column.getDataList();

							// -- header
							if (headers != null) {

								for (HanaHeader header : headers) {
									l++;
									header.setParent(column.getId());
									header.setId(column.getId() + l);
									printHeader(header);
									// System.out.println(
									// map.getId() + "," + grid.getId() + "," + column.getId() + "," +
									// header.getId());
								}
							} // end of header

							// - data
							if (datas != null) {
								l = 0;
								for (HanaData data : datas) {
									l++;
									data.setParent(column.getId());
									data.setId(column.getId() + l);
									printData(data);
									// System.out
									// .println(map.getId() + "," + grid.getId() + "," + column.getId() + "," +
									// data.getId());
								}
							} // --end of data
						}
					} // end of column
				}
			} // -- end of grid

			// -- panel
			if (panels != null) {
				for (HanaPanel panel : panels) {
					j++;
					panel.setParent(map.getId());
					panel.setId(map.getId() + j);
					printPanel(panel);
					int k = 0;
					/*
					 * List<HanaColumn> columns = panel.getColumnList();
					 * 
					 * // - column if (columns != null) { for (HanaColumn column : columns) { k++;
					 * column.setParent(panel.getId()); column.setId(panel.getId() + k); int l = 0;
					 * printColumn(column); List<HanaHeader> headers = column.getHeaderList();
					 * List<HanaData> datas = column.getDataList();
					 * 
					 * // -- header if (headers != null) {
					 * 
					 * for (HanaHeader header : headers) { l++; header.setParent(column.getId());
					 * header.setId(column.getId() + l); printHeader(header); // System.out.println(
					 * // map.getId() + "," + panel.getId() + "," + column.getId() + "," + //
					 * header.getId()); } } // end of header
					 * 
					 * // - data if (datas != null) { l = 0; for (HanaData data : datas) { l++;
					 * data.setParent(column.getId()); data.setId(column.getId() + l);
					 * printData(data); // System.out // .println(map.getId() + "," + panel.getId()
					 * + "," + column.getId() + "," + // data.getId()); } } // --end of data } } //
					 * end of column
					 */ }
			} // -- end of panel

			// -- pushbutton
			if (pushbuttons != null) {
				for (HanaPushbutton pushbutton : pushbuttons) {
					j++;
					pushbutton.setParent(map.getId());
					pushbutton.setId(map.getId() + j);
					printPushbutton(pushbutton);
					int k = 0;
				}
			} // end of pushbutton

			// -- image
			if (images != null) {
				for (HanaImage image : images) {
					j++;
					image.setParent(map.getId());
					image.setId(map.getId() + j);
					printImage(image);
					int k = 0;
				}
			} // end of image

			
			// -- screen
			if (screens != null) {
				for (HanaScreen screen : screens) {
					j++;
					screen.setParent(map.getId());
					screen.setId(map.getId() + j);
					printScreen(screen);
					int k = 0;
				}
			} // end of screen
			
		}

	}

	public static void main(String[] args) {
		File f = new File("C:\\xFrame\\project\\screen\\XAP\\01_COMPONENT\\07_GRID\\01_BASIC\\grid_basic_02.xml");

		prepareXML(f);
		insertXML();
	}
}
