

import java.io.File;

public class FolderCheck {
	
	public void FolderCreate(String email, String collectionName) {
		// TODO Auto-generated method stub
		File Folder = new File(uploadController.ImagePath+File.separator + email);// 저장위치 확정하고 경로완성할
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
				System.out.println(email+" 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println(email+"기존에 생성된 폴더 입니다.");
		}
		
		
		Folder = new File(uploadController.ImagePath +File.separator+email + File.separator + collectionName);// 저장위치 확정하고 경로완성할
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
				System.out.println(collectionName+" 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println(collectionName+"기존에 생성된 폴더 입니다.");
		}
		Folder = new File(uploadController.ImagePath+File.separator + email + File.separator + collectionName+File.separator+"subimg");
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
				System.out.println("subimg 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("subimg 기존에 생성된 폴더 입니다.");
		}

	}

	public void FolderDelete(String email, String collectionName) {
		String path = uploadController.ImagePath+File.separator + email + File.separator + collectionName;
		File folder = new File(path);
		try {
			while (folder.exists()) {
				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

				for (int j = 0; j < folder_list.length; j++) {
					folder_list[j].delete(); // 파일 삭제
					System.out.println(collectionName+"폴더내 파일이 삭제되었습니다.");

				}

				if (folder_list.length == 0 && folder.isDirectory()) {
					folder.delete(); // 대상폴더 삭제
					System.out.println(collectionName+" 폴더가 삭제되었습니다.");
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void ImageDelete(String email,String collectionName, String filename) {
		String path = uploadController.ImagePath+File.separator+email+File.separator+collectionName+File.separator+filename;
		File file = new File(path);
		if(file.exists()) {
			try {
			file.delete();
			System.out.println(filename+"image가 삭제되었습니다.");
			}catch(Exception e) {
				e.getStackTrace();
			}
		}else {
			System.out.println(filename+"이 존재하지 않습니다.");
		}
	}
}
