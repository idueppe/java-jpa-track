package de.crowdcode.jpa.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Orga extends Akteur {
	
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Data> data = new ArrayList<>();

	public Orga() {
	}
	
	public Orga(Data... data)
	{
		this.data.addAll(Arrays.asList(data));
	}
	
	@Override
	public Orga getOrganisation() {
		return getSelf(this);
	}
	
	@Override
	public void accept(AkteurVisitor visitor) {
		visitor.visit(this);
	}
	
	private Orga getSelf(Orga orga)
	{
		return orga;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public List<Data> getData() {
		return data;
	}



	public void setData(List<Data> data) {
		this.data = data;
	}

}
