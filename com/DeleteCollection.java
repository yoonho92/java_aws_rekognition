
import java.util.ArrayList;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DeleteCollectionRequest; 
public class DeleteCollection {
	public ArrayList<AwsVo> DeleteCollectionact(String email,String collectionName) {
		AwsVo AwsVo = new AwsVo();
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AwsVo.setEmail(email);
		AwsVo.setCollectionName(collectionName);
		String collectionId = email.substring(0,email.lastIndexOf('@'))+'.'+collectionName;
		AwsVo.setCollectionId(collectionId);
		AwsVo.setAction("DeleteCollection");		
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		System.out.println("Deleting collections");
		DeleteCollectionRequest request = new DeleteCollectionRequest() .withCollectionId(collectionId);
		try{
		rekognitionClient.deleteCollection(request);
		AwsVo.setState("Collection Deleted");
		AwsVo.setStcode(200);
		voList.add(AwsVo);
		}catch(AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			voList.add(AwsVo);
			return voList;
		}
		
		
		return voList;
		
	}
}
