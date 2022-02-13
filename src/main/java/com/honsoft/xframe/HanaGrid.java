package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "grid")
public class HanaGrid {
	private String id;
	private String parent;
	private String accept_dropfiles;
	private String autocheckrow_editing;
	private String autozoom;
	private String back_color;
	private String border;
	private String border_color;
	private String cell_dividecolor;
	private String cell_dividerowcount;
	private String cell_height;
	private String cell_linecolor;
	private String column_moveable;
	private String column_sort;
	private String column_sort_disp;
	private String control_id;
	private String description;
	private String dragable;
	private String dynamic_fixedcols;
	private String dynamic_fixedrows;
	private String fixedcols;
	private String fixedrows;
	private String font;
	private String gradient_effect;
	private String gradient_endcolor;
	private String gradient_startcolor;
	private String header_clicksort;
	private String header_height;
	private String header_horzcount;
	private String header_style;
	private String header_vertcount;
	private String height;
	private String horzscrollbar_style;
	private String in_index;
	private String linenumber_backcolor;
	private String linenumber_forecolor;
	private String linenumber_show;
	private String linenumber_title;
	private String link_data;
	private String max_addablerows;
	private String mid_statistics;
	private String multi_checkrow;
	private String multi_select;
	private String name;
	private String on_checkrowclick;
	private String on_dropfiles;
	private String on_itemclick;
	private String on_itemdblclick;
	private String on_itemeditcomplete;
	private String on_itemselchange;
	private String on_keydown;
	private String out_index;
	private String popupmenu_show;
	private String row_addable;
	private String row_moveable;
	private String samplerows;
	private String selectcell_backcolor;
	private String selectcell_forecolor;
	private String selectrow_backcolor;
	private String selectrow_bordercolor;
	private String selectrow_style;
	private String statistics_column;
	private String statistics_row;
	private String tabstop;
	private String use_checkrow;
	private String use_selectblock;
	private String version;
	private String vertscrollbar_style;
	private String width;
	private String x;
	private String y;
	private List<HanaColumn> columnList;

