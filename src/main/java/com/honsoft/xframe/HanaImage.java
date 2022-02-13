package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "image")
public class HanaImage {
	private String id;
	private String parent;
	private String autosize;
	private String back_color;
	private String border;
	private String border_color;
	private String control_id;
	private String height;
	private String image;
	private String image_fillstyle;
	private String imagesize;
	private String name;
	private String on_click;
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

	@XmlAttribute(name = "autosize")
	public String getAutosize() {
		return autosize;
	}

	public void setAutosize(String autosize) {
		this.autosize = autosize;
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

	@XmlAttribute(name = "image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@XmlAttribute(name = "image_fillstyle")
	public String getImage_fillstyle() {
		return image_fillstyle;
	}

	public void setImage_fillstyle(String image_fillstyle) {
		this.image_fillstyle = image_fillstyle;
	}

	@XmlAttribute(name = "imagesize")
	public String getImagesize() {
		return imagesize;
	}

	public void setImagesize(String imagesize) {
		this.imagesize = imagesize;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "on_click")
	public String getOn_click() {
		return on_click;
	}

	public void setOn_click(String on_click) {
		this.on_click = on_click;
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
