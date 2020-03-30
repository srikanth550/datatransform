package com.srikanth.data.transform.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "_id", "description", "geo", "tmc", "roadName", "eventCode", "severity", "validStart", "validEnd",
		"type", "lastUpdated" })
public class Locations {
	
	private String _id;
	
	private String description;
	
	@JsonInclude(Include.NON_NULL)
	private String roadName;
	
	private Integer eventCode;
	
	private Integer severity;
	
	private String validStart;
	
	private String validEnd;
	
	private String type;
	
	private String lastUpdated;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public Integer getEventCode() {
		return eventCode;
	}

	public void setEventCode(Integer eventCode) {
		this.eventCode = eventCode;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public String getValidStart() {
		return validStart;
	}

	public void setValidStart(String validStart) {
		this.validStart = validStart;
	}

	public String getValidEnd() {
		return validEnd;
	}

	public void setValidEnd(String validEnd) {
		this.validEnd = validEnd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	@JsonInclude(Include.NON_NULL)
	private Geo geo;
	
	@JsonInclude(Include.NON_NULL)
	private Tmc tmc;
	
	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public Tmc getTmc() {
		return tmc;
	}

	public void setTmc(Tmc tmc) {
		this.tmc = tmc;
	}

	public class Geo {
		private String type;
		
		private Double[] coordinates;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Double[] getCoordinates() {
			return coordinates;
		}
		public void setCoordinates(Double[] coordinates) {
			this.coordinates = coordinates;
		}
	}
	
	public class Tmc {
		private Integer table;
		private Integer id;
		private String direction;
		
		public Integer getTable() {
			return table;
		}
		public void setTable(Integer table) {
			this.table = table;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
	}
}
