package g7w14.interfaces;

import g7w14.data.InvoiceBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for the Invoice data access object
 * 
 * @author Martin Nafekh 1032763
 * 
 */
public interface InvoiceDAOInterface {

	/**
	 * Receives a SELECT statement and returns the result of the query in an
	 * ArrayList
	 * 
	 * @param sql
	 *            passed SELECT statement
	 * @return ArrayList of query results
	 * @throws SQLException
	 */
	public ArrayList<InvoiceBean> queryRecords(String sql,
			InvoiceBean invoiceBean) throws SQLException;

	/**
	 * Inserts the passed invoice object into the database
	 * 
	 * @param sql
	 *            Prepared INSERT statement
	 * @param invoice
	 *            to be inserted
	 * @return 1 if success, 0 if fail
	 * @throws SQLException
	 */
	public int insertRecord(String sql, InvoiceBean invoice)
			throws SQLException;

}
