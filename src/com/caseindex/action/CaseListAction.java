package com.caseindex.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.caseindex.bean.CaseList;
import com.caseindex.dao.CaseListDao;
import com.caseindex.util.Page;

public class CaseListAction {
	private int pageIndex;   //用于struts重定向传输时参数传递
	HttpServletRequest request =ServletActionContext.getRequest();
	public String getCaseList(){
		//HttpServletRequest request =ServletActionContext.getRequest();		
		int iPageSize=10;
		Page page = new Page();	
		int iPageIndex=1;
		int iTimeBeg=0;    //搜索起始时间
		int iTimeEnd=0;    //所搜结束时间
		int iRowCount=0;
		String sUTime="";
		String sKeyWord="";  //搜索关键字
		Date date;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		//获取当前页码
		String sPageIndex=request.getParameter("pageIndex");
		
		//设置页码
		if(sPageIndex==null || "".equals(sPageIndex)){
		}else{
			iPageIndex=Integer.parseInt(request.getParameter("pageIndex"));
		}
		
		//设置搜索条件-时间
		String sTimeBeg=request.getParameter("timeBeg");		
		if(sTimeBeg!=null&&(!"".equals(sTimeBeg))){			
			try {
				date = sdf.parse(sTimeBeg);
				sUTime=date.getTime()/1000+"";
				iTimeBeg=Integer.parseInt(sUTime);	
				request.setAttribute("timeBeg",sTimeBeg);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}
		String sTimeEnd=request.getParameter("timeEnd");
		if(sTimeEnd!=null&&(!"".equals(sTimeEnd))){
			try {
				date = sdf.parse(sTimeEnd);
				sUTime=date.getTime()/1000+"";
				iTimeEnd=Integer.parseInt(sUTime);	
				request.setAttribute("timeEnd",sTimeEnd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		
		//设置每页最大显示条数
		page.setiPageSize(iPageSize);
		
		//设置搜索关键字
		sKeyWord=request.getParameter("keyWord");
		String sHql="SELECT COUNT(*) AS rowCount FROM CaseList WHERE 1=1";  
		if(sKeyWord==null || "".equals(sKeyWord)){	
		}else{
			sHql = sHql + " AND keyword LIKE '%"+sKeyWord+"%'";
		}
		if(iTimeBeg!=0) sHql=sHql+" and time>" +iTimeBeg;
		if(iTimeEnd!=0) sHql=sHql+" and time<" +iTimeEnd;

		page.setiRowCount(sHql);
		iRowCount=page.getiRowCount();
		
		//设置每页显示行数
		page.setiPageCount();
		int iPageCount=page.getiPageCount();

		//设置页码
		if(iPageIndex < 0 || iPageIndex > iPageCount){
			iPageIndex=1;
		}
		
		//获取xml中传递过来的pageIndex参数
		if(getPageIndex()!=0){
			iPageIndex=getPageIndex();	
		}
		
		//查询
		CaseListDao cid = new CaseListDao();
		List<CaseList> caseItems = cid.getList(iPageIndex, iPageSize,sKeyWord,iTimeBeg,iTimeEnd);
		
		request.setAttribute("lists", caseItems);
		request.setAttribute("pageCount", iPageCount);	
		request.setAttribute("pageSize", iPageSize);
		request.setAttribute("pageIndex", iPageIndex);

		return "caseList";
	}
	
	public String addCaseList(){
		//获取当前页码
		String sPageIndex=request.getParameter("pageIndex");
		int iPageIndex=1;
		
		int iListId=0;
		
		//设置页码
		if(sPageIndex==null || "".equals(sPageIndex)){
			iPageIndex=1;
		}else{
			iPageIndex=Integer.parseInt(request.getParameter("pageIndex"));
		}
		
		//获取新增行的参数
		String sKeyword=request.getParameter("keyword");
		String sDescribe=request.getParameter("des");
		String sFilename=request.getParameter("filename");
		
		//获取修改行的参数
		String sListId = request.getParameter("listId");
		if(sListId==null || "".equals(sListId)){
			iListId=0;
		}else{iListId = Integer.parseInt(sListId);}
		String sListKeyword = request.getParameter("listKeyword");
		String sListDes = request.getParameter("listDes");
		String sListFilename = request.getParameter("listFilename");
		
		CaseListDao cld = new CaseListDao();
		
		if(iListId==0){                         //判断是新建还是修改
			ArrayList<String> paraList = new ArrayList<String>();          //判断新增内容是否为空
			paraList.add(sKeyword);
			paraList.add(sDescribe);
			paraList.add(sFilename);
			for(String it: paraList){
				if(it==null || "".equals(it)){
					System.out.println("no value");
				}else{
					cld.addList(sKeyword, sFilename, sDescribe);
					break;
				}
			}	
		}else{
			cld.updateList(iListId, sListKeyword, sListDes, sListFilename);
		}
			
		setPageIndex(iPageIndex);
		//request.setAttribute("pageIndex", iPageIndex);
		return "returnList";	
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
	
}
