package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tab_order")
public class HanaTab_order {
	private String id;
	private String parent;
	private String order_info;
	private String order_option;

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

	@XmlAttribute(name = "order_info")
	public String getOrder_info() {
		return order_info;
	}

	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}

	@XmlAttribute(name = "order_option")
	public String getOrder_option() {
		return order_option;
	}

	public void setOrder_option(String order_option) {
		this.order_option = order_option;
	}

}
