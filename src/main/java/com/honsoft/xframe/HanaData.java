package com.honsoft.xframe;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class HanaData {

	private String id;
	private String parent;
	private String apply_rightpattern;
	private String backcolor;
	private String cellmerge;
	private String combobox_autocodevalid;
	private String combobox_editable;
	private String combobox_excelstyle;
	private String data_type;
	private String description;
	private String editable;
	private String excel_rw;
	private String forecolor;
	private String hostdata_includedot;
	private String image;
	private String image_horzalign;
	private String imemode;
	private String input_type;
	private String link_data;
	private String max_length;
	private String midstatistics_show;
	private String midstatistics_type;
	private String name;
	private String pattern;
	private String picklist;
	private String picklist_linkdata;
	private String picklist_selstyle;
	private String picklist_viewstyle;
	private String prefilldata;
	private String resizable;
	private String sorting;
	private String statistics_show;
	private String statisticsrow_type;
	private String text_horzalign;
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

	@XmlAttribute(name = "apply_rightpattern")
	public String getApply_rightpattern() {
		return apply_rightpattern;
	}

	public void setApply_rightpattern(String apply_rightpattern) {
		this.apply_rightpattern = apply_rightpattern;
	}

	@XmlAttribute(name = "backcolor")
	public String getBackcolor() {
		return backcolor;
	}

	public void setBackcolor(String backcolor) {
		this.backcolor = backcolor;
	}

	@XmlAttribute(name = "cellmerge")
	public String getCellmerge() {
		return cellmerge;
	}

	public void setCellmerge(String cellmerge) {
		this.cellmerge = cellmerge;
	}

	@XmlAttribute(name = "combobox_autocodevalid")
	public String getCombobox_autocodevalid() {
		return combobox_autocodevalid;
	}

	public void setCombobox_autocodevalid(String combobox_autocodevalid) {
		this.combobox_autocodevalid = combobox_autocodevalid;
	}

	@XmlAttribute(name = "combobox_editable")
	public String getCombobox_editable() {
		return combobox_editable;
	}

	public void setCombobox_editable(String combobox_editable) {
		this.combobox_editable = combobox_editable;
	}

	@XmlAttribute(name = "combobox_excelstyle")
	public String getCombobox_excelstyle() {
		return combobox_excelstyle;
	}

	public void setCombobox_excelstyle(String combobox_excelstyle) {
		this.combobox_excelstyle = combobox_excelstyle;
	}

	@XmlAttribute(name = "data_type")
	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	@XmlAttribute(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute(name = "editable")
	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	@XmlAttribute(name = "excel_rw")
	public String getExcel_rw() {
		return excel_rw;
	}

	public void setExcel_rw(String excel_rw) {
		this.excel_rw = excel_rw;
	}

	@XmlAttribute(name = "forecolor")
	public String getForecolor() {
		return forecolor;
	}

	public void setForecolor(String forecolor) {
		this.forecolor = forecolor;
	}

	@XmlAttribute(name = "hostdata_includedot")
	public String getHostdata_includedot() {
		return hostdata_includedot;
	}

	public void setHostdata_includedot(String hostdata_includedot) {
		this.hostdata_includedot = hostdata_includedot;
	}

	@XmlAttribute(name = "image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@XmlAttribute(name = "image_horzalign")
	public String getImage_horzalign() {
		return image_horzalign;
	}

	public void setImage_horzalign(String image_horzalign) {
		this.image_horzalign = image_horzalign;
	}

	@XmlAttribute(name = "imemode")
	public String getImemode() {
		return imemode;
	}

	public void setImemode(String imemode) {
		this.imemode = imemode;
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

	@XmlAttribute(name = "midstatistics_show")
	public String getMidstatistics_show() {
		return midstatistics_show;
	}

	public void setMidstatistics_show(String midstatistics_show) {
		this.midstatistics_show = midstatistics_show;
	}

	@XmlAttribute(name = "midstatistics_type")
	public String getMidstatistics_type() {
		return midstatistics_type;
	}

	public void setMidstatistics_type(String midstatistics_type) {
		this.midstatistics_type = midstatistics_type;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "pattern")
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@XmlAttribute(name = "picklist")
	public String getPicklist() {
		return picklist;
	}

	public void setPicklist(String picklist) {
		this.picklist = picklist;
	}

	@XmlAttribute(name = "picklist_linkdata")
	public String getPicklist_linkdata() {
		return picklist_linkdata;
	}

	public void setPicklist_linkdata(String picklist_linkdata) {
		this.picklist_linkdata = picklist_linkdata;
	}

	@XmlAttribute(name = "picklist_selstyle")
	public String getPicklist_selstyle() {
		return picklist_selstyle;
	}

	public void setPicklist_selstyle(String picklist_selstyle) {
		this.picklist_selstyle = picklist_selstyle;
	}

	@XmlAttribute(name = "picklist_viewstyle")
	public String getPicklist_viewstyle() {
		return picklist_viewstyle;
	}

	public void setPicklist_viewstyle(String picklist_viewstyle) {
		this.picklist_viewstyle = picklist_viewstyle;
	}

	@XmlAttribute(name = "prefilldata")
	public String getPrefilldata() {
		return prefilldata;
	}

	public void setPrefilldata(String prefilldata) {
		this.prefilldata = prefilldata;
	}

	@XmlAttribute(name = "resizable")
	public String getResizable() {
		return resizable;
	}

	public void setResizable(String resizable) {
		this.resizable = resizable;
	}

	@XmlAttribute(name = "sorting")
	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	@XmlAttribute(name = "statistics_show")
	public String getStatistics_show() {
		return statistics_show;
	}

	public void setStatistics_show(String statistics_show) {
		this.statistics_show = statistics_show;
	}

	@XmlAttribute(name = "statisticsrow_type")
	public String getStatisticsrow_type() {
		return statisticsrow_type;
	}

	public void setStatisticsrow_type(String statisticsrow_type) {
		this.statisticsrow_type = statisticsrow_type;
	}

	@XmlAttribute(name = "text_horzalign")
	public String getText_horzalign() {
		return text_horzalign;
	}

	public void setText_horzalign(String text_horzalign) {
		this.text_horzalign = text_horzalign;
	}

	@XmlAttribute(name = "width")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
