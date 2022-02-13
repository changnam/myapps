package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "map")
public class HanaMap {
	private String id;
	private String parent;
	private List<HanaActivex> activexList;
	private List<HanaGrid> gridList;
	private List<HanaScreen> screenList;
	private List<HanaPushbutton> pushbuttonList;
	private List<HanaImage> imageList;
	private List<HanaPanel> panelList;

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

	@XmlElement(name = "activex")
	public List<HanaActivex> getActivexList() {
		return activexList;
	}

	public void setActivexList(List<HanaActivex> activexList) {
		this.activexList = activexList;
	}

	@XmlElement(name = "grid")
	public List<HanaGrid> getGridList() {
		return gridList;
	}

	public void setGridList(List<HanaGrid> gridList) {
		this.gridList = gridList;
	}

	@XmlElement(name = "screen")
	public List<HanaScreen> getScreenList() {
		return screenList;
	}

	public void setScreenList(List<HanaScreen> screenList) {
		this.screenList = screenList;
	}

	@XmlElement(name = "pushbutton")
	public List<HanaPushbutton> getPushbuttonList() {
		return pushbuttonList;
	}

	public void setPushbuttonList(List<HanaPushbutton> pushbuttonList) {
		this.pushbuttonList = pushbuttonList;
	}

	@XmlElement(name = "image")
	public List<HanaImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<HanaImage> imageList) {
		this.imageList = imageList;
	}

	@XmlElement(name = "panel")
	public List<HanaPanel> getPanelList() {
		return panelList;
	}

	public void setPanelList(List<HanaPanel> panelList) {
		this.panelList = panelList;
	}
	
	
}
