package de.crowdcode.jpa.vehicles;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmbeddedJpaTest {

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
	public void test_1_Embedded() throws Exception {
		txBegin();
		Address address = new Address("street","42","27374","Visselhövede");
		Customer customer = new Customer("landdata",address);
		em.persist(customer);
		
		txCommit();
		em.clear();
		Customer found = em.find(Customer.class, customer.getId());
		assertEquals("27374", found.getAddress().getZip());
		assertEquals("27374", found.getZip());
		
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
