package de.crowdcode.jpa.visitor;

import javax.persistence.Entity;

import de.crowdcode.jpa.vehicles.AbstractEntity;

@Entity
public abstract class Akteur extends AbstractEntity {

	
	public abstract Orga getOrganisation();
	
	public abstract void accept(AkteurVisitor visitor);
	
	
}
