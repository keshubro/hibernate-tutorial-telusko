package com.telusko;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Alien.class);
        ServiceRegistry serviceRegistry  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry );
        Session session1 = sessionFactory.openSession();
        
        Transaction transaction = session1.beginTransaction();
            Alien fetcedAlien = session1.get(Alien.class, 101);
            System.out.println(fetcedAlien);

            fetcedAlien = session1.get(Alien.class, 102);
            System.out.println(fetcedAlien);

            // It won't make a query to the DB. Instead, it will first check on the first level cache. If not found, second level cache.
            fetcedAlien = session1.get(Alien.class, 101);
            System.out.println(fetcedAlien);
        transaction.commit();
    }
}
