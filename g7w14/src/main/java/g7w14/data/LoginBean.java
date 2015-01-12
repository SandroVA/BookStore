package g7w14.data;

import g7w14.business.UserManager;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Simple login bean.
 * 
 * @author itcuties http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 * 
 *         Modified to use faces-config.xml navigation by Ken Fogel Modified by
 *         Svetlana Shopova
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 7765876811740798583L;

	private String username;
	private String password;

	private boolean loggedIn;

	@Inject
	private NavigationBean navigationBean;
	@Inject
	private UserManager userManager;

	/**
	 * Login operation.
	 * 
	 * @return
	 */
	public String doLogin() {
		// Get every user from our sample database :)
		ArrayList<UserBean> users = userManager.getAllUsers();

		for (UserBean user : users) {
			String dbUsername = user.getUsername();
			String dbPassword = user.getPassword();
			long userTypeId = user.getUserTypeId();

			// Successful login
			if (dbUsername.equals(username) && dbPassword.equals(password)
					&& userTypeId == 2) {
				loggedIn = true;
				return navigationBean.toAdmin();
			}

		}

		// Set login ERROR
		FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		// To to login page
		return navigationBean.toLogin();

	}

	/**
	 * Logout operation.
	 * 
	 * @return
	 */
	public String doLogout() {
		// Set the parameter indicating that user is logged in to false
		loggedIn = false;

		// Set logout message
		FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return navigationBean.toLogin();
	}

	// ------------------------------
	// Getters & Setters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
