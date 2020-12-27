
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonIO {
	public JsonIO() {
		
	}
	public Boolean Write(String InputJson,String email) {
			//입력받은 string중 index값이 존재하는 key value를 해당하는 email(계정) 폴더의 GuestGroup 폴더내에 저장
			String SavePath = uploadController.ImagePath+File.separator+email+File.separator+"GuestGroup"+ File.separator + "GuestGroup.txt";
			//json to HashMap<String,ArrayList<SubimgVo>>
			HashMap<String, ArrayList<SubimgVo>> Inputobj;
			try {
				Inputobj = new ObjectMapper().readValue(InputJson, new TypeReference<HashMap<String,ArrayList<SubimgVo>>>() {
				});

			HashMap<String,String> confJson = new HashMap<String, String>();
			String index = "";
			for (String Key : Inputobj.keySet()) {
				//프론트쪽에서 key값없이 value만 들어오는 에러가 발생하여 해당 값을 read시 에러가 발생하여
				//key값이 없으면서 value의 index값이 존재하는 값을 continue
				if((Key.length()==0) == (Inputobj.get(Key).get(0).getIndex().length()!=0)) continue;
				//같은 Key의 ArrayList의 SubimgVo의 index에는 모두 동일한 값이 들어있기때문에 Key에 해당하는 Value에서 임의의 값 한개만 가져오도록 한다
				if(Inputobj.get(Key).get(0).getIndex()==null) continue;
				index = Inputobj.get(Key).get(0).getIndex();
				confJson.put(Key, index);
				
			}
			//정리된 객체를 json string으로 변환
			String OutputJson = new ObjectMapper().writeValueAsString(confJson);
			//지정된 path에 파일 저장
			FileWriter FOS = new FileWriter(SavePath);
			FOS.write(OutputJson);
			FOS.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block				
				e.printStackTrace();
				return false;
			}
		return true;
		
	}
	public String Read(String path) throws JsonProcessingException {
		//path의 txt파일을 읽어서 반환
		String result = "내용이 없습니다.";
		try {
			FileReader FR = new FileReader(path);
			BufferedReader BR = new BufferedReader(FR);
			String line="";
			StringBuilder SB = new StringBuilder();
			while((line = BR.readLine())!=null) {
				SB.append(line).append('\n');
			}
			FR.close();
			BR.close();
			result = SB.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("파일이 존재하지 않습니다.");
			e.printStackTrace();
		}
		return result;
		
	}
}
