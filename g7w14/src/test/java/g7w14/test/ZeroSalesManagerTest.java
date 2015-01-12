package g7w14.test;

import static org.junit.Assert.*;
import g7w14.business.TotalSalesManager;
import g7w14.business.ZeroSalesReportManager;
import g7w14.data.BookBean;
import g7w14.data.CalendarBean;
import g7w14.data.TotalSalesBean;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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

@RunWith(Arquillian.class)
public class ZeroSalesManagerTest {

	@Inject
	CalendarBean calendar;
	@Inject
	ZeroSalesReportManager manager;
	@Inject
	BookBean book;
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
				.addClasses(CalendarBean.class, ZeroSalesReportManager.class, BookBean.class);
	}
	
	@Test
	public void testZeroSales() throws SQLException{
		calendar.setEndSelectedDate(new Date(2014,03,03));
		calendar.setStartSelectedDate(new Date(2014,01,03));
		
		ArrayList<BookBean> results = null;
		results= manager.getZeroSales();
		
		assertTrue(results.size() == 27);
	}
	

}
