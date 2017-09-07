package com.caseindex.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;


import com.caseindex.util.HibernateSessionFactory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		addMember();
	}
	
	public static void addMember(){
		//Member mb = new Member("jhon");
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		
		tx.commit();
		HibernateSessionFactory.closeSession();
		/*System.out.println("i:"+ i);*/
	}
	


}
