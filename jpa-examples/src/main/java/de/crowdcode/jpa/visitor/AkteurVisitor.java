package de.crowdcode.jpa.visitor;

public interface AkteurVisitor {

	public void visit(Orga orga);
	public void visit(Unternehmer unternehmer);
	
}
