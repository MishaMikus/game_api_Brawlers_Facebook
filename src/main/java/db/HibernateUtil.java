package db;

import db.bean.CallEntry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Properties;

public class HibernateUtil {
    static Properties prop;

    static {
        prop = new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/game_api_bot?createDatabaseIfNotExist=true");
        prop.setProperty("hibernate.connection.username", "root");
        prop.setProperty("hibernate.connection.password", "1234");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.hbm2ddl.auto", "update");
    }

    public static void main(String... args) {
        SessionFactory sessionFactory = new AnnotationConfiguration()
                .addPackage("db.bean")
                .addProperties(prop)
                .addAnnotatedClass(CallEntry.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        if(shemaDoNotExists(session)){
            System.out.println(session.createSQLQuery("CREATE SCHEMA 'game_api_bot'").getQueryReturns());
            tx.commit();
        }
        session.save(new CallEntry("asmdf"));
        System.out.println(session.get(CallEntry.class, 2l));
        tx.commit();
        sessionFactory.close();
    }

    private static boolean shemaDoNotExists(Session session) {
        return session.createSQLQuery(
                "SELECT  game_api_bot FROM information_schema.schemata WHERE schema_name = 'game_api_bot'").
                getQueryReturns().size()==0;
    }
}