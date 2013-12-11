package de.crowdcode.jpa.messages;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageContentTest {
	
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
	public void test_1_Persist() {
		Message message = new Message();
		message.setText("Hello Database");
		message.setContent("Huge Content");
		
		em.persist(message);
		assertNotNull(message.getId());
		messageId = message.getId();
	}
	
	@Test
	public void test_2_Find() throws Exception {
		Message msg = em.find(Message.class, messageId);
		msg.getText();
	}
	

	@Test
	public void test_3_Merge() {
		Message message = new Message();
		message.setId(messageId);
		message.setText("Database");
		message.setContent("Small Content");
		
		em.merge(message);
		assertNotNull(message.getId());
		messageId = message.getId();
	}
	
}
