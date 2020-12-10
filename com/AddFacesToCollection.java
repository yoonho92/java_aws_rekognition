

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.amazonaws.services.rekognition.model.UnindexedFace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
//얼굴 추가시 저장되는 바운딩박스 메서드 변경 MFbounding -> Bounding
public class AddFacesToCollection {

	public ArrayList<AwsVo> AddFacesToCollectionact(String collectionName, String email,String fileName) {
		AwsVo AwsVo = new AwsVo();
		ArrayList<AwsVo> voList = new ArrayList<AwsVo>();
		String path = uploadController.ImagePath+File.separator+email+File.separator+collectionName;
		
		
		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
		Image photo = new AwsPath(path+File.separator+fileName).photo_source;
		String collectionId = email.substring(0,email.lastIndexOf('@'))+'.'+collectionName;
		IndexFacesRequest indexFacesRequest = new IndexFacesRequest().withImage(photo)
				.withQualityFilter(QualityFilter.AUTO).withMaxFaces(1).withCollectionId(collectionId)
				.withExternalImageId(fileName).withDetectionAttributes("DEFAULT");
		try {
			IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);
			
			List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
			for (FaceRecord faceRecord : faceRecords) {
				//System.out.println(" Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
				AwsVo = new AwsVo();
				AwsVo.setFaceId(faceRecord.getFace().getFaceId());
				AwsVo.setConfidence(faceRecord.getFace().getConfidence());
				AwsVo.setFilename(fileName);
				AwsVo.setEmail(email);	
					
				AwsVo.setCollectionName(collectionName);
				AwsVo.setCollectionId(collectionId);
				AwsVo.setAction("AddFacesToCollectionact");
				AwsVo.setState("Face Added");
				AwsVo.setStcode(200);
				AwsVo.setBoundingBoxLeft(faceRecord.getFace().getBoundingBox().getLeft());
				AwsVo.setBoundingBoxTop(faceRecord.getFace().getBoundingBox().getTop());
				AwsVo.setBoundingBoxWidth(faceRecord.getFace().getBoundingBox().getWidth());
				AwsVo.setBoundingBoxHeight(faceRecord.getFace().getBoundingBox().getHeight());
				
				voList.add(AwsVo);
				List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
				if(!unindexedFaces.isEmpty()) System.out.println("Faces indexed successful");
			for (UnindexedFace unindexedFace : unindexedFaces) {
					
					//System.out.println(" Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
				for (String reason : unindexedFace.getReasons()) {
					System.out.println("unindexing reason :"+reason);
				}	
				}					
			}
		} catch (AmazonRekognitionException e) {
			AwsVo =new Error().Erroract(e, AwsVo);
			AwsVo.setFilename(fileName);
			AwsVo.setEmail(email);	
			AwsVo.setCollectionName(collectionName);
			AwsVo.setCollectionId(collectionId);
			AwsVo.setAction("AddFacesToCollectionact");
			voList.add(AwsVo);
			return voList;
		}
		if(voList.isEmpty()) {
			AwsVo = new AwsVo();
			AwsVo.setFilename(fileName);
			AwsVo.setEmail(email);	
			AwsVo.setCollectionName(collectionName);
			AwsVo.setCollectionId(collectionId);
			AwsVo.setAction("AddFacesToCollectionact");
			AwsVo.setState("식별 가능한 Face가 존재하지 않습니다.");
			AwsVo.setStcode(400);
			voList.add(AwsVo);
		}
		return voList;
	}
}
