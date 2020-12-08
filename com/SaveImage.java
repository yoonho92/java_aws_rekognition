



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Part;

public class SaveImage {
	//현재 시간으로 파일의 이름을 설정
	Date today = new Date();
	String Filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(today)+".jpg"; 
	String Path;
	public SaveImage(String email, String collectionName, Part part) {
		
		
		Path=uploadController.ImagePath+File.separator+email+File.separator+collectionName+File.separator+Filename;
		try {
			InputStream IS = part.getInputStream();
			FileOutputStream FOS= new FileOutputStream(Path);
			int c;
			byte[] b = new byte[10240];
			while((c=IS.read(b))!=-1) {
				FOS.write(b,0,c);
			}
			IS.close();
			FOS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("SaveImage 클래스 error");
			e.printStackTrace();
		}
	}
	
		public SaveImage(String email, String url) {
			Path=uploadController.ImagePath+File.separator+email+File.separator+Filename;
			try {
				InputStream IS = new URL(url).openStream();
				FileOutputStream FOS= new FileOutputStream(Path);
				int c;
				byte[] b = new byte[10240];
				while((c=IS.read(b))!=-1) {
					FOS.write(b,0,c);
				}
				IS.close();
				FOS.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("SaveImage 클래스 error");
				e.printStackTrace();
			}
		}

}
