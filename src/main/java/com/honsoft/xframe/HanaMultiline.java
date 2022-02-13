package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "multiline")
public class HanaMultiline {
	private String id;
	private String parent;
	private String apply_inputtypecolor;
	private String back_color;
	private String border;
	private String border_color;
	private String control_id;
	private String font;
	private String fore_color;
	private String full_shape;
	private String hangul;
	private String height;
	private String in_index;
	private String input_type;
	private String line_gap;
	private String max_length;
	private String name;
	private String on_focusin;
	private String on_focusout;
	private String show_horzscroll;
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

	@XmlAttribute(name = "apply_inputtypecolor")
	public String getApply_inputtypecolor() {
		return apply_inputtypecolor;
	}

	public void setApply_inputtypecolor(String apply_inputtypecolor) {
		this.apply_inputtypecolor = apply_inputtypecolor;
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

	@XmlAttribute(name = "font")
	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	@XmlAttribute(name = "fore_color")
	public String getFore_color() {
		return fore_color;
	}

	public void setFore_color(String fore_color) {
		this.fore_color = fore_color;
	}

	@XmlAttribute(name = "full_shape")
	public String getFull_shape() {
		return full_shape;
	}

	public void setFull_shape(String full_shape) {
		this.full_shape = full_shape;
	}

	@XmlAttribute(name = "hangul")
	public String getHangul() {
		return hangul;
	}

	public void setHangul(String hangul) {
		this.hangul = hangul;
	}

	@XmlAttribute(name = "height")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@XmlAttribute(name = "in_index")
	public String getIn_index() {
		return in_index;
	}

	public void setIn_index(String in_index) {
		this.in_index = in_index;
	}

	@XmlAttribute(name = "input_type")
	public String getInput_type() {
		return input_type;
	}

	public void setInput_type(String input_type) {
		this.input_type = input_type;
	}

	@XmlAttribute(name = "line_gap")
	public String getLine_gap() {
		return line_gap;
	}

	public void setLine_gap(String line_gap) {
		this.line_gap = line_gap;
	}

	@XmlAttribute(name = "max_length")
	public String getMax_length() {
		return max_length;
	}

	public void setMax_length(String max_length) {
		this.max_length = max_length;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "on_focusin")
	public String getOn_focusin() {
		return on_focusin;
	}

	public void setOn_focusin(String on_focusin) {
		this.on_focusin = on_focusin;
	}

	@XmlAttribute(name = "on_focusout")
	public String getOn_focusout() {
		return on_focusout;
	}

	public void setOn_focusout(String on_focusout) {
		this.on_focusout = on_focusout;
	}

	@XmlAttribute(name = "show_horzscroll")
	public String getShow_horzscroll() {
		return show_horzscroll;
	}

	public void setShow_horzscroll(String show_horzscroll) {
		this.show_horzscroll = show_horzscroll;
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
