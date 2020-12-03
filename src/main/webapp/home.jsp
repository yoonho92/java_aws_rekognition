<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현관</title>
<script type="text/javascript">
<%String cp = request.getContextPath();%>
async function FaceAuthentication(){
    let return_data ={error:0,message:''};
    try {
    	let image ="http://210.94.22.142:12000/stream/snapshot.jpeg?delay_s=0"
        let data = new FormData();
        data.append('image',image);                
        let response = await fetch('<%= cp %>/faceauthentication',{
          method: 'POST',
        body: data
        });
        
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
        let FAimgjson = await response.json();
        document.getElementById("faceindexed").src = FAimgjson.imgPath;
        document.getElementById("msg").innerText = FAimgjson.msg;
    } catch (e) {
      return_data={error:1,message:e.message};
      console.log(e.message)
    } return return_data;
  }

window.onload=function(){
	document.getElementById("FAbtn").addEventListener('click', async function() {
        let FA = await FaceAuthentication();
        if(FA.error ==0) document.getElementById('state').innerText="FA OK";
        else if(FA.error ==1) document.getElementById('state').innerText="FA fail";
      });
	
	
}
</script>
</head>
<body>

<h1>문 앞</h1>
<br>
<embed src="http://210.94.22.142:12000/stream" width="660" height="510" ></embed>
<button id="FAbtn">들어가기</button>
<h4 id="state"></h4>
<img alt="" src="./images/white.png" id="faceindexed"> <h2 id="msg"> </h2>
</body>
</html>