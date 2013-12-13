package de.crowdcode.jpa.companies;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.crowdcode.jpa.common.AbstractEntity;

@Entity
public class Company extends AbstractEntity {

	private String name;
	
	@OneToMany(mappedBy="company")
//	@OneToMany()
//	@JoinColumn(name="company_id")
	private List<Product> products = new LinkedList<>();
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Company() {}
	
	public Company(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + getId() + ", name=" + name + ", products="
				+ products + "]";
	}
	
	
	
}
