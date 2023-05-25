package com.example.gallery.dao;

import com.example.gallery.database.HibernateHelper;
import com.example.gallery.model.ImageDataSet;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ImageDAO {

    private SessionFactory sessionFactory;

    public ImageDAO(){
        sessionFactory= HibernateHelper.getSessionFactory();
    }

    public void save(ImageDataSet dataSet){
        Session session= sessionFactory.openSession();
        Transaction trn=session.beginTransaction();
        session.persist(dataSet);
        trn.commit();
        session.close();
    }

    public ImageDataSet get(Integer id){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        ImageDataSet image=session.get(ImageDataSet.class, id);
        session.getTransaction().commit();
        session.close();
        return  image;
    }

    public void delete(Integer id){

        ImageDataSet imageDataSet= get(id);
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.remove(imageDataSet);
        session.getTransaction().commit();
        session.close();
    }

    public List<ImageDataSet> getAll(){

        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        CriteriaBuilder critBuilder=session.getCriteriaBuilder();
        CriteriaQuery<ImageDataSet> cq=critBuilder.createQuery(ImageDataSet.class);
        Root<ImageDataSet> root=cq.from(ImageDataSet.class);
        CriteriaQuery<ImageDataSet> all= cq.select(root);
        TypedQuery<ImageDataSet> query = session.createQuery(all);
        List<ImageDataSet> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }
}
