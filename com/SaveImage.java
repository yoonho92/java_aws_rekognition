




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Part;

public class SaveImage {
	//매개변수로 받은 email과 collection name을 이용하여 저장경로를 지정하고, 파일이름을 현재 날짜로 하여 저장
	Date today = new Date();
	String Filename = new SimpleDateFormat("yyyyMMddHHmmss").format(today)+".jpg"; 
	String Path;
	public SaveImage(String email, String collectionName, Part part) {
		
		
		Path=uploadController.ImagePath+File.separator+email+File.separator+collectionName+File.separator+Filename;
		try {
			InputStream IS = part.getInputStream();
			FileOutputStream FOS= new FileOutputStream(Path);
			int c;
			//한번의 10240바이트를 쓰기처리하도록 함 - 파일의 크기만큼 반복
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
	
		//방문자의 이미지를 url로 받아올경우
		public SaveImage(String email, String url) {
			Path=uploadController.ImagePath+File.separator+email+File.separator+"GuestGroup"+File.separator+Filename;
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
