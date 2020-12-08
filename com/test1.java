
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test1 {

	public static void main(String[] args) throws JsonProcessingException {
		// TODO Auto-generated method stub
		HashMap<String, HashSet<SubimgVo>> map = new GuestList("yoonho2015@gmail.com").voSameFace;
		String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map);
		
		try {
//			FileOutputStream FOS = new FileOutputStream(uploadController.ImagePath+File.separator+"yoonho2015@gmail.com"+File.separator+"GuestGroup"+File.separator+"a.txt");
//			FOS.write(json.getBytes());
			FileReader FR = new FileReader(uploadController.ImagePath+File.separator+"yoonho2015@gmail.com"+File.separator+"GuestGroup"+File.separator+"a.txt");
			BufferedReader BR = new BufferedReader(FR);
			String line="";
			StringBuilder SB = new StringBuilder();
			while((line = BR.readLine())!=null) {
				SB.append(line).append('\n');
			}
			FR.close();
			BR.close();
			System.out.println(SB.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
