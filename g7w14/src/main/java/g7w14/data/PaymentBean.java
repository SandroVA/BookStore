package g7w14.data;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Payment Bean
 * @author Ian Ozturk
 *
 */

@Named("payment")
@SessionScoped
public class PaymentBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1275486072239341990L;
	private double amount;
	private CreditCardBean card = new CreditCardBean("");
	private Date date = new Date();

	public void setAmount(double newValue) {
		amount = newValue;
	}

	public double getAmount() {
		return amount;
	}

	public void setCard(CreditCardBean newValue) {
		card = newValue;
	}

	public CreditCardBean getCard() {
		return card;
	}

	public void setDate(Date newValue) {
		date = newValue;
	}

	public Date getDate() {
		return date;
	}
}
