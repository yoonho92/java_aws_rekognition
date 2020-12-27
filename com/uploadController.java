

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class uploadController2
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024
		* 10, location = "/data/image", fileSizeThreshold = 1024 * 1024 * 10)
@WebServlet({ "/index", "/upload", "/createcollection", "/listcollections", "/deletecollection",
		"/addfacestocollection", "/listfacesincollection", "/deletefacesfromcollection",
		"/searchfacematchingimagecollection", "/faceauthentication", "/guestlist", "/guestsetindex" })
public class uploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String ImagePath = "/data/image";
	public static String subimgPath = "http://yoonhonas.synology.me:8081/awsrekog/img";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public uploadController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//cors에러 방지
		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw;
		pw = response.getWriter();
		String collectionName = null;
		String email = null; //필수데이터
		JSONObject DBjson = null; //프론트에 연결된 db의 회원정보가 담겨짐
		JSONObject JsonStringObj = null; //	프론트에서 입력된 collection name이나 index등의 기타 데이터가 담겨진 오브젝트 변수
		String JsonString = null; // 프론트에서 입력된 collection name이나 index등의 기타 데이터가 담겨진 변수
		String resultJson = null; //프론트로 반환되는 변수
		
		// 클라이언트측에서 요청하는 기능에 따라 DBjson가 null일 경우가 있기 때문에 null값을 참조하는 것을 방지하기 위해 체크
		if (request.getParameter("DBjson") != null) {
			DBjson = new JSONObject(request.getParameter("DBjson"));
			email = DBjson.getString("cm_email");			
		} else {
			System.out.println("DBjson에 값이 없습니다. (email, collection)");
			email = request.getParameter("email");
			}
		
		// 클라이언트측에서 요청하는 기능에 따라 JsonData가 null일 경우가 있기 때문에 null값을 참조하는 것을 방지하기 위해 체크
		if (request.getParameter("JsonData") != null) {
			JsonString = request.getParameter("JsonData");
			// null값 방지
			if (!request.getServletPath().equals("/guestsetindex")) {
				JsonStringObj = new JSONObject(JsonString);
			}
			if (JsonString.contains("collectionName")) {
				collectionName = new JSONObject(JsonString).getString("collectionName");
			}
		}
		
		//email의 값 여부 체크
		if(email==null) {
			pw.print("return, email is null");
			return;
			}
		
		//email 형식 체크 및 처음사용자의 서버 폴더 및 aws의 collection 셋팅
		new Welcome(email);

		switch (request.getServletPath()) {
		case "/index":
			request.getRequestDispatcher("index.jsp").forward(request, response);
			break;
			
		case "/createcollection":
			//기능이 동작하기 위한 정보가 담겨있는지 체크
			if ((collectionName == null)) {
				System.out.println("return, createcollection, collectionName");
				return;
			}
			new FolderCheck(email, collectionName).FolderCreate();
			ArrayList<AwsVo> CCresult = new CreateCollection().CreateCollectionAct(email, collectionName);
			resultJson = new ObjectMapper().writeValueAsString(CCresult);
			pw.print(resultJson);
			break;

		case "/listcollections":
			ArrayList<AwsVo> LCresult = new ListCollections().ListCollectionact(email);
			resultJson = new ObjectMapper().writeValueAsString(LCresult);
			pw.print(resultJson);
			break;
			
		case "/deletecollection":
			//기능이 동작하기 위한 정보가 담겨있는지 체크
			if ((collectionName == null) || !new FolderCheck(email, collectionName).checkIndex) {
				System.out.println("return, deletecollection, collectionName or FolderCheck");
				return;
			}
			
			ArrayList<AwsVo> DCresult = new DeleteCollection().DeleteCollectionact(email, collectionName);
			new FolderCheck(email, collectionName).FolderDelete(email, collectionName);
			resultJson = new ObjectMapper().writeValueAsString(DCresult);
			pw.print(resultJson);
			break;
			
		case "/addfacestocollection":
			//기능이 동작하기 위한 정보가 담겨있는지 체크
			if ((collectionName == null) || !new FolderCheck(email, collectionName).checkIndex) {
				System.out.println("return, addfacestocollection, collectionName or FolderCheck");
				return;
			}
			
			Part AFCpart = request.getPart("file");
			// 사용자가 저장 파일의 이름은 현재 날짜와 시간으로 변환되어 저장되기 때문에 변수에 새로운 파일이름을 저장
			String AFCfilename = new SaveImage(email, collectionName, AFCpart).Filename;
			ArrayList<AwsVo> AFCresult = new AddFacesToCollection().AddFacesToCollectionact(collectionName, email,
					AFCfilename);
			new SubImage(AFCresult);
			SubimgVo SubimgVo = new ToSubimgVo(AFCresult, AFCfilename).SubimgVo;
			resultJson = new ObjectMapper().writeValueAsString(SubimgVo);
			pw.print(resultJson);
			break;
			
		case "/listfacesincollection":
			//기능이 동작하기 위한 정보가 담겨있는지 체크
			if ((collectionName == null) || !new FolderCheck(email, collectionName).checkIndex) {
				System.out.println("return, listfacesincollection, collectionName or FolderCheck");
				return;
			}
			
			ArrayList<SubimgVo> LFCresult = new ListFacesInCollection().ListFacesInCollectionact(collectionName, email);
			resultJson = new ObjectMapper().writeValueAsString(LFCresult);
			pw.print(resultJson);
			break;
			
		case "/deletefacesfromcollection":
			//기능이 동작하기 위한 정보가 담겨있는지 체크
			if ((collectionName == null) || !new FolderCheck(email, collectionName).checkIndex) {
				System.out.println("return, deletefacesfromcollection, collectionName or FolderCheck");
				return;
			}
			
			String filename = JsonStringObj.getString("filename");
			String facesId[] = { JsonStringObj.getString("faceId") };// 웹에서 select된 이미지의 faceId로 이루어진 배열 생성
			ArrayList<AwsVo> DFCresult = new DeleteFacesFromCollection().DeleteFacesFromCollectionact(email,
					collectionName, facesId);
			new FolderCheck(email, collectionName).ImageDelete(filename);
			resultJson = new ObjectMapper().writeValueAsString(DFCresult);
			pw.print(resultJson);
			break;
			
		case "/faceauthentication":
			//getPart 매개변수에 프론트쪽에서 업로드해주는 이미지의 key값 입력
			Part FApart = request.getPart("data");
			//업로드 받는 이미지를 저장
			String SaveFilename = new SaveImage(email,"GuestGroup", FApart).Filename;
			//업로드 받은 이미지에 대해서 인물식별
			ArrayList<AwsVo> FAresult = new FaceAuthentication(email, SaveFilename).voList;
			collectionName = FAresult.get(0).getCollectionName();
			//해당하는 collection폴더에 썸네일 파일로 저장
			new SubImage(FAresult);
			switch (FAresult.get(0).getStcode()) {
			case 200:
				//거주자로 분류
				
				//외부로 출력할 Vo로 변환
				SubimgVo SubimgVo200 = new ToSubimgVo(FAresult, SaveFilename).SubimgVo;
				SubimgVo200.setMsg(FAresult.get(0).getState());
				//json출력을 위해 객체 -> json 변환
				resultJson = new ObjectMapper().writeValueAsString(SubimgVo200);
				pw.print(resultJson);
				break;
				
			case 210:
				//주의 인물로 분류				
				// 이동시킬 파일 경로
				String Filepath = ImagePath + File.separator + email + File.separator+"GuestGroup"+File.separator + SaveFilename;
				// 이동 대상 경로
				String Topath = ImagePath + File.separator + email + File.separator + "CautionGroup" + File.separator
						+ SaveFilename;				
				// 파일 이동, 실패시 리턴
				if (!new FolderCheck(email, "CautionGroup").FileMove(Filepath, Topath)) {
					System.out.println("break,case210, FolderMove 실패");
					break;
				}
				
				//외부로 출력할 Vo로 변환
				SubimgVo SubimgVo210 = new ToSubimgVo(FAresult, SaveFilename).SubimgVo;
				resultJson = new ObjectMapper().writeValueAsString(SubimgVo210);
				pw.print(resultJson);
				break;
				
			case 220:
				//custom 그룹에 할당된 인물로 분류
				SubimgVo SubimgVo220 = new ToSubimgVo(FAresult, SaveFilename).SubimgVo;
				resultJson = new ObjectMapper().writeValueAsString(SubimgVo220);
				pw.print(resultJson);
				break;
				
			case 300:
				// 카메라에서 사람 얼굴이 식별이 되었으나 collection내에 일치하는 얼굴이 없는 경우 - 방문자 분류
				
				// 이동시킬 파일
				//Filepath = ImagePath + File.separator + email + File.separator + Filename;
				// 이동 대상 경로
				//Topath = ImagePath + File.separator + email + File.separator + "GuestGroup" + File.separator
				//		+ Filename;
				// 파일 이동, 실패시 리턴
//				if (!new FolderCheck(email, "GuestGroup").FileMove(Filepath, Topath)) {
//					System.out.println("FolderMove 실패");
//					return;
//				}
				
				// GuestGroup에 해당 face 추가
				ArrayList<AwsVo> GuestAFCresult = new AddFacesToCollection().AddFacesToCollectionact("GuestGroup",
						email, SaveFilename);
				// 동일 인물별로 바인딩하여 각각의 폴더로 이미지파일 이동
				new GuestList(email).Classification();
				//외부로 출력할 Vo로 변환
				SubimgVo SubimgVo300 = new ToSubimgVo(GuestAFCresult, SaveFilename).SubimgVo;
				resultJson = new ObjectMapper().writeValueAsString(SubimgVo300);
				pw.print(resultJson);
				break;
				
			default:
				break;
			}
			
			break; //case "/faceauthentication" break;
		case "/guestlist":
			HashMap<String, HashSet<SubimgVo>> GL = new GuestList(email).voSameFace;
			
			if (GL.isEmpty()) {
				System.out.println("return, guestlist, GusetGruop is empty");
				pw.print(GL);
				return;
			}
			
			//GuestGroup.txt파일을 읽어서 index가 존재하면 GL에 저장
			String confFilePath = ImagePath + File.separator + email + File.separator + "GuestGroup" + File.separator
					+ "GuestGroup.txt";
			if (new File(confFilePath).exists()) {
				// json파일을 읽어서 String에 저장
				String confJson = new JsonIO().Read(confFilePath);
				// json형식으로 이루어진 String을 HashMap형태의 객체로 저장
				HashMap<String, String> confMap = new ObjectMapper().readValue(confJson, HashMap.class);
				// 위의 HashMap을, 반복문을 이용 value값에 필요한 값이 있는지 확인
				for (String Key : confMap.keySet()) {
					if (confMap.get(Key).equals("t")) {
						// 필요한 값이 나오면 GL과 conMap이 같은 Key를 공유한다는 것을 이용해 GL의 해당Value에 값 대입
						for (SubimgVo GLvo : GL.get(Key)) {
							GLvo.setIndex(confMap.get(Key));
						}
					}
				}
			}
			
			resultJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(GL);
			pw.write(resultJson);
			break;
			
		case "/guestsetindex":
			//선택된 방문지에게 특정 인덱스를 부여하여 텍스트파일로 저장
			Boolean result = new JsonIO().Write(JsonString, email);
			pw.print(result);
			break;
			
		default:
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
