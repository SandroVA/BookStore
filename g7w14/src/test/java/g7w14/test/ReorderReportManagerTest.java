package g7w14.test;

import static org.junit.Assert.*;
import g7w14.business.ReorderReportManager;
import g7w14.business.ZeroSalesReportManager;
import g7w14.data.BookBean;
import g7w14.data.CalendarBean;
import g7w14.data.ReorderReportBean;

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
public class ReorderReportManagerTest {


	@Inject
	ReorderReportManager manager;
	@Inject
	ReorderReportBean rrb;
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
				.addClasses(ZeroSalesReportManager.class, ReorderReportBean.class);
	}
	
	@Test
	public void testZeroSales() throws SQLException{
		
		ArrayList<ReorderReportBean> results = null;
		results= manager.getAll();
		
		assertTrue(results.size() == 5);
	}

}
