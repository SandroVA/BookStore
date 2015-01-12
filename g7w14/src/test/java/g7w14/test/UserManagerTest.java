package g7w14.test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import g7w14.business.UserManager;
import g7w14.data.UserBean;
import g7w14.persistence.UserDAO;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests UserManager class
 * 
 * @author Svetlana Shopova
 * @since 10.02.2014
 */
@RunWith(Arquillian.class)
public class UserManagerTest {
	

	@Inject
	static
	UserBean user;
	@Inject
	UserDAO ud;
	@Inject
	static
	UserBean user2;
	@Inject
	static
	UserManager manager;

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
				.addClasses(UserDAO.class, UserManager.class, UserBean.class);
	}
	

	/**
	 * Tests inserting without password - checked
	 */	
	@Test
	public void testInsertUserWithoutPass() {

		user.setUsername("damian");

		assertTrue(manager.insertUser() != null);
	}

	/**
	 * Test inserting with valid username and password - checked
	 */	
	@Test
	public void testInsertValidUsernameAndPassword() {

		user.setUsername("patrick77");
		user.setPassword("blabla");
		assertTrue(manager.insertUser() == null);
	}

	/**
	 * Test deleting user - checked
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testDeletingExistingUser() throws SQLException {
		ArrayList<Object> data = new ArrayList<Object>();
		data.add("carlos");
		data.add("antares");
		data.add(1l);
		ud.insert("Insert into users(Username,Password,UserTypeId) values(?,?,?)", data);
		
		user.setUsername("carlos");
		
		ArrayList<UserBean> users = manager.search();

		user2=users.get(0);
		assertTrue(manager.deleteUser(user2) == 1);

	}

	/**
	 * Test deleting non existing user
	 */
	@Test
	public void testDeletingNonExistingUser() {
		
		user.setUserId(50000l);
		assertTrue(manager.deleteUser(user) == 0);
	}

	/**
	 * Test updating password - checked
	 * @throws SQLException 
	 */
	@Test
	public void testUpdatePassword() throws SQLException {

		user.setUserId(7l);
		user.setPassword("desi79");
		

		assertTrue(manager.update() == 1);
	}

	/**
	 * Updating password of nonexisting user - checked
	 */
	@Test
	public void testUpdatingPasswordNonexistingUser() {

		user.setUserId(50000l);
		assertTrue(manager.update() == 0);
	}

	
	/**
	 * Select all records - checked
	 */
	@Test
	public void testGetAll()
	{
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		
		users = manager.getAll();
		assertTrue(users.size()>0);
	}
	
	/**
	 * Test select by existing username - checked
	 * @throws SQLException 
	 */
	@Test
	public void testSelectByExisingUsername() throws SQLException
	{
		user.setUsername("svetlana");
		
		assertTrue( manager.search().size()==1);
		
	}
	
	/**
	 * Test select by non existing username - checked
	 */
	@Test
	public void testSelectByNonExistingUsername()
	{
		user.setUsername("nikodim");
		
		assertTrue(manager.search().size()==0);
	}
	
	
	@AfterClass
	public static void seTearDown()
	{
		user.setUsername("patrick77");
		
		ArrayList<UserBean> users = manager.search();

		user2=users.get(0);
		assertTrue(manager.deleteUser(user2) == 1);
	}
}
