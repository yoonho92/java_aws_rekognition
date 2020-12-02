

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;

import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class SearchFaceMatchingImageCollection {
	public ArrayList<AwsVo> SearchFaceMatchingImageCollectionact(String email, String collectionName,
			String fileName){
		AwsVo AwsVo = new AwsVo();
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

		Image image = new AwsPath(uploadController.ImagePath+File.separator+email+File.separator + fileName).photo_source;
// Search collection for faces similar to the largest face in the image.
		String collectionId = email.substring(0,email.lastIndexOf('@'))+'.'+collectionName;
		SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
				.withCollectionId(collectionId).withImage(image).withFaceMatchThreshold(70F).withMaxFaces(1);

		try {
		SearchFacesByImageResult searchFacesByImageResult = rekognitionClient
				.searchFacesByImage(searchFacesByImageRequest);
		System.out.println("Faces matching largest face in image from" + fileName);
		List<FaceMatch> faceImageMatches = searchFacesByImageResult.getFaceMatches();
		for (FaceMatch face : faceImageMatches) {
			AwsVo = new AwsVo();
			AwsVo.setAction("SearchFaceMatchingImageCollection");
			AwsVo.setCollectionName(collectionName);
			AwsVo.setEmail(email);
			AwsVo.setFilename(fileName);
			AwsVo.setStcode(200);
			AwsVo.setCollectionId(collectionId);
			AwsVo.setBoundingBoxHeight(searchFacesByImageResult.getSearchedFaceBoundingBox().getHeight());
			AwsVo.setBoundingBoxWidth(searchFacesByImageResult.getSearchedFaceBoundingBox().getWidth());
			AwsVo.setBoundingBoxTop(searchFacesByImageResult.getSearchedFaceBoundingBox().getTop());
			AwsVo.setBoundingBoxLeft(searchFacesByImageResult.getSearchedFaceBoundingBox().getLeft());
			
			AwsVo.setMFconfidence(face.getFace().getConfidence().toString());
			AwsVo.setMFid(face.getFace().getFaceId());
			AwsVo.setMFsimilarity(face.getSimilarity().toString());			
			AwsVo.setMFfilename(face.getFace().getExternalImageId());
			AwsVo.setMFboundingBoxLeft(face.getFace().getBoundingBox().getLeft());
			AwsVo.setMFboundingBoxTop(face.getFace().getBoundingBox().getTop());
			AwsVo.setMFboundingBoxWidth(face.getFace().getBoundingBox().getWidth());
			AwsVo.setMFboundingBoxHeight(face.getFace().getBoundingBox().getHeight());
			System.out.println(AwsVo);
			voList.add(AwsVo);
		}
		}catch (AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			AwsVo.setAction("SearchFaceMatchingImageCollection");
			AwsVo.setCollectionId(collectionId);
			AwsVo.setFilename(fileName);
			AwsVo.setStcode(500);
			voList.add(AwsVo);
			
			return voList;
			}	

		if(voList.isEmpty()) {
			AwsVo.setAction("SearchFaceMatchingImageCollection");
			AwsVo.setEmail(email);
			AwsVo.setFilename(fileName);
			AwsVo.setStcode(400);
			AwsVo.setState("식별된 Face가 없습니다. \n다시 시도해주세요.");
			voList.add(AwsVo);
		}
		System.out.println("성공");
		return voList;
	}
}
