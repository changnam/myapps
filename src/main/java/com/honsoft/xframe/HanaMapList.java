package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "map_list")
public class HanaMapList {
	private String id;
	private List<HanaMap> hanaMapList;

	@XmlElement(name = "map")
	public List<HanaMap> getHanaMapList() {
		return hanaMapList;
	}

	public void setHanaMapList(List<HanaMap> hanaMapList) {
		this.hanaMapList = hanaMapList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
