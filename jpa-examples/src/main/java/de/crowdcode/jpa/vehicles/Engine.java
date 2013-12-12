package de.crowdcode.jpa.vehicles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlType()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Engine extends AbstractEntity {

	@Column(length=20)
 	private String name = "default";
	
	private double ps;

	@Enumerated(EnumType.STRING)
	@Column(length=10)
	private EngineType engineType;

	public Engine() {
	}

	public Engine(String name, double ps, EngineType engineType) {
		super();
		this.name = name;
		this.ps = ps;
		this.engineType = engineType;
	}

	public double getPs() {
		return ps;
	}

	public void setPs(double ps) {
		this.ps = ps;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngine(EngineType engineType) {
		this.engineType = engineType;
	}
}
