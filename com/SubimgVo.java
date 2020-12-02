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
	public void setMsg(String errmsg) {
		this.msg = errmsg;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	@Override
	public String toString() {
		return "SubimgVo [email=" + email + ", collectionName=" + collectionName + ", filename=" + filename
				+ ", imgPath=" + imgPath + ", faceId=" + faceId + ", msg=" + msg + ", errcode=" + errcode + "]";
	}
	
	
}
