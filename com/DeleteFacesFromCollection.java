

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DeleteFacesRequest;
import com.amazonaws.services.rekognition.model.DeleteFacesResult;

import java.util.ArrayList;
import java.util.List;

public class DeleteFacesFromCollection {

	public ArrayList<AwsVo> DeleteFacesFromCollectionact(String collectionId,String facesId[]) {
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AwsVo AwsVo = new AwsVo();
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest().withCollectionId(collectionId)
				.withFaceIds(facesId);
		try {
		DeleteFacesResult deleteFacesResult = rekognitionClient.deleteFaces(deleteFacesRequest);
		List<String> faceRecords = deleteFacesResult.getDeletedFaces();
		System.out.println(Integer.toString(faceRecords.size()) + " face(s) deleted:");
		for (String face : faceRecords) {
			AwsVo = new AwsVo();
			AwsVo.setAction("DeleteFacesFromCollection");
			AwsVo.setCollectionId(collectionId);
			AwsVo.setFaceId(face);//face는 faceId값 들어있음
			AwsVo.setState("Faces Deleted");
			voList.add(AwsVo);
		}
		}catch (AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			AwsVo.setAction("DeleteFacesFromCollection");
			AwsVo.setCollectionId(collectionId);
			voList.add(AwsVo);
			return voList;
			}		
		
		
		return voList;
	}
}
