package com.telusko;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Alien.class);
        ServiceRegistry serviceRegistry  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry );
        Session session1 = sessionFactory.openSession();
        Session session2 = sessionFactory.openSession();

        Transaction transaction = session1.beginTransaction();
            Query q1 = session1.createQuery("from Alien where aid=101");
            q1.setCacheable(true);  // Enable second level cache for this query
            Alien fetchedAlien = (Alien) q1.uniqueResult();
            System.out.println(fetchedAlien);
        transaction.commit();

        session1.close();

        Transaction transaction2 = session2.beginTransaction();
             // It will check in the second level cache as the session where the same thing was queried is different
            Query q2 = session2.createQuery("from Alien where aid=101");
            q2.setCacheable(true);  // Enable second level cache for this query
            Alien fetchedAlien1 = (Alien) q2.uniqueResult();
            System.out.println(fetchedAlien1);
        transaction2.commit();
    }
}
