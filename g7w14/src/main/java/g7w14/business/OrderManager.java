package g7w14.business;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import g7w14.data.CustomerBean;
import g7w14.data.OrderBean;
import g7w14.data.UserBean;
import g7w14.enums.OperationType;
import g7w14.persistence.OrderDAO;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;

/**
 * Generates the SQL to insert, update, or search for records in the orders
 * table
 * 
 * @author Martin Nafekh 1032763, Svetlana Shopova
 * Updated by Joseph He
 */
@Named("orderManager")
@SessionScoped
public class OrderManager implements Serializable {

	private static final long serialVersionUID = 5704087530989339019L;
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	@Inject
	private OrderDAO orderDao;
	@Inject
	private OrderBean orderBean;
	@Inject
	private CustomerBean customer;
	@Inject
	private CustomerManager cManager;
	@Inject
	private UserBean user;
	@Inject
	private ShoppingCartManager shoppingCartManager;

	private ArrayList<Object> statement;
	private ArrayList<Object> values;
	private ArrayList<OrderBean> data;
	private boolean render;

	private ArrayList<OrderBean> order;

	private void resetLists() {
		values = new ArrayList<Object>();
		data = new ArrayList<OrderBean>();
	}

	@SuppressWarnings("unchecked")
	public int insertRecord() {
		String selectStatement = "Insert into orders(";
		int result = 0;

		// statement = createSQL(selectStatement, OperationType.INSERT,
		// orderBean);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		try {
			result = orderDao.insert(selectStatement, orderBean);
		} catch (SQLException e) {
			log.error("Error in inserting a record in orders table", e);
		}

