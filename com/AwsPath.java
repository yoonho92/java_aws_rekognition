

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;

public class AwsPath {
	//파일의 경로를 받아서 rekognition 메서드에서 읽을수 있는 Image객체로 변환
	public Image photo_source=null;

	public AwsPath(String path)
	{
	String photo = path;
    ByteBuffer sourceImageBytes=null;
    try (InputStream inputStream = new FileInputStream(new File(photo))) {
        sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream)); }
                   catch(Exception e)
        {
        System.out.println("Failed to load source image " + photo);
        System.exit(1); }        
        Image source=new Image() .withBytes(sourceImageBytes);
        photo_source= source;
}}
