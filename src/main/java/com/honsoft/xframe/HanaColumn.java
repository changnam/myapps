package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "column")
public class HanaColumn {
	private String id;
	private String parent;
	private String row_data;
	private List<HanaData> dataList;
	private List<HanaHeader> headerList;

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

	@XmlAttribute(name = "row")
	public String getRow_data() {
		return row_data;
	}

	public void setRow_data(String row_data) {
		this.row_data = row_data;
	}

	@XmlElement(name = "data")
	public List<HanaData> getDataList() {
		return dataList;
	}

	public void setDataList(List<HanaData> dataList) {
		this.dataList = dataList;
	}

	@XmlElement(name = "header")
	public List<HanaHeader> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<HanaHeader> headerList) {
		this.headerList = headerList;
	}

}
