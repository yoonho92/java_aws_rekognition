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
        AFCdata.append('collectionName',document.getElementById("CCtext").value);
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
async function CreateCollection() {


    let return_data = {error:0,message:''};
    try{
    	let data = new FormData();
    	data.append('collectionName',document.getElementById('CCtext').value);
    let response = await fetch('<%=cp%>/createcollection',{
      method: 'POST',
      body: data
    });
    if(response.status != 200) {throw new Error('HTTP response code != 200');

    }
    let responseText = await response.text();
    console.log(responseText);
    }catch (e) {
        return_data={error:1,message:e.message};
      }
    return return_data;
  }
async function ListFacesInCollection(){
	

    let return_data ={error:0,message:''};
    try {
        let LFCdata = new FormData();
        LFCdata.append('collectionName',document.getElementById('LFCtext').value);   
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
async function ListCollections() {
	

    let return_data = {error:0,message:''};
    try {
   	  let LCdata = new FormData();

      let response = await fetch('<%=cp%>/listcollections',{
        method:'POST',
        body: LCdata
      });
      if(response.status != 200) {throw new Error('HTTP response code != 200');}
      let response_json =await response.text();
      document.getElementById('LCh6').innerText=response_json;
    } catch (e) {
      return_data={error:1,message:e.message};
    }
    return return_data;
  }
async function DeleteFacesFromCollection(){
	

    let return_data ={error:0,message:''};
    try {
        let DFCdata = new FormData();
        DFCdata.append('DFCtext',document.getElementById('DFCtext').value);
        DFCdata.append('collectionName',document.getElementById("CCtext").value);
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

async function DeleteCollection() {
	let dbResponse = await fetch('<%=cp%>/selectOne',{
		method:'POST',
	});
	let DBjson = await dbResponse.text();

    let return_data = {error:0,message:''};
    try{
    	let DLdata = new FormData();
        DLdata.append('collectionName',document.getElementById('DLtext').value);
        DLdata.append('DBjson',DBjson);
     let response = await fetch('<%=cp%>/deletecollection',{
      method: 'POST',
      body: DLdata
    });


    if(response.status != 200) {throw new Error('HTTP response code != 200');}
    let response_json =await response.text();
    document.getElementById('DLh4').innerText=response_json;
    
    }catch (e) {
        return_data={error:1,message:e.message};
      }
    return return_data;
  }
async function GuestList(){

    let return_data ={error:0,message:''};
    try {
        let GLdata = new FormData();
        let response = await fetch('<%=cp%>/guestlist',{
          method: 'POST',
        body:GLdata
        });
        let response_text = await response.text();
        response_json = JSON.parse(response_text);
      	
        response_text = response_text.replace(/\n/g,"<br>");
        
        document.getElementById('GLh4').innerHTML= response_text;
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
    }catch (e) {
      return_data={error:1,message:e.message};
    } return return_data;
  }
async function FaceAuthentication(){
	
    let return_data ={error:0,message:''};
    try {
    	var FAupload =document.getElementById("FAupload");
        if(FAupload.files.length==0){
          throw new Error('No file selected');
        }else{
          let FAdata = new FormData();
          FAdata.append('file',FAupload.files[0]);   

          let response = await fetch('<%=cp%>/faceauthentication',{
            method: 'POST',
          body: FAdata
          });
        
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
        let FAimgjson = await response.json();
        }
    } catch (e) {
      return_data={error:1,message:e.message};
      console.log(e.message)
    } return return_data;
  }
window.onload=function(){
	document.getElementById("CCbtn").addEventListener('click', async function(){
	    let CC = await CreateCollection();
	    if(CC.error==0) document.getElementById('state').innerText="CC OK";
	    else if(CC.error ==1) alert("collection creating faied - "+ CC.message)
	  });
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
	document.getElementById("DLbtn").addEventListener('click', async function(){
	    let DL = await DeleteCollection();
	    if(DL.error==0) document.getElementById('state').innerText="DL OK";
	    else if(DL.error ==1) alert("collection deleting faied - "+ DL.message)
	});
	document.getElementById("DFCbtn").addEventListener('click', async function() {
        let DFC = await DeleteFacesFromCollection();
        if(DFC.error ==0)document.getElementById('state').innerText="DFC OK";
        else if(DFC.error ==1) document.getElementById('state').innerText="DFC fail";
      });
	document.getElementById("LCbtn").addEventListener('click', async function(){
	    let LC = await ListCollections();
	    if(LC.error==0) document.getElementById('state').innerText="LC OK";
	    else if(LC.error ==1) alert("collection creating faied - "+ LC.message)
	});
	document.getElementById("GLbtn").addEventListener('click', async function() {
	    let GL = await GuestList();
	    if(GL.error ==0)document.getElementById('state').innerText="GL OK";
	    else if(GL.error ==1) document.getElementById('state').innerText="GL fail";
	  });
	document.getElementById("FAbtn").addEventListener('click', async function() {
	    let FA = await FaceAuthentication();
	    if(FA.error ==0) document.getElementById('state').innerText="FA OK";
	    else if(FA.error ==1) document.getElementById('state').innerText="FA fail";
	  });
}

</script>
</head>
<body>
	
	<input type="file" accept="image/*" id="AFCupload" name="upload3"
		value="" onchange="previewFile()">
	<input type="button" id="AFCbtn" value="AddFacesToCollection">
	<br>
	<br>
	<input type="text" name="" value="" id="CCtext">
	<input type="button" name="" value="CreateCollection" id="CCbtn">
	<br>
	<br>
	<input type="button" name="" value="ListCollections" id="LCbtn">
	<h4 id="LCh6"></h4>
	<br>
	<br>
	<input type="text" name="" value="" id="DLtext">
	<input type="button" name="" value="DeleteCollection" id="DLbtn">
	<h4 id="DLh4"></h4>
	<br>
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
	<input type="button" name="" value="GuestList" id="GLbtn">
	<h4 id="GLh4"></h4>
	<br>
	<br>

	<input type="file" accept="image/*" id="FAupload" name="upload4"
		value="" onchange="previewFile()">
	<input type="button" id="FAbtn" value="FA">
<img src="./images/link_theapplication_2893.ico" height="100"
		alt="preview4" id="FAuploadimg">

	<h2 id="state"></h2>
</body>
</html>