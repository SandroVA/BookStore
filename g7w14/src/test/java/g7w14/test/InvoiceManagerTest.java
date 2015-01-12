package g7w14.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import g7w14.business.InvoiceManager;
import g7w14.data.InvoiceBean;
import g7w14.persistence.InvoiceDAO;

/**
 * Tests the InvoiceManager and InvoiceDAO classes
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@RunWith(Arquillian.class)
public class InvoiceManagerTest {
	@Inject
	InvoiceDAO dbm;
	@Inject
	static InvoiceBean invoice;
	@Inject
	static InvoiceManager manager;

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
				.addClasses(InvoiceDAO.class, InvoiceManager.class,
						InvoiceBean.class);
	}

	@Test
	public void testQuery() throws SQLException {
		ArrayList<InvoiceBean> queryResult = new ArrayList<InvoiceBean>();
		queryResult = manager.search(invoice);
		assertNotNull(queryResult);
	}

	@Test
	public void testInsert() throws SQLException {
		int insertResult;

		invoice.setQuantity(1);
		invoice.setOrderId(1);
		invoice.setNetValue(20.00);
		invoice.setPst(1.4);
		invoice.setGst(1.2);
		invoice.setHst(1.5);
		invoice.setTotalGross(24.10);

		insertResult = manager.insertRecord(invoice);
		assertEquals(1, insertResult);
	}
}
