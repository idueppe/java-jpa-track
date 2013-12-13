package de.crowdcode.jpa.vehicles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.crowdcode.jpa.common.AbstractEntity;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JaxbJpaTest {

	private static EntityManagerFactory emf;
	
	private EntityManager em;
	
	@BeforeClass
	public static void setUpClass()
	{
		emf = Persistence.createEntityManagerFactory("jpa-examples");
	}
	
	@Before
	public void setUp()
	{
		em = emf.createEntityManager();
	}

	@After
	public void tearDown()
	{
		em.close();
	}
	
	@Test
	public void test_1_SerializeManufacturers() throws Exception {
		Manufacturer bugatti = buildDefaultData();
		persistInTX(bugatti);
		em.clear(); // löschen des first level cache

		Manufacturer manufacturer = em.find(Manufacturer.class, bugatti.getId());
		
		assertEquals(EngineType.PETROL,manufacturer.getVehicles().get(0).getEngine().getEngineType());
		assertEquals(1200.0,manufacturer.getVehicles().get(0).getEngine().getPs(), 0.00001);
		
		System.out.println(manufacturer.getVehicles().get(0).getEngine().getPs());
		System.out.println(manufacturer.getVehicles().get(0).getEngine().getName());
		System.out.println(manufacturer.getVehicles().get(0).getEngine().getEngineType());
		
		JAXBContext jaxbContext = JAXBContext.newInstance(AbstractEntity.class, Engine.class, EngineType.class, Manufacturer.class, Price.class, Vehicle.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		QName qname = new QName("http://vehicle","manufacturer");
		JAXBElement<Manufacturer> jaxbElement = new JAXBElement<Manufacturer>(qname, Manufacturer.class, manufacturer);
		marshaller.marshal(jaxbElement, new File("target/export.xml"));
	}
	
	@Test
	public void test_2_DeserializeManufacturers() throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(AbstractEntity.class, Engine.class, EngineType.class, Manufacturer.class, Price.class, Vehicle.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Manufacturer manufacturer = (Manufacturer) unmarshaller.unmarshal(new File("target/export.xml"));
		assertNotNull(manufacturer);
		persistInTX(manufacturer);
	}

	private void persistInTX(Manufacturer bugatti) {
		txBegin();
		em.persist(bugatti);
		txCommit();
	}

	private Manufacturer buildDefaultData() {
		Manufacturer bugatti = new Manufacturer("Bugatti");
		Engine engine = new Engine("Engine", 1200.0, EngineType.PETROL);
		
		Price price = new Price(1_200_000,"EUR",new Date());
		new Vehicle(bugatti, "Veyron Supersports", engine, price);
		
		Price priceGS = new Price(1_000_000,"EUR",new Date());
		new Vehicle(bugatti, "Grand Sport", engine, priceGS);
		return bugatti;
	}

	private void txBegin() {
		em.getTransaction().begin();
	}
	
	private void txCommit() {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
	}
	
	@AfterClass
	public static void tearDownClass()
	{
		emf.close();
	}

}
