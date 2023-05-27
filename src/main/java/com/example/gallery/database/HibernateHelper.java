package com.example.gallery.database;


import com.example.gallery.model.ImageDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateHelper {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private static SessionFactory factory;

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            factory=new Configuration()
                    .configure("/hibernate.configuration.xml")
                    .addAnnotatedClass(ImageDataSet.class)
                    .buildSessionFactory();
        }
        return factory;
    }
}