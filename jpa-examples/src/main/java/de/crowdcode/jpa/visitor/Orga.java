package de.crowdcode.jpa.visitor;

import javax.persistence.Entity;

@Entity
public class Orga extends Akteur {

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

}
