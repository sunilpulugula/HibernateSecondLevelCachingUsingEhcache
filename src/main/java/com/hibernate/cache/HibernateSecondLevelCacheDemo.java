package com.hibernate.cache;

import java.net.URL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

import com.hibernate.cache.model.User;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 23/11/15
 */
public class HibernateSecondLevelCacheDemo {

    public static void main(String[] args) {

        SessionFactory sessionFactory = buildSessionFactory();

        //enabling statistics
        final Statistics statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        //Adding entries to user table
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User userOne = new User(1, "user1", "user1", "user", true);
        User userTwo = new User(2, "user2", "user2", "user", false);
        session.save(userOne);
        session.save(userTwo);
        session.getTransaction().commit();
        session.close();



        Session sessionOne = sessionFactory.openSession();
        Session sessionTwo = sessionFactory.openSession();

        Transaction transactionOne = sessionOne.beginTransaction();
        Transaction transactionTwo = sessionTwo.beginTransaction();

        //printing initial statistics
        printStatistics(statistics,null,0);

        User user1=(User)sessionOne.load(User.class, 1);
        printStatistics(statistics,user1,1);

        User user2=(User)sessionOne.load(User.class, 1);
        printStatistics(statistics,user2,2);

        User user3=(User)sessionOne.load(User.class, 2);
        printStatistics(statistics,user3,3);

        User user4=(User)sessionTwo.load(User.class, 1);
        printStatistics(statistics,user4,4);

        User user5=(User)sessionTwo.load(User.class, 2);
        printStatistics(statistics,user5,5);

        transactionOne.commit();
        transactionTwo.commit();
        sessionOne.close();
        sessionTwo.close();
        sessionFactory.close();

    }



    public static void printStatistics(Statistics statistics, User user , int count)
    {
        System.out.println("***************");
        System.out.println("Hit : "+ count);
        if(user != null)
            System.out.println("User Details :" + user.toString());
        System.out.println("Entity fetch count :" + statistics.getEntityFetchCount());
        System.out.println("Second level cache hit count : "+ statistics.getSecondLevelCacheHitCount());
        System.out.println("Second level cache put count : " + statistics.getSecondLevelCachePutCount());
        System.out.println("Second level cache miss count : " + statistics.getSecondLevelCacheMissCount());
        System.out.println("***************");
    }

    public static SessionFactory buildSessionFactory() {
        URL url = HibernateSecondLevelCacheDemo.class.getClassLoader().getResource("configuration/hibernate.cfg.xml");
        Configuration configuration = new Configuration();
        configuration.configure(url);
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(ssrb.build());
    }

}
