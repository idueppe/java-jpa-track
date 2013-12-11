package de.crowdcode.jpa.vehicles;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Manufacturer extends AbstractEntity {
	
	@Column(length=20)
	private String name;
	
	@OneToMany(mappedBy="manufacturer", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List<Vehicle> vehicles = new LinkedList<>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Vehicle latestVehicle;
	
	public Manufacturer() {
	}
	
	public Manufacturer(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Vehicle getLatestVehicle() {
		return latestVehicle;
	}

	public void setLatestVehicle(Vehicle latestVehicle) {
		this.latestVehicle = latestVehicle;
	}
	

}
