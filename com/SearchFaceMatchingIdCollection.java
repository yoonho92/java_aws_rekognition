

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.SearchFacesRequest;
import com.amazonaws.services.rekognition.model.SearchFacesResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
//GuestList에서 쓰기위한 VO SubimgVo 추가
public class SearchFaceMatchingIdCollection {

	public ArrayList<SubimgVo> SearchFaceMatchingIdCollectionact(String email, String collectionName, String faceId) {
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		ArrayList<SubimgVo> voSubList = new ArrayList<SubimgVo>();
		AwsVo AwsVo = new AwsVo();
		SubimgVo SubimgVo = new SubimgVo();
		String collectionId = email.substring(0,email.lastIndexOf('@'))+'.'+collectionName;
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		SearchFacesRequest searchFacesRequest = new SearchFacesRequest().withCollectionId(collectionId)
				.withFaceId(faceId).withFaceMatchThreshold(70F).withMaxFaces(10);
		try {
		SearchFacesResult searchFacesByIdResult = rekognitionClient.searchFaces(searchFacesRequest);
		List<FaceMatch> faceImageMatches = searchFacesByIdResult.getFaceMatches();
		for (FaceMatch face : faceImageMatches) {
			AwsVo = new AwsVo();
			AwsVo.setAction("SearchFaceMatchingImageCollection");
			AwsVo.setCollectionId(collectionId);
			
		
			AwsVo.setMFconfidence(face.getFace().getConfidence());
			AwsVo.setMFid(face.getFace().getFaceId());
			AwsVo.setMFsimilarity(face.getSimilarity().toString());			
			AwsVo.setMFfilename(face.getFace().getExternalImageId());
			AwsVo.setMFboundingBoxLeft(face.getFace().getBoundingBox().getLeft());
			AwsVo.setMFboundingBoxTop(face.getFace().getBoundingBox().getTop());
			AwsVo.setMFboundingBoxWidth(face.getFace().getBoundingBox().getWidth());
			AwsVo.setMFboundingBoxHeight(face.getFace().getBoundingBox().getHeight());
			
			voList.add(AwsVo);
			
			SubimgVo= new SubimgVo();
			SubimgVo.setCollectionName(collectionName);
			SubimgVo.setEmail(email);
			SubimgVo.setFilename(face.getFace().getExternalImageId());
			SubimgVo.setFaceId(face.getFace().getFaceId());
			String imgPath ="http://localhost:8080/localTest/img"+File.separator+email+File.separator+collectionName+File.separator+"subimg"+File.separator+face.getFace().getExternalImageId();
			SubimgVo.setImgPath(imgPath);
			
			voSubList.add(SubimgVo);
			
		}}catch (AmazonRekognitionException e) {
			SubimgVo =new Error().Erroract(e, SubimgVo);
			SubimgVo.setFilename(faceId);
			voSubList.add(SubimgVo);
			return voSubList;
			}	
		return voSubList;
	}
}