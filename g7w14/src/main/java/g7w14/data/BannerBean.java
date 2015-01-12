package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * BannerBean that contains the url and image name of the banner
 * @author Ian Ozturk and Joseph He
 *
 */

@Named("banner")
@RequestScoped
public class BannerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 33235575670723953L;
	
	private long bannerId;
	private String image_name;
	private String banner_Url;
	private boolean chosen;
	
	
	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public long getBannerId() {
		return bannerId;
	}
	
	public void setBannerId(long bannerId) {
		this.bannerId = bannerId;
	}
	
	public String getImage_name() {
		return image_name;
	}
	
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	
	public String getBanner_Url() {
		return banner_Url;
	}
	
	public void setBanner_Url(String banner_Url) {
		this.banner_Url = banner_Url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bannerId ^ (bannerId >>> 32));
		result = prime * result
				+ ((banner_Url == null) ? 0 : banner_Url.hashCode());
		result = prime * result
				+ ((image_name == null) ? 0 : image_name.hashCode());
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
		BannerBean other = (BannerBean) obj;
		if (bannerId != other.bannerId)
			return false;
		if (banner_Url == null) {
			if (other.banner_Url != null)
				return false;
		} else if (!banner_Url.equals(other.banner_Url))
			return false;
		if (image_name == null) {
			if (other.image_name != null)
				return false;
		} else if (!image_name.equals(other.image_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BannerBean [bannerId=" + bannerId + ", image_name="
				+ image_name + ", banner_Url=" + banner_Url + "]";
	}
	
	
	
}
