/**
 * Martin Nafekh
 * 1032763
 * LoginActionBean.java
 */
package g7w14.business;

import g7w14.data.UserBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Checks the credentials of the user trying to log in, and returns an action
 * based on their permissions
 * 
 * @author Martin Nafekh 1032763
 */
@Named("loginActionBean")
@RequestScoped

/**
 * @author Sandro Victoria Arena and Ian Ozturk and Martin Nafekh
 * @author 1036757
 *
 */
public class LoginActionBean implements Serializable {
	private static final long serialVersionUID = 3783659296773783595L;
	@Inject
	private UserManager userMgr;
	@Inject
	private UserBean user;

	public String login() {
		ArrayList<UserBean> matches = userMgr.search();

		if (!matches.isEmpty()) {
			UserBean matchedUser;
			matchedUser = matches.get(0);
			if (matchedUser.getPassword().equals(user.getPassword())) {
				if (matchedUser.getUserTypeId() == 2) {
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../admin/secure/adminHome.xhtml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (matchedUser.getUserTypeId() == 1) {
					user.setEditable(true);
					user.setUserId(matchedUser.getUserId());
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		
		return null;
	}
	
	/**
	 * Sets user bean to its default values
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return
	 */
	public String logout() {
		user.setEditable(false);
		user.setUsername("");
		user.setPassword("");
		user.setUserId(-1);
		user.setUserTypeId(-1);
		
		return null;
	}
}
