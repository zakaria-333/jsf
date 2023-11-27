/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;

import java.util.ArrayList;
import java.util.List;
import ma.projet.beans.Service;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import ma.projet.beans.Employe;

/**
 *
 * @author pc
 */
public class ServiceService implements IDao<Service>{
    @Override
    public boolean create(Service o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        return true;
    }
    @Override
    public boolean update(Service o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        return true;
    }
    @Override
    public boolean delete(Service o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Service getById(int id) {
        Service service  = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        service  = (Service) session.get(Service.class, id);
        session.getTransaction().commit();
        return service;
    }

    @Override
    public List<Service> getAll() {
        
        List <Service> services = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        services  = session.createQuery("from Service").list();
        session.getTransaction().commit();
        return services;
    }
    
  
    
    public List<Employe> employeparservice(Service s){
        List <Employe> employes = new ArrayList<Employe>();
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes  = session.createQuery("from Employe e where e.service= :s").setParameter("s", s).list();
        session.getTransaction().commit();
        return employes;
        
    }
    
    
    public List<Employe> getChiefsForService(Service service) {
        List<Employe> chiefs = new ArrayList<>();

        // Supposons que les employés dans la liste employes de l'entité Service contiennent tous les employés associés à ce service,
        // et que parmi eux se trouvent les chefs spécifiques
        for (Employe employe : service.getEmployes()) {
            // Ajoutez les employés considérés comme chefs à la liste des chefs
            if (employe.isChief()) { // Ajoutez une méthode dans la classe Employe pour vérifier si l'employé est un chef ou non
                chiefs.add(employe);
            }
        }

        return chiefs;
    }
}
