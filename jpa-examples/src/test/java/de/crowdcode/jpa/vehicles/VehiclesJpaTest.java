package de.crowdcode.jpa.vehicles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VehiclesJpaTest {

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
		Manufacturer bugatti = buildDefaultData();
		
		txBegin();
		em.persist(bugatti);
		txCommit();
		
	}
	
	@Test
	public void test_2_LoadVehicle() throws Exception {
		Manufacturer bugatti = buildDefaultData();
		
		txBegin();
		em.persist(bugatti);
		txCommit();
		em.clear();
		Vehicle vehicle = em.find(Vehicle.class, bugatti.getVehicles().get(0).getId());
		assertNotNull(vehicle);
		
	}

	@Test
	public void test_3_LoadEagerManufacturer() throws Exception {
		Manufacturer bugatti = buildDefaultData();
		
		txBegin();
		em.persist(bugatti);
		txCommit();
		
		em.clear();
		Manufacturer manufacturer = em.find(Manufacturer.class, bugatti.getId());
		assertNotNull(manufacturer);
		
	}

	@Test
	public void test_4_PriceOrder() throws Exception {
		Manufacturer bugatti = buildDefaultData();
		Vehicle vehicle = bugatti.getVehicles().get(0);
		
		Price price = new Price(12.0, "EUR", new Date(System.currentTimeMillis() - 1_000_000));
		vehicle.getPrice().add(price);
		
		txBegin();
		em.persist(bugatti);
		txCommit();
		
		em.clear();
		
		Vehicle found = em.find(Vehicle.class, vehicle.getId());
		
		Price previousPrice = found.getPrice().get(1);
		assertEquals(price, previousPrice);
	}


	private Manufacturer buildDefaultData() {
		Manufacturer bugatti = new Manufacturer("Bugatti");
		Engine engine = new Engine("Engien", 1200.0, EngineType.PETROL);
		
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
