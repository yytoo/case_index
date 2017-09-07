/********ajax图片上传******************/
function fileUpload(aBasePath,aflag){
		var basePath=$('#basePath').attr("value");	
		var formId=	$('#hiddenId').attr("value");
			$.ajaxFileUpload({
				url:"uploadCaseImg",
				secureuri:"false",
				fileElementId:"upload",
				dataType:"json",
				data:{
                        flag:aflag,
                        formId:formId,
                        },
				success:function(data){				
				 	$('#img').attr('src',aBasePath+"PicFile/"+data.sPicPath); 
				 	$('#pic').val(data.sPicPath); 
				}
			});
		}

/********获取当前时间，并显示在class为“showTime”的单元格内********/
function showTime(){           
	var now=new Date();
	var year=now.getFullYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hours=now.getHours();
	var minutes=now.getMinutes();
	var seconds=now.getSeconds();
	$(".showTime").html(""+year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds+"");
	setTimeout("showTime()",1000);
}