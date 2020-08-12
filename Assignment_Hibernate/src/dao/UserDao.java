/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Users;
import java.io.Serializable;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Admin
 */
public class UserDao implements BaseDAO<Users> {

    public final SessionFactory sf = HibernateUtil.getSessionFactory();
    Session session = null;
    Transaction transaction = null;

    @Override
    public ArrayList<Users> getAllDB() {
        ArrayList<Users> list = new ArrayList<>();

        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            list = (ArrayList<Users>) session.createCriteria(Users.class).list();
            session.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addDB(Users t) {
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean updateDB( Users t) {
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.update( t);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean deleteDB( Users t) {
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.delete( t);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public Users findToObject(Object username) {
        try {
            Users us = new Users();
            session = sf.openSession();
            transaction = session.beginTransaction();
            us = (Users) session.get(Users.class, (Serializable) username);
            session.close();
            return us;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
