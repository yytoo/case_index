package com.caseindex.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.caseindex.util.HibernateSessionFactory;

public class ToolDao {
	public int getRowCount(String aHql){
		
		int iRowCount=0;
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Integer> list = session.createQuery(aHql).list();
		iRowCount=((Number)list.get(0)).intValue();		
		tx.commit();
		HibernateSessionFactory.closeSession();
		return iRowCount;
	}
}
