/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import ma.projet.beans.Service;
import ma.projet.beans.Employe;
import ma.projet.service.EmployeService;
import ma.projet.service.ServiceService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;


import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import org.primefaces.event.FileUploadEvent;
/**
 *
 * @author pc
 */
@ManagedBean(name = "employeBean")
public class EmployeBean {
    
    private Service service;

    private Employe employe;
    
    private List<Employe> employes;

    private ServiceService serviceService;
    private EmployeService employeService;
    private static ChartModel barModel;
    private Date date1;
    private Date date2;
    private List<Employe> employesBetweenDates;
    private Employe selectedChef;


    public EmployeBean() {
        employe = new Employe();
        selectedChef=new Employe();
        employeService = new EmployeService();
        employe.setService(new Service());
        //employe.setPhoto("photo.png");
        serviceService = new ServiceService();
    

    }

       public Employe getSelectedChef() {
        return selectedChef;
    }

    public void setSelectedChef(Employe selectedChef) {
        this.selectedChef = selectedChef;
    }

    
    
    

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = employeService.getAll();
        }
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public String onCreateAction() {
        employe.setChef(selectedChef);
        employeService.create(employe);
        employe = new Employe();
        return null;
    }

   public String onDeleteAction() {

        employeService.delete(employeService.getById(employe.getId()));
        return null;
    }
    
    
    public List<Employe> serviceLoad() {
        for (Employe e : employeService.getAll()) {
            if (e.getService().equals(service)) {
                employes.add(e);
            }
        }
        return employes;

    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }

    public void onEdit(RowEditEvent event) {
        employe = (Employe) event.getObject();
        Service newservice = serviceService.getById(this.employe.getService().getId());
        employe.setService(newservice);
        employeService.update(employe);
        
    }
    

    


    public void onCancel(RowEditEvent event) {
    }

    public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries employes = new ChartSeries();
        employes.setLabel("employes");
        model.setAnimate(true);
        for (Object[] m : employeService.nbemploye()) {
            employes.set(m[1], Integer.parseInt(m[0].toString()));
        }
        model.addSeries(employes);

        return model;
    }

    public List<Employe> employeLoad() {
        employesBetweenDates = employeService.getbydates(date1, date2);
        return null;

    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Employe> getEmployesBetweenDates() {
        return employesBetweenDates;
    }

    public void setEmployesBetweenDates(List<Employe> employesBetweenDates) {
        this.employesBetweenDates = employesBetweenDates;
    }
    
    


public void handleFileUpload(FileUploadEvent event) {
        try {
            InputStream inputStream = event.getFile().getInputstream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileContent = outputStream.toByteArray();
            System.out.println("fileContent: " + Arrays.toString(fileContent));
            employe.setPhoto(fileContent);
            outputStream.close();
            inputStream.close();

            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error uploading file");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    

    public ByteArrayInputStream getPhotoStream(byte[] photoBytes) {
        if (null != photoBytes) {
            ByteArrayInputStream b = new ByteArrayInputStream(photoBytes);
            int byteRead;
            while ((byteRead = b.read()) != -1) {
                System.out.print((char) byteRead);
            }
            return b;
        } else {
            System.out.println("Photo bytes are null");
            return null;
        }

    }
    
    public String getPhotoBase64(byte[] photoBytes) {
    if (photoBytes != null && photoBytes.length > 0) {
        return Base64.getEncoder().encodeToString(photoBytes);
    }
    return "";
}
}
