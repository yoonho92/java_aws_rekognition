
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
 * Servlet implementation class uploadController
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024
		* 10, location = "/Users/yoonho/Desktop/java/uploadfile", fileSizeThreshold = 1024 * 1024 * 10)
@WebServlet({ "/index", "/upload", "/createcollection", "/listcollections", "/deletecollection",
		"/addfacestocollection", "/listfacesincollection", "/deletefacesfromcollection",
		"/faceauthentication","/welcome","/home","/guestlist"})
public class uploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String ImagePath ="/Users/yoonho/Desktop/java/uploadfile";
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

		response.addHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		String collectionName;
		String email;
		String name;
		String InputJson;
		email = "yoonho2015@gmail.com";
		name = "yoonho";
		//첫 사용자시 Welcome 실행, collection을 생성하고 기본 폴더 생성
		new Welcome(email);
		switch (request.getServletPath()) {
		case "/home":
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;
		case "/welcome":
			email = "qkdgkrrnt@namver.com";
			new Welcome(email);
			break;
		case "/addfacestocollection":

			collectionName = "GuestGroup";
			if(!new FolderCheck(email, collectionName).checkIndex) {
				break;
			}
			Part AFCpart = request.getPart("file");
			String AFCfilename = new SaveImage(email, collectionName, AFCpart).Filename;
			ArrayList<AwsVo> AFCresult = new AddFacesToCollection().AddFacesToCollectionact(collectionName, email,
					AFCfilename);
			new SubImage(AFCresult);
			System.out.println(AFCresult);
			String AFCresultjson = new ObjectMapper().writeValueAsString(AFCresult);
			pw = response.getWriter();
			pw.print(AFCresultjson);
			break;
		case "/createcollection":
			collectionName = request.getParameter("collectionName");

			new FolderCheck(email, collectionName).FolderCreate();
			ArrayList<AwsVo> CCresult = new CreateCollection().CreateCollectionAct(email, collectionName);			
			String CCresultjson = new ObjectMapper().writeValueAsString(CCresult);
			System.out.println(CCresultjson);
			pw = response.getWriter();
			pw.print(CCresultjson);
			break;
		case "/listcollections":
			ArrayList<AwsVo> LCresult = new ListCollections().ListCollectionact(email, name);
			String LCresultjson = new ObjectMapper().writeValueAsString(LCresult);
			pw = response.getWriter();
			pw.print(LCresultjson);
			break;
		case "/deletecollection":
			collectionName = request.getParameter("collectionName");
			ArrayList<AwsVo> DCresult = new DeleteCollection().DeleteCollectionact(email, collectionName);
			System.out.println(DCresult);
			new FolderCheck(email, collectionName).FolderDelete(email, collectionName);
			String DCresultjson = new ObjectMapper().writeValueAsString(DCresult);
			System.out.println(DCresultjson);
			pw = response.getWriter();
			pw.print(DCresultjson);
			break;
		case "/listfacesincollection":
			
			collectionName = request.getParameter("LFCtext");
			email = "yoonho2015@gmail.com";
			ArrayList<SubimgVo> LFCresult = new ListFacesInCollection().ListFacesInCollectionact(collectionName, email);
			String LFCresultjson = new ObjectMapper().writeValueAsString(LFCresult);
			pw = response.getWriter();
			pw.print(LFCresultjson);
			break;
		case "/deletefacesfromcollection":
			collectionName = request.getParameter("DFCtext");
			String facesId[] = { "" };// 웹에서 select된 목록 받아서 faceid 배열 생
			String filename ="";
			String collectionId = "yoonho2015."+collectionName;
			ArrayList<AwsVo> DFCresult = new DeleteFacesFromCollection().DeleteFacesFromCollectionact(collectionId,
					facesId);
			new FolderCheck(email, collectionName).ImageDelete(filename);
			System.out.println(DFCresult);
			break;
		case "/faceauthentication":

			pw = response.getWriter();
			String imageSrc = request.getParameter("image");
			String SaveFilename = new SaveImage(email,imageSrc).Filename;
			ArrayList<AwsVo> FAresult = new FaceAuthentication(email, SaveFilename).voList;
			collectionName = FAresult.get(0).getCollectionName();
			
			switch (FAresult.get(0).getStcode()) {
			case 200:
				System.out.println("/faceauthentication 내부 switch문 case 200 진입");
				new SubImage(FAresult);
				String imgPath = "http://yoonhonas.synology.me:8081/awsrekog/img" + File.separator + email + File.separator
						+ collectionName + File.separator + "subimg" + File.separator + SaveFilename;
				SubimgVo SubimgVo = new SubimgVo();
				SubimgVo.setImgPath(imgPath);
				SubimgVo.setEmail(email);
				SubimgVo.setCollectionName(collectionName);
				SubimgVo.setMsg(FAresult.get(0).getState() + "\n" + "어서오세요");
				String FAresultJson = new ObjectMapper().writeValueAsString(SubimgVo);
				System.out.println(FAresultJson);
				pw.print(FAresultJson);
				break;
			case 210:
				break;
			case 220:
				break;
			case 300:
				//카메라에서 사람 얼굴이 식별이 되었으나 collection내에 일치하는 얼굴이 없는 경우
				System.out.println("/faceauthentication 내부 switch문 case 300 진입");
				String Filename = SaveFilename;
				//이동시킬 파일 
				String Filepath = ImagePath +File.separator+ email + File.separator + Filename;
				//이동 대상 경로
				String Topath =  ImagePath +File.separator+ email + File.separator + "GuestGroup"+File.separator + Filename;
				//파일 이동, 실패시 리턴
				if(!new FolderCheck(email,"GuestGroup").FileMove(Filepath, Topath)) {System.out.println("FolderMove 실패"); return;}	
				//GuestGroup에 해당 face 추가
				ArrayList<AwsVo> GesutAFCresult = new AddFacesToCollection().AddFacesToCollectionact("GuestGroup", email,
						Filename);
				//썸네일 이미지 생성
				new SubImage(GesutAFCresult);
				//동일 인물별로 분류를 하고 폴더생성 후 파일 이동
				new GuestList(email).Classification();
				imgPath = "http://localhost:8080/localTest/img" + File.separator + email + File.separator
						+ "GuestGroup" + File.separator + "subimg" + File.separator + Filename;
				SubimgVo = new SubimgVo();
				SubimgVo.setImgPath(imgPath);
				SubimgVo.setMsg(FAresult.get(0).getState());
				FAresultJson = new ObjectMapper().writeValueAsString(SubimgVo);
				pw.print(FAresultJson);
				break;
			default:
				System.out.println("/faceauthentication 내부 switch문 case default 진입");
				break;
			}
		case "/guestlist":
			System.out.println("GL진입");
			HashMap<String, HashSet<SubimgVo>> GL = new GuestList(email).voSameFace;
			String confFilePath = ImagePath+File.separator+email+File.separator+"GuestGroup"+ File.separator + "GuestGroup.txt";
			if(new File(confFilePath).exists()) {
				//json파일을 읽어서 String에 저장
				String confJson = new JsonIO().Read(confFilePath);
				//json형식으로 이루어진 String을 HashMap형태의 객체로 저장
				HashMap<String,String> confMap = new ObjectMapper().readValue(confJson, HashMap.class);
				System.out.println(confMap);
				//위의 HashMap을, 반복문을 이용 value값에 필요한 값이 있는지 확인
				for (String Key: confMap.keySet()) {
					if(confMap.get(Key).equals("t")) {
						System.out.println("if");
						//필요한 값이 나오면 GL과 conMap이 같은 Key를 공유한다는 것을 이용해 GL의 해당Value에 값 대입
						for (SubimgVo GLvo : GL.get(Key)) {
							GLvo.setIndex(confMap.get(Key));
						}
					}
				}
			}
			String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(GL);
			System.out.println(json);
			pw = response.getWriter();
			pw.write(json);
			break;
		case"/guestsetindex":
			
			InputJson = request.getParameter("JsonData");
			Boolean result = new JsonIO().Write(InputJson, email);
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
