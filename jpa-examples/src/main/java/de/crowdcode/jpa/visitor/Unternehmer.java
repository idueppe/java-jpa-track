package de.crowdcode.jpa.visitor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Unternehmer extends Akteur {

	@ManyToOne
	private Orga orga;
	
	public Unternehmer() {
	}
	
	public Unternehmer(Orga orga) {
		this.orga = orga;
	}

	@Override
	public Orga getOrganisation() {
		return orga;
	}
	
	@Override
	public void accept(AkteurVisitor visitor) {
		visitor.visit(this);
	}
	
}
