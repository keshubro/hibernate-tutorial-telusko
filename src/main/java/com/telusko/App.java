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
        AlienName name = new AlienName();
        name.setFirstName("Keshav");
        name.setMiddleName("Kumar");
        name.setLastName("Sharma");

        Alien keshav = new Alien();
        keshav.setAid(101);
        keshav.setAname(name);
        keshav.setColor("Green");

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Alien.class);
        ServiceRegistry serviceRegistry  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry );
        Session session = sessionFactory.openSession();

        // Save data in DB
        Transaction transaction = session.beginTransaction();
            session.save(keshav);
        transaction.commit();

        // Fetch data from DB
        // Will give us null if data does not exist in the DB
        Alien fetcedAlien = session.get(Alien.class, 101);
        System.out.println(fetcedAlien);
    }
}
