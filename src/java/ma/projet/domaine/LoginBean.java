package ma.projet.domaine;

import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.projet.beans.Employe;
import ma.projet.service.EmployeService;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
    private EmployeService employeService;
    private String email;
    private String password;
    private boolean showInvalidCredentials = false;

    // Getters et setters pour email, password et showInvalidCredentials

    public void checkCredentials() {
        if (!isValidCredentials()) {
            showInvalidCredentials = true;
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid email or password"));
        } else {
            showInvalidCredentials = false;
            try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/faces/web/employe/employeForm.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidCredentials() {
        List<Employe> employes;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("from Employe where email = :email AND password = :password")
            .setParameter("email", email)
            .setParameter("password", password)
            .list();
        session.getTransaction().commit();
        session.close();

        return !employes.isEmpty();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isShowInvalidCredentials() {
        return showInvalidCredentials;
    }

    public void setShowInvalidCredentials(boolean showInvalidCredentials) {
        this.showInvalidCredentials = showInvalidCredentials;
    }
}
