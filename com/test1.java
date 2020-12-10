
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test1 {

	public static void main(String[] args) throws JsonProcessingException {
		// TODO Auto-generated method stub
		
		new FolderCheck("yoonho2015@gmail.com", "aaaaa").FolderCreate();
	}

}
