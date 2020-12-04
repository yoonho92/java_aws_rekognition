import java.io.File;
import java.util.ArrayList;
//GuestGroup collection 생성및 폴더 생성
public class Welcome {

	public Welcome(String email) {
		
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
		File Folder1 = new File(uploadController.ImagePath+File.separator + email);// 저장위치 확정하고 경로완성할
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder1.exists()) {
			try {
				Folder1.mkdir(); // 폴더 생성합니다.
				System.out.println(email+" 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {}
		File Folder2 = new File(uploadController.ImagePath +File.separator+email + File.separator + "ResidentGroup");
		if (!Folder2.exists()) {
			try {
				Folder2.mkdir(); // 폴더 생성합니다.
				System.out.println("ResidentGroup 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {}
		File Folder3 = new File(uploadController.ImagePath +File.separator+email + File.separator + "CautionGroup");
		if (!Folder3.exists()) {
			try {
				Folder3.mkdir(); // 폴더 생성합니다.
				System.out.println("CautionGroup 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {}
		File Folder4 = new File(uploadController.ImagePath +File.separator+email + File.separator + "GesutGroup");
		if (!Folder4.exists()) {
			try {
				Folder4.mkdir(); // 폴더 생성합니다.
				System.out.println("GesutGroup 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {}
		if(Folder1.exists() && Folder2.exists() && Folder3.exists()) System.out.println("모든 폴더가 생성되었습니다.");
		else System.out.println("일부 폴더가 생성되지 않았습니다.");
	}
}
