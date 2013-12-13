package de.crowdcode.jpa.visitor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import de.crowdcode.jpa.common.AbstractEntity;

@Entity
public class Benutzer extends AbstractEntity {
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Akteur akteur;
	
	public Benutzer() {
	}

	public Benutzer(Akteur akteur) {
		super();
		this.akteur = akteur;
	}

	public Akteur getAkteur() {
		return akteur;
	}

	public void setAkteur(Akteur akteur) {
		this.akteur = akteur;
	}
}
