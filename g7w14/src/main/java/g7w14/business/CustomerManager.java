/**
 * 
 */
package g7w14.business;

import g7w14.data.CalendarBean;
import g7w14.data.CustomerBean;
import g7w14.data.UserBean;
import g7w14.persistence.CustomerDAO;
import g7w14.persistence.UserDAO;
import g7w14.util.DateConverter;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;

import g7w14.enums.*;

/**
 * This class manages CustomerBean and create SQL statements for CustomerDAO
 * 
 * @author Svetlana Shopova
 * updated by Joseph He
 * @since 21.02.2014
 * @version 1
 */
@Named("customerManager")
@SessionScoped
public class CustomerManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -171585422620814188L;
	@Inject
	private CustomerDAO cdao;
	@Inject
	private UserDAO udao;
	@Inject
	private CustomerBean customer;
	@Inject
	private CalendarBean calendar;
	@Inject
	private UserBean user;
	private ArrayList<Object> statement;
	private ArrayList<Object> values;
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	private ArrayList<CustomerBean> allClients = null;
	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());
	private String fullName;
	private boolean render;


	


	public CustomerManager() {
		super();
		render=false;
	}

	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param customerId
	 * @return
	 */
	public String getName(long customerId) {
			values = new ArrayList<Object>();
			ArrayList<UserBean> data = null;
			String selectStatement = "select users.Username from users join customer on customer.UserId = users.UserId where customer.CustomerId = ?";
			String name;
			
			values.add(customerId);
	
			try {
				data = udao.selectUsername(selectStatement, values);
			} catch (SQLException e) {

				log.error(e.getMessage());
			}

			name = data.get(0).getUsername();

			return name;
	}
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param usrerId
	 * @return
	 */
	public long getCustomerId(long userId) {
		String sql = "select * from customer where customer.userId = ?";
		values = new ArrayList<Object>();
		values.add(userId);
		ArrayList<CustomerBean> data = null;
		
		try {
			data = udao.getCustomerId(sql, values);
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		return data.get(0).getCustomerId();
	}
	
	/**
	 * This method delete a record from the table Customer
	 * 
	 * @param customer
	 *            CustomerBean - data for deleting
	 * @return int -result of the operation if operation is successful
	 */
	public int deleteRecord() {

		int result = 0;
		try {
			result = cdao.delete("Delete from customer where CustomerId = ?",
					customer.getCustomerId());
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		return result;
	}// end of delete()

	/**
	 * This method insert a record in the table Customer
	 * 
	 * @param customer
	 *            - CustomerBean - data for inserting
	 * @return - result of the operation
	 */
	@SuppressWarnings("unchecked")
	public String insert() {

		String selectStatement = "Insert into customer(";

		statement = createSelectStatement(selectStatement,
				OperationType.INSERT, customer);
		String sql = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		try {
			cdao.insert(sql, values);
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		return null;
	}// end of insertRecord()
	
	/**
	 * This method insert a record in the table Customer
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param customer
	 *            - CustomerBean - data for inserting
	 * @return - result of the operation
	 */
	@SuppressWarnings("unchecked")
	public String insertClient() {
		customer.setUserId(user.getUserId());
		values = new ArrayList<Object>();
		String sql = "select * from customer where userId = ?";
		values.add(user.getUserId());
		ArrayList<CustomerBean> bean = null;
		try {
			bean =  cdao.select(sql, values);
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		
		if(bean.isEmpty()) {
			values = new ArrayList<Object>();
			String selectStatement = "Insert into customer(";
	
			statement = createSelectStatement(selectStatement,
					OperationType.INSERT, customer);
			sql = (String) statement.get(0);
			values = (ArrayList<Object>) statement.get(1);

			try {
				cdao.insert(sql, values);
			} catch (SQLException e) {
	
				log.error(e.getMessage());
			}
		}
		else {
			values = new ArrayList<Object>();
			String 	selectStatement = "Update customer set  L_Name = ?, F_Name =?, Company=?,"
					+ "Address1=?, Address2=?, Province=?, Postal_Code=?, Country=?, Home_Phone=?,"
					+ "Cell_Phone=?, Email=?, Title=?, City=? where userId=?";
			
			values.add(customer.getL_Name());
			values.add(customer.getF_Name());
			values.add(customer.getCompany());
			values.add(customer.getAddress1());
			values.add(customer.getAddress2());
			values.add(customer.getProvince());
			values.add(customer.getPostal_Code());
			values.add(customer.getCountry());
			values.add(customer.getHome_Phone());
			values.add(customer.getCell_Phone());
			values.add(customer.getEmail());
			values.add(customer.getTitle());
			values.add(customer.getCity());
			values.add(user.getUserId());

			try {
				cdao.update(selectStatement, values);
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
			values = new ArrayList<Object>();
		}
		return null;
	}// end of insertRecord()

	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMyInfo() {
		values = new ArrayList<Object>();
		ArrayList<CustomerBean> data = null;
		String selectStatement = "select * from customer where ";

		customer.setUserId(user.getUserId());
	
		statement = createSelectStatement(selectStatement,
				OperationType.SEARCH, customer);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);
		
		try {
			data = cdao.select(selectStatement, values);
		} catch (SQLException e) {

			log.error(e.getMessage());
		}
		
		if (!data.isEmpty()) {
			customer.setUserId(0);
			customer.setAddress1(data.get(0).getAddress1());
			customer.setAddress2(data.get(0).getAddress2());
			customer.setCell_Phone(data.get(0).getCell_Phone());
			customer.setCity(data.get(0).getCity());
			customer.setCompany(data.get(0).getCompany());
			customer.setCountry(data.get(0).getCountry());
			customer.setEmail(data.get(0).getEmail());
			customer.setF_Name(data.get(0).getF_Name());
			customer.setHome_Phone(data.get(0).getHome_Phone());
			customer.setL_Name(data.get(0).getL_Name());
			customer.setPostal_Code(data.get(0).getPostal_Code());
			customer.setProvince(data.get(0).getProvince());
			customer.setTitle(data.get(0).getTitle());
			customer.setCustomerId(data.get(0).getCustomerId());
		}
		return "";
	}
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return
	 */
	public long getCustomerId() {
		ArrayList<CustomerBean> data = new ArrayList<CustomerBean>();
		
		try {
			data = cdao.getCustomerId(user.getUserId());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return data.get(0).getCustomerId();
	}
	
	/**
	 * This method retrieves all records of the table Customer
	 * 
	 * @return data ArrayList<CustomerBean> - list of beans with all records
	 * 
	 */
	public ArrayList<CustomerBean> getAll() {

		if (allClients == null) {
			try {
				allClients = cdao.getAll();
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
		}

		return allClients;
	}// end of retrieveContacts()

	/**
	 * This method select records of the table which respect a certain
	 * conditions
	 * 
	 * @param customer
	 *            CustomerBean - data for deleting
	 * @return CustomerBean list of records
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CustomerBean> search() {
		String selectStatement = "select * from customer where ";

		statement = createSelectStatement(selectStatement,
				OperationType.SEARCH, customer);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		ArrayList<CustomerBean> data = null;
		try {
			data = cdao.select(selectStatement, values);
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		return data;
	}// end of search()
	
	/**
	 * This method update an existing record in the table Customer
	 * 
	 * @param customer
	 *            CustomerBean - data for updating
	 * @return String - result of the operation
	 */
	public String updateAll() {
		String selectStatement = "Update customer set  L_Name = ?, F_Name =?, Company=?,"
				+ "Address1=?, Address2=?, Province=?, Postal_Code=?, Country=?, Home_Phone=?,"
				+ "Cell_Phone=?, Email=? where CustomerId=?";

		values = new ArrayList<Object>();

		for (CustomerBean cust : allClients) {
			values.add(cust.getL_Name());
			values.add(cust.getF_Name());
			values.add(cust.getCompany());
			values.add(cust.getAddress1());
			values.add(cust.getAddress2());
			values.add(cust.getProvince());
			values.add(cust.getPostal_Code());
			values.add(cust.getCountry());
			values.add(cust.getHome_Phone());
			values.add(cust.getCell_Phone());
			values.add(cust.getEmail());

			try {
				cdao.update(cust.getCustomerId(), selectStatement, values);
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
			values = new ArrayList<Object>();
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(strings.getString("updatedCustomers")));

		return null;
	}// end of updateRecord()

	/**
	 * This method create the sql statement depending of the type of the
	 * operation
	 * 
	 * @param selectStatement
	 *            - the begining of an sql statement
	 * @param type
	 *            - enum- type of the operation - insert, search, update
	 * @return an ArrayList of two objects- 1 - the sql statement, 2 - ArrayList
	 *         of values
	 */
	private ArrayList<Object> createSelectStatement(String selectStatement,
			OperationType type, CustomerBean customer) {
		statement = new ArrayList<Object>();
		values = new ArrayList<Object>();
		String addition = "";
		int start = selectStatement.length();

		switch (type) {
		case INSERT:
			addition = ",";
			break;
		case SEARCH:
			addition = " LIKE ? and ";
			break;
		case UPDATE:
			addition = "=?,";
			break;
		}

		if (type.equals(OperationType.SEARCH)) {
			if (customer.getCustomerId() > 0l) {
				selectStatement = selectStatement + "CustomerId=? and ";
				values.add(new Long(customer.getCustomerId()));
			}
		}

		if (!customer.getL_Name().trim().equals("")) {
			selectStatement = selectStatement + "L_Name" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getL_Name() + "%");
			else
				values.add(customer.getL_Name());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "L_Name" + addition;
				values.add(customer.getL_Name());
			}
		}
		if (!customer.getF_Name().trim().equals("")) {
			selectStatement = selectStatement + "F_Name" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getF_Name() + "%");
			else
				values.add(customer.getF_Name());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "F_Name" + addition;
				values.add(customer.getF_Name());
			}
		}

		if (!customer.getTitle().trim().equals("")) {
			selectStatement = selectStatement + "Title" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getTitle() + "%");
			else
				values.add(customer.getTitle());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "Title" + addition;
				values.add(customer.getTitle());
			}
		}

		if (customer.getUserId() > 0) {
			selectStatement = selectStatement + "UserId" + addition;
			if (type == OperationType.SEARCH)
				values.add(customer.getUserId());
			else
				values.add(customer.getUserId());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "UserId" + addition;
				values.add(customer.getUserId());
			}
		}

		if (!customer.getCompany().trim().equals("")) {
			selectStatement = selectStatement + "Company" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getCompany() + "%");
			else
				values.add(customer.getCompany());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "Company" + addition;
				values.add(customer.getCompany());
			}
		}
		if (!customer.getAddress1().trim().equals("")) {
			selectStatement = selectStatement + "ADDRESS1" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getAddress1() + "%");
			else
				values.add(customer.getAddress1());
		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "ADDRESS1" + addition;
				values.add(customer.getAddress1());
			}
		}
		if (!customer.getAddress2().trim().equals("")) {
			selectStatement = selectStatement + "ADDRESS2" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getAddress2() + "%");
			else
				values.add(customer.getAddress2());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				if (!customer.getAddress2().trim().equals("")) {
					selectStatement = selectStatement + "ADDRESS2" + addition;
					values.add(customer.getAddress2());
				}
			}
		}

		if (!customer.getCity().trim().equals("")) {
			selectStatement = selectStatement + "CITY" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getCity() + "%");
			else
				values.add(customer.getCity());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "CITY" + addition;
				values.add(customer.getCity());
			}
		}
		if (!customer.getProvince().trim().equals("")) {
			selectStatement = selectStatement + "PROVINCE" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getProvince() + "%");
			else
				values.add(customer.getProvince());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "PROVINCE" + addition;
				values.add(customer.getProvince());
			}
		}
		if (!customer.getPostal_Code().trim().equals("")) {
			selectStatement = selectStatement + "POSTAL_CODE" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getPostal_Code() + "%");
			else
				values.add(customer.getPostal_Code());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "POSTAL_CODE" + addition;
				values.add(customer.getPostal_Code());
			}
		}
		if (!customer.getCountry().trim().equals("")) {
			selectStatement = selectStatement + "COUNTRY" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getCountry() + "%");
			else
				values.add(customer.getCountry());
		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "COUNTRY" + addition;
				values.add(customer.getCountry());
			}
		}

		if (!customer.getHome_Phone().trim().equals("")) {
			selectStatement = selectStatement + "Home_Phone" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getHome_Phone() + "%");
			else
				values.add(customer.getHome_Phone());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "Home_Phone" + addition;
				values.add(customer.getHome_Phone());
			}
		}
		if (!customer.getCell_Phone().trim().equals("")) {
			selectStatement = selectStatement + "Cell_Phone" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + customer.getCell_Phone() + "%");
			else
				values.add(customer.getCell_Phone());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "Cell_Phone" + addition;
				values.add(customer.getCell_Phone());
			}
		}

		if (!customer.getEmail().trim().equals("")) {
			selectStatement = selectStatement + "EMAIL" + addition;

			if (type == OperationType.SEARCH)
				values.add("%" + customer.getEmail() + "%");
			else
				values.add(customer.getEmail());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "EMAIL" + addition;
				values.add(customer.getEmail());
			}
		}

		switch (type) {
		case INSERT:
			String params = "values(";
			for (int i = 0; i < values.size(); i++) {
				params = params + "?,";
			}

			params = params.substring(0, params.length() - 1) + ")";
			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 1))
					+ ")" + params;
			break;
		case SEARCH:
			if (selectStatement.length() == start) {
				return null;
			}

			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 4));
			break;
		case UPDATE:
			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 1))
					+ " where CustomerId = ? ";
			break;
		}

		statement.add(selectStatement);
		statement.add(values);
		return statement;
	}// end of createSelectStatement()

	/**
	 * This method return the sales by a customer in specific date range
	 * 
	 * @return the amount of sales by a customer
	 */
	public String getSalesByCustomer() {
		double totals = 0.0;
		String sql = "select sum(quantity*price) "
				+ "from customer join orders using (CustomerId)"
				+ "join orderitem using (OrderId) "
				+ "where customer.F_Name = ? and  customer.L_Name = ?  and Address1 = ?"
				+ " and Order_Date between ? and ? and Available=1 ";
		setNamesFromFullName();
		try {
			totals = cdao.salesByCustomer(sql, customer.getF_Name(),
					customer.getL_Name(), customer.getAddress1(),
					DateConverter.convertDate(calendar.getStartSelectedDate()),
					DateConverter.convertDate(calendar.getEndSelectedDate()));

		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(strings.getString("totalSales") + " "
						+ customer.getF_Name() + " " + customer.getL_Name()
						+ " " + String.valueOf(totals)));
		return null;
	}// end of getSalesByCustomer()

	/**
	 * @author Svetlana Shopova
	 */
	public double getTotalSalesByName(String firstName, String lastName, String address)
	{
		double totals=0.0;
		calendar.setStartSelectedDate(new Date(new GregorianCalendar(1900,01,01).getTimeInMillis()));
		calendar.setEndSelectedDate(new Date(new GregorianCalendar().getTimeInMillis()));
		
		
		String sql = "select sum(quantity*price) "
				+ "from customer join orders using (CustomerId)"
				+ "join orderitem using (OrderId) "
				+ "where customer.F_Name = ? and  customer.L_Name = ?  and Address1 = ?"
				+ " and Order_Date between ? and ? and Available=1 ";
		
		try {
			totals = cdao.salesByCustomer(sql, firstName,
					lastName, address,
					DateConverter.convertDate(calendar.getStartSelectedDate()),
					DateConverter.convertDate(calendar.getEndSelectedDate()));

		} catch (SQLException e) {

			log.error(e.getMessage());
		}
		
		return totals;
	}//end of getTotalSalesByName()
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * This method creates a list with the names and 
	 * addresses of all customers in the database
	 * in format "last name:first name:address"
	 * @return a list with strings
	 */
	public ArrayList<String> getAllCustomerNames() {

		ArrayList<String> allNames = new ArrayList<String>();
		ArrayList<CustomerBean> all = getAll();
		
		
		for (CustomerBean cust : all) {

			allNames.add(cust.getL_Name() + ":" + cust.getF_Name() + ":"
					+ cust.getAddress1());
		}

		return allNames;
	}//end of getAllCustomerNames()
	
	/**This method create report for 
	 * adminTopClients.xhtml 
	 * @return - list with clients who have orders
	 * @author - Svetlana Shopova
	 */
	public ArrayList<CustomerBean> getTopClients()
	{
		String sql= "select customerId, L_Name, F_Name,City, Address1, sum(quantity*price) as Sales "
				+ "from customer  join orders using (CustomerId) "
				+ "join orderItem using(OrderId) "
				+ "where Order_Date between ? and ? "
				+ "group by CustomerId "
				+ "order by Sales desc";
		
		ArrayList<CustomerBean> topClients = null;
		try {
			topClients = cdao.getTopClients(sql, DateConverter.convertDate(calendar.getStartSelectedDate()),
					DateConverter.convertDate(calendar.getEndSelectedDate()));
		} catch (SQLException e) {
			
			log.error(e.getMessage());
		}
		
		return topClients;
	}// end of getTopClients()

	/**
	 * This method splits full name of the customer to first name and last name
	 * and set customer bean's fields
	 * @author Svetlana Shopova
	 */
	public void setNamesFromFullName() {

		String[] names = fullName.split(":");
		
		customer.setL_Name(names[0]);
		customer.setF_Name(names[1]);
		customer.setAddress1(names[2]);
		
		
		
	}// end of setNamesFromFullName()

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public boolean isRender() {
		return render;
	}

	/**
	 * @author Svetlana Shopova
	 * @param render
	 */
	public void setRender(boolean render) {
		this.render = render;
	}
	
	/**
	 * This method reset the form in adminTopClient.xhtml
	 * @return
	 */
	public String resetTopClient()
	{
		setRender(false);
		return "adminTopClient";
	}
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String searchByLastName()
	{
		values=new ArrayList<Object>();
		if(customer.getL_Name()!=null && !customer.getL_Name().trim().equals(""))
		{
			values.add(customer.getL_Name());
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidLastCustomerName")));
			return null;
		}
		
		
		try {
			allClients=cdao.select("select * from customer where L_Name=?", values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidLastCustomerName")));
				
			log.error(e.getMessage());
			return null;
		}
		
		if(allClients.size()==0)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("unfoundClient")));
				return null;
		}
		
		return "adminClientEditing";
	}//end of searchByLastName()
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String searchByFirstName()
	{
		values=new ArrayList<Object>();
		if(customer.getF_Name()!=null && !customer.getF_Name().trim().equals(""))
		{
			values.add(customer.getF_Name());
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidLastCustomerName")));
				return null;
		}
		
		
		try {
			allClients=cdao.select("select * from customer where F_Name=?", values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidLastCustomerName")));
			log.error(e.getMessage());
			return null;
		}
		
		if(allClients.size()==0)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("unfoundClient")));
			return null;
		}
		
		return "adminClientEditing";
	}//end of searchByFirstName()
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String searchByPhone()
	{
		values=new ArrayList<Object>();
		if(customer.getHome_Phone()!=null && !customer.getHome_Phone().trim().equals(""))
		{
			values.add(customer.getHome_Phone());
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidClientPhone")));
				return null;
		}
		
		
		try {
			allClients=cdao.select("select * from customer where Home_Phone=?", values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidClientPhone")));
			log.error(e.getMessage());
			return null;
		}
		
		if(allClients.size()==0)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("unfoundClient")));
			return null;
		}
		
		return "adminClientEditing";
	}//end of searchByPphone()
	
	

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String searchById()
	{
		values=new ArrayList<Object>();
		if(customer.getCustomerId()>0 )
		{
			values.add(customer.getCustomerId());
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidCustomerId")));
				return null;
		}
		
		
		try {
			allClients=cdao.select("select * from customer where CustomerId=?", values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("invalidCustomerId")));
			log.error(e.getMessage());
			return null;
		}
		
		if(allClients.size()==0)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("unfoundClient")));
			return null;
		}
		
		return "adminClientEditing";
	}//end of searchByPphone()
	
	
	public ArrayList<CustomerBean> getAllClients() {
		return allClients;
	}

	public void setAllClients(ArrayList<CustomerBean> allClients) {
		this.allClients = allClients;
	}
	

	
}// end of CustomerManager class
