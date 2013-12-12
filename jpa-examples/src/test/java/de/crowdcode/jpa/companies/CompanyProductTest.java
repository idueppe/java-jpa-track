package de.crowdcode.jpa.companies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyProductTest {
	
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
		em.persist(company);
		em.persist(product1);
		em.persist(product2);
		commitTx();
	}
	
	@Test
	public void test_4_Persist() {
		beginTx();
		Company company = new Company("tutego");
		Product product1 = new Product("name1", company);
		Product product2 = new Product("name2", company);
		em.persist(company);
		em.persist(product1);
		em.persist(product2);
		commitTx();
		em.clear();
		Company landdata = new Company("land-data");
		landdata.getProducts().add(product1);
		product1.setCompany(landdata);
		beginTx();
		em.persist(landdata);
		em.merge(product1);
		commitTx();
		em.clear();
		Product found = em.find(Product.class, product1.getId());
		System.out.println(found.getCompany());
		
	}
	
	
	@Test(expected=RollbackException.class)
	public void test_2_ConstraintViolation() {
		beginTx();
		Company company = new Company("tutego");
		Product product1 = new Product("name1", company);
		Product product2 = new Product("name1", company);
		em.persist(company);
		em.persist(product1);
		em.persist(product2);
		commitTx();
	}
	
	@Test
	public void testDeferred() throws Exception {
		beginTx();
		Company company = new Company("tutego");
		Product product1 = new Product("name1", company);
		Product product2 = new Product("name2", company);

		Query deferred = em.createNativeQuery("set constraint all deferred");
		deferred.executeUpdate();
		em.persist(product1);
		em.persist(product2);
		em.persist(company);
		em.flush();
		commitTx();
		
		em.clear();
		Company comp = new Company("2");
		product1.setCompany(comp);
		beginTx();
		em.persist(comp);
		em.merge(product1);
		commitTx();
		em.clear();
		Product found = em.find(Product.class, product1.getId());
		assertEquals(company, found.getCompany());
		
	}
	
	
	@Test
	public void test_3_References() throws Exception {
//		Product b = em.getReference(Product.class, 2L);
		List<AbstractProduct> o = em.createQuery("SELECT o FROM de.crowdcode.jpa.companies.AbstractProduct o", AbstractProduct.class).getResultList();
		assertNotNull(o);
 		
	}	
	
}
