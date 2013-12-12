package de.crowdcode.jpa.vehicles;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum EngineType {

	@XmlEnumValue("diesel") DIESEL,
	@XmlEnumValue("petrol") PETROL,
	@XmlEnumValue("hybrid") HYBRID
	
}
