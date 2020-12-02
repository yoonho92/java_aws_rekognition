

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.SearchFacesRequest;
import com.amazonaws.services.rekognition.model.SearchFacesResult;
import java.util.ArrayList;
import java.util.List;

public class SearchFaceMatchingIdCollection {

	public ArrayList<AwsVo> SearchFaceMatchingIdCollectionact(String collectionId, String faceId) {
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AwsVo AwsVo = new AwsVo();
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		SearchFacesRequest searchFacesRequest = new SearchFacesRequest().withCollectionId(collectionId)
				.withFaceId(faceId).withFaceMatchThreshold(70F).withMaxFaces(1);
		try {
		SearchFacesResult searchFacesByIdResult = rekognitionClient.searchFaces(searchFacesRequest);
		List<FaceMatch> faceImageMatches = searchFacesByIdResult.getFaceMatches();
		for (FaceMatch face : faceImageMatches) {
			AwsVo = new AwsVo();
			AwsVo.setAction("SearchFaceMatchingImageCollection");
			AwsVo.setCollectionId(collectionId);
			AwsVo.setMFconfidence(face.getFace().getConfidence().toString());
			AwsVo.setMFfilename(face.getFace().getExternalImageId());
			AwsVo.setMFid(face.getFace().getFaceId());
			AwsVo.setMFsimilarity(face.getSimilarity().toString());
			AwsVo.setFilename(faceId);
			
			voList.add(AwsVo);
		}}catch (AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			AwsVo.setAction("SearchFaceMatchingImageCollection");
			AwsVo.setCollectionId(collectionId);
			AwsVo.setFilename(faceId);
			AwsVo.setState("Error");
			voList.add(AwsVo);
			return voList;
			}	
		return voList;
	}
}