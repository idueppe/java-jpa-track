package de.crowdcode.jpa.visitor;

import javax.persistence.Entity;

import de.crowdcode.jpa.common.AbstractEntity;

@Entity
public class Data extends AbstractEntity {

	private String content;

	public Data() {
	}

	public Data(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
