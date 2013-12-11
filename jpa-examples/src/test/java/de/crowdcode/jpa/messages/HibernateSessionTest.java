package de.crowdcode.jpa.messages;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateSessionTest {
	
	private static EntityManagerFactory emf;
	
	private EntityManager em;
	
	private static Long messageId;
	
	@BeforeClass
	public static void setUpClass()
	{
		emf = Persistence.createEntityManagerFactory("jpa-examples");
	}
	
	@Before
	public void setUp()
	{
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}
	
	@After
	public void tearDown()
	{
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.close();
	}
	
	@AfterClass
	public static void tearDownClass()
	{
		emf.close();
	}

	@Test
	public void test_1_Save() {
		Message message = new Message();
		message.setText("Hello Database");
		message.setContent("Huge Content");

		Session session = ((HibernateEntityManager) em).getSession();
		session.save(message);
		assertNotNull(message.getId());
		messageId = message.getId();
	}
	

	
}
