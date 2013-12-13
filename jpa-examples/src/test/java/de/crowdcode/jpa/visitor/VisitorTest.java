package de.crowdcode.jpa.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	
	

//	@Test(expected=ClassCastException.class) 
	@Test()
	public void testVisitorWithoutVisitor() {
		Long benutzerId = buildDataStructureInDB();
		Benutzer b = em.find(Benutzer.class, benutzerId);

//		System.out.println(b.getAkteur().getClass());
//		System.out.println(b.getAkteur().getClass().getSuperclass());
//		System.out.println(b.getAkteur().getClass().getSuperclass().getSuperclass());
//		System.out.println(b.getAkteur().getOrganisation().getClass());
//		System.out.println(b.getAkteur().getOrganisation().getClass().getSuperclass());
//		System.out.println(b.getAkteur().getOrganisation().getClass().getSuperclass().getSuperclass());
		
		
// 		Produces an ClassCastException if Lazy Loading is active in Version 4.1.6.Final
// 		newer version will perform a Proxy Narrowing
		Orga found = b.getAkteur().getOrganisation(); 
		assertEquals("crowdcode", found.getName());
		assertEquals("data 1", found.getData().get(0).getContent());
		assertNotNull(found);
	}
	
	@Test()
	public void testWithInnerVisitor() {
		Long benutzerId = buildDataStructureInDB();
		Benutzer b = em.find(Benutzer.class, benutzerId);
		
		System.out.println(b.getAkteur().getClass());
		System.out.println(b.getAkteur().getClass().getSuperclass());
		System.out.println(b.getAkteur().getClass().getSuperclass().getSuperclass());
		
// 		Produces an ClassCastException if Lazy Loading is active
		Orga found = b.getAkteur().getVisitorOrga().pop();
		assertEquals("crowdcode", found.getName());
		assertNotNull(found);
	}
	
	
	@Test
	public void testVisitor() {
		Long benutzerId = buildDataStructureInDB();
		Benutzer b = em.find(Benutzer.class, benutzerId);
		
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
		
	}

	private Long buildDataStructureInDB() {
		beginTx();
		Orga orga = new Orga(new Data("data 1"), new Data("data 2"));
		orga.setName("crowdcode");
		Benutzer benutzer = new Benutzer(orga);
		
		em.persist(benutzer);
		em.persist(orga);
		commitTx();
		em.clear();
		return benutzer.getId();
	}
	
	

	
}
