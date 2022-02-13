package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hangul_field")
public class HanaHangul_field {
	private String id;
	private String parent;
	private String apply_inputtypecolor;
	private String autosize;
	private String back_color;
	private String border_color;
	private String border_color_focus;
	private String control_id;
	private String default_value;
	private String description;
	private String enable;
	private String font;
	private String fore_color;
	private String height;
	private String hidden;
	private String in_index;
	private String input_type;
	private String link_data;
	private String max_length;
	private String mousehover_forecolor;
	private String name;
	private String on_focusin;
	private String on_keydown;
	private String tooltip;
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

	@XmlAttribute(name = "border_color")
	public String getBorder_color() {
		return border_color;
	}

	public void setBorder_color(String border_color) {
		this.border_color = border_color;
	}

	@XmlAttribute(name = "border_color_focus")
	public String getBorder_color_focus() {
		return border_color_focus;
	}

	public void setBorder_color_focus(String border_color_focus) {
		this.border_color_focus = border_color_focus;
	}

	@XmlAttribute(name = "control_id")
	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	@XmlAttribute(name = "default_value")
	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	@XmlAttribute(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute(name = "enable")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
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

	@XmlAttribute(name = "height")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@XmlAttribute(name = "hidden")
	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
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

	@XmlAttribute(name = "link_data")
	public String getLink_data() {
		return link_data;
	}

	public void setLink_data(String link_data) {
		this.link_data = link_data;
	}

	@XmlAttribute(name = "max_length")
	public String getMax_length() {
		return max_length;
	}

	public void setMax_length(String max_length) {
		this.max_length = max_length;
	}

	@XmlAttribute(name = "mousehover_forecolor")
	public String getMousehover_forecolor() {
		return mousehover_forecolor;
	}

	public void setMousehover_forecolor(String mousehover_forecolor) {
		this.mousehover_forecolor = mousehover_forecolor;
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

	@XmlAttribute(name = "on_keydown")
	public String getOn_keydown() {
		return on_keydown;
	}

	public void setOn_keydown(String on_keydown) {
		this.on_keydown = on_keydown;
	}

	@XmlAttribute(name = "tooltip")
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
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
