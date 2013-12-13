package de.crowdcode.jpa.companies;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.crowdcode.jpa.common.AbstractEntity;

@Entity
public class Organisation extends AbstractEntity {
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Company> allCompanies = new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Product> products = new ArrayList<>();
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Company> getAllCompanies() {
		return allCompanies;
	}

	public void setAllCompanies(List<Company> companies) {
		this.allCompanies = companies;
	}

	@Override
	public String toString() {
		return "Organisation [allCompanies=" + allCompanies + ", products="
				+ products + "]";
	}

}
