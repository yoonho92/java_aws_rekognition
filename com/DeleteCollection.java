
import java.util.ArrayList;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DeleteCollectionRequest; 
public class DeleteCollection {
	public ArrayList<AwsVo> DeleteCollectionact(String collectionId) {
		AwsVo AwsVo = new AwsVo();
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		System.out.println("Deleting collections");
		DeleteCollectionRequest request = new DeleteCollectionRequest() .withCollectionId(collectionId);
		try{
		rekognitionClient.deleteCollection(request);
		AwsVo.setAction("DeleteCollection");
		AwsVo.setCollectionId(collectionId);
		AwsVo.setState("Collection Deleted");
		voList.add(AwsVo);
		}catch(AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			AwsVo.setAction("DeleteCollection");
			AwsVo.setCollectionId(collectionId);
			AwsVo.setState("Error");
			voList.add(AwsVo);
			return voList;
		}
		
		
		return voList;
		
	}
}
