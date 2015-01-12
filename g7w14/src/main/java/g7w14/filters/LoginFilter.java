package g7w14.filters;

import g7w14.data.LoginBean;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;




/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the login.xhml page.
 * 
 * @author itcuties http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 * 
 *         Changed to annotation from web.xml
 * 
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = { "/admin/secure/*" })
public class LoginFilter implements Filter {

	@Inject
	LoginBean loginBean;
	@SuppressWarnings("unused")
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);


	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		

		/*LoginBean loginBean = (LoginBean) ((HttpServletRequest) request)
				.getSession().getAttribute("loginBean");*/

		// For the first application request there is no loginBean in the
		// session so user needs to log in
		// For other requests loginBean is present but we need to check if user
		// has logged in successfully
		
		if (loginBean == null || !loginBean.isLoggedIn()) {
			

			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath
					+ "/admin/adminLogin.xhtml");
		} else {
			

			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}

}

