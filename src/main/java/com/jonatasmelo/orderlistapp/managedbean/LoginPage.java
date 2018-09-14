package com.jonatasmelo.orderlistapp.managedbean;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class LoginPage {

    @ManagedProperty("#{userSession}")
    private UserSession userSession;

    private String username;
    private String password;

    public String login() {
        userSession.invalidate();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || !userSession.login(username, password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Username and/or password not expected"));
            return null;
        }

        return "/orders.jsf?faces-redirect=true";
    }

    public void validateLogin() {
        if (!userSession.isLoggedIn()) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) ctx.getApplication().getNavigationHandler();
            nav.performNavigation("/index.jsf?faces-redirect=true");
        }
    }

    public String logout() {
        userSession.invalidate();
        return "/index.jsf?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}
