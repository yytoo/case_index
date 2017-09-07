<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'caseindex.jsp' starting page</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/myjs.js"></script>
    <link href="<%=request.getContextPath()%>/css/list.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="onload()">
  <form action="caseList" name="listForm" id="listForm" method="post">
 	 <div class="searchDiv">
    		<span>关键字搜索</span>
    		<input type="text" name="keyWord" id="keyWord" class="keyWord" >
    		<span>时间段</span>   		
    		从<input type="text" name="timeBeg" id="timeBeg" value="">
    		到<input type="text" id="timeEnd" name="timeEnd" value="">
    		<input type="button"  onclick="screenings()" value="筛选">
    </div>
    <table class="table" id="table1">
 
    	<tr>
    		<th class="id">序号</th>
    		<th class="keyword">关键字</th>
    		<th class="des">描述</th>
    		<th class="filename">文件</th>
    		<th class="listdate">日期</th>
    		<th class="listbutton">修改按钮</th>
    	</tr>
		<s:iterator value="#request.lists" id="lists">
	    	<tr class="tr">
	    		<td class="id_td listId"><s:property value="#lists.id"/></td>
	    		<td class="id_td listKeyword"><s:property value="#lists.keyword"/></td>
	    		<td class="id_td listDes"><s:property value="#lists.des"/></td>
	    		<td class="id_td listFilename"><s:property value="#lists.filename"/></td>
	    		<td class="id_td listFormTime" ><s:property value="#lists.formTime"/></td>
	    		<td class="id_td"><input type="button" value="修改" onclick="changeContent(this)"><input type="button" value="保存" onclick="save(this)"></td>
	    	</tr>
		</s:iterator>
		
    </table>
    <div class="buttons">
		<p hidden id="hiddenP">${request.pageIndex}</p>
		<input type="button" id="lastPage"  class="changePage" value="上一页" onclick="lastPage5()">&nbsp;&nbsp;
		 第<input type="text" id="pageIndex" name="pageIndex" value=${request.pageIndex}>页&nbsp;&nbsp;共<span id="pageCount">${request.pageCount}</span>页&nbsp;&nbsp;
		<input type="button" id="jump" class="changePage" onclick="pageJump()" value="跳转">
		<input type="button" id="nextPage" class="changePage" value="下一页" onclick="nextPage5()">
		<input type="button" onclick="add()" value="新增一行,最多一行">				
	</div>
	</form>
  </body>
  
  <script type="text/javascript">	
  		var updateCount=0;  //每次页面刷新置0，方式反复点击修改，每次只能点击一次修改按钮
  		
  		//删选处时间日历的显示
	 	 $(function(){
	  		$('#timeBeg').datepicker({  
	  			dateFormat: "yy-mm-dd"
	  			 });
	  		$('#timeEnd').datepicker({  
	  			dateFormat: "yy-mm-dd"  
                });
	 	 });
	 	 
	 	 //加载时初始化
		function onload(){
		
			showTime();              //获取当前时间，显示在新插入行的时间单元格,该js在my.js中
			
			var pageIndex=$("#hiddenP").html();
			var maxPage=$("#pageCount").html();
			if(pageIndex==1){                              //判断当前是否第一页，是，则去除上一页按钮的onclick事件，并使按钮变灰色
				$("#lastPage").removeAttr("onclick");
				$("#lastPage").css("background","#aaa");
			}
			if(pageIndex==maxPage){
				$("#nextPage").removeAttr("onclick");
				$("#nextPage").css("background","#aaa");
			}
			
			$("#timeBeg").val("${timeBeg}");    //之前查询过的内容，翻页后继续保留，显示内容为查询结果的翻页情况
			$("#timeEnd").val("${timeEnd}");			
		}
		
		//翻页
		function lastPage5(){
			$("#listForm").attr("action","caseList");   //将form的action目标改回select
			var lastIndex=parseInt($("#hiddenP").html())-1;
			 $("#pageIndex").val(lastIndex);
			 $("#listForm").submit();	
		}
		 function nextPage5(){
			 $("#listForm").attr("action","caseList");
			var nextIndex=parseInt($("#hiddenP").html())+1;
			 $("#pageIndex").val(nextIndex);
			 $("#listForm").submit();
		}
		function pageJump(){
		$("#listForm").attr("action","caseList");
			$("#listForm").submit();
		} 
		
		//开始筛选	    
		function screenings(){
			$("#listForm").attr("action","caseList");    //将form的action目标改回select
			 $("#pageIndex").val("1");

			 $("#listForm").submit(); 
		}
		
		//table添加新的空白输入行
		function add(){
			var rowNum=$(".appendRow").length;
			if(rowNum==0){
				$("#table1").append("<tr class='appendRow'>"+
	    		"<td class='id_td'></td>"+
	    		"<td class='id_td'><input type='text' name='keyword' id='keyword' value=''></td>"+
	    		"<td class='id_td'><input type='text' name='des' id='des' value=''></td>"+
	    		"<td class='id_td'><input type='text' name='filename' id='filename' value=''></td>"+
	    		"<td class='id_td'><span class='showTime'></span></td>"+
	    		"<td class='id_td'><input type='button' onclick='saveNew()' value='保存新增的行'></td></tr>");
			}else{
				alert("当前已有空行");
			}
		 	   	
		}
		
		//保存新增的空白输入行
		function saveNew(){
			var varFilename=$("#filename").val();
			var varDes=$("#des").val();
			var varKeyword=$("#keyword").val();
			if(varKeyword==""){
				alert("关键字不能为空");
			}else{
				$("#listForm").attr("action","addList");   //改变form提交的目标action，变更为add
				$("#listForm").submit();
			}
		}
		
		//文本信息变input输入框,并将value默认原值
		function changeContent(obj){
			var tmp = obj;
			var par=$(tmp).parent();
			if(updateCount==0){
				var objKeyword=par.siblings(".listKeyword");
				var valKeyword = objKeyword.html();
				objKeyword.html("<input type='text' name='listKeyword' value='"+valKeyword+"' />");  //加上那么属性，用于下一步save提交
				
				var objDes = par.siblings(".listDes");
				var valDes = objDes.html();
				objDes.html("<input type='text' name='listDes' value='"+valDes+"' />");
				
				var objFilename = par.siblings(".listFilename");
				var valFilename = objFilename.html();
				objFilename.html("<input type='text' name='listFilename' value='"+valFilename+"' />");
				
				var objId=par.siblings(".listId");
				var valId = objId.html();
				objId.html("<input hidden type='text' name='listId' value='"+valId+"' />"+valId);   //form提交时无法直接提交<tr>的html，添加一个input用于提交id
				
				updateCount=updateCount+1;
			}else{
				alert("每次只能修改一个");
			}
			
		}
		
		//保存修改的行
		function save(obj){			
			$("#listForm").attr("action","addList");   //改变form提交的目标action，变更为add
			$("#listForm").submit();
		
		}
  </script>
</html>
