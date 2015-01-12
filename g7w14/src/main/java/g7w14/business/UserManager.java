/**
 * 
 */
package g7w14.business;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import g7w14.data.UserBean;
import g7w14.persistence.UserDAO;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;

/**
 * This class manages UserBean and create SQL statements for UserDAO
 * 
 * @author Svetlana Shopova, Sandro Victoria Arena and Ian Ozturk
 * @since 15.02.2014
 * @version 1.0
 */
@Named("userManager")
@SessionScoped
public class UserManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1436713494715418705L;
	
	@Inject
	private UserDAO ud;
	@Inject
	private UserBean user;
	Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	private ArrayList<UserBean> allUsers;
	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());
	
	private ArrayList<Object> values;

	/**
	 * This method deletes a user from table 'Users'
	 * 
	 * @param user
	 *            - user that will be deleted
	 * @return int result of the operation (1-successful,0- error, 2 - all
	 *         records are deleted)
	 */
	public int deleteUser(UserBean user) {
		int result = 0;

		try {
			result = ud.delete("Delete from users where UserId=? ",
					user.getUserId());
		} catch (SQLException sqle) {
			log.error(sqle.getMessage());
		}

		return result;
	}// end of deleteUser()
	
	/**
	 * This method insert a record in the table contacts
	 * 
	 * @param user
	 *            - UserBean - data for inserting
	 * @return - result of the operation *
	 */
	
	public String insertUser() {

		

		String selectStatement = "Insert into users(username,password, userTypeId) values(?,?,?)";

		ArrayList<Object> values = new ArrayList<Object>();
		values.add(user.getUsername());
		values.add(user.getPassword());
		values.add(user.getUserTypeId());

		try {
			ud.insert(selectStatement, values);
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		return null;
	}// end of insertRecord()
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return
	 */
	public String insertClient() {

		String selectStatement = "Insert into users(username,password, userTypeId) values(?,?,?)";

		ArrayList<Object> values = new ArrayList<Object>();
		values.add(user.getUsername());
		values.add(user.getPassword());
		values.add(1L);

		try {
			ud.insert(selectStatement, values);
			FacesContext.getCurrentInstance().getExternalContext().redirect("userRegistrationSuccess.xhtml");
		} catch (Exception e) {

			log.error(e.getMessage());
		}
		
		return null;
	}// end of insertRecord()

	/**
	 * This method retrieves the results of the query and returns them as a list
	 * of records
	 * 
	 * @return ArrayList<UserBean> list of records
	 * 
	 */
	public ArrayList<UserBean> getAll() {		
		
		 allUsers = null;
		try {
			 allUsers = (ArrayList<UserBean>) ud.getAll();
		} catch (SQLException e) {

			log.error(e.getMessage());
			return null;
		}

		return  allUsers;
	}// end retrieveUsers()

	/**
	 * This method select records of the table which respect a certain
	 * conditions
	 * 
	 * @param user
	 *            UserBean - data for deleting
	 * @return UserBean list of records
	 * 
	 */
	public ArrayList<UserBean> search() {
		
		
		String selectStatement = "select * from users where ";
		int size = selectStatement.length();

		values = new ArrayList<Object>();

		
		if (user.getUserId() > 0) {
			selectStatement = selectStatement + "UserId=?, ";

			values.add(user.getUserId());
		}

		if (!user.getUsername().trim().equals("")) {
			selectStatement = selectStatement + "Username=?, ";

			values.add(user.getUsername());
		}
		
		if (selectStatement.length() > size) {
			selectStatement = selectStatement.substring(0,
					selectStatement.length() - 2);
			
			try {
				allUsers = (ArrayList<UserBean>) ud.select(selectStatement, values);
				
			} catch (SQLException e) {

				 log.error(
				 "Error in searching a record in user table", e);
			}
		}
		return allUsers;
	}// end of search()

	/**
	 * This method update an existing record in the table contacts
	 * 
	 * @param user
	 *            UserBean - data for updating
	 * @return int - result of the operation *
	 */
	public int update() {

		String selectStatement = "Update users set password=? where UserId=? ";

		int result = 0;
		
		try {
			
			result = ud.update(selectStatement, user.getUserId(),
					user.getPassword());
			
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		return result;
	}// end of updateRecord()
	
	/**
	 * This method update users in the page adminEditingUser
	 * @return
	 */
	public String updateAll()
	{
		String selectStatement = "Update users set password=? where UserId=? ";
		
		
		for(int i=0; i<allUsers.size();i++)
		{
			try {
				
				ud.update(selectStatement, allUsers.get(i).getUserId(),
						allUsers.get(i).getPassword());
				
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
		}
		
		return null;
	}//end of updateAll()
	
	/**
	 * This method get all usernames in the database
	 * @return
	 */
	public ArrayList<String> getAllNames()
	{
		ArrayList<String> allNames= new ArrayList<String>();
		ArrayList<UserBean> allUsers = getAll();
		
		for(UserBean user:allUsers)
		{
			allNames.add(user.getUsername());
		}
		
		return allNames;
	}//end getAllNames()
	
	
	/**
	 * This method get all names of users 
	 * that still are not clients and
	 * @return list with usernames of the users
	 */
	public ArrayList<String> getAllNewNames()
	{
		
		ArrayList<String> allNames = null;
		try {
			allNames = ud.getAllNew();
		} catch (SQLException e) {
			
			log.error(e.getMessage());}			
		
		
		return allNames;
	}//end of getAllNewNames
	
	/**
	 * This method returns a list with all  users to adminEditingUser
	 * @return
	 */
	public ArrayList<UserBean> getAllUsers()
	{
		if(allUsers==null)
		{
			allUsers = getAll();
		}
		
		return allUsers;
	}//end of getAllUsers()
	
	public String editUser()	
	{
		
		if(user.getUsername()==null ||  user.getUsername().trim().equals(""))
		{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("missingUsername")));
			return null;
		}
		
		allUsers=   search();
		if(allUsers.size()<1)
		{
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("missingUsername")));
			return null;
		}
		
		return "adminEditingUser";
	}

}// end of UserManager()
