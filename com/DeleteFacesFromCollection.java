


import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DeleteFacesRequest;
import com.amazonaws.services.rekognition.model.DeleteFacesResult;

import java.util.ArrayList;
import java.util.List;

public class DeleteFacesFromCollection {
	//aws의 서버에서 업로드한 이미지를 collection에서 삭제하는 클래스
	public ArrayList<AwsVo> DeleteFacesFromCollectionact(String email,String collectionName,String facesId[]) {
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AwsVo AwsVo = new AwsVo();
		String collectionId = email.substring(0,email.lastIndexOf('@'))+'.'+collectionName;
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest().withCollectionId(collectionId)
				.withFaceIds(facesId);
		try {
		DeleteFacesResult deleteFacesResult = rekognitionClient.deleteFaces(deleteFacesRequest);
		List<String> faceRecords = deleteFacesResult.getDeletedFaces();

		System.out.println(Integer.toString(faceRecords.size()) + " face(s) deleted:");
		for (String face : faceRecords) {
			AwsVo = new AwsVo();
			AwsVo.setEmail(email);
			AwsVo.setAction("DeleteFacesFromCollection");
			AwsVo.setCollectionId(collectionId);
			AwsVo.setFaceId(face);//face는 faceId값 들어있음
			AwsVo.setState(face+ "가 삭제 되었습니다.");
			AwsVo.setStcode(200);
			voList.add(AwsVo);
		}
		}catch (AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			AwsVo.setAction("DeleteFacesFromCollection");
			AwsVo.setCollectionId(collectionId);
			voList.add(AwsVo);
			System.out.println(voList);
			return voList;
			}		
		
		
		return voList;
	}
}
