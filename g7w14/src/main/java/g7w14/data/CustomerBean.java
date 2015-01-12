/**
 * 
 */
package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Customer bean that represents a customer in the customer table
 * @author Ian Ozturk
 * 
 */
@Named
@SessionScoped
public class CustomerBean implements Serializable {

	
	private static final long serialVersionUID = 1456358475465468L;

	private long customerId;
	private long userId;	
	private String title;
	private String l_Name;
	private String f_Name;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String country;
	private String postal_Code;
	private String home_Phone;
	private String cell_Phone;
	private String email;
	private boolean editable;
	private double sales;

	public CustomerBean() {
		super();

		title = "";
		l_Name = "";
		f_Name = "";
		company = "";
		address1 = "";
		address2 = "";
		city = "";
		province = "";
		country = "";
		postal_Code = "";
		home_Phone = "";
		cell_Phone = "";
		email = "";
		editable = false;
		sales=0.00;
		
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getL_Name() {
		
		return l_Name;
	}

	public void setL_Name(String l_Name) {
		
		if(l_Name.contains("\'"))
		{
			l_Name.replace("\'", "\\'");
		}
		this.l_Name = l_Name;
	}

	public String getF_Name() {
		return f_Name;
	}

	public void setF_Name(String f_Name) {
		
		if(f_Name.contains("\'"))
		{
			f_Name.replace("\'", "\\'");
		}
		this.f_Name = f_Name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostal_Code() {
		return postal_Code;
	}

	public void setPostal_Code(String postal_Code) {
		this.postal_Code = postal_Code;
	}

	public String getHome_Phone() {
		return home_Phone;
	}

	public void setHome_Phone(String home_Phone) {
		this.home_Phone = home_Phone;
	}

	public String getCell_Phone() {
		return cell_Phone;
	}

	public void setCell_Phone(String cell_Phone) {
		this.cell_Phone = cell_Phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result
				+ ((cell_Phone == null) ? 0 : cell_Phone.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((f_Name == null) ? 0 : f_Name.hashCode());
		result = prime * result
				+ ((home_Phone == null) ? 0 : home_Phone.hashCode());
		result = prime * result + ((l_Name == null) ? 0 : l_Name.hashCode());
		result = prime * result
				+ ((postal_Code == null) ? 0 : postal_Code.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		long temp;
		temp = Double.doubleToLongBits(sales);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerBean other = (CustomerBean) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (cell_Phone == null) {
			if (other.cell_Phone != null)
				return false;
		} else if (!cell_Phone.equals(other.cell_Phone))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (customerId != other.customerId)
			return false;
		if (editable != other.editable)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (f_Name == null) {
			if (other.f_Name != null)
				return false;
		} else if (!f_Name.equals(other.f_Name))
			return false;
		if (home_Phone == null) {
			if (other.home_Phone != null)
				return false;
		} else if (!home_Phone.equals(other.home_Phone))
			return false;
		if (l_Name == null) {
			if (other.l_Name != null)
				return false;
		} else if (!l_Name.equals(other.l_Name))
			return false;
		if (postal_Code == null) {
			if (other.postal_Code != null)
				return false;
		} else if (!postal_Code.equals(other.postal_Code))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (Double.doubleToLongBits(sales) != Double
				.doubleToLongBits(other.sales))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerBean [customerId=" + customerId + ", userId=" + userId
				+ ", title=" + title + ", l_Name=" + l_Name + ", f_Name="
				+ f_Name + ", company=" + company + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", province="
				+ province + ", country=" + country + ", postal_Code="
				+ postal_Code + ", home_Phone=" + home_Phone + ", cell_Phone="
				+ cell_Phone + ", email=" + email + ", editable=" + editable
				+ ", sales=" + sales + "]";
	}

	

}
