

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwsVo {
	String name;
	String email;
	String collectionId;
	String collectionName;
	String action;
	String filename;
	String faceId;
	float confidence;
	String state;
	int stcode;
	float boundingBoxLeft;
	float boundingBoxTop;
	float boundingBoxWidth;
	float boundingBoxHeight;
	String errmsg;
	String errcode;
	String MFid;
	String MFfilename;
	String MFsimilarity;
	float MFconfidence;
	int ImageWidth;
	int ImageHeight;
	float MFboundingBoxLeft;
	float MFboundingBoxTop;
	float MFboundingBoxWidth;
	float MFboundingBoxHeight;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getStcode() {
		return stcode;
	}
	public void setStcode(int stcode) {
		this.stcode = stcode;
	}
	public float getBoundingBoxLeft() {
		return boundingBoxLeft;
	}
	public void setBoundingBoxLeft(float boundingBoxLeft) {
		this.boundingBoxLeft = boundingBoxLeft;
	}
	public float getBoundingBoxTop() {
		return boundingBoxTop;
	}
	public void setBoundingBoxTop(float boundingBoxTop) {
		this.boundingBoxTop = boundingBoxTop;
	}
	public float getBoundingBoxWidth() {
		return boundingBoxWidth;
	}
	public void setBoundingBoxWidth(float boundingBoxWidth) {
		this.boundingBoxWidth = boundingBoxWidth;
	}
	public float getBoundingBoxHeight() {
		return boundingBoxHeight;
	}
	public void setBoundingBoxHeight(float boundingBoxHeight) {
		this.boundingBoxHeight = boundingBoxHeight;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getMFid() {
		return MFid;
	}
	public void setMFid(String mFid) {
		MFid = mFid;
	}
	public String getMFfilename() {
		return MFfilename;
	}
	public void setMFfilename(String mFfilename) {
		MFfilename = mFfilename;
	}
	public String getMFsimilarity() {
		return MFsimilarity;
	}
	public void setMFsimilarity(String mFsimilarity) {
		MFsimilarity = mFsimilarity;
	}
	public float getMFconfidence() {
		return MFconfidence;
	}
	public void setMFconfidence(float mFconfidence) {
		MFconfidence = mFconfidence;
	}
	public int getImageWidth() {
		return ImageWidth;
	}
	public void setImageWidth(int imageWidth) {
		ImageWidth = imageWidth;
	}
	public int getImageHeight() {
		return ImageHeight;
	}
	public void setImageHeight(int imageHeight) {
		ImageHeight = imageHeight;
	}
	public float getMFboundingBoxLeft() {
		return MFboundingBoxLeft;
	}
	public void setMFboundingBoxLeft(float mFboundingBoxLeft) {
		MFboundingBoxLeft = mFboundingBoxLeft;
	}
	public float getMFboundingBoxTop() {
		return MFboundingBoxTop;
	}
	public void setMFboundingBoxTop(float mFboundingBoxTop) {
		MFboundingBoxTop = mFboundingBoxTop;
	}
	public float getMFboundingBoxWidth() {
		return MFboundingBoxWidth;
	}
	public void setMFboundingBoxWidth(float mFboundingBoxWidth) {
		MFboundingBoxWidth = mFboundingBoxWidth;
	}
	public float getMFboundingBoxHeight() {
		return MFboundingBoxHeight;
	}
	public void setMFboundingBoxHeight(float mFboundingBoxHeight) {
		MFboundingBoxHeight = mFboundingBoxHeight;
	}
	@Override
	public String toString() {
		return "AwsVo [name=" + name + ", email=" + email + ", collectionId=" + collectionId + ", collectionName="
				+ collectionName + ", action=" + action + ", filename=" + filename + ", faceId=" + faceId
				+ ", confidence=" + confidence + ", state=" + state + ", stcode=" + stcode + ", boundingBoxLeft="
				+ boundingBoxLeft + ", boundingBoxTop=" + boundingBoxTop + ", boundingBoxWidth=" + boundingBoxWidth
				+ ", boundingBoxHeight=" + boundingBoxHeight + ", errmsg=" + errmsg + ", errcode=" + errcode + ", MFid="
				+ MFid + ", MFfilename=" + MFfilename + ", MFsimilarity=" + MFsimilarity + ", MFconfidence="
				+ MFconfidence + ", ImageWidth=" + ImageWidth + ", ImageHeight=" + ImageHeight + ", MFboundingBoxLeft="
				+ MFboundingBoxLeft + ", MFboundingBoxTop=" + MFboundingBoxTop + ", MFboundingBoxWidth="
				+ MFboundingBoxWidth + ", MFboundingBoxHeight=" + MFboundingBoxHeight + "]";
	}
	
	
}
