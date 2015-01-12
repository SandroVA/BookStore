/**
 * 
 */
package g7w14.data;

import java.io.Serializable;

/**
 * This class represents the file uploaded. It sets the mime type so that the
 * file can be properly decoded.
 * 
 * The Richfaces live demo site source code requires it but the file is not
 * available there. Instead it is found at:
 * http://grepcode.com/file/repository.jboss
 * .org/nexus/content/repositories/releases
 * /org.richfaces.examples/richfaces-showcase
 * /4.1.0.CR2/org/richfaces/demo/fileupload/UploadedImage.java
 */
public class UploadedImage implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5143985518442946501L;
	private String name;
	private String mime;
	private long length;
	private byte[] data;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		int extDot = name.lastIndexOf('.');
		if (extDot > 0) {
			String extension = name.substring(extDot + 1);
			if ("bmp".equals(extension)) {
				mime = "image/bmp";
			} else if ("jpg".equals(extension)) {
				mime = "image/jpeg";
			} else if ("gif".equals(extension)) {
				mime = "image/gif";
			} else if ("png".equals(extension)) {
				mime = "image/png";
			} else {
				mime = "image/unknown";
			}
		}
		int sep = name.lastIndexOf(System.getProperty("file.separator"));
		if (sep > 0) {
			this.name = name.substring(sep + 1);
		}
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getMime() {
		return mime;
	}
}
