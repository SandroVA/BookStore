/**
 * 
 */
package g7w14.test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import g7w14.business.CustomerManager;
import g7w14.data.CustomerBean;
import g7w14.persistence.CustomerDAO;
import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class test CustomerManager
 * @author Svetlana Shopova
 * @since 11.02.2014
 *
 */
@RunWith(Arquillian.class)
public class CustomerManagerTest {

	@Inject
	CustomerBean customer;
	@Inject
	CustomerManager manager;
	static int result;
	ArrayList<CustomerBean>data;
	@Inject
	CustomerBean testCustomer;
	@Inject	
	CustomerDAO cdao;
	
	ArrayList<Object> values;
	
	@Deployment
	public static WebArchive deploy() {
		Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	    log.info("Deply");
		return ShrinkWrap
				.create(WebArchive.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsManifestResource(
						new FileAsset(new File(
								"src/main/webapp/META-INF/context.xml")),
						"context.xml")
				.addClasses(CustomerBean.class, CustomerManager.class, CustomerDAO.class);
	}
	
	
		

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		result=0;
		data = new ArrayList<CustomerBean>();
		values= new ArrayList<Object>();
	}

	/**
	 * Test delete existing customer -checked
	 * @throws SQLException 
	 */	
	@Test
	public void testDeleteExistingCustomer() throws SQLException {
		
		
		String sql = "Insert into customer(F_Name,L_Name,Title,UserId,Address1,Address2, Company, Province,City, Country,Postal_Code,"
				+ "Home_phone,Cell_Phone, Email, CategoryId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		values.add("Boris");
		values.add("Gomes");
		values.add("Mr.");
		values.add(84l);
		values.add("4512 Kirkland str. ");
		values.add("apt.306 ");
		
		values.add("Parabel");
		values.add("Quebec");
		values.add("Montreal");
		values.add("Canada");
		values.add("H2J 4V8");
		values.add("514-569-4124");
		values.add("514-236-4563");
		values.add("bgms@gmail.com");
		values.add(3l);
		
		cdao.insert(sql,values);
		
		
		values = new ArrayList<Object>();
		values.add("Boris");
		customer = cdao.select("Select * from customer where F_Name=?", values).get(0);
		
		
		result = manager.deleteRecord();
		assertTrue(result == 1);

	}

	
	/**
	 * Test deleting non-existing record - checked
	 */	
	@Test
	public void testDeleteRecordWrongId()  {
		customer.setCustomerId(20000l);
		
		result = manager.deleteRecord();
		assertFalse(result == 1);

	}
	
	/**
	 * Insert new customer - checked
	 * @throws SQLException 
	 */
	@Test
	public void testInsertNewCustomer() throws SQLException {
		
 		customer.setF_Name("KAMEN");
		customer.setL_Name("MILANOV Test");
		customer.setUserId(11l);
		customer.setTitle("Mr.");
		customer.setCompany("KATER SPORT");
		customer.setAddress1("6565 COTE-DES-NEIGES");
		customer.setAddress2("4 floor");
		customer.setCity("MONTREAL");
		customer.setProvince("QUEBEC");
		customer.setPostal_Code("H3S 1L9");
		customer.setCountry("CANADA");
		customer.setHome_Phone("514-665-1234");
		customer.setCell_Phone("514-689-7531");
		customer.setEmail("kmil190@gmail");
	
		
		manager.insert();		
		
		testCustomer.setL_Name("MILANOV Test");
		customer = manager.search().get(0);
		result = manager.deleteRecord();
		assertTrue(result == 1);
	}
	
	/**
	 * Test retrieveCustomers() - checked
	 */
	@Test
	public void testRetrieveCustomers() {
		data = manager.getAll();
		assertTrue(data.size()>0);
	}
	
	/**
	 * @throws SQLException -checked
	 * 
	 */	
	@Test
	public void testSearchById() throws SQLException {
		customer.setCustomerId(9l);
		
		data=manager.search();
		assertTrue(data.size() > 0);
	}
	
	/**
	 * Checked
	 */	
	@Test
	public void testSearchWrongId() {
		customer.setCustomerId(1l);
		
		data = manager.search();
		assertTrue(data.size() == 0);
	}

	
	/**
	 * Test searching by last name - checked
	 */	
	@Test
	public void testSearchLastName() {
		customer.setL_Name("Thomas");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

		
	
	/**
	 *  Checked
	 */
	
	@Test
	public void testSearchWrongLastName() {
		customer.setL_Name("POLAK");
		
		data = manager.search();
		assertTrue(data.size() == 0);
	}

	
	/**
	 * Test searching by first name -checked
	 */	
	@Test
	public void testFirstName() {
		customer.setF_Name("MARIA");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	
	/**
	 * Finding a record using first 3 letters for Company checked
	 */	
	@Test
	public void testSearchCompanyName() {
		customer.setCompany("COL");		
		data = manager.search();
		assertTrue(data.size() == 1);

	}

	
	/**
	 * Checked
	 */	
	@Test
	public void testSearchAddress1() {
		customer.setAddress1("1584");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	
		
		
	/**
	 * Checked
	 */	
	@Test
	public void testSearchCity() {
		customer.setCity("Montreal");
		
		data = manager.search();
		assertTrue(data.size() > 0);
	}

	
	/**
	 * Checked
	 */	
	@Test
	public void testSearchProvince() {
		customer.setProvince("Ontario");		
		data = manager.search();
		assertTrue(data.size() == 2);
	}

	

	/**
	 * Checked
	 */	
	@Test
	public void testSearchPostCode() {
		customer.setPostal_Code("H3S");
	
		data = manager.search();
		assertTrue(data.size() == 2);
	}

	
	

	/**
	 * Checked
	 */	
	@Test
	public void testPhone() {
		customer.setHome_Phone("514-987");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	

	/**
	 * Checked
	 */	
	@Test
	public void testSearchEmail() {
		customer.setEmail("tts");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	/**
	 * Search by first name and last name  - checked
	 */	
	@Test	
	public void testSearchTwoFields1() {
		customer.setF_Name("MARK");
		customer.setL_Name("JOHNES");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	/**
	 * Search by first name and wrong last name - checked
	 */	
	@Test
	public void testSearchTwoFieldsWrong1() {
		customer.setF_Name("Maria");
		customer.setL_Name("BAHAR");
		
		data = manager.search();
		assertFalse(data.size() == 1);
	}

	/**
	 * Search by last name and phone -checked
	 */	
	@Test
	public void testSearchTwoFields2() {
		customer.setHome_Phone("514-598");
		customer.setL_Name("STEFANOV");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	/**
	 * Search by Province and first name -checked
	 */	
	@Test
	public void testSearchTwoFields3() {
		customer.setProvince("Ontario");
		customer.setF_Name("Igor");
		
		data = manager.search();
		assertTrue(data.size() == 1);
	}

	@Test
	public void testUpdate() throws SQLException
	{
		
		customer.setCustomerId(9l);
		values.add("Katlea");
		int result = cdao.update(9l,"Update customer set company=? where CustomerId=?", values);
		assertTrue(result==1);
	}
	
	
	
}