	   public String getId(){
	     return id;
	   }
	   public void setId(String id){
	     this.id = id;
	   }
	   public String getParent(){
	     return parent;
	   }
	   public void setParent(String parent){
	     this.parent = parent;
	   }
		@XmlAttribute(name = "accept_dropfiles")
		public String getAccept_dropfiles() {
			return accept_dropfiles;
		}
		public void setAccept_dropfiles(String accept_dropfiles) {
			this.accept_dropfiles = accept_dropfiles;
		}
		@XmlAttribute(name = "autocheckrow_editing")
		public String getAutocheckrow_editing() {
			return autocheckrow_editing;
		}
		public void setAutocheckrow_editing(String autocheckrow_editing) {
			this.autocheckrow_editing = autocheckrow_editing;
		}
		@XmlAttribute(name = "autozoom")
		public String getAutozoom() {
			return autozoom;
		}
		public void setAutozoom(String autozoom) {
			this.autozoom = autozoom;
		}
		@XmlAttribute(name = "back_color")
		public String getBack_color() {
			return back_color;
		}
		public void setBack_color(String back_color) {
			this.back_color = back_color;
		}
		@XmlAttribute(name = "border")
		public String getBorder() {
			return border;
		}
		public void setBorder(String border) {
			this.border = border;
		}
		@XmlAttribute(name = "border_color")
		public String getBorder_color() {
			return border_color;
		}
		public void setBorder_color(String border_color) {
			this.border_color = border_color;
		}
		@XmlAttribute(name = "cell_dividecolor")
		public String getCell_dividecolor() {
			return cell_dividecolor;
		}
		public void setCell_dividecolor(String cell_dividecolor) {
			this.cell_dividecolor = cell_dividecolor;
		}
		@XmlAttribute(name = "cell_dividerowcount")
		public String getCell_dividerowcount() {
			return cell_dividerowcount;
		}
		public void setCell_dividerowcount(String cell_dividerowcount) {
			this.cell_dividerowcount = cell_dividerowcount;
		}
		@XmlAttribute(name = "cell_height")
		public String getCell_height() {
			return cell_height;
		}
		public void setCell_height(String cell_height) {
			this.cell_height = cell_height;
		}
		@XmlAttribute(name = "cell_linecolor")
		public String getCell_linecolor() {
			return cell_linecolor;
		}
		public void setCell_linecolor(String cell_linecolor) {
			this.cell_linecolor = cell_linecolor;
		}
		@XmlAttribute(name = "column_moveable")
		public String getColumn_moveable() {
			return column_moveable;
		}
		public void setColumn_moveable(String column_moveable) {
			this.column_moveable = column_moveable;
		}
		@XmlAttribute(name = "column_sort")
		public String getColumn_sort() {
			return column_sort;
		}
		public void setColumn_sort(String column_sort) {
			this.column_sort = column_sort;
		}
		@XmlAttribute(name = "column_sort_disp")
		public String getColumn_sort_disp() {
			return column_sort_disp;
		}
		public void setColumn_sort_disp(String column_sort_disp) {
			this.column_sort_disp = column_sort_disp;
		}
		@XmlAttribute(name = "control_id")
		public String getControl_id() {
			return control_id;
		}
		public void setControl_id(String control_id) {
			this.control_id = control_id;
		}
		@XmlAttribute(name = "description")
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		@XmlAttribute(name = "dragable")
		public String getDragable() {
			return dragable;
		}
		public void setDragable(String dragable) {
			this.dragable = dragable;
		}
		@XmlAttribute(name = "dynamic_fixedcols")
		public String getDynamic_fixedcols() {
			return dynamic_fixedcols;
		}
		public void setDynamic_fixedcols(String dynamic_fixedcols) {
			this.dynamic_fixedcols = dynamic_fixedcols;
		}
		@XmlAttribute(name = "dynamic_fixedrows")
		public String getDynamic_fixedrows() {
			return dynamic_fixedrows;
		}
		public void setDynamic_fixedrows(String dynamic_fixedrows) {
			this.dynamic_fixedrows = dynamic_fixedrows;
		}
		@XmlAttribute(name = "fixedcols")
		public String getFixedcols() {
			return fixedcols;
		}
		public void setFixedcols(String fixedcols) {
			this.fixedcols = fixedcols;
		}
		@XmlAttribute(name = "fixedrows")
		public String getFixedrows() {
			return fixedrows;
		}
		public void setFixedrows(String fixedrows) {
			this.fixedrows = fixedrows;
		}
		@XmlAttribute(name = "font")
		public String getFont() {
			return font;
		}
		public void setFont(String font) {
			this.font = font;
		}
		@XmlAttribute(name = "gradient_effect")
		public String getGradient_effect() {
			return gradient_effect;
		}
		public void setGradient_effect(String gradient_effect) {
			this.gradient_effect = gradient_effect;
		}
		@XmlAttribute(name = "gradient_endcolor")
		public String getGradient_endcolor() {
			return gradient_endcolor;
		}
		public void setGradient_endcolor(String gradient_endcolor) {
			this.gradient_endcolor = gradient_endcolor;
		}
		@XmlAttribute(name = "gradient_startcolor")
		public String getGradient_startcolor() {
			return gradient_startcolor;
		}
		public void setGradient_startcolor(String gradient_startcolor) {
			this.gradient_startcolor = gradient_startcolor;
		}
		@XmlAttribute(name = "header_clicksort")
		public String getHeader_clicksort() {
			return header_clicksort;
		}
		public void setHeader_clicksort(String header_clicksort) {
			this.header_clicksort = header_clicksort;
		}
		@XmlAttribute(name = "header_height")
		public String getHeader_height() {
			return header_height;
		}
		public void setHeader_height(String header_height) {
			this.header_height = header_height;
		}
		@XmlAttribute(name = "header_horzcount")
		public String getHeader_horzcount() {
			return header_horzcount;
		}
		public void setHeader_horzcount(String header_horzcount) {
			this.header_horzcount = header_horzcount;
		}
		@XmlAttribute(name = "header_style")
		public String getHeader_style() {
			return header_style;
		}
		public void setHeader_style(String header_style) {
			this.header_style = header_style;
		}
		@XmlAttribute(name = "header_vertcount")
		public String getHeader_vertcount() {
			return header_vertcount;
		}
		public void setHeader_vertcount(String header_vertcount) {
			this.header_vertcount = header_vertcount;
		}
		@XmlAttribute(name = "height")
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		@XmlAttribute(name = "horzscrollbar_style")
		public String getHorzscrollbar_style() {
			return horzscrollbar_style;
		}
		public void setHorzscrollbar_style(String horzscrollbar_style) {
			this.horzscrollbar_style = horzscrollbar_style;
		}
		@XmlAttribute(name = "in_index")
		public String getIn_index() {
			return in_index;
		}
		public void setIn_index(String in_index) {
			this.in_index = in_index;
		}
		@XmlAttribute(name = "linenumber_backcolor")
		public String getLinenumber_backcolor() {
			return linenumber_backcolor;
		}
		public void setLinenumber_backcolor(String linenumber_backcolor) {
			this.linenumber_backcolor = linenumber_backcolor;
		}
		@XmlAttribute(name = "linenumber_forecolor")
		public String getLinenumber_forecolor() {
			return linenumber_forecolor;
		}
		public void setLinenumber_forecolor(String linenumber_forecolor) {
			this.linenumber_forecolor = linenumber_forecolor;
		}
		@XmlAttribute(name = "linenumber_show")
		public String getLinenumber_show() {
			return linenumber_show;
		}
		public void setLinenumber_show(String linenumber_show) {
			this.linenumber_show = linenumber_show;
		}
		@XmlAttribute(name = "linenumber_title")
		public String getLinenumber_title() {
			return linenumber_title;
		}
		public void setLinenumber_title(String linenumber_title) {
			this.linenumber_title = linenumber_title;
		}
		@XmlAttribute(name = "link_data")
		public String getLink_data() {
			return link_data;
		}
		public void setLink_data(String link_data) {
			this.link_data = link_data;
		}
		@XmlAttribute(name = "max_addablerows")
		public String getMax_addablerows() {
			return max_addablerows;
		}
		public void setMax_addablerows(String max_addablerows) {
			this.max_addablerows = max_addablerows;
		}
		@XmlAttribute(name = "mid_statistics")
		public String getMid_statistics() {
			return mid_statistics;
		}
		public void setMid_statistics(String mid_statistics) {
			this.mid_statistics = mid_statistics;
		}
		@XmlAttribute(name = "multi_checkrow")
		public String getMulti_checkrow() {
			return multi_checkrow;
		}
		public void setMulti_checkrow(String multi_checkrow) {
			this.multi_checkrow = multi_checkrow;
		}
		@XmlAttribute(name = "multi_select")
		public String getMulti_select() {
			return multi_select;
		}
		public void setMulti_select(String multi_select) {
			this.multi_select = multi_select;
		}
		@XmlAttribute(name = "name")
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@XmlAttribute(name = "on_checkrowclick")
		public String getOn_checkrowclick() {
			return on_checkrowclick;
		}
		public void setOn_checkrowclick(String on_checkrowclick) {
			this.on_checkrowclick = on_checkrowclick;
		}
		@XmlAttribute(name = "on_dropfiles")
		public String getOn_dropfiles() {
			return on_dropfiles;
		}
		public void setOn_dropfiles(String on_dropfiles) {
			this.on_dropfiles = on_dropfiles;
		}
		@XmlAttribute(name = "on_itemclick")
		public String getOn_itemclick() {
			return on_itemclick;
		}
		public void setOn_itemclick(String on_itemclick) {
			this.on_itemclick = on_itemclick;
		}
		@XmlAttribute(name = "on_itemdblclick")
		public String getOn_itemdblclick() {
			return on_itemdblclick;
		}
		public void setOn_itemdblclick(String on_itemdblclick) {
			this.on_itemdblclick = on_itemdblclick;
		}
		@XmlAttribute(name = "on_itemeditcomplete")
		public String getOn_itemeditcomplete() {
			return on_itemeditcomplete;
		}
		public void setOn_itemeditcomplete(String on_itemeditcomplete) {
			this.on_itemeditcomplete = on_itemeditcomplete;
		}
		@XmlAttribute(name = "on_itemselchange")
		public String getOn_itemselchange() {
			return on_itemselchange;
		}
		public void setOn_itemselchange(String on_itemselchange) {
			this.on_itemselchange = on_itemselchange;
		}
		@XmlAttribute(name = "on_keydown")
		public String getOn_keydown() {
			return on_keydown;
		}
		public void setOn_keydown(String on_keydown) {
			this.on_keydown = on_keydown;
		}
		@XmlAttribute(name = "out_index")
		public String getOut_index() {
			return out_index;
		}
		public void setOut_index(String out_index) {
			this.out_index = out_index;
		}
		@XmlAttribute(name = "popupmenu_show")
		public String getPopupmenu_show() {
			return popupmenu_show;
		}
		public void setPopupmenu_show(String popupmenu_show) {
			this.popupmenu_show = popupmenu_show;
		}
		@XmlAttribute(name = "row_addable")
		public String getRow_addable() {
			return row_addable;
		}
		public void setRow_addable(String row_addable) {
			this.row_addable = row_addable;
		}
		@XmlAttribute(name = "row_moveable")
		public String getRow_moveable() {
			return row_moveable;
		}
		public void setRow_moveable(String row_moveable) {
			this.row_moveable = row_moveable;
		}
		@XmlAttribute(name = "samplerows")
		public String getSamplerows() {
			return samplerows;
		}
		public void setSamplerows(String samplerows) {
			this.samplerows = samplerows;
		}
		@XmlAttribute(name = "selectcell_backcolor")
		public String getSelectcell_backcolor() {
			return selectcell_backcolor;
		}
		public void setSelectcell_backcolor(String selectcell_backcolor) {
			this.selectcell_backcolor = selectcell_backcolor;
		}
		@XmlAttribute(name = "selectcell_forecolor")
		public String getSelectcell_forecolor() {
			return selectcell_forecolor;
		}
		public void setSelectcell_forecolor(String selectcell_forecolor) {
			this.selectcell_forecolor = selectcell_forecolor;
		}
		@XmlAttribute(name = "selectrow_backcolor")
		public String getSelectrow_backcolor() {
			return selectrow_backcolor;
		}
		public void setSelectrow_backcolor(String selectrow_backcolor) {
			this.selectrow_backcolor = selectrow_backcolor;
		}
		@XmlAttribute(name = "selectrow_bordercolor")
		public String getSelectrow_bordercolor() {
			return selectrow_bordercolor;
		}
		public void setSelectrow_bordercolor(String selectrow_bordercolor) {
			this.selectrow_bordercolor = selectrow_bordercolor;
		}
		@XmlAttribute(name = "selectrow_style")
		public String getSelectrow_style() {
			return selectrow_style;
		}
		public void setSelectrow_style(String selectrow_style) {
			this.selectrow_style = selectrow_style;
		}
		@XmlAttribute(name = "statistics_column")
		public String getStatistics_column() {
			return statistics_column;
		}
		public void setStatistics_column(String statistics_column) {
			this.statistics_column = statistics_column;
		}
		@XmlAttribute(name = "statistics_row")
		public String getStatistics_row() {
			return statistics_row;
		}
		public void setStatistics_row(String statistics_row) {
			this.statistics_row = statistics_row;
		}
		@XmlAttribute(name = "tabstop")
		public String getTabstop() {
			return tabstop;
		}
		public void setTabstop(String tabstop) {
			this.tabstop = tabstop;
		}
		@XmlAttribute(name = "use_checkrow")
		public String getUse_checkrow() {
			return use_checkrow;
		}
		public void setUse_checkrow(String use_checkrow) {
			this.use_checkrow = use_checkrow;
		}
		@XmlAttribute(name = "use_selectblock")
		public String getUse_selectblock() {
			return use_selectblock;
		}
		public void setUse_selectblock(String use_selectblock) {
			this.use_selectblock = use_selectblock;
		}
		@XmlAttribute(name = "version")
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		@XmlAttribute(name = "vertscrollbar_style")
		public String getVertscrollbar_style() {
			return vertscrollbar_style;
		}
		public void setVertscrollbar_style(String vertscrollbar_style) {
			this.vertscrollbar_style = vertscrollbar_style;
		}
		@XmlAttribute(name = "width")
		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
		@XmlAttribute(name = "x")
		public String getX() {
			return x;
		}
		public void setX(String x) {
			this.x = x;
		}
		@XmlAttribute(name = "y")
		public String getY() {
			return y;
		}
		public void setY(String y) {
			this.y = y;
		}
		@XmlElement(name = "column")
		public List<HanaColumn> getColumnList() {
			return columnList;
		}
		public void setColumnList(List<HanaColumn>columnList) {
			this.columnList = columnList;
		}



}
