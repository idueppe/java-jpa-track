package de.crowdcode.jpa.examples;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.crowdcode.jpa.vehicles.Engine;
import de.crowdcode.jpa.vehicles.EngineType;
import de.crowdcode.jpa.vehicles.Manufacturer;
import de.crowdcode.jpa.vehicles.Price;
import de.crowdcode.jpa.vehicles.Vehicle;

public class DocumentJpaTest {

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
	public void test_1_DocumentPersist() throws Exception {
		Manufacturer bugatti = new Manufacturer("Bugatti");
		Engine engine = new Engine("Engien", 1200.0, EngineType.PETROL);
		
		Price price = new Price(1_200_000,"EUR",new Date(), null);
		Vehicle veyron = new Vehicle(bugatti, "Veyron Supersports", engine, price);
		
		Price priceGS = new Price(1_000_000,"EUR",new Date(), new Date(System.currentTimeMillis()+60_000));
		Vehicle grandSports = new Vehicle(bugatti, "Grand Sport", engine, priceGS);
		
		txBegin();
		em.persist(bugatti);
		
		
		em.persist(new Document<>(bugatti, "content"));
		em.persist(new Document<>(veyron, "veyron"));
		em.persist(new Document<>(grandSports, "grandsports"));
		em.persist(new Document<>(engine, "engine"));
		
		txCommit();
		
		
		Query query = em.createQuery("SELECT d FROM Document d WHERE d.targetDiscriminator = :target");
		query.setParameter("target", "V");
		List<Document<?>> docs = query.getResultList();
		
		for (Document<?> doc : docs)
			System.out.println(doc.getTarget().getClass());
		
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
