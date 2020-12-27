

import java.util.ArrayList;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest; 
public class CreateCollection  {
	//aws의 서버에 collection을 생성하는 클래스
	public ArrayList<AwsVo> CreateCollectionAct(String email, String collectionName){
	AwsVo AwsVo = new AwsVo();
	ArrayList<AwsVo> voList= new ArrayList<AwsVo>();
	String collectionId = email.substring(0,email.lastIndexOf('@'))+'.'+collectionName;
	AwsVo.setAction("CreateCollection");
	AwsVo.setCollectionName(collectionName);
	AwsVo.setEmail(email);
	AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
	System.out.println("Creating collection: " +collectionId );
	CreateCollectionRequest request = new CreateCollectionRequest() .withCollectionId(collectionId);
	try{
		AwsVo.setCollectionId(collectionId);	
		AwsVo.setState("Collection Created");
		AwsVo.setStcode(rekognitionClient.createCollection(request).getStatusCode());
		voList.add(AwsVo);
	}
	catch(AmazonRekognitionException e){
		AwsVo =new Error().Erroract(e, AwsVo);	
		voList.add(AwsVo);
		return voList;
	}
	
	
	return voList;
	
	
}}
