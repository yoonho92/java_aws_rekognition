

import java.util.ArrayList;
import java.util.List;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;

public class ListCollections {
	//aws의 서버에서 collection 목록을 불러와 반환 하는 클래스
	public ArrayList<AwsVo> ListCollectionact(String email) {
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
				voList.add(AwsVo);
				return voList;
			}
				
			collectionIds = listCollectionsResult.getCollectionIds();
			
			for (String collectionId : collectionIds) {
				//colectionId는 email 앞부분과 collectionName으로 이루어져있으므로 요청한 당사자의 collection list만을 보여주기 위한 if문
				if(!collectionId.substring(0,collectionId.lastIndexOf('.')).equals(email.substring(0,email.lastIndexOf('@')))||collectionId.contains("GuestGroup")) continue;
				AwsVo= new AwsVo();
				AwsVo.setCollectionId(collectionId);
				AwsVo.setCollectionName(collectionId.substring(collectionId.lastIndexOf('.')+1));
				AwsVo.setAction("ListCollections");
				AwsVo.setState("Collection Listed");
				AwsVo.setStcode(200);
				AwsVo.setEmail(email);
				voList.add(AwsVo);
			
			}
		} while (listCollectionsResult != null && listCollectionsResult.getNextToken() != null);
		return voList;
	}
}