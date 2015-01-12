package g7w14.data;

import java.io.Serializable;

/**
 * Credit card bean
 * @author Ian Ozturk
 *
 */

public class CreditCardBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -406236497925763557L;
	private String number;

	public CreditCardBean(String number) {
		this.number = number;
	}

	public String toString() {
		return number;
	}
}
