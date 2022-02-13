package com.honsoft.xframe;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "text")
public class HanaText {
	private String id;
	private String parent;
	private String autosize;
	private String back_color;
	private String border;
	private String border_color;
	private String control_id;
	private String font;
	private String fore_color;
	private String gradient_effect;
	private String gradient_endcolor;
	private String gradient_startcolor;
	private String height;
	private String hidden;
	private String horz_align;
	private String image;
	private String image_position;
	private String mousehover;
	private String mousehover_backcolor;
	private String mousehover_color;
	private String mousehover_forecolor;
	private String mousehover_gradientendcolor;
	private String mousehover_gradientstartcolor;
	private String name;
	private String on_click;
	private String text;
	private String tooltip;
	private String version;
	private String vert_align;
	private String width;
	private String x;
	private String y;

	   public String getId(){
	     return id;
	   }
	   public void setId(String id){
	     this.id = id;
	   }
	   public String getParent(){
	     return parent;
	   }
	   public void setParent(String parent){
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
		@XmlAttribute(name = "image")
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		@XmlAttribute(name = "image_position")
		public String getImage_position() {
			return image_position;
		}
		public void setImage_position(String image_position) {
			this.image_position = image_position;
		}
		@XmlAttribute(name = "mousehover")
		public String getMousehover() {
			return mousehover;
		}
		public void setMousehover(String mousehover) {
			this.mousehover = mousehover;
		}
		@XmlAttribute(name = "mousehover_backcolor")
		public String getMousehover_backcolor() {
			return mousehover_backcolor;
		}
		public void setMousehover_backcolor(String mousehover_backcolor) {
			this.mousehover_backcolor = mousehover_backcolor;
		}
		@XmlAttribute(name = "mousehover_color")
		public String getMousehover_color() {
			return mousehover_color;
		}
		public void setMousehover_color(String mousehover_color) {
			this.mousehover_color = mousehover_color;
		}
		@XmlAttribute(name = "mousehover_forecolor")
		public String getMousehover_forecolor() {
			return mousehover_forecolor;
		}
		public void setMousehover_forecolor(String mousehover_forecolor) {
			this.mousehover_forecolor = mousehover_forecolor;
		}
		@XmlAttribute(name = "mousehover_gradientendcolor")
		public String getMousehover_gradientendcolor() {
			return mousehover_gradientendcolor;
		}
		public void setMousehover_gradientendcolor(String mousehover_gradientendcolor) {
			this.mousehover_gradientendcolor = mousehover_gradientendcolor;
		}
		@XmlAttribute(name = "mousehover_gradientstartcolor")
		public String getMousehover_gradientstartcolor() {
			return mousehover_gradientstartcolor;
		}
		public void setMousehover_gradientstartcolor(String mousehover_gradientstartcolor) {
			this.mousehover_gradientstartcolor = mousehover_gradientstartcolor;
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
