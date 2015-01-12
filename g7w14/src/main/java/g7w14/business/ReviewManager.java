package g7w14.business;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import g7w14.data.ReviewBean;
import g7w14.data.UserBean;
import g7w14.enums.OperationType;
import g7w14.persistence.ReviewDAO;

import org.apache.openejb.util.Logger;

/**
 * This class dynamically creates sql statements and send them to Review data
 * object. It controls selecting, inserting, selecting and updating of the
 * appointments table of the database.
 * 
 * @author Sandro Victoria Arena, Ian Ozturk, Svetlana Shopova
 * @since 2.13.2014
 */
@Named("reviewManager")
@SessionScoped
public class ReviewManager implements Serializable {

	private static final long serialVersionUID = -8672494037644172822L;
	@Inject
	private ReviewDAO review;
	@Inject
	private ReviewBean reviewBean;
	@Inject
	private UserBean user;
	@Inject
	private UserPreferencesManager preferences;
	@Inject
	private CustomerManager custManager;
	private ArrayList<ReviewBean> data;
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	private ArrayList<Object> statement;
	private ArrayList<Object> values;
	private ArrayList<ReviewBean> allReviews;
	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());

	/**
	 * This method deletes a record in the table Customer_Review
	 * 
	 * @param Review
	 *            - Review that will be deleted
	 * @return int result of the operation
	 * 
	 */
	public int deleteRecord() {

		int result = 0;
		try {
			result = review.deleteRecord(
					"Delete from customer_review where ReviewId = ?",
					reviewBean.getReviewId());
		} catch (SQLException e) {
			log.error("Error in deleting a record in customer_review table", e);
		}

		return result;
	}// end of deleteRecord()

	/**
	 * This method inserts a record in the table Customer_Review
	 * 
	 * @param Review
	 *            - Review that will be inserted
	 * @return int result of the operation
	 * 
	 */
	@SuppressWarnings("unchecked")
	public int insertRecord() {
		
		String selectStatement = "Insert into customer_review(";
		int result = 0;

		statement = createSQL(selectStatement, OperationType.INSERT, reviewBean);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		try {
			result = review.insertRecord(selectStatement, values);
		} catch (SQLException e) {

			log.error("Error in inserting a record in customer_review table", e);

		}

		return result;
	}// end of insertRecord()
	
	/**
	 * Inserts a review in the review table
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String insertReview() {
			reviewBean.setBookId(preferences.getBookId());
			reviewBean.setCustomerId(custManager.getCustomerId());
			reviewBean.setApproved(false);

			String selectStatement = "Insert into customer_review(";
			
			statement = createSQL(selectStatement, OperationType.INSERT, reviewBean);
			selectStatement = (String) statement.get(0);
			values = (ArrayList<Object>) statement.get(1);

			try {
				review.insertRecord(selectStatement, values);
			} catch (SQLException e) {

				log.error("Error in inserting a record in customer_review table", e);

			}
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("userGetBook.xhtml?bookId=" + preferences.getBookId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}// end of insertRecord()
	
	/**
	 * Retrieves the 5 latest reviews in the review table
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return data List of all reviews
	 */
	public ArrayList<ReviewBean> retrieveLatestReviews() {
		data = null;
		try {
			data = review.getAll();
		} catch (SQLException e) {

			log.error("Error in retrieving a record in Review table", e);
			return null;
		}
		
		ArrayList<ReviewBean> latestReviews = new ArrayList<ReviewBean>();
		int y = 0;
		for(int i = 1; y < 5 && i < data.size()+1; i++) {
			if (data.get(data.size()-i) != null && preferences.getBookId() == data.get(data.size() - i).getBookId()) {
				latestReviews.add(data.get(data.size()-i));
				y++;
			}
		}
		
		return latestReviews;
	}

	/**
	 * This method updates an existing record in the table Customer_Review
	 * 
	 * @param Review
	 *            - Review that will be updated
	 * @return int result of the operation
	 * 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String updateRecord() {

		String selectStatement = "Update customer_review set ";
		int result = 0;

		statement = createSQL(selectStatement, OperationType.UPDATE, reviewBean);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		try {
			result = review.updateRecord(reviewBean.getReviewId(), selectStatement,
					values);
		} catch (SQLException e) {

			log.error("Error in updating a record in Customer_Review table", e);
		}

		return null;
	}// end of updateRecord()

	/**
	 * This method selects the records of the Customer_Review table with respect
	 * to certain conditions
	 * 
	 * @param Review
	 *            - Review that will be searched
	 * @return ArrayList of all the ReviewBeans found
	 * 
	 */
	public ArrayList<ReviewBean> search(ReviewBean bean) {

		String selectStatement = "select * from customer_review where ";

		statement = createSQL(selectStatement, OperationType.SEARCH, bean);
		if (statement != null) {
			selectStatement = (String) statement.get(0);

			try {
				allReviews = review.getReviews(selectStatement);
			} catch (SQLException e) {

				log.error(
						"Error in searching a record in customer_review table",
						e);
			}
		}
		return allReviews;
	}// end of search()

	/**
	 * This method select all records of the table
	 * 
	 * @return ArrayList of all the ReviewBeans
	 * @author Svetlana Shopova
	 */
	public ArrayList<ReviewBean> getAll() {
		try {
			allReviews = review.getAll();
		} catch (SQLException e) {

			log.error("Error in searching a record in customer_review table", e);
		}

		return allReviews;
	}// end of search()

	private void resetLists() {
		// keeps values of the fields which have not null value
		values = new ArrayList<Object>();

		allReviews = new ArrayList<ReviewBean>();
	}

	/**
	 * This method creates the SQL needed to Insert, Update or SEARCH in the
	 * Customer_Review table.
	 * 
	 * @param selectStatement
	 *            - An incomplete SQL statement
	 * @param type
	 *            - The operation type (Either INSERT, UPDATE or SEARCH)
	 * @param Review
	 *            - Review that will be searched, updated or inserted
	 * 
	 * @return The complete SQL statement
	 * 
	 */
	private ArrayList<Object> createSQL(String selectStatement,
			OperationType type, ReviewBean bean) {
		resetLists();
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


		if (bean.getReviewId() > 0l) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "ReviewId=? and";
				values.add(new Long(bean.getReviewId()));
			}
			else
				selectStatement = selectStatement + "ReviewId" + addition;
				values.add(new Long(bean.getReviewId()));
		}

		if (bean.getReview_Date() != null
				&& bean.getReview_Date().after(new Timestamp(0))) {
			selectStatement = selectStatement + "Review_Date" + addition;
			values.add(bean.getReview_Date());
		}


		if (bean.getCustomerId() > 0l) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "CustomerId" + addition;
				values.add(new Long(bean.getCustomerId()));
			}
			else {
				selectStatement = selectStatement + "CustomerId" + addition;
				values.add(new Long(bean.getCustomerId()));
			}
		}

		if (bean.getRating() > -1) {
			selectStatement = selectStatement + "Rating" + addition;
			values.add(bean.getRating());
		}

		if (!bean.getReviewText().trim().equals("")) {
			selectStatement = selectStatement + "Review_Text" + addition;
			if (type == OperationType.SEARCH)
				values.add("%" + bean.getReviewText() + "%");
			else
				values.add(bean.getReviewText());

		} else {
			if (type.equals(OperationType.UPDATE)) {
				selectStatement = selectStatement + "Review_Text" + addition;
				values.add(bean.getReviewText());
			}
		}

		if (bean.getCustomerId() > 0l) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "BookId" + addition;
				values.add(new Long(bean.getCustomerId()));
			}
			else {
				selectStatement = selectStatement + "BookId" + addition;
				values.add(new Long(bean.getBookId()));
			}
		}

		selectStatement = selectStatement + "approved" + addition;
		values.add(new Boolean(bean.isApproved()));

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
					+ " where ReviewId = ? ";
			break;
		}

		statement.add(selectStatement);
		statement.add(values);
		return statement;
	}// end of createSelectStatement()

	/**
	 * This method update all existing reviews in theri Approved field in
	 * adminReviewManagement.xhtml
	 * 
	 * @author Svetlana Shopova
	 * @return
	 */
	public String approveReview() {

		String sql = "update customer_review set Approved=? where ReviewId=?";
		values = new ArrayList<Object>();
		for (ReviewBean ob : allReviews) {

			try {
				review.approveReview(sql, ob.getReviewId(), ob.isApproved());
			} catch (SQLException e) {
				log.error(e.getMessage());
			}

		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(strings.getString("updatedReviews")));
		return null;
	}//end of approveReview()

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public ArrayList<ReviewBean> getAllReviews() {

		if (allReviews == null) {
			allReviews = getAll();
		}
		return allReviews;
	}//end of getAllReviews()

	/**
	 * @author Svetlana Shopova
	 * @param allReviews
	 */
	public void setAllReviews(ArrayList<ReviewBean> allReviews) {
		this.allReviews = allReviews;
	}//end of setAllReviews()
}
