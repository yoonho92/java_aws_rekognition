

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class ToSubimgVo {
	//AwsVo를 SubimgVo로 변환
	SubimgVo SubimgVo;

	public ToSubimgVo(ArrayList<AwsVo> voList,String SaveImageName) {
		AwsVo AwsVo = voList.get(0);
		String imgPath = uploadController.subimgPath + File.separator + AwsVo.getEmail()
				+ File.separator + AwsVo.getCollectionName() + File.separator + "subimg" + File.separator + AwsVo.getFilename();
		SubimgVo = new SubimgVo();
		SubimgVo.setEmail(AwsVo.getEmail());
		SubimgVo.setImgPath(imgPath);
		SubimgVo.setMsg(AwsVo.getState());
		SubimgVo.setFilename(AwsVo.getFilename());
		SubimgVo.setFaceId(AwsVo.getFaceId());
		SubimgVo.setCollectionName(AwsVo.getCollectionName());
		boolean patternCheck = Pattern.matches("\\d+\\.jpg", AwsVo.getFilename());
		if (patternCheck) {
			SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
			Date to = null;
			try {
				to = fm.parse(AwsVo.getFilename());
				SubimgVo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(to));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
