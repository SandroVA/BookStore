/**
 * 
 */
package g7w14.test;

import static org.junit.Assert.*;
import g7w14.business.AuthorManager;
import g7w14.business.BookManager;
import g7w14.business.CustomerManager;
import g7w14.data.AuthorBean;
import g7w14.data.BookBean;
import g7w14.data.CustomerBean;
import g7w14.persistence.BookDAO;
import g7w14.persistence.CustomerDAO;

import java.io.File;

import javax.inject.Inject;

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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Svetlana Shopova
 * @since 28.03.2014
 */
@RunWith(Arquillian.class)
public class AuthorManagerTest {
	@Inject
	BookBean book;
	@Inject
	AuthorBean author;
	@Inject
	AuthorManager manager;
	
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
				.addClasses(BookBean.class, AuthorManager.class, AuthorBean.class);
	}
	
	@Test
	public void saveAuthorValidIDs()
	{
		author.setAuthorFirstName("Marion");
		author.setAuthorLastName("Smith");
		book.setBookId(46);
		assertTrue(manager.saveAuthor()==null);
	}

}
