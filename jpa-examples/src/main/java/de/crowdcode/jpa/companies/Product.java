package de.crowdcode.jpa.companies;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "name_company", columnNames = {
		"name", "company_id" }) })
public class Product extends AbstractProduct {


	private String name;

	@ManyToOne
	@JoinColumn(name="company_id", updatable=false) //, nullable=true, insertable=false, updatable=false)
	private Company company;

	public Product() {
	}

	public Product(String name, Company company) {
		super();
		this.name = name;
		company.getProducts().add(this);
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Product [id=" + getId() + ", name=" + name + "]";
	}

}
