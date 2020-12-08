import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;

//동일 방문자끼리 묶어서 key값을 index로 갖는 ArrayList 생성
public class GuestList {
	HashMap<String, HashSet<SubimgVo>> voSameFace;
	private String email;

	public GuestList(String email) throws JsonProcessingException {
		this.email = email;
		// hashset을 key value 를 가지는 hashmap에 저장하기위해 변수 선언
		voSameFace = new HashMap<String, HashSet<SubimgVo>>();
		// collection내에 저장된 Face를 ArrayList에 저장
		ArrayList<SubimgVo> voFaceList = new ListFacesInCollection().ListFacesInCollectionact("GuestGroup", email);
		// 동일인물로 검색된 SubimgVo를 voFaceList에서 미리 제거해주는 것이 요점
		for (SubimgVo faceListVo : voFaceList) {
			HashSet<SubimgVo> faceSet = new HashSet<SubimgVo>();

			// 첫번째 FaceId 변수에 저장
			String faceId = faceListVo.getFaceId();
			
			// 정규표현식으로 날짜포맷이 아닌것을 필터링
			boolean patternCheck = Pattern.matches("\\d+\\.jpg", faceListVo.getFilename());
			if (patternCheck) {
				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
				Date to = null;
				try {
					to = fm.parse(faceListVo.getFilename());
					faceListVo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(to));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// hashset 변수에 SubimgVo객체 저장
			faceSet.add(faceListVo);			
			// 해당 FaceId로 collection내에서 동일한 Face 검색
			ArrayList<SubimgVo> voSearchList = new SearchFaceMatchingIdCollection()
					.SearchFaceMatchingIdCollectionact("yoonho2015@gmail.com", "GuestGroup", faceId);
			// 검색 결과로 나온 ArrayList를 foreach문을 이용해 hashset에 저장
			for (SubimgVo searchListVo : voSearchList) {
				// voFaceList에서 동일한 항목이 있는지 확인하고 있으면 제거하여 중복하여 검색하는 것을 방지
				if (voFaceList.contains(searchListVo)) {
					voFaceList.remove(searchListVo);
				}
				// faceSet에 추가
				faceSet.add(searchListVo);
			}
			voSameFace.put(faceId, faceSet);
		}
	}

	public void Classification() {
		System.out.println("GuestList 클래스, Claasification 메서드");
		Iterator<String> iter = voSameFace.keySet().iterator();
		while (iter.hasNext()) {
			String Key = iter.next();
			// 대상폴더 경로설정
			String Topath = uploadController.ImagePath + File.separator + email + File.separator + "GuestGroup"
					+ File.separator + Key;
			String RF = Key;
			File file = new File(Topath);
			// 폴더가 기존에 존재하는지 유무 검사
			if (!file.exists()) {
				file.mkdir();
				System.out.println("Classification 메서드, 폴더 생성 : " + Key);
			} else {
				// 기존에 폴더가 존재할경우 Key에 해당하는 value의 파일들이 Key에 해당하는 폴더이름에 들어갈수 있도록 하기
				String folderPath = uploadController.ImagePath + File.separator + email + File.separator + "GuestGroup";
				String[] arrPath = new File(folderPath).list();
				// GuestGroup내 폴더 목록 순환
				for (String folderId : arrPath) {
					// hashmap내의 해당 Key의 value인 hashset을 순환하여 내부에 있는 faceId와 동일한 faceId로 이루어진 폴더이름
					// 찾기
					for (SubimgVo SubimgVo : voSameFace.get(Key)) {
						if (!folderId.equals(SubimgVo.getFaceId())) {
							// 못찾으면 continue
							continue;
						} else {
							// 찾으면 RealFolder에 넣고 실제 path에 반영
							RF = folderId;
						}
					}
				}
			}
			// 숫자 index를 key로 사용시 hashmap의 데이터의 순서가 바뀌면 폴더에 들어간 파일과 뒤에 들어올 파일이 동일인물이 아니게 될
			// 가능성이 있음
			// 폴더이름으로 인물의 FaceId중 하나를 사용하여 매번 들어올 hashmap의 해당 key의 value에 이 폴더이름이 포함되어있는지 확인
			// 폴더이름으로 사용되는 대표 FaceId가 collection내에서 삭제될경우 hashmap의 value에서 값을 찾을수 없어 파일이 들어갈
			// 폴더를 찾지못하는경우가
			// 생길수 있으니 삭제가 시도될경우 폴더이름을 collection 내에 다른 FaceId로 변경을 하던지 다른대책을 찾을것
			for (SubimgVo SubimgVo : voSameFace.get(Key)) {
				Topath = uploadController.ImagePath + File.separator + email + File.separator + "GuestGroup"
						+ File.separator + RF + File.separator + SubimgVo.filename;
				String filePath = uploadController.ImagePath + File.separator + email + File.separator + "GuestGroup"
						+ File.separator + SubimgVo.filename;
				if (!new File(filePath).exists() || new File(Topath + File.separator + SubimgVo.filename).exists()) {
					continue;
				}
				Boolean bool = new FolderCheck().FileMove(filePath, Topath);
				if (bool)
					System.out.println(SubimgVo.filename + "->" + RF + " Move성공");
				else
					System.out.println(SubimgVo.filename + "->" + RF + " Move실패");
			}
		}
	}

}
