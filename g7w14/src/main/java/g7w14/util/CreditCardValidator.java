package g7w14.util;

import g7w14.data.CreditCardBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This is a custom validator that must implement the Validator interface.
 * The annotation provides the name that is used in the tag
 * This implementation performs the Luhn test
 *
 *@author Ian Ozturk
 */
@FacesValidator("g7w14.util.CreditCardValidator")
public class CreditCardValidator implements Validator {
	public void validate(FacesContext context, UIComponent component,
			Object value) {
		if (value == null)
			return;
		String cardNumber;
		if (value instanceof CreditCardBean)
			cardNumber = value.toString();
		else
			cardNumber = value.toString().replaceAll("\\D", ""); // remove
																	// non-digits
		if (!luhnCheck(cardNumber)) {
			FacesMessage message = g7w14.util.Messages.getMessage(
					"src/main/resources/validation_messages", "badLuhnCheck", null);
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	private static boolean luhnCheck(String cardNumber) {
		int sum = 0;

		for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
			sum += Integer.parseInt(cardNumber.substring(i, i + 1));
			if (i > 0) {
				int d = 2 * Integer.parseInt(cardNumber.substring(i - 1, i));
				if (d > 9)
					d -= 9;
				sum += d;
			}
		}

		return sum % 10 == 0;
	}
}
