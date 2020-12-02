

import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public class JsonTranform {

	JSONObject Jsonrecv(HttpServletRequest request) throws IOException{
		ServletInputStream input = request.getInputStream();
		int len = request.getContentLength();
		byte[] buf = new byte[len];
		input.readLine(buf, 0, len);
		String text = new String(buf);
		JSONObject obj = new JSONObject(text);
		return obj;
	}
	
}
