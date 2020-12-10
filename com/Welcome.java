

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

//email 폴더 존재유무 검사후 collection을 생성하고 string 배열에 담긴 기본 collection 목록을 반복문을 이용해 폴더를 생성
public class Welcome {

	public Welcome(String email) {
		boolean emailCheck = Pattern.matches("\\w+@\\w+\\.[a-zA-Z]+", email);
		if(!emailCheck) {
			System.out.println("유효한 이메일 형식이 아닙니다.");
			return;
		}
		String[] folderList = {"ResidentGroup","CautionGroup","GuestGroup"};
		
		//해당 디렉토리가 없을경우 디렉토리를 생성
		File Folder = new File(uploadController.ImagePath+File.separator + email);
		if (!Folder.exists()) {
			ArrayList<AwsVo> AwsVo =new CreateCollection().CreateCollectionAct(email,"ResidentGroup");
			if(AwsVo.get(0).stcode!=200) {
				System.out.println("Welcome class에서 ResidentGroup collection 생성 실패");
				System.out.println(AwsVo.get(0).errmsg);
				}
			AwsVo =new CreateCollection().CreateCollectionAct(email,"GeustGroup");
			if(AwsVo.get(0).stcode!=200) {
				System.out.println("Welcome class에서 GeustGroup 생성 실패");
				System.out.println(AwsVo.get(0).errmsg);
				}
			try {
				Folder.mkdir(); //계정 폴더 생성
				System.out.println(email+" 폴더가 생성되었습니다.");
				for (String FolderName : folderList) {
					Folder = new File(uploadController.ImagePath+File.separator + email + File.separator + FolderName);
					Folder.mkdir(); //기본 collection 폴더 생성
					System.out.println(FolderName+" 폴더가 생성되었습니다.");
					Folder = new File(uploadController.ImagePath+File.separator + email + File.separator + FolderName + File.separator + "subimg");
					Folder.mkdir(); // 썸네일 폴더 생성
					System.out.println("subimg 폴더가 생성되었습니다.");
				}
			
			} catch (Exception e) {
				e.getStackTrace();
			}
		}else System.out.println("email 폴더가 존재합니다. Welcome process pass");
	}
}
	
