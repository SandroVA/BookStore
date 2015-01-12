package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * Simple navigation bean
 * @author itcuties
 * http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 * 
 * Modified to use faces-config.xml navigation by Ken Fogel
 * Modified by Svetlana Shopova
 */
@Named
@SessionScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = 1520318172495977648L;

	/**
	 * Redirect to login page.
	 * @return Login page navigation string.
	 */
	public String redirectToLogin() {
		return "redirectToLogin";
	}
	
	/**
	 * Go to login page.
	 * @return Login page navigation string.
	 */
	public String toLogin() {
		return "toLogin";
	}
	
	
	
	/**
	 * Redirect to welcome page.
	 * @return Welcome page navigation string.
	 */
	public String redirectToAdmin() {
		return "redirectToAdmin";
	}
	
	/**
	 * Go to welcome page.
	 * @return Welcome page navigation string.
	 */
	public String toAdmin() {
		return "toAdmin";
	}
	
	/**
	 * Redirect to welcome page.
	 * @return Welcome page navigation string.
	 */
	public String redirectToUser() {
		return "redirectToUser";
	}
	
	/**
	 * Go to welcome page.
	 * @return Welcome page navigation string.
	 */
	public String toUser() {
		return "toUser";
	}
}

