

import java.io.File;
import java.util.ArrayList;
public class FaceAuthentication {
	//방문자가 발생하였을시 방문자의 이미지를 받아 collection에서 존재여부를 체크하고 분류
	ArrayList<AwsVo> voList;
	public FaceAuthentication(String email,String SaveFilename) {
		
		File file = new File(uploadController.ImagePath+File.separator+email);
		 voList = new ArrayList<AwsVo>();
		 int check=0;
		String[] fileList = file.list();
		for (String collectionName : fileList) {
			//collection이름으로 디렉터리를 생성했으므로 디렉터리가 아닌경우 pass

			if(!new File(uploadController.ImagePath+File.separator+email+File.separator+collectionName).isDirectory()||collectionName.equals("GuestGroup")) continue;

			System.out.println(collectionName +"검색 시작");
			voList = new SearchFaceMatchingImageCollection().SearchFaceMatchingImageCollectionact(email,collectionName,SaveFilename);	
			if(voList.get(0).getStcode() == 200) {
				switch (voList.get(0).getCollectionName()) {
				case "ResidentGroup":
					System.out.println("resi 진입");
					voList.get(0).setState("거주자 확인");
					check =1;
					break;
				case "CautionGroup":
					voList.get(0).setState("주의 인물 확인");
					voList.get(0).setStcode(210);
					check =1;
					break;
				default:
					voList.get(0).setState("설정하신 "+ voList.get(0).getCollectionName()+"그룹에서 인물이 식별되었습니다.");
					voList.get(0).setStcode(220);
					check =1;
					break;
				}
			}else {
				voList.get(0).setState("거주자 불일치");
				System.out.println("collection name :"+collectionName+" 내에 존재하지 않습니다.");
			}
			if(check==1) break;
		}
		
	}
}
