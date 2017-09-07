package com.caseindex.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.caseindex.bean.CaseList;
import com.caseindex.util.HibernateSessionFactory;

public class CaseListDao {

	public List<CaseList> getList(int aPageIndex, int aPageSize,String keyWord, int timeBegin, int timeFinal){
		int iPageIndex=(aPageIndex-1)*aPageSize;
		String sFuncDate="0";
		String sFormatDate="";
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT cl.`id`, cl.`keyword`,cl.`filename`,cl.`des`,cl.`time` FROM case_list cl WHERE 1=1";
		if(keyWord==null || "".equals(keyWord)){
		}else{
			sql = sql+" AND cl.`keyword` LIKE '%"+keyWord+"%'";
		}
		if(timeBegin!=0){
			sql = sql + " AND time>"+timeBegin;
		}
		if(timeFinal!=0){
			sql = sql +" AND time<"+timeFinal;
		}
		//if(aPageIndex!=1)
		sql = sql + " ORDER BY cl.id LIMIT "+iPageIndex+','+aPageSize+";";
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<CaseList> caseList =(List<CaseList>)session.createSQLQuery(sql).addEntity(CaseList.class).list();
		
		for(CaseList cl:caseList){
			sFuncDate=cl.getTime()+"";
			if(sFuncDate==null || "".equals(sFuncDate)){
				sFormatDate="";
			}else{
				sFormatDate= sdf.format(Long.parseLong(sFuncDate)*1000);				
			}
			cl.setFormTime(sFormatDate);
		}
			
		tx.commit();
		HibernateSessionFactory.closeSession();
		return caseList;
		
	}
	
	public void addList(String aKeyword, String aFilename, String aDescribe){
		//获得当前时间时间戳-10位
		Date date = new Date();
    	long lTime = date.getTime()/1000;
    	String sDateTime=lTime+"";
    	//sDateTime=sDateTime.substring(0, 10);
    	int iCurrentTime=Integer.parseInt(sDateTime);   	
    	
    	Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
    	
    	String sql = "INSERT INTO case_list(keyword, filename, des, TIME) VALUE(?,?,?,?);";
    	Query query = session.createSQLQuery(sql);
    	query.setString(0,aKeyword);
    	query.setString(1, aFilename);
    	query.setString(2,aDescribe);
    	query.setInteger(3, iCurrentTime);
    	
    	query.executeUpdate();
    	tx.commit();
		HibernateSessionFactory.closeSession();
	}
	
	public void updateList(int aListId, String aListKeyword, String aListDes, String aListFilename){
		//获得当前时间时间戳-10位
		Date date = new Date();
    	long lTime = date.getTime()/1000;
    	String sDateTime=lTime+"";
    	//sDateTime=sDateTime.substring(0, 10);
    	int iCurrentTime=Integer.parseInt(sDateTime);   	
    	
    	Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
    	
    	String sql = "UPDATE case_list SET keyword=?, filename=?, des=?, TIME=? WHERE id=?;";
    	Query query = session.createSQLQuery(sql);   	
    	query.setString(0,aListKeyword);
    	query.setString(1,aListFilename);
    	query.setString(2,aListDes);    	
    	query.setInteger(3, iCurrentTime);
    	query.setInteger(4, aListId);
    	
    	query.executeUpdate();
    	tx.commit();
		HibernateSessionFactory.closeSession();
	}
}
