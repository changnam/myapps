package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pushbutton")
public class HanaPushbutton {
	private String id;
	private String parent;
	private String arrayindex;
	private String back_color;
	private String border;
	private String border_color;
	private String click_setfocus;
	private String control_id;
	private String description;
	private String drawfocusrect;
	private String enable;
	private String font;
	private String fore_color;
	private String gradient_effect;
	private String gradient_endcolor;
	private String gradient_startcolor;
	private String height;
	private String hidden;
	private String horz_align;
	private String image_disable;
	private String image_down;
	private String image_fillstyle;
	private String image_focus;
	private String image_hover;
	private String image_normal;
	private String mouse_cursor;
	private String name;
	private String on_mousedown;
	private String on_mousein;
	private String on_mouseup;
	private String on_rdblclick;
	private String push_state;
	private String tabstop;
	private String text;
	private String tooltip;
	private String version;
	private String vert_align;
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

	@XmlAttribute(name = "arrayindex")
	public String getArrayindex() {
		return arrayindex;
	}

	public void setArrayindex(String arrayindex) {
		this.arrayindex = arrayindex;
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

	@XmlAttribute(name = "click_setfocus")
	public String getClick_setfocus() {
		return click_setfocus;
	}

	public void setClick_setfocus(String click_setfocus) {
		this.click_setfocus = click_setfocus;
	}

	@XmlAttribute(name = "control_id")
	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	@XmlAttribute(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute(name = "drawfocusrect")
	public String getDrawfocusrect() {
		return drawfocusrect;
	}

	public void setDrawfocusrect(String drawfocusrect) {
		this.drawfocusrect = drawfocusrect;
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

	@XmlAttribute(name = "gradient_effect")
	public String getGradient_effect() {
		return gradient_effect;
	}

	public void setGradient_effect(String gradient_effect) {
		this.gradient_effect = gradient_effect;
	}

	@XmlAttribute(name = "gradient_endcolor")
	public String getGradient_endcolor() {
		return gradient_endcolor;
	}

	public void setGradient_endcolor(String gradient_endcolor) {
		this.gradient_endcolor = gradient_endcolor;
	}

	@XmlAttribute(name = "gradient_startcolor")
	public String getGradient_startcolor() {
		return gradient_startcolor;
	}

	public void setGradient_startcolor(String gradient_startcolor) {
		this.gradient_startcolor = gradient_startcolor;
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

	@XmlAttribute(name = "horz_align")
	public String getHorz_align() {
		return horz_align;
	}

	public void setHorz_align(String horz_align) {
		this.horz_align = horz_align;
	}

	@XmlAttribute(name = "image_disable")
	public String getImage_disable() {
		return image_disable;
	}

	public void setImage_disable(String image_disable) {
		this.image_disable = image_disable;
	}

	@XmlAttribute(name = "image_down")
	public String getImage_down() {
		return image_down;
	}

	public void setImage_down(String image_down) {
		this.image_down = image_down;
	}

	@XmlAttribute(name = "image_fillstyle")
	public String getImage_fillstyle() {
		return image_fillstyle;
	}

	public void setImage_fillstyle(String image_fillstyle) {
		this.image_fillstyle = image_fillstyle;
	}

	@XmlAttribute(name = "image_focus")
	public String getImage_focus() {
		return image_focus;
	}

	public void setImage_focus(String image_focus) {
		this.image_focus = image_focus;
	}

	@XmlAttribute(name = "image_hover")
	public String getImage_hover() {
		return image_hover;
	}

	public void setImage_hover(String image_hover) {
		this.image_hover = image_hover;
	}

	@XmlAttribute(name = "image_normal")
	public String getImage_normal() {
		return image_normal;
	}

	public void setImage_normal(String image_normal) {
		this.image_normal = image_normal;
	}

	@XmlAttribute(name = "mouse_cursor")
	public String getMouse_cursor() {
		return mouse_cursor;
	}

	public void setMouse_cursor(String mouse_cursor) {
		this.mouse_cursor = mouse_cursor;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "on_mousedown")
	public String getOn_mousedown() {
		return on_mousedown;
	}

	public void setOn_mousedown(String on_mousedown) {
		this.on_mousedown = on_mousedown;
	}

	@XmlAttribute(name = "on_mousein")
	public String getOn_mousein() {
		return on_mousein;
	}

	public void setOn_mousein(String on_mousein) {
		this.on_mousein = on_mousein;
	}

	@XmlAttribute(name = "on_mouseup")
	public String getOn_mouseup() {
		return on_mouseup;
	}

	public void setOn_mouseup(String on_mouseup) {
		this.on_mouseup = on_mouseup;
	}

	@XmlAttribute(name = "on_rdblclick")
	public String getOn_rdblclick() {
		return on_rdblclick;
	}

	public void setOn_rdblclick(String on_rdblclick) {
		this.on_rdblclick = on_rdblclick;
	}

	@XmlAttribute(name = "push_state")
	public String getPush_state() {
		return push_state;
	}

	public void setPush_state(String push_state) {
		this.push_state = push_state;
	}

	@XmlAttribute(name = "tabstop")
	public String getTabstop() {
		return tabstop;
	}

	public void setTabstop(String tabstop) {
		this.tabstop = tabstop;
	}

	@XmlAttribute(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@XmlAttribute(name = "tooltip")
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "vert_align")
	public String getVert_align() {
		return vert_align;
	}

	public void setVert_align(String vert_align) {
		this.vert_align = vert_align;
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
