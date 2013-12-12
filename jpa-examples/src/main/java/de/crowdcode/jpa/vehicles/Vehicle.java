package de.crowdcode.jpa.vehicles;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Vehicle extends AbstractEntity {

	@ManyToOne(fetch=FetchType.LAZY)
	private Manufacturer manufacturer;
	
	@Column(length=20)
	private String name;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY)
	@JoinColumn(name="vehicle_id")
	private Engine engine;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@OrderBy("validFrom DESC")
	private List<Price> prices = new LinkedList<>();
	
	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Price currentPrice;
	
	public Vehicle() {}

	public Vehicle(Manufacturer manufacturer, String name, Engine engine, Price currentPrice) {
		super();
		this.manufacturer = manufacturer;
		manufacturer.getVehicles().add(this);
		manufacturer.setLatestVehicle(this);
		this.name = name;
		this.engine = engine;
		this.currentPrice = currentPrice;
		prices.add(currentPrice);
	}

	@XmlTransient
	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public List<Price> getPrice() {
		return prices;
	}

	public void setPrice(List<Price> price) {
		this.prices = price;
	}
	
	
}
