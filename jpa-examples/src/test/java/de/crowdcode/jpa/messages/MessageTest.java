package de.crowdcode.jpa.messages;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageTest {
	
	private static EntityManagerFactory emf;
	
	private EntityManager em;
	
	private static Long messageId;
	
	@BeforeClass
	public static void setUpClass()
	{
		// EntityManagerFactory nach möglich nur ein einziges mal erstell.
		// EMF ist thread safe
		emf = Persistence.createEntityManagerFactory("jpa-examples");
	}
	
	@Before
	public void setUp()
	{
		// Beliebig häufig für jeden Thread erstellen.
		// EntityManager ist nicht thread safe
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
//		message.setId(messageId);
		message.setText("Hello Database");
		
		em.persist(message);
		assertNotNull(message.getId());
		messageId = message.getId();
	}
	
	@Test
	public void test_2_Find()
	{
		Message message = em.find(Message.class, messageId);
		assertEquals("Hello Database", message.getText());
	}
	
	@Test
	public void test_3_Update()
	{
		em.getTransaction().rollback();
		
		Message message = em.find(Message.class, messageId);
		
		message.setText("Update Database");
		em.getTransaction().begin();
		em.getTransaction().commit();
	}
	
	@Test
	public void test_4_merge()
	{
		Message message = em.find(Message.class, messageId);
		
		em.detach(message);
		message.setText("UPDATE 1");
		Message merged = em.merge(message);
		message.setText("UPDATE 2"); // update beim detached object
		merged.setText("UPDATE 3"); // update beim attached object
		
		em.getTransaction().commit();
	}
	
	@Test
	public void test_5_refresh()
	{
		Message message = em.find(Message.class, messageId);
		
		Query query = em.createNativeQuery("UPDATE tbl_message SET text=:text WHERE id=:id");
		query.setParameter("id", messageId);
		query.setParameter("text", "updated");
		query.executeUpdate();
		
		assertEquals("UPDATE 3", message.getText());
		
		em.refresh(message);
		
		assertEquals("updated", message.getText());
	}
	
	
	@Test
	public void test_6_flush()
	{
		em.setFlushMode(FlushModeType.AUTO);
		Message message = em.find(Message.class, messageId);
		message.setText("flush");
		em.flush();
		message.setText("after flush");
		// em.flush()
		em.createQuery("SELECT m FROM Message m").getResultList();
		em.createNativeQuery("UPDATE tbl_message SET text='updated' WHERE text like '%after%'").executeUpdate();
		em.createNativeQuery("SELECT * FROM tbl_message WHERE text like '%after%'").getResultList();
//		em.clear();
		em.flush();
		em.find(Message.class, messageId);
		em.refresh(message);
		
	}
	
	@Test
	public void test_7_findby()
	{
		TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m WHERE m.id = :id", Message.class);
		query.setParameter("id", messageId);
		query.setFirstResult(0);
		query.setMaxResults(1);
		Message message = query.getSingleResult();
		assertEquals("updated", message.getText());
	}
	
	@Test
	public void test_9_1_Delete()
	{
//		Message message = em.find(Message.class, messageId);
		Message reference = em.getReference(Message.class, messageId);
		em.remove(reference);
	}
	
	@Test
	public void test_9_9_execute_ql()
	{
		Message message = new Message();
//		message.setId(13L);
		message.setText("Hello Database");
		em.persist(message);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		em.createQuery("UPDATE Message m SET m.text = 'x'").executeUpdate();
		em.createQuery("DELETE Message").executeUpdate();
		
		em.getTransaction().commit();
	}

}
