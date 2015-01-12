package g7w14.test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import g7w14.business.OrderManager;
import g7w14.data.OrderBean;
import g7w14.persistence.OrderDAO;

/**
 * Tests the OrderManager and OrderDAO classes
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@RunWith(Arquillian.class)
public class OrderManagerTest {
	@Inject
	OrderDAO dao;
	@Inject
	static OrderBean order;
	@Inject
	static OrderManager manager;

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
				.addClasses(OrderDAO.class, OrderManager.class, OrderBean.class);
	}

	@Test
	public void testQuery() throws SQLException {
		ArrayList<OrderBean> queryResult = new ArrayList<OrderBean>();
		order.setOrderId(0);
		queryResult = manager.search();
		assertTrue(queryResult.size() == 1);
	}

	@Test
	public void testInsert() throws SQLException {
		int insertResult;
		Calendar orderDate = new GregorianCalendar();
		Calendar shipDate = new GregorianCalendar();

		order.setCustomerId(1);
		order.setOrderDate(new Timestamp(orderDate.getTimeInMillis()));
		order.setShipDate(new Timestamp(shipDate.getTimeInMillis()));
		order.setTitle("");
		order.setsLastName("Lastname");
		order.setsFirstName("Firstname");
		order.setsCompany("");
		order.setsAddress1("123 Address");
		order.setsAddress2("");
		order.setsCity("City");
		order.setsProvince("Quebec");
		order.setsCountry("Canada");
		order.setsPostalCode("H9H2W1");

		insertResult = manager.insertRecord();
		assertEquals(1, insertResult);
	}

	@Test
	public void testUpdate() throws SQLException {
		String updateResult;

		Calendar orderDate = new GregorianCalendar();
		Calendar shipDate = new GregorianCalendar();

		order.setOrderId(0);
		order.setCustomerId(1);
		order.setOrderDate(new Timestamp(orderDate.getTimeInMillis()));
		order.setShipDate(new Timestamp(shipDate.getTimeInMillis()));
		order.setTitle("");
		order.setsLastName("Lastname");
		order.setsFirstName("Firstname");
		order.setsCompany("Companyname");
		order.setsAddress1("123 Address");
		order.setsAddress2("");
		order.setsCity("City");
		order.setsProvince("Quebec");
		order.setsCountry("Canada");
		order.setsPostalCode("H9H2W1");

		updateResult = manager.updateRecord();
		assertEquals(null, updateResult);
	}
}
