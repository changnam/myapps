package com.honsoft.xframe;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "screen")
public class HanaScreen {
	private String id;
	private String parent;
	private String back_color;
	private String back_imagesize;
	private String CPI;
	private String directory;
	private String height;
	private String horzscrollbar_style;
	private String last_update_date;
	private String lattice_x;
	private String lattice_y;
	private String LPI;
	private String on_activate;
	private String on_destroy;
	private String on_keydown;
	private String on_load;
	private String on_submitcomplete;
	private String printmediatype;
	private String printscreen;
	private String screen_help;
	private String screen_version;
	private String screenid;
	private String scriptcode;
	private String title;
	private String trancode;
	private String version;
	private String vertscrollbar_style;
	private String width;

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

	@XmlAttribute(name = "back_color")
	public String getBack_color() {
		return back_color;
	}

	public void setBack_color(String back_color) {
		this.back_color = back_color;
	}

	@XmlAttribute(name = "back_imagesize")
	public String getBack_imagesize() {
		return back_imagesize;
	}

	public void setBack_imagesize(String back_imagesize) {
		this.back_imagesize = back_imagesize;
	}

	@XmlAttribute(name = "CPI")
	public String getCPI() {
		return CPI;
	}

	public void setCPI(String CPI) {
		this.CPI = CPI;
	}

	@XmlAttribute(name = "directory")
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	@XmlAttribute(name = "height")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@XmlAttribute(name = "horzscrollbar_style")
	public String getHorzscrollbar_style() {
		return horzscrollbar_style;
	}

	public void setHorzscrollbar_style(String horzscrollbar_style) {
		this.horzscrollbar_style = horzscrollbar_style;
	}

	@XmlAttribute(name = "last_update_date")
	public String getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(String last_update_date) {
		this.last_update_date = last_update_date;
	}

	@XmlAttribute(name = "lattice_x")
	public String getLattice_x() {
		return lattice_x;
	}

	public void setLattice_x(String lattice_x) {
		this.lattice_x = lattice_x;
	}

	@XmlAttribute(name = "lattice_y")
	public String getLattice_y() {
		return lattice_y;
	}

	public void setLattice_y(String lattice_y) {
		this.lattice_y = lattice_y;
	}

	@XmlAttribute(name = "LPI")
	public String getLPI() {
		return LPI;
	}

	public void setLPI(String LPI) {
		this.LPI = LPI;
	}

	@XmlAttribute(name = "on_activate")
	public String getOn_activate() {
		return on_activate;
	}

	public void setOn_activate(String on_activate) {
		this.on_activate = on_activate;
	}

	@XmlAttribute(name = "on_destroy")
	public String getOn_destroy() {
		return on_destroy;
	}

	public void setOn_destroy(String on_destroy) {
		this.on_destroy = on_destroy;
	}

	@XmlAttribute(name = "on_keydown")
	public String getOn_keydown() {
		return on_keydown;
	}

	public void setOn_keydown(String on_keydown) {
		this.on_keydown = on_keydown;
	}

	@XmlAttribute(name = "on_load")
	public String getOn_load() {
		return on_load;
	}

	public void setOn_load(String on_load) {
		this.on_load = on_load;
	}

	@XmlAttribute(name = "on_submitcomplete")
	public String getOn_submitcomplete() {
		return on_submitcomplete;
	}

	public void setOn_submitcomplete(String on_submitcomplete) {
		this.on_submitcomplete = on_submitcomplete;
	}

	@XmlAttribute(name = "printmediatype")
	public String getPrintmediatype() {
		return printmediatype;
	}

	public void setPrintmediatype(String printmediatype) {
		this.printmediatype = printmediatype;
	}

	@XmlAttribute(name = "printscreen")
	public String getPrintscreen() {
		return printscreen;
	}

	public void setPrintscreen(String printscreen) {
		this.printscreen = printscreen;
	}

	@XmlAttribute(name = "screen_help")
	public String getScreen_help() {
		return screen_help;
	}

	public void setScreen_help(String screen_help) {
		this.screen_help = screen_help;
	}

	@XmlAttribute(name = "screen_version")
	public String getScreen_version() {
		return screen_version;
	}

	public void setScreen_version(String screen_version) {
		this.screen_version = screen_version;
	}

	@XmlAttribute(name = "screenid")
	public String getScreenid() {
		return screenid;
	}

	public void setScreenid(String screenid) {
		this.screenid = screenid;
	}

	@XmlAttribute(name = "scriptcode")
	public String getScriptcode() {
		return scriptcode;
	}

	public void setScriptcode(String scriptcode) {
		this.scriptcode = scriptcode;
	}

	@XmlAttribute(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlAttribute(name = "trancode")
	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "vertscrollbar_style")
	public String getVertscrollbar_style() {
		return vertscrollbar_style;
	}

	public void setVertscrollbar_style(String vertscrollbar_style) {
		this.vertscrollbar_style = vertscrollbar_style;
	}

	@XmlAttribute(name = "width")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
