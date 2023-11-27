/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.util.Date;
import java.util.List;

import ma.projet.beans.Service;
import ma.projet.beans.Employe;

import javax.faces.bean.ManagedBean;
import ma.projet.service.EmployeService;
import ma.projet.service.ServiceService;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author pc
 */

@ManagedBean(name = "serviceBean")
public class ServiceBean {
    

    private Service service;

    private Employe employe;
    
    private List<Employe> employes;
    private List<Service> services;

    private ServiceService serviceService;
    private EmployeService employeService;
    private static ChartModel barModel;


    public ServiceBean() {
        service = new Service();
        serviceService = new ServiceService();
        employe = new Employe();
        employeService = new EmployeService();
    }

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = service.getEmployes();
        }
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Service> getServices() {
        if (services == null) {
            services = serviceService.getAll();
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String onCreateAction() {
        serviceService.create(service);
        service  = new Service();
        return null;
    }

    public void onDeleteAction() {
        service.setEmployes(null);
        serviceService.delete(service);

    }

    public void onEdit(RowEditEvent event) {
        service = (Service) event.getObject();
        service.setEmployes(null);
        serviceService.update(service);
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }

    public void onCancel(RowEditEvent event) {
    }

    public void onEditm(RowEditEvent event) {
        employe = (Employe) event.getObject();
        Service service = serviceService.getById( this.employe.getService().getId() ) ;
        employe.setService(service);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
    }
    

    public String onDeleteActionm() {

        employeService.delete(employeService.getById(employe.getId()));
        return null;
    }

    public List<Employe> serviceLoad() {
        for (Employe m : employeService.getAll()) {
            if (m.getService().equals(employe)) {
                employes.add(m);
            }
        }
        return employes;

    }

    public void onCancelm(RowEditEvent event) {
    }

    public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries services = new ChartSeries();
        services.setLabel("Services");
        model.setAnimate(true);
        for (Service s : serviceService.getAll()) {
            services.set(s.getNom(), employeService.getEmployesByService(s).size());;
        }
        model.addSeries(services);
        
                
        return model;
    }
    
    
  
}