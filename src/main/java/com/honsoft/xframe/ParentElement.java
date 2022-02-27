package com.honsoft.xframe;

public class ParentElement {
	private String parent_name;
	private int parent_id;
	
	public ParentElement(String parent_name, int parent_id) {
		this.parent_name = parent_name;
		this.parent_id = parent_id;
	}
	
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	
	

}
