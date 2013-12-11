package de.crowdcode.jpa.vehicles;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Customer extends AbstractEntity {

	private String name;

	@Embedded
	@AttributeOverrides(value = { @AttributeOverride(name="street", column=@Column(name="strasse")) })
	private Address address ;

	public Customer() {
		address = new Address();
	}

	public Customer(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getStreet() {
		return address.getStreet();
	}

	public void setStreet(String street) {
		address.setStreet(street);
	}

	public String getHouseNo() {
		return address.getHouseNo();
	}

	public void setHouseNo(String houseNo) {
		address.setHouseNo(houseNo);
	}

	public String getZip() {
		return address.getZip();
	}

	public void setZip(String zip) {
		address.setZip(zip);
	}

	public String getCity() {
		return address.getCity();
	}

	public void setCity(String city) {
		address.setCity(city);
	}

	public int hashCode() {
		return address.hashCode();
	}

	public boolean equals(Object obj) {
		return address.equals(obj);
	}

	public String toString() {
		return address.toString();
	}

}
