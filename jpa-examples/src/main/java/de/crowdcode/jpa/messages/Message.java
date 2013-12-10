package de.crowdcode.jpa.messages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity()
@Table(name="tbl_message")
@SecondaryTable(name="tbl_message_content")
public class Message {

	@Id
	@GeneratedValue(generator="messageSequence")
	@SequenceGenerator(name="messageSequence", sequenceName="MessageSequence")
	private Long id;

	@Column(length=1024, nullable=false)
	private String text;
	
	@Column(table="tbl_message_content")
	private String content;
	
	@Transient
	private String securedHash;
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
