package g7w14.business;

import g7w14.data.BookBean;
import g7w14.data.CalendarBean;
import g7w14.data.PublisherBean;
import g7w14.persistence.PublisherDAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import g7w14.util.*;

/**
 * This class manages interaction with Publisher table in database
 * 
 * @author Svetlana Shopova and Sandro Victoria Arena
 * @since 10.03.2014
 * @version 1.0
 * 
 */
@Named("publisherManager")
@SessionScoped
public class PublisherManager implements Serializable {

	private static final long serialVersionUID = 4265599352180435850L;
	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());
	@Inject
	PublisherDAO pdao;
	@Inject
	private PublisherBean publ;
	@Inject
	private CalendarBean calendar;
	@Inject
	private BookBean book;
	@Inject
	private BookManager bManager;

	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);

	public PublisherManager() {
		super();

	}

	/**
	 *This method return the name of the publisher in the database
	 * @author Sandro Victoria Arena and Ian Ozturk 
	 * @return list with publishers
	 */
	public ArrayList<PublisherBean> getPublisher() {
		ArrayList<PublisherBean> bean = null;
		try {
			String sql = "Select * from publisher where publisherId = ?";
			bean = pdao.getPublisherById(sql);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return bean;
	}// end of getPublisher()

	/**
	 * This method return the name of the publisher in the database
	 * @author Sandro Victoria Arena and Ian Ozturk 
	 * @return list with publishers
	 */
	public ArrayList<PublisherBean> getPublisher(long id) {
		ArrayList<PublisherBean> bean = null;

		try {
			String sql = "Select * from publisher where publisherId = ?";
			bean = pdao.getPublisherById(sql, id);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return bean;
	}// end of getBookAuthor

	/**
	 * This create list with all existing publishers
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PublisherBean> getAll() {

		ArrayList<PublisherBean> publishers = null;
		try {
			publishers = pdao.getAll();
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return publishers;
	}// end getAll()

	/**
	 * This method create list with the names of all publishers
	 * 
	 * @return list with all publishers
	 */
	public ArrayList<String> getAllNames() {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<PublisherBean> allPublishers = getAll();

		names.add(" ");
		for (PublisherBean publisher : allPublishers) {
			names.add(publisher.getPublisherName());
		}

		return names;
	}// end getAllNames()

	/**
	 * This method find all sales for a chosen publisher within defined by the
	 * admin date range
	 * 
	 * @return
	 */
	public String getSalesByPublisher() {

		double totals = 0.0;
		String sql = "select sum(quantity*price) "
				+ "from orders join orderItem  using(OrderId) "
				+ "join book using (BookId) "
				+ "join publisher using (PublisherId) "
				+ "where publisher.Publisher_Name = ? "
				+ "and Order_Date between ? and ? and Available=1";
		try {
			totals = pdao.salesByPublisher(sql, publ.getPublisherName(),
					DateConverter.convertDate(calendar.getStartSelectedDate()),
					DateConverter.convertDate(calendar.getEndSelectedDate()));
		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(String.valueOf(totals)));
		return null;
	}// end with getSalesByPublisher()

	/**
	 * This method save a new publisher in database
	 * 
	 * @return
	 */
	public String savePublisher() {

		String sql = "insert into publisher(Publisher_Name) values(?)";
		try {
			if (pdao.insertPublisher(sql) == 1) {

				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(strings
										.getString("insertedPublisher")));
			}
		} catch (SQLException e) {

			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(strings
									.getString("duplicatedPublishers")));
			log.error(e.getMessage());

		}
		return "adminInsertPublisher";

	}// end of savePublisher()

	/**
	 * This method gets the id of a publisher by its name
	 * @author Sandro Victoria Arena and Ian Ozturk 
	 * @return
	 */
	public long getPubllisherIdByName() {

		ArrayList<PublisherBean> bean = null;

		String sql = "Select * from publisher where Publisher_Name = ?";

		try {

			bean = pdao.getPublisherByName(sql);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return bean.get(0).getPublisherId();
	}

	public String insertBook() {

		if (publ.getPublisherName() != null
				&& !publ.getPublisherName().trim().equals("")) {
			book.setPublisherId(getPubllisherIdByName());
			bManager.insertBook();
			return "adminInsertAuthor";
		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("insertPublisherFail")));

			return null;
		}

	}

}// end of PublisherManager class