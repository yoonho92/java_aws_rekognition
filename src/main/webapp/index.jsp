<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
<% String cp = request.getContextPath(); %>
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
    document
    }
    }else{
    	alert("파일크기가 5MB를 초과합니다.");
    	document.getElementById(id).value='';
    }
  }
  
async function upload(){
    let return_data ={error:0,message:''};
    try {
    	var upload =document.getElementById("upload");
      if(upload.files.length==0){
        throw new Error('No file selected');
      }else{
        let data = new FormData();
        data.append('file',upload.files[0]);                
        let response = await fetch('<%= cp %>/index',{
          method: 'POST',
          credentials: 'same-origin',
          header:{enctype : 'multipart/form-data'},
        body: data
        });
        
        if(response.status != 200) {throw new Error('HTTP response code != 200'); }
         document.getElementById('uploadstatus').innerText="OK";}
    } catch (e) {
      return_data={error:1,message:e.message};
    } return return_data;
  }
window.onload=function(){
document.getElementById("uploadbtn").addEventListener('click', async function() {
    let uploada = await upload();
    if(uploada.error ==0) alert('image uploaded successful');
    else if(uploada.error ==1) alert('image uploading failed - '+uploada.message)
  });
}
 
</script>
</head>
<body>
<input type="file" accept="image/*" id="upload" name="upload" value="" onchange="previewFile()">
         <input type="button" id="uploadbtn" value="ok"> <br>
         <h6 id="uploadstatus">상태</h6>
         <img src="./images/link_theapplication_2893.ico" height="100" alt="preview3" id="uploadimg" >
</body>
</html>