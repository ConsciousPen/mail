package com.helpers;

import com.ApplicationManager;
import com.User;
import com.base.HelperBase;
import com.utils.HibernateUtil;
import org.hibernate.Session;

public class HibernateHelper extends HelperBase {

	public HibernateHelper(ApplicationManager manager) {
	  super(manager);
	}

	public String getUserId(User user) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		return session.createQuery("Select id from User where login =?")
				.setParameter(0,user.login).uniqueResult().toString();
	}
}
