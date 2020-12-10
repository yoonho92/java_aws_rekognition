

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class SubImage {
	//MFbounding 메서드 제거
	public SubImage(ArrayList<AwsVo> voList) {
		AwsVo AwsVo = voList.get(0);
		if(AwsVo.stcode>=400) {
			System.out.println("SubImage 클래스 fail, stcode :"+AwsVo.stcode);
			return;
			}
		String email = AwsVo.getEmail();
		String collectionName = AwsVo.getCollectionName();
		String filename = AwsVo.getFilename();
		float left = AwsVo.getBoundingBoxLeft();
		float top = AwsVo.getBoundingBoxTop();
		float width = AwsVo.getBoundingBoxWidth();
		float height = AwsVo.getBoundingBoxHeight();
		try {
			
			File imgfile = new File(uploadController.ImagePath+File.separator+email+File.separator+collectionName+File.separator+filename);
			if(!imgfile.isFile()) {
				imgfile = new File(uploadController.ImagePath+File.separator+email+File.separator+filename);

				}
			InputStream IS = new FileInputStream(imgfile);
			BufferedImage BI = ImageIO.read(IS);

			BufferedImage subimage = BI.getSubimage((int)(left*BI.getWidth()) , (int)(top*BI.getHeight()),(int)(width*BI.getWidth()), (int)(height*BI.getHeight()));
			File croppath = new File(uploadController.ImagePath+File.separator+email+File.separator+collectionName+File.separator+"subimg"+File.separator+filename);
			ImageIO.write(subimage,"jpg",croppath);
			System.out.println("subimage 생성 성공");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("subimage 생성실패");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("subimage 입출력에러");
		}

	}
	
}
