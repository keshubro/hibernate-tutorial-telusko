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
        Laptop laptop = new Laptop();
        laptop.setLaptopId(101);
        laptop.setLaptopName("Dell");

        Student student = new Student();
        student.setName("Keshav");
        student.setRollNo(1);
        student.setMarks(52);
        student.setLaptop(laptop);

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Laptop.class).addAnnotatedClass(Student.class);
        ServiceRegistry serviceRegistry  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry );
        Session session = sessionFactory.openSession();

        // Save data in DB
        Transaction transaction = session.beginTransaction();
            session.save(laptop);
            session.save(student);
        transaction.commit();
    }
}
