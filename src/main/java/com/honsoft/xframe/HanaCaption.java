package com.honsoft.xframe;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "caption")
public class HanaCaption {
	private String id;
	private String parent;
	private String classid;
	private String control_id;
	private String DayClick;
	private String designtime_create;
	private String height;
	private String MemoClick;
	private String name;
	private String onselectdate;
	private String version;
	private String width;
	private String x;
	private String y;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@XmlAttribute(name = "classid")
	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	@XmlAttribute(name = "control_id")
	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	@XmlAttribute(name = "DayClick")
	public String getDayClick() {
		return DayClick;
	}

	public void setDayClick(String DayClick) {
		this.DayClick = DayClick;
	}

	@XmlAttribute(name = "designtime_create")
	public String getDesigntime_create() {
		return designtime_create;
	}

	public void setDesigntime_create(String designtime_create) {
		this.designtime_create = designtime_create;
	}

	@XmlAttribute(name = "height")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@XmlAttribute(name = "MemoClick")
	public String getMemoClick() {
		return MemoClick;
	}

	public void setMemoClick(String MemoClick) {
		this.MemoClick = MemoClick;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "onselectdate")
	public String getOnselectdate() {
		return onselectdate;
	}

	public void setOnselectdate(String onselectdate) {
		this.onselectdate = onselectdate;
	}

	@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}
