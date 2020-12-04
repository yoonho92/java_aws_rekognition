
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class uploadController
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024
		* 10, location = "/Users/yoonho/Desktop/java/uploadfile", fileSizeThreshold = 1024 * 1024 * 10)
@WebServlet({ "/index", "/upload", "/createcollection", "/listcollections", "/deletecollection",
		"/addfacestocollection", "/listfacesincollection", "/deletefacesfromcollection",
		"/faceauthentication","/welcome","/home" })
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
		
		PrintWriter pw;
		String collectionName;
		String email;
		String name;
		switch (request.getServletPath()) {
		case "/home":
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;
		case "/welcome":
			email = "qkdgkrrnt@namver.com";
			new Welcome(email);
			break;
		case "/addfacestocollection":
//			DBjson = new JSONObject(request.getParameter("DBjson"));
			email = "yoonho2015@gmail.com";
			name = "yoonho";
//			collectionName = request.getParameter("collectionName");
			collectionName = "ResidentGroup";
			new FolderCheck(email, collectionName);
			Part AFCpart = request.getPart("file");
			new SaveImage(email, collectionName, AFCpart);
			ArrayList<AwsVo> AFCresult = new AddFacesToCollection().AddFacesToCollectionact(collectionName, email,
					AFCpart.getSubmittedFileName());
			System.out.println(AFCresult);
			new SubImage(AFCresult);

			String AFCresultjson = new ObjectMapper().writeValueAsString(AFCresult);
			pw = response.getWriter();
			pw.print(AFCresultjson);
			break;
		case "/createcollection":
			email = "yoonho2015@gmail.com";
			name = "yoonho";
			collectionName = request.getParameter("collectionName");
			new FolderCheck(email, collectionName);
			ArrayList<AwsVo> CCresult = new CreateCollection().CreateCollectionAct(email, collectionName);
			String CCresultjson = new ObjectMapper().writeValueAsString(CCresult);
			System.out.println(CCresultjson);
			pw = response.getWriter();
			pw.print(CCresultjson);
			break;
		case "/listcollections":
			email = "yoonho2015@gmail.com";
			name = "yoonho";
			collectionName = request.getParameter("collectionName");
			ArrayList<AwsVo> LCresult = new ListCollections().ListCollectionact(email, name);
			String LCresultjson = new ObjectMapper().writeValueAsString(LCresult);
			pw = response.getWriter();
			pw.print(LCresultjson);
			break;
		case "/deletecollection":
			email = "yoonho2015@gmail.com";
			name = "yoonho";
			collectionName = request.getParameter("collectionName");
			ArrayList<AwsVo> DCresult = new DeleteCollection().DeleteCollectionact(email, collectionName);
			System.out.println(DCresult);
			new FolderCheck().FolderDelete(email, collectionName);
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
			System.out.println(collectionName);
			String facesId[] = { "" };// 웹에서 select된 목록 받아서 faceid 배열 생
			String filename ="";
			email = "yoonho2015@gmail.com";
			String collectionId = "yoonho2015."+collectionName;
			ArrayList<AwsVo> DFCresult = new DeleteFacesFromCollection().DeleteFacesFromCollectionact(collectionId,
					facesId);
			new FolderCheck().ImageDelete(email, collectionName,filename);
			System.out.println(DFCresult);
			break;
		case "/faceauthentication":
			email = "yoonho2015@gmail.com";
			response.setContentType("text/html;charset=utf-8");
			pw = response.getWriter();
			System.out.println(request.getContentType());
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
				System.out.println("/faceauthentication 내부 switch문 case 300 진입");
				//이동시킬 파일 
				String Filename = SaveFilename; 
				//이동 대상 경로
				String Topath =  ImagePath +File.separator+ email + File.separator + "GuestGroup"+ File.separator +File.separator + Filename;
				//파일 이동, 실패시 리턴
				if(!new FolderCheck(email,"GuestGroup").FileMove(Filename, Topath)) {System.out.println("FolderMove 실패"); return;}	
				//GuestGroup에 해당 face 추가
				ArrayList<AwsVo> GesutAFCresult = new AddFacesToCollection().AddFacesToCollectionact("GuestGroup", email,
						Filename);
				new SubImage(GesutAFCresult);
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
