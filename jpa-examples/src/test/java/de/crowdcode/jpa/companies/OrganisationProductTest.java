package de.crowdcode.jpa.companies;

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
public class OrganisationProductTest {
	
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

	@Test
	public void test_1_Persist() {
		beginTx();
		Company company = new Company("tutego");
		Product product1 = new Product("name1", company);
		Product product2 = new Product("name2", company);
		Organisation orga = new Organisation();
		
		orga.getAllCompanies().add(company);
		orga.getProducts().add(product1);
		orga.getProducts().add(product2);
		
		em.persist(orga);
		commitTx();
	}
	
	
	@Test
	public void test_3_References() throws Exception {
//		Product b = em.getReference(Product.class, 2L);
		List<AbstractProduct> o = em.createQuery("SELECT o FROM de.crowdcode.jpa.companies.AbstractProduct o", AbstractProduct.class).getResultList();
		assertNotNull(o);
 		
	}	
	
}
