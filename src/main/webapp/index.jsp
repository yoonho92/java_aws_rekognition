<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function previewFile() {
	var id = event.currentTarget.id;
    var preview = document.querySelector("#"+id+"img");
    var file = document.getElementById(id).files[0];
    console.log(id);
    console.log(file);
    console.log(preview);
    if(file.size < 1024*1024*5){


    var reader = new FileReader();
    reader.addEventListener("load",function(){
      preview.src = reader.result;
      
    },false);
    if(file){
      reader.readAsDataURL(file);
    }
    }else{
    	alert("파일크기가 5MB를 초과합니다.");
    	document.getElementById(id).value='';
    }
  }
<%String cp = request.getContextPath();%>
async function AddFacesToCollection(){
	

    let return_data ={error:0,message:''};
    try {
    	var AFCupload =document.getElementById("AFCupload");
      if(AFCupload.files.length==0){
        throw new Error('No file selected');
      }else{
        let AFCdata = new FormData();
        AFCdata.append('file',AFCupload.files[0]);   
        let response = await fetch('<%=cp%>/addfacestocollection',{
          method: 'POST',
        body: AFCdata
        });
        let response_json = await response.text();
        document.getElementById('AFCh4').innerText=response_json;
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
    } 
    }catch (e) {
      return_data={error:1,message:e.message};
    } return return_data;
  }
async function ListFacesInCollection(){
	

    let return_data ={error:0,message:''};
    try {
        let LFCdata = new FormData();
        LFCdata.append('LFCtext',document.getElementById('LFCtext').value);   
        let response = await fetch('<%=cp%>/listfacesincollection',{
          method: 'POST',
        body:LFCdata
        });
        let response_json = await response.json();
        console.log(response_json);
        document.getElementById('LFCh4').innerText= JSON.stringify(response_json);
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
    }catch (e) {
      return_data={error:1,message:e.message};
    } return return_data;
  }
async function DeleteFacesFromCollection(){
	

    let return_data ={error:0,message:''};
    try {
        let DFCdata = new FormData();
        DFCdata.append('DFCtext',document.getElementById('DFCtext').value);   
        let response = await fetch('<%=cp%>/deletefacesfromcollection',{
          method: 'POST',
        body:DFCdata
        });
        let response_json = await response.json();
        console.log(response_json);
        document.getElementById('DFCh4').innerText= JSON.stringify(response_json);
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
    }catch (e) {
      return_data={error:1,message:e.message};
    } return return_data;
  }
async function SearchFaceMatchingImageCollection(){
    let return_data ={error:0,message:''};
    try {
    	var SFimgupload =document.getElementById("SFimageupload");
      if(SFimgupload.files.length==0){
        throw new Error('No file selected');
      }else{
        let data = new FormData();
        data.append('file',SFimgupload.files[0]);                
        let response = await fetch('<%= cp %>/searchfacematchingimagecollection',{
          method: 'POST',
        body: data
        });
        let SFimgjson = await response.json();
        console.log(SFimgjson)
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
         document.getElementById('SFimagestatus').innerText="OK";}
    } catch (e) {
      return_data={error:1,message:e.message};
    } return return_data;
  }
window.onload=function(){
	document.getElementById("AFCbtn").addEventListener('click', async function() {
	    let AFC = await AddFacesToCollection();
	    if(AFC.error ==0) document.getElementById('state').innerText="AFC OK";
	    else if(AFC.error ==1) document.getElementById('state').innerText="AFC fail";
	  });
	document.getElementById("LFCbtn").addEventListener('click', async function() {
        let LFC = await ListFacesInCollection();
        if(LFC.error ==0)document.getElementById('state').innerText="LFC OK";
        else if(LFC.error ==1) document.getElementById('state').innerText="LFC fail";
      });
	document.getElementById("DFCbtn").addEventListener('click', async function() {
        let DFC = await DeleteFacesFromCollection();
        if(DFC.error ==0)document.getElementById('state').innerText="DFC OK";
        else if(DFC.error ==1) document.getElementById('state').innerText="DFC fail";
      });
	document.getElementById("SFimagebtn").addEventListener('click', async function() {
        let SFimage = await SearchFaceMatchingImageCollection();
        if(SFimage.error ==0) document.getElementById('state').innerText="SFimage OK";
        else if(SFimage.error ==1) document.getElementById('state').innerText="SFimage fail";
      });
}
</script>
</head>
<body>
	<
	<input type="file" accept="image/*" id="AFCupload" name="upload3"
		value="" onchange="previewFile()">
	<input type="button" id="AFCbtn" value="AddFacesToCollection">
	<br>
	<h4 id="AFCh4"></h4>
	<img src="./images/link_theapplication_2893.ico" height="100"
		alt="preview3" id="AFCuploadimg">
	<br>
	<br>
	<br>
	<input type="text" name="LFCtext" value="" id="LFCtext">
	<input type="button" name="" value="ListFacesInCollection" id="LFCbtn">
	<h4 id="LFCh4"></h4>
	<br>
	<br>
	<input type="text" name="DFCtext" value="" id="DFCtext">
	<input type="button" name="" value="DeleteFacesFromCollection"
		id="DFCbtn">
	<h4 id="DFCh4"></h4>
	<br>
	<br>
	<input type="file" accept="image/*" id="SFimageupload" name="upload4"
		value="" onchange="previewFile()">
    <input type="button" id="SFimagebtn" value="ok">
    <br>
    

	
	<br>
	<h6 id="SFimagestatus">상태</h6>
	<img src="./images/link_theapplication_2893.ico" height="100"
		alt="preview4" id="SFimageuploadimg">
	<h2 id="state"></h2>
</body>
</html>