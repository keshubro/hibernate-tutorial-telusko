package com.telusko;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Random;

public class App 
{
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Students.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                                                .applySettings(configuration.getProperties())
                                                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

              // Save 50 Students objects in DB
//            Random r = new Random();
//
//            for(int i =1; i<=50; i++) {
//                Students s = new Students();
//                s.setRollno(i);
//                s.setName("Name " + i);
//                s.setMarks(r.nextInt(100));
//                session.save(s);
//            }

            // Get all the rows from the Students table
            Query q = session.createQuery("from Students");
            List<Students> students = q.list();   // The list() method returns a list of Students

            for(Students s : students) {
                System.out.println(s);
            }

            // Get the students with marks > 50
            Query greaterThan50Query = session.createQuery("from Students where marks > 50");
            List<Students> studentsWithMarksGreaterThan50 = greaterThan50Query.list();   // The list() method returns a list of Students

            for(Students s : studentsWithMarksGreaterThan50) {
                System.out.println(s);
            }

            // Get a single row
            Query singleRowResult = session.createQuery("from Students where rollno=7");
            Students student = (Students) singleRowResult.uniqueResult();
            System.out.println("Student with roll 7 : " + student);

            // Get Student names only
            Query studentNamesQuery = session.createQuery("select name from Students");
            List<String> studentNames = studentNamesQuery.list();
            studentNames.forEach(System.out::println);

            // Get name and marks columns
            // If we have data from multiple columns, the return type of the HQL query will be an array of objects where each object will be data from each column
            Query nameAndMarksQuery = session.createQuery("select s.name, s.marks from Students s");
            List<Object[]> namesAndMarks = nameAndMarksQuery.list();
            namesAndMarks.forEach(e -> System.out.println(e[0] + " -> " + e[1]));

            // Get sum of marks
            Query sumOfMarksQuery = session.createQuery("select sum(s.marks) from Students s where s.marks > 60");
            Long sumOfMarks = (Long) sumOfMarksQuery.uniqueResult();
            System.out.println("Sum is " + sumOfMarks);

            // Get only the students whose marks > 50
            // Normally, this value "50" will come from a variable for dynamic behaviour.
            int minMarks = 50;
            Query newQuery = session.createQuery("from Students s where s.marks > :minMarks");
            newQuery.setParameter("minMarks", minMarks);
            List<Students> newResultList = newQuery.list();

            System.out.println("Only the students with marks > " + minMarks);
            newResultList.forEach(System.out::println);

        transaction.commit();
    }
}
