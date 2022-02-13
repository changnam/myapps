package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "panel")
public class HanaPanel {
	private String id;
	private String parent;
	private String back_color;
	private String border;
	private String control_id;
	private String height;
	private String name;
	private String width;
	private String x;
	private String y;
	private List<HanaGrid> gridList;
	private List<HanaHangul_field> hangul_fieldList;
	private List<HanaImage> imageList;
	private List<HanaMultiline> multilineList;
	private List<HanaNumericex_field> numericex_fieldList;
	private List<HanaPushbutton> pushbuttonList;
	private List<HanaTab_order> tab_orderList;
	private List<HanaTable> tableList;
	private List<HanaText> textList;

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
		@XmlAttribute(name = "control_id")
		public String getControl_id() {
			return control_id;
		}
		public void setControl_id(String control_id) {
			this.control_id = control_id;
		}
		@XmlAttribute(name = "height")
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		@XmlAttribute(name = "name")
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		@XmlElement(name = "grid")
		public List<HanaGrid> getGridList() {
			return gridList;
		}
		public void setGridList(List<HanaGrid>gridList) {
			this.gridList = gridList;
		}
		@XmlElement(name = "hangul_field")
		public List<HanaHangul_field> getHangul_fieldList() {
			return hangul_fieldList;
		}
		public void setHangul_fieldList(List<HanaHangul_field>hangul_fieldList) {
			this.hangul_fieldList = hangul_fieldList;
		}
		@XmlElement(name = "image")
		public List<HanaImage> getImageList() {
			return imageList;
		}
		public void setImageList(List<HanaImage>imageList) {
			this.imageList = imageList;
		}
		@XmlElement(name = "multiline")
		public List<HanaMultiline> getMultilineList() {
			return multilineList;
		}
		public void setMultilineList(List<HanaMultiline>multilineList) {
			this.multilineList = multilineList;
		}
		@XmlElement(name = "numericex_field")
		public List<HanaNumericex_field> getNumericex_fieldList() {
			return numericex_fieldList;
		}
		public void setNumericex_fieldList(List<HanaNumericex_field>numericex_fieldList) {
			this.numericex_fieldList = numericex_fieldList;
		}
		@XmlElement(name = "pushbutton")
		public List<HanaPushbutton> getPushbuttonList() {
			return pushbuttonList;
		}
		public void setPushbuttonList(List<HanaPushbutton>pushbuttonList) {
			this.pushbuttonList = pushbuttonList;
		}
		@XmlElement(name = "tab_order")
		public List<HanaTab_order> getTab_orderList() {
			return tab_orderList;
		}
		public void setTab_orderList(List<HanaTab_order>tab_orderList) {
			this.tab_orderList = tab_orderList;
		}
		@XmlElement(name = "table")
		public List<HanaTable> getTableList() {
			return tableList;
		}
		public void setTableList(List<HanaTable>tableList) {
			this.tableList = tableList;
		}
		@XmlElement(name = "text")
		public List<HanaText> getTextList() {
			return textList;
		}
		public void setTextList(List<HanaText>textList) {
			this.textList = textList;
		}

}
