import java.io.File;

public class Welcome {

	public Welcome(String email) {
		
		
		new CreateCollection().CreateCollectionAct(email,"ResidentGroup");
		
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
		if(Folder1.exists() && Folder2.exists() && Folder3.exists()) System.out.println("모든 폴더가 생성되었습니다.");
		else System.out.println("일부 폴더가 생성되지 않았습니다.");
	}
}
