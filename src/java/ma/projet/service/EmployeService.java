/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author pc
 */
public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Employe getById(int id) {
        Employe employe = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employe = (Employe) session.get(Employe.class, id);
        session.getTransaction().commit();
        return employe;
    }

    @Override
    public List<Employe> getAll() {

        List<Employe> employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("from Employe").list();
        session.getTransaction().commit();
        return employes;
    }

    public List<Object[]> nbemploye() {
        List<Object[]> employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("select count(e.nom), e.service from Employe e group by e.service").list();
        session.getTransaction().commit();
        return employes;
    }

    public List<Employe> getbydates(Date d1, Date d2) {
        List<Employe> employes = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("from Employe e where e.dateNaissance between :d1 and :d2").setParameter("d1", d1).setParameter("d2", d2).list();
        session.getTransaction().commit();
        return employes;

    }

    public List<Employe> getEmployeesForChef(Employe chef) {

        List<Employe> employes = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("FROM Employe e WHERE e.chef = :chef").setParameter("chef", chef).list();
        session.getTransaction().commit();
        return employes;

    }
    
    public List<Employe> getEmployesByService(Service s){
        List <Employe> employes = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "FROM Employe e WHERE e.service = :service";
        employes = session.createQuery(hql)
            .setParameter("service", s)
            .list();
        session.getTransaction().commit();
        return employes;
    }

}
