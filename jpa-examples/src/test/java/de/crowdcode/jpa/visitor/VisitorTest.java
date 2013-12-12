package de.crowdcode.jpa.visitor;

import static org.junit.Assert.assertNotNull;

import java.util.List;

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
public class VisitorTest {
	
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

	private void beginTx() {
		em.getTransaction().begin();
	}
	
	@After
	public void tearDown()
	{
		em.close();
	}

	private void commitTx() {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
	}
	
	@AfterClass
	public static void tearDownClass()
	{
		emf.close();
	}
	
	private Orga organisation;

	@Test
	public void testVisitor() {
		beginTx();
		Orga orga = new Orga();
		Benutzer benutzer = new Benutzer(orga);
		
		em.persist(benutzer);
		em.persist(orga);
		commitTx();
		
		em.clear();
		Benutzer b = em.find(Benutzer.class, benutzer.getId());
		
//		Akteur akteur = em.find(Akteur.class, orga.getId());
		
		
		System.out.println(b.getAkteur().getClass());
		System.out.println(b.getAkteur().getClass().getSuperclass());
		System.out.println(b.getAkteur().getClass().getSuperclass().getSuperclass());
		
		Orga found = b.getAkteur().getOrganisation();
		
		
		b.getAkteur().accept(new AkteurVisitor() {
			
			@Override
			public void visit(Unternehmer unternehmer) {
				organisation = unternehmer.getOrganisation();
				System.out.println(unternehmer.getOrganisation());
			}
			
			@Override
			public void visit(Orga orga) {
				organisation = orga;
				System.out.println(orga);
			}
		});
		
		System.out.println(organisation);
		
		assertNotNull(found);
	}
	
	

	
}
