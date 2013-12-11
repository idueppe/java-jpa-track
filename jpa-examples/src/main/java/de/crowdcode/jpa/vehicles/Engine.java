package de.crowdcode.jpa.vehicles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Engine extends AbstractEntity {

	@Column(length=20)
	private String name;
	
	private double ps;

	@Enumerated(EnumType.STRING)
	private EngineType engine;

	public Engine() {
	}

	public Engine(String name, double ps, EngineType engine) {
		super();
		this.name = name;
		this.ps = ps;
		this.engine = engine;
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

	public EngineType getEngine() {
		return engine;
	}

	public void setEngine(EngineType engine) {
		this.engine = engine;
	}
}
