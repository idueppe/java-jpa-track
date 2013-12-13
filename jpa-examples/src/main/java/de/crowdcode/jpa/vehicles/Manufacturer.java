package de.crowdcode.jpa.vehicles;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import de.crowdcode.jpa.common.AbstractEntity;

@Entity
@XmlRootElement
@XmlType(name="manufacturer",namespace="http://vehicle")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Manufacturer extends AbstractEntity {
	
	@Column(length=20)
	private String name;
	
	@OneToMany(mappedBy="manufacturer", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List<Vehicle> vehicles = new LinkedList<>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@XmlTransient
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
		for (Vehicle vehicle : vehicles)
			vehicle.setManufacturer(this);
	}

	@XmlTransient
	public Vehicle getLatestVehicle() {
		return latestVehicle;
	}

	public void setLatestVehicle(Vehicle latestVehicle) {
		this.latestVehicle = latestVehicle;
	}
	

}
