package g7w14.test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import g7w14.business.ReviewManager;
import g7w14.business.UserManager;
import g7w14.data.ReviewBean;
import g7w14.data.UserBean;
import g7w14.persistence.ReviewDAO;
import g7w14.persistence.UserDAO;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests ReviewManager class
 * 
 * @author Sandro Victoria Arena
 * @since 20.02.2014
 */
@RunWith(Arquillian.class)
public class ReviewManagerTest {

	@Inject 
	ReviewManager manager;
	@Inject
	ReviewBean bean;
	@Inject
	ReviewDAO dao;
	@Inject
	ReviewBean bean2;
	
	@Before
	public void before() throws SQLException {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(999);
		GregorianCalendar cal = new GregorianCalendar(2019,10,10);
		data.add(new Timestamp(cal.getTimeInMillis()));
		data.add(9);
		data.add(10);
		data.add("This is a review text");
		data.add(true);
		data.add(1);
		try {
			dao.insertRecord("Insert into Customer_Review(reviewId,review_Date,customerId,rating,review_Text,approval_Status,bookId) values(?,?,?,?,?,?,?)", data);
		} catch (Exception e) {
		}
	}
	
	@After
	public void after() {
		ArrayList<Object> values = new ArrayList<Object>();
		values.add("999");
		try {
			dao.select("Select * from Customer_Review where ReviewId = ? ", values);
		} catch (Exception e) {
		}
	}
	
	@Deployment
	public static WebArchive deploy() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"))
				.addAsManifestResource(
						new FileAsset(new File(
								"src/main/webapp/META-INF/context.xml")),
						"context.xml")
				.addClasses(ReviewManager.class, ReviewBean.class, ReviewDAO.class);
	}
	
	/**
	 * Test deleting user  - checked
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testDeletingExistingReview() throws SQLException {

		ArrayList<Object> values = new ArrayList<Object>();
		values.add("This is a review text");
		
	
		bean2 = dao.select("Select * from Customer_Review where Review_Text = ? ", values).get(0);
		
		int result = manager.deleteRecord();
		assertEquals(1,result);
	}

	/**
	 * Test deleting non existing user  - checked
	 */
	@Test(expected=AssertionError.class) 
	public void testDeletingNonExistingUser() {
		bean2.setBookId(999);
		bean2.setReviewId(999);
		bean2.setCustomerId(999);
		manager.deleteRecord();
		
		fail("Did not throw AssertionError Exception");
	}

	/**
	 * Select all records - checked
	 */
	@Test
	public void testGetAll()
	{
		ArrayList<ReviewBean> bean3 = new ArrayList<ReviewBean>();
		
		bean3 = manager.getAll();
		
		if(bean3.size() < 1)
			fail("testGetAll Method did not find any beans");
	}
	
	/**
	 * Test select by existing ID - checked
	 * @throws SQLException 
	 */
	@Test
	public void testSelectByExisingReview() throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		ArrayList<ReviewBean> amountOfBeans = new ArrayList<ReviewBean>();
		values.add("999");
		
		amountOfBeans = dao.select("Select * from Customer_Review where ReviewId = ? ", values);
		bean2 = amountOfBeans.get(0);
		
		if(amountOfBeans.size() != 1)
			fail("testSelectByExisingReview Method Did not find bean");
	}
	
	/**
	 * Test select by non existing ID - checked
	 */
	@Test
	public void testSelectByNonExistingReview() throws SQLException
	{
		ArrayList<Object> values = new ArrayList<Object>();
		ArrayList<ReviewBean> amountOfBeans = new ArrayList<ReviewBean>();
		values.add("9999");
		
		amountOfBeans = dao.select("Select * from Customer_Review where ReviewId = ? ", values);
		
		if(amountOfBeans.size() != 0)
			fail("testSelectByNonExistingReview Found wrong bean");
	}
	
	/**
	 * Test updating 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Currently Broken~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * @throws SQLException 
	 */
	@Test
	public void testUpdate() throws SQLException {
		ArrayList<Object> values = new ArrayList<Object>();
		values.add("999");
		bean2 = dao.select("Select * from Customer_Review where ReviewId = ? ", values).get(0);
		bean2.setRating(12);	
		
		assertTrue(manager.updateRecord() == null);
	}
	
	/**
	 * Test inserting - checked
	 * @throws SQLException 
	 */	
	@Test
	public void testInsert() throws SQLException {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(1000);
		GregorianCalendar cal = new GregorianCalendar(2019,10,10);
		data.add(new Timestamp(cal.getTimeInMillis()));
		data.add(9);
		data.add(9);
		data.add("Test Insert");
		data.add(false);
		data.add(1);
		int result = dao.insertRecord("Insert into Customer_Review(reviewId,review_Date,customerId,rating,review_Text,approval_Status,bookId) values(?,?,?,?,?,?,?)", data);

		ArrayList<Object> values = new ArrayList<Object>();
		values.add("Test Insert");
		
		bean2 = dao.select("Select * from Customer_Review where Review_Text = ? ", values).get(0);
		
		manager.deleteRecord();
		assertEquals(1,result);
	}
	
}
