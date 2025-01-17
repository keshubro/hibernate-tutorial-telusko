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
        // New state - When we create an object using the new keyword
        Laptops laptop = new Laptops();

        // Transient state - When we assign values to data members
        laptop.setLaptopId(101);
        laptop.setBrand("Dell");
        laptop.setPrice(1000);

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Laptops.class);
        ServiceRegistry serviceRegistry  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry );
        Session session = sessionFactory.openSession();

        // Save data in DB
        Transaction transaction = session.beginTransaction();
            session.save(laptop);   // Managed state - when we call the save method
            laptop.setPrice(950);   // This will update the price to 950 in the DB automatically
        transaction.commit();

        session.detach(laptop); // Detached state
        laptop.setPrice(900);   // This updated value will not be persisted in the DB.
    }
}
