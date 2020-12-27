


import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.ListFacesRequest;
import com.amazonaws.services.rekognition.model.ListFacesResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;


public class ListFacesInCollection {
//aws서버에서 매개변수로 받은 collection을 조회하여 해당 collection 내의 이미지정보를 반환하는 클래스
	public ArrayList<SubimgVo> ListFacesInCollectionact(String collectionName,String email) throws JsonProcessingException {
		
		ArrayList<AwsVo> voList=new ArrayList<AwsVo>();
		ArrayList<SubimgVo> SubimgVoList=new ArrayList<SubimgVo>();
		AwsVo AwsVo = new AwsVo();
		SubimgVo SubimgVo = new SubimgVo();
		
		String collectionId = email.substring(0,email.lastIndexOf('@')) + '.'+collectionName;
		if(collectionName == "CautionGroup") collectionId = collectionName;
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		ListFacesResult listFacesResult = null;
		System.out.println("Faces in collection " + collectionName);
		String paginationToken = null;
		do {
			if (listFacesResult != null) {
				paginationToken = listFacesResult.getNextToken();
			}
			ListFacesRequest listFacesRequest = new ListFacesRequest().withCollectionId(collectionId).withMaxResults(1)
					.withNextToken(paginationToken);
			try {
			listFacesResult = rekognitionClient.listFaces(listFacesRequest);
			}catch(AmazonRekognitionException e){
				SubimgVo =new Error().Erroract(e,SubimgVo);
				voList.add(AwsVo);
				
			}		
			List<Face> faces = listFacesResult.getFaces();
			for (Face face : faces) {
				AwsVo = new AwsVo();
				AwsVo.setFaceId(face.getFaceId());
				AwsVo.setAction("ListFacesInCollection");
				AwsVo.setCollectionName(collectionName);
				AwsVo.setCollectionId(collectionId);
				AwsVo.setFilename(face.getExternalImageId());
				AwsVo.setEmail(email);
				voList.add(AwsVo);

				SubimgVo= new SubimgVo();
				SubimgVo.setCollectionName(collectionName);
				SubimgVo.setEmail(email);
				SubimgVo.setFilename(face.getExternalImageId());
				SubimgVo.setFaceId(face.getFaceId());
				String imgPath = uploadController.subimgPath +File.separator+email+File.separator+collectionName+File.separator+"subimg"+File.separator+face.getExternalImageId();
				SubimgVo.setImgPath(imgPath);
				SubimgVoList.add(SubimgVo);
			}			 
		} while (listFacesResult != null && listFacesResult.getNextToken() != null);
		return SubimgVoList;
	}	
}
