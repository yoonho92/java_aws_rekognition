
import java.util.ArrayList;
import java.util.List;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;

public class ListCollections {
	public ArrayList<AwsVo> ListCollectionact(String email,String name) {
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		AwsVo AwsVo = new AwsVo();
		List<String> collectionIds;
		AmazonRekognition amazonRekognition = AmazonRekognitionClientBuilder.defaultClient();
		
		int limit = 10;
		ListCollectionsResult listCollectionsResult = null;
		String paginationToken = null;
		do {
			if (listCollectionsResult != null) {
				paginationToken = listCollectionsResult.getNextToken();
			}
			ListCollectionsRequest listCollectionsRequest = new ListCollectionsRequest().withMaxResults(limit)
					.withNextToken(paginationToken);
			try {
			listCollectionsResult = amazonRekognition.listCollections(listCollectionsRequest);
			}catch(AmazonRekognitionException e){
				AwsVo =new Error().Erroract(e, AwsVo);				
			
				AwsVo.setState("Error");
				AwsVo.setEmail(email);
				AwsVo.setName(name);
				voList.add(AwsVo);
				return voList;
			}
				
			collectionIds = listCollectionsResult.getCollectionIds();
			
			for (String collectionId : collectionIds) {
				AwsVo= new AwsVo();
				AwsVo.setCollectionId(collectionId);
				AwsVo.setAction("ListCollections");
				AwsVo.setState("Collection Listed");
				AwsVo.setStcode(200);
				AwsVo.setEmail(email);
				AwsVo.setName(name);
				voList.add(AwsVo);
				
			}
		} while (listCollectionsResult != null && listCollectionsResult.getNextToken() != null);
		return voList;
	}
}