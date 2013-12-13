package de.crowdcode.jpa.visitor;

import java.util.Stack;

import javax.persistence.Entity;

import de.crowdcode.jpa.common.AbstractEntity;

@Entity
public abstract class Akteur extends AbstractEntity {

	
	public abstract Orga getOrganisation();
	
	public abstract void accept(AkteurVisitor visitor);
	
	public Stack<Orga> getVisitorOrga()
	{
		// just need a final transport box from inner class to outer class 
		final Stack<Orga> stack = new Stack<>();
		
		this.accept(new AkteurVisitor() {
			
			@Override
			public void visit(Unternehmer unternehmer) {
				stack.push(unternehmer.getOrganisation());
			}
			
			@Override
			public void visit(Orga orga) {
				stack.push(orga);
			}
		});
		
		
		return stack;
	}
	
	
}
