package com.honsoft.xframe;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "header")
public class HanaHeader {

	private String id;
	private String parent;
	private String backcolor;
	private String forecolor;
	private String title;
	private String vert_align;

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

	@XmlAttribute(name = "backcolor")
	public String getBackcolor() {
		return backcolor;
	}

	public void setBackcolor(String backcolor) {
		this.backcolor = backcolor;
	}

	@XmlAttribute(name = "forecolor")
	public String getForecolor() {
		return forecolor;
	}

	public void setForecolor(String forecolor) {
		this.forecolor = forecolor;
	}

	@XmlAttribute(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlAttribute(name = "vert_align")
	public String getVert_align() {
		return vert_align;
	}

	public void setVert_align(String vert_align) {
		this.vert_align = vert_align;
	}

}
