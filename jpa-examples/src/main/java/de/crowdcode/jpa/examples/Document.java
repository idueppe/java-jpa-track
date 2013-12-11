package de.crowdcode.jpa.examples;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import de.crowdcode.jpa.vehicles.AbstractEntity;
import de.crowdcode.jpa.vehicles.Engine;
import de.crowdcode.jpa.vehicles.Manufacturer;
import de.crowdcode.jpa.vehicles.Vehicle;

@Entity
@AnyMetaDef( name="targetType", 
	idType = "long", 
	metaType = "string", 
	metaValues = {
		@MetaValue( value = "V", targetEntity = Vehicle.class ),
		@MetaValue( value = "M", targetEntity = Manufacturer.class ),
		@MetaValue( value = "E", targetEntity = Engine.class )
} )
public class Document<T extends AbstractEntity> extends AbstractEntity{
	
	@Any(metaDef="targetType", metaColumn = @Column(name="target_discriminator", length=1))
	@JoinColumn(name="target_id")
	public T target;
	
	public String content;
	
	
	public Document() {
	}
	
	public Document(T target, String content)
	{
		this.target = target;
		this.content = content;
	}

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
