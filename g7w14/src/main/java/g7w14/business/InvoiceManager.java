package g7w14.business;

import g7w14.data.InvoiceBean;
import g7w14.enums.OperationType;
import g7w14.persistence.InvoiceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates the SQL to insert or search for records in the Invoice table
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@Named("invoiceManager")
@RequestScoped
public class InvoiceManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()
			.getName());
	@Inject
	private InvoiceDAO invoice;
	private ArrayList<Object> statement;
	private ArrayList<Object> values;
	private ArrayList<InvoiceBean> data;

	/**
	 * This method insert a record in the invoice table
	 * 
	 * @param invoiceBean
	 *            - InvoiceBean - data for inserting
	 * @return - result of the operation *
	 */
	@SuppressWarnings("unchecked")
	public int insertRecord(InvoiceBean invoiceBean) {

		String selectStatement = "Insert into invoice(";
		int result = 0;

		statement = createSQL(selectStatement, OperationType.INSERT,
				invoiceBean);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);

		try {
			result = invoice.insertRecord(selectStatement, invoiceBean);
		} catch (SQLException e) {
			logger.error("Error in inserting a record in invoice table", e);
		}

		return result;
	}// end of insertRecord()

	private void resetLists() {
		values = new ArrayList<Object>();
		data = new ArrayList<InvoiceBean>();
	}

	/**
	 * Searches the invoice table for record(s) matching the passed bean
	 * 
	 * @param invoice
	 * @return ArrayList of matching record(s)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<InvoiceBean> search(InvoiceBean invoice) {
		String selectStatement = "select * from invoice where ";

		statement = createSQL(selectStatement, OperationType.SEARCH, invoice);

		if (statement != null) {
			selectStatement = (String) statement.get(0);
			values = (ArrayList<Object>) statement.get(1);

			try {
				data = (ArrayList<InvoiceBean>) this.invoice.queryRecords(
						selectStatement, invoice);
			} catch (SQLException e) {
				logger.error("Error in searching a record in invoice table", e);
			}
		}

		return data;
	}

	/**
	 * Generates the SQL statement required to perform the passed operation type
	 * 
	 * @param selectStatement
	 * @param type
	 *            enum
	 * @param invoice
	 *            bean
	 * @return arraylist containing select statement and values
	 */
	private ArrayList<Object> createSQL(String selectStatement,
			OperationType type, InvoiceBean invoice) {
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
			if (invoice.getInvoiceId() != -1) {
				selectStatement += ("InvoiceId" + addition);
				values.add(new Long(invoice.getInvoiceId()));
			}
		}

		if (invoice.getQuantity() != 0) {
			selectStatement += "Quantity" + addition;
			values.add(invoice.getQuantity());
		}

		if (invoice.getOrderId() != -1) {
			selectStatement += "OrderId" + addition;
			values.add(invoice.getOrderId());
		}

		if (invoice.getNetValue() != 0) {
			selectStatement += "Net_Value" + addition;
			values.add(invoice.getNetValue());
		}

		if (invoice.getPst() != 0) {
			selectStatement += "PST" + addition;
			values.add(invoice.getPst());
		}

		if (invoice.getGst() != 0) {
			selectStatement += "GST" + addition;
			values.add(invoice.getGst());
		}

		if (invoice.getHst() != 0) {
			selectStatement += "HST" + addition;
			values.add(invoice.getHst());
		}

		if (invoice.getTotalGross() != 0) {
			selectStatement += "Total_Gross" + addition;
			values.add(invoice.getTotalGross());
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
		default:
			if (selectStatement.length() == start) {
				return null;
			}

			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 4));
			break;
		}

		logger.info(selectStatement, type);
		statement.add(selectStatement);

		statement.add(values);
		return statement;
	}
}
