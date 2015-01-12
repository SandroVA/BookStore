package g7w14.persistence;

import g7w14.data.InvoiceBean;
import g7w14.interfaces.InvoiceDAOInterface;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 
 * @author Martin Nafekh 1032763
 * 
 *         Connects to the database in order to perform a query or insert
 *         operation on the Invoice table
 */
public class InvoiceDAO implements Serializable, InvoiceDAOInterface {

	private static final long serialVersionUID = 6069279366569212340L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.InvoiceDAOInterface#queryRecords(java.lang.String,
	 * g7w14.data.InvoiceBean)
	 */
	@Override
	public ArrayList<InvoiceBean> queryRecords(String sql,
			InvoiceBean invoiceBean) throws SQLException {
		ArrayList<InvoiceBean> records = new ArrayList<>();

		try (Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setInt(1, invoiceBean.getQuantity());
			statement.setInt(2, invoiceBean.getOrderId());
			statement.setDouble(3, invoiceBean.getNetValue());
			statement.setDouble(4, invoiceBean.getPst());
			statement.setDouble(5, invoiceBean.getGst());
			statement.setDouble(6, invoiceBean.getHst());
			statement.setDouble(7, invoiceBean.getTotalGross());

			try (ResultSet results = statement.executeQuery()) {
				while (results.next()) {
					InvoiceBean invoice = new InvoiceBean();
					invoice.setInvoiceId(results.getInt("InvoiceId"));
					invoice.setQuantity(results.getInt("Quantity"));
					invoice.setOrderId(results.getInt("OrderId"));
					invoice.setNetValue(results.getDouble("Net_Value"));
					invoice.setPst(results.getDouble("PST"));
					invoice.setGst(results.getDouble("GST"));
					invoice.setHst(results.getDouble("HST"));
					invoice.setTotalGross(results.getDouble("Total_Gross"));
					records.add(invoice);
				}
			}

		}

		return records;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.InvoiceDAOInterface#insertRecord(java.lang.String,
	 * g7w14.data.InvoiceBean)
	 */
	@Override
	public int insertRecord(String sql, InvoiceBean invoice)
			throws SQLException {
		int results = 0;

		try (Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, invoice.getQuantity());
			statement.setInt(2, invoice.getOrderId());
			statement.setDouble(3, invoice.getNetValue());
			statement.setDouble(4, invoice.getPst());
			statement.setDouble(5, invoice.getGst());
			statement.setDouble(6, invoice.getHst());
			statement.setDouble(7, invoice.getTotalGross());

			results = statement.executeUpdate();
		}

		return results;
	}
}
