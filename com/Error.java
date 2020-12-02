
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;

public class Error {
	AwsVo Erroract(AmazonRekognitionException e,AwsVo awsVo){
		awsVo.setState("Error");
		awsVo.setErrmsg(e.getErrorMessage());
		awsVo.setErrcode(e.getErrorCode());
		return awsVo;
	}
	SubimgVo Erroract(AmazonRekognitionException e,SubimgVo SubimgVo){		
		SubimgVo.setMsg(e.getErrorMessage());
		SubimgVo.setErrcode(e.getErrorCode());
		return SubimgVo;
	}
}


//서블릿4.0버전을 사용하다가 3.1버전으로 내렸을때 multipartconfig를 사용할때
//jsp의 ajax코드에서 header에 {enctype : 'multipart/form-data'} 을 넣어주지 않으면 에러 발생
//클라우드상에 올린 서버에서 원하는 결과물이 나오지않아 게속 시간을 낭비했는데
//cli에서 실시간 log를 확인할 수 있는 방법을 찾았고
//알고보니 생각지도 못한 에러가 여러곳에서 발생하고 있었다.
//로그를 보는것은 정말 중요

//동일한 경로의 path를 클래스별로 하드코딩을 하다보니 다른 환경에서 테스트를 할때 일일히 바꿔주어야하고
//미처 발견하지 못하고 바꾸지 못한 path로 시간이 지연되는것을 느낌
//path를 변수에 저장해서 필요할때 끌어쓰는방식으로 바꿔야겠다고 느낌

//jackson 사용시 json을 vo객체에 자동 맵핑해주는지 알아볼것

//외부서버로 파일을 업로드시 cors policy 에러발생
//Access-Control-Allow-Origin:* 를 서버측 응답헤더에 추가하여 해
//Request.credentials
//omit: 절대로 cookie 들을 전송하거나 받지 않는다.
//same-origin: URL이 호출 script 와 동일 출처(same origin)에 있다면, user credentials (cookies, basic http auth 등..)을 전송한다. 
//이것은 default 값이다.
//include: cross-origin 호출이라 할지라도 언제나 user credentials (cookies, basic http auth 등..)을 전송한다.



//org.json.JSONobject는 parsing이전에 json string전체를 로딩하기때문에 
//큰 크기를 가진 json파일에서는 비효율적이다.
//그리고 가장큰 약점은 JSONException인데 이것은 try/catch문을 반드시 써야하기때문에 큰 불편으로 다가온다.

//Gson은 200kiB이하의 작은 바이너리파일에서 효율적이고 간단하고 빠르다.
//GSON과 jackson이 json data를 다루는 가장 유명한 솔루션들이