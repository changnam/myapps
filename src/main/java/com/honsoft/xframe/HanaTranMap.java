package com.honsoft.xframe;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tranmap")
public class HanaTranMap {
	private String id;
	private String parent;
	private String defaultmap;
	private String fcode;
	private String mapinfo;
	private String type;

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
		@XmlAttribute(name = "defaultmap")
		public String getDefaultmap() {
			return defaultmap;
		}
		public void setDefaultmap(String defaultmap) {
			this.defaultmap = defaultmap;
		}
		@XmlAttribute(name = "fcode")
		public String getFcode() {
			return fcode;
		}
		public void setFcode(String fcode) {
			this.fcode = fcode;
		}
		@XmlAttribute(name = "mapinfo")
		public String getMapinfo() {
			return mapinfo;
		}
		public void setMapinfo(String mapinfo) {
			this.mapinfo = mapinfo;
		}
		@XmlAttribute(name = "type")
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}


}
