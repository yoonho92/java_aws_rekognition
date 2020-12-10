import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubimgVo {
	String email;
	String collectionName;
	String filename;
	String imgPath;
	String faceId;
	String msg;
	String errcode;
	String Date;
	String Index;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getIndex() {
		return Index;
	}
	public void setIndex(String index) {
		Index = index;
	}
	@Override
	public String toString() {
		return "SubimgVo [email=" + email + ", collectionName=" + collectionName + ", filename=" + filename
				+ ", imgPath=" + imgPath + ", faceId=" + faceId + ", msg=" + msg + ", errcode=" + errcode + ", Date="
				+ Date + ", Index=" + Index + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Date == null) ? 0 : Date.hashCode());
		result = prime * result + ((Index == null) ? 0 : Index.hashCode());
		result = prime * result + ((collectionName == null) ? 0 : collectionName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((errcode == null) ? 0 : errcode.hashCode());
		result = prime * result + ((faceId == null) ? 0 : faceId.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((imgPath == null) ? 0 : imgPath.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
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
		SubimgVo other = (SubimgVo) obj;
		if (Date == null) {
			if (other.Date != null)
				return false;
		} else if (!Date.equals(other.Date))
			return false;
		if (Index == null) {
			if (other.Index != null)
				return false;
		} else if (!Index.equals(other.Index))
			return false;
		if (collectionName == null) {
			if (other.collectionName != null)
				return false;
		} else if (!collectionName.equals(other.collectionName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (errcode == null) {
			if (other.errcode != null)
				return false;
		} else if (!errcode.equals(other.errcode))
			return false;
		if (faceId == null) {
			if (other.faceId != null)
				return false;
		} else if (!faceId.equals(other.faceId))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (imgPath == null) {
			if (other.imgPath != null)
				return false;
		} else if (!imgPath.equals(other.imgPath))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		return true;
	}
	

	
}