		return result;
	}

	/**
	 * @author Ian Sumanion and Sandro Victoria Arena
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int insertCustomerOrder() {
		int result = 0;
		String selectStatement = "Insert into orders(";

		GregorianCalendar date = new GregorianCalendar();
		GregorianCalendar shipDate = new GregorianCalendar();
		shipDate.add(Calendar.DATE, 5);

		orderBean.setCustomerId(cManager.getCustomerId(user.getUserId()));
		orderBean.setsAddress1(customer.getAddress1());
		orderBean.setsAddress2(customer.getAddress2());
		orderBean.setsCity(customer.getAddress2());
		orderBean.setsCompany(customer.getCompany());
		orderBean.setsCountry(customer.getCountry());
		orderBean.setTitle(customer.getTitle());
		orderBean.setsFirstName(customer.getF_Name());
		orderBean.setsLastName(customer.getL_Name());
		orderBean.setsPostalCode(customer.getPostal_Code());
		orderBean.setsProvince(customer.getProvince());
		orderBean.setOrderDate(new Timestamp(date.getTimeInMillis()));
		orderBean.setShipDate(new Timestamp(date.getTimeInMillis()));

		statement = createSQL(selectStatement, OperationType.INSERT);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		try {
			result = orderDao.insert(selectStatement, orderBean);
		} catch (SQLException e) {
			log.error("Error in inserting a record in orders table", e);
		}

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("userThanks.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<OrderBean> search() {
		String selectStatement = "Select * from orders where ";
		statement = createSQL(selectStatement, OperationType.SEARCH);

		if (statement != null) {
			selectStatement = (String) statement.get(0);
			values = (ArrayList<Object>) statement.get(1);

			try {
				data = (ArrayList<OrderBean>) this.orderDao.queryRecords(
						selectStatement, values);
			} catch (SQLException e) {
				log.error("Error in searching a record in orders table", e);
			}
		}

		return data;
	}

	/**
	 * 
	 * @author Svetlana Shopova
	 * @return
	 */

	public String updateRecord() {
		String selectStatement = "Update orders set  Title = ?, SL_Name=?, SF_Name=?, "
				+ "SCompany = ?, SAddress1=?, SAddress2=?, SCity =?, "
				+ " SProvince=?, SCountry = ?, SPostal_Code = ? where OrderId=?";

		int result = 0;

		try {
			result = orderDao.update(selectStatement, orderBean);
		} catch (SQLException e) {
			log.error("Error in updating a record in orders table", e);
		}

		if (result == 1) {
			return "adminOrderManagement";
		} else {
			return null;
		}
	}

	/**
	 * This method returns all existing orders
	 * 
	 * @return - list with orders
	 * @author Svetlana Shopova
	 */
	public ArrayList<OrderBean> getAll() {
		ArrayList<OrderBean> all = new ArrayList<OrderBean>();

		try {
			all = orderDao.retrieveAll("select * from orders");
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return all;
	}// end of getAll()

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public ArrayList<Long> getAllIdForCustomer() {

		ArrayList<Long> all = new ArrayList<Long>();
		String sql = "select OrderId from orders join customer using (CustomerId)"
				+ "	where F_Name=? and L_Name=? and Address1=?";

		try {
			all = orderDao.retrieveAll(sql, customer.getF_Name(),
					customer.getL_Name(), customer.getAddress1());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return all;
	}// end of getAll()

	/**
	 * This method returns a list with id numbers of all existing orders
	 * 
	 * @return list with numbers of the orders
	 * @author Svetlana Shopova
	 */
	public ArrayList<Long> getAllOrderId() {

		ArrayList<Long> allId = new ArrayList<Long>();
		ArrayList<OrderBean> all = getAll();

		for (OrderBean b : all) {
			allId.add(b.getOrderId());
		}

		return allId;
	}// end of getAllOrderId()

	/**
	 * 
	 * @return
	 * @author Svetlana Shopova
	 * 
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
	 * THis method find an order by its OrderId
	 * 
	 * @author Svetlana Shopova
	 * @return list with one order
	 */
	public ArrayList<OrderBean> getOrder() {

		if (order == null) {
			values = new ArrayList<Object>();
			String sql = "select * from orders where OrderId=?";
			values.add(orderBean.getOrderId());
			try {
				order = orderDao.queryRecords(sql, values);
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
		}

		return order;
	}

	/**
	 * 
	 * @return
	 */
	public String updateAll() {
		int result = 0;
		String selectStatement = "Update orders set  Title = ?, SL_Name=?, SF_Name=?, "
				+ "SCompany = ?, SAddress1=?, SAddress2=?, SCity =?, "
				+ " SProvince=?, SCountry = ?, SPostal_Code = ? where OrderId=?";
		for (OrderBean ob : order) {
			try {
				result = orderDao.update(selectStatement, ob);
			} catch (SQLException e) {
				log.error("Error in updating a record in orders table", e);
			}
		}
		if (result == 1) {
			return "adminOrderManagement";
		} else {
			return null;
		}
	}

	private ArrayList<Object> createSQL(String selectStatement,
			OperationType type) {
		statement = new ArrayList<Object>();
		resetLists();
		String addition = "";
		int start = selectStatement.length();

		switch (type) {
		case INSERT:
			addition = ",";
			break;
		case SEARCH:
			addition = " =? and ";
			break;
		case UPDATE:
			addition = "=?,";
			break;
		}

		if (type.equals(OperationType.SEARCH)) {
			if (orderBean.getOrderId() != -1) {
				selectStatement += "OrderId" + addition;
				values.add(new Long(orderBean.getOrderId()));
			}
		}

		if (orderBean.getCustomerId() != -1) {
			selectStatement += "CustomerId" + addition;
			values.add(new Long(orderBean.getCustomerId()));
		}

		if (orderBean.getOrderDate() != null) {
			selectStatement += "Order_Date" + addition;
			values.add(orderBean.getOrderDate());
		}

		if (orderBean.getShipDate() != null) {
			selectStatement += "Ship_Date" + addition;
			values.add(orderBean.getShipDate());
		}

		if (orderBean.getTitle() != null && !orderBean.getTitle().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "Title like ? and ";
				values.add("%" + orderBean.getTitle() + "%");
			} else {
				selectStatement += "Title" + addition;
				values.add(orderBean.getTitle());
			}
		}

		if (orderBean.getsLastName() != null
				&& !orderBean.getsLastName().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SL_Name like ? and ";
				values.add("%" + orderBean.getsLastName() + "%");
			} else {
				selectStatement += "SL_name" + addition;
				values.add(orderBean.getsLastName());
			}
		}

		if (orderBean.getsFirstName() != null
				&& !orderBean.getsFirstName().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SF_Name like ? and ";
				values.add("%" + orderBean.getsFirstName() + "%");
			} else {
				selectStatement += "SF_Name" + addition;
				values.add(orderBean.getsFirstName());
			}
		}

		if (orderBean.getsCompany() != null
				&& !orderBean.getsCompany().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SCompany like ? and ";
				values.add("%" + orderBean.getsCompany() + "%");
			} else {
				selectStatement += "SCompany" + addition;
				values.add(orderBean.getsCompany());
			}
		}

		if (orderBean.getsAddress1() != null
				&& !orderBean.getsAddress1().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SAddress1 like ? and ";
				values.add("%" + orderBean.getsAddress1() + "%");
			} else {
				selectStatement += "SAddress1" + addition;
				values.add(orderBean.getsAddress1());
			}
		}

		if (orderBean.getsAddress2() != null
				&& !orderBean.getsAddress2().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SAddress2 like ? and ";
				values.add("%" + orderBean.getsAddress2() + "%");
			} else {
				selectStatement += "SAddress2" + addition;
				values.add(orderBean.getsAddress2());
			}
		}

		if (orderBean.getsCity() != null && !orderBean.getsCity().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SCity like ? and ";
				values.add("%" + orderBean.getsCity() + "%");
			} else {
				selectStatement += "SCity" + addition;
				values.add(orderBean.getsCity());
			}
		}

		if (orderBean.getsProvince() != null
				&& !orderBean.getsProvince().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SProvince like ? and ";
				values.add("%" + orderBean.getsProvince() + "%");
			} else {
				selectStatement += "SProvince" + addition;
				values.add(orderBean.getsProvince());
			}
		}

		if (orderBean.getsCountry() != null
				&& !orderBean.getsCountry().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SCountry like ? and ";
				values.add("%" + orderBean.getsCountry() + "%");
			} else {
				selectStatement += "SCountry" + addition;
				values.add(orderBean.getsCountry());
			}
		}

		if (orderBean.getsPostalCode() != null
				&& !orderBean.getsPostalCode().equals("")) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement += "SPostal_Code like ? and ";
				values.add("%" + orderBean.getsPostalCode() + "%");
			} else {
				selectStatement += "SPostal_Code" + addition;
				values.add(orderBean.getsPostalCode());
			}
		}

		if (selectStatement.length() == start) {
			return null;
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
		case UPDATE:
			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 1))
					+ " where id = ?";
			values.add(orderBean.getOrderId());
			break;
		default:
			if (selectStatement.length() == start) {
				return null;
			}

			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 4));
			break;
		}

		statement.add(selectStatement);
		statement.add(values);

		return statement;
	}

}
