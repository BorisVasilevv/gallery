package com.example.gallery.database;


import com.example.gallery.model.ImageDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateHelper {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private static SessionFactory factory;
    //?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false
    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            /*Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/bdnar");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
            configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            configuration.addAnnotatedClass(ImageDataSet.class);
            builder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = builder.build();
            factory = configuration.buildSessionFactory(serviceRegistry);*/


            factory=new Configuration()
                    .configure("/hibernate.configuration.xml")
                    .addAnnotatedClass(ImageDataSet.class)
                    .buildSessionFactory();
        }
        return factory;
    }


}