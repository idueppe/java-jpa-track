package de.crowdcode.jpa.vehicles;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Vehicle extends AbstractEntity {

	@ManyToOne
	private Manufacturer manufacturer;
	
	@Column(length=20)
	private String name;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="vehicle_id")
	private Engine engine;
	
	@OneToMany(cascade={CascadeType.ALL})
	private List<Price> prices = new LinkedList<>();
	
	@OneToOne
	private Price currentPrice;
	
	public Vehicle() {}

	public Vehicle(Manufacturer manufacturer, String name, Engine engine, Price currentPrice) {
		super();
		this.manufacturer = manufacturer;
		manufacturer.getVehicles().add(this);
		this.name = name;
		this.engine = engine;
		this.currentPrice = currentPrice;
		prices.add(currentPrice);
	}

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
