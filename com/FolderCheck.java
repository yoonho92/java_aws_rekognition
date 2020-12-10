
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FolderCheck {


	String email;
	String collectionName;
	Boolean checkIndex;
	//서블릿의 모든 분기의 시작점으로 AWS기능 실행 및 FolderCheck 메서드 실행 이전에 필수 폴더를 체크하여 안정성 향상
	public FolderCheck(String email, String collectionName) {
		this.email = email;
		this.collectionName = collectionName;
		File Folder = new File(uploadController.ImagePath + File.separator + email + File.separator +collectionName);
		if(!Folder.exists()) {
			checkIndex = false;
			System.out.println("FolderCheck 클래스 : Foldercheck fail");
		}
		System.out.println("FolderCheck 클래스 : Foldercheck pass");
		checkIndex = true;
	}
	
	public void FolderCreate() {
		// TODO Auto-generated method stub

		File Folder = new File(uploadController.ImagePath + File.separator + email);// 저장위치 확정하고 경로완성할
		// Welcome이 있으므로 email 폴더 체크는 생략
//		if (!Folder.exists()) {
//			try {
//				Folder.mkdir(); // 폴더 생성합니다.
//				System.out.println(email + " 폴더가 생성되었습니다.");
//			} catch (Exception e) {
//				e.getStackTrace();
//			}
//		} else {
//			System.out.println(email + "기존에 생성된 폴더 입니다.");
//		}

		Folder = new File(uploadController.ImagePath + File.separator + email + File.separator + collectionName);
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
				System.out.println(collectionName + " 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println(collectionName + "기존에 생성된 폴더 입니다.");
		}
		Folder = new File(uploadController.ImagePath + File.separator + email + File.separator + collectionName
				+ File.separator + "subimg");
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
		String path = uploadController.ImagePath + File.separator + email + File.separator + collectionName;
		File folder = new File(path);
		try {
			while (folder.exists()) {
				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

				for (int j = 0; j < folder_list.length; j++) {
					if (folder_list[j].isFile()) {
						folder_list[j].delete();
						System.out.println(collectionName + "폴더내 파일이 삭제되었습니다.");
					} else {
						FolderDelete(email, collectionName + File.separator + folder_list[j].getName());
						// 재귀호출로 해당 폴더 경로에서 delete메서드 다시 실행
					}
				}
				if (folder_list.length == 0 && folder.isDirectory()) {
					folder.delete(); // 대상폴더 삭제
					System.out.println(collectionName + " 폴더가 삭제되었습니다.");
				}

			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void ImageDelete(String filename) {
		String path = uploadController.ImagePath + File.separator + email + File.separator + collectionName
				+ File.separator + filename;
		File file = new File(path);
		if (file.exists()) {
			try {
				file.delete();
				System.out.println(filename + "image가 삭제되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println(filename + "이 존재하지 않습니다.");
		}
	}

	public Boolean FileMove(String Filepath, String Topath) {
		try {
			Path filePath = Paths.get(Filepath);
			if(!new File(Filepath).exists()) {
				System.out.println("FolderCheck 클래스, FileMove 메서드, 파일이 존재하지 않습니다. : "+ filePath.getFileName());
			}
			Path filePathToMove = Paths.get(Topath);
			Files.move(filePath, filePathToMove);
			System.out.println("file move success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
