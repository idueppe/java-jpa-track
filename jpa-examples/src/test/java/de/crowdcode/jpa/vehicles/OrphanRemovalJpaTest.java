package de.crowdcode.jpa.vehicles;

import static org.junit.Assert.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrphanRemovalJpaTest {

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
	public void test_1_ManufacturerVehiclePersist() throws Exception {
		Manufacturer bugatti = new Manufacturer("Bugatti");
		Engine engine = new Engine("Engien", 1200.0, EngineType.PETROL);
		
		Price price = new Price(1_200_000,"EUR",new Date());
		Vehicle veyron = new Vehicle(bugatti, "Veyron Supersports", engine, price);
		
		Price priceGS = new Price(1_000_000,"EUR",new Date());
		Vehicle grandSports = new Vehicle(bugatti, "Grand Sport", engine, priceGS);
		
		txBegin();
		em.persist(bugatti);
		txCommit();
		em.clear(); // löschen des first level cache
		txBegin();
		Manufacturer manufacturer = em.find(Manufacturer.class, bugatti.getId());
		manufacturer.getVehicles().remove(0);
		txCommit();
		
		
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
