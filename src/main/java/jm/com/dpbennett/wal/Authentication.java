/*
Business Entity Library (BEL) - A foundational library for JSF web applications 
Copyright (C) 2017  D P Bennett & Associates Limited

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

Email: info@dpbennett.com.jm
 */
package jm.com.dpbennett.wal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import jm.com.dpbennett.business.entity.JobManagerUser;
import jm.com.dpbennett.business.entity.SystemOption;
import jm.com.dpbennett.wal.utils.Utils;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Desmond Bennett
 */
public class Authentication implements Serializable {

    @PersistenceUnit(unitName = "JMTSPU")
    private EntityManagerFactory EMF;
    private JobManagerUser user;
    private String username;
    private String logonMessage;
    private String password;
    private Integer loginAttempts;
    private Boolean userLoggedIn;
    private List<AuthenticationListener> loginListeners;

    public Authentication() {
        password = "";
        username = "";
        loginAttempts = 0;
        userLoggedIn = false;
        logonMessage = "Please provide your login details below:";
        user = new JobManagerUser();
        loginListeners = new ArrayList<>();
    }

    public Authentication(JobManagerUser user) {
        password = "";
        username = "";
        loginAttempts = 0;
        userLoggedIn = false;
        logonMessage = "Please provide your login details below:";
        this.user = user;
        loginListeners = new ArrayList<>();
    }

    public void reset() {
        password = "";
        username = "";
        loginAttempts = 0;
        userLoggedIn = false;
        logonMessage = "Please provide your login details below:";
        user = new JobManagerUser();    
        PrimeFaces.current().executeScript("PF('loginDialog').show();");
    }

    public void addLoginListener(AuthenticationListener loginListener) {        
            loginListeners.add(loginListener);
    }

    private void notifyLoginListeners() {
        for (AuthenticationListener loginListener : loginListeners) {
            loginListener.completeLogin();
        }
    }
    
    public void notifyLogoutListeners() {
        for (AuthenticationListener loginListener : loginListeners) {
            loginListener.completeLogout();
        }
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Boolean getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(Boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Get user as currently stored in the database
     *
     * @param em
     * @return
     */
    public JobManagerUser getUser(EntityManager em) {
        if (user == null) {
            return new JobManagerUser();
        } else {
            try {
                if (user.getId() != null) {
                    JobManagerUser foundUser = em.find(JobManagerUser.class, user.getId());
                    if (foundUser != null) {
                        em.refresh(foundUser);
                        user = foundUser;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                return new JobManagerUser();
            }
        }

        return user;
    }

    public JobManagerUser getUser() {
        if (user == null) {
            return new JobManagerUser();
        }
        return user;
    }

    public void setUser(JobManagerUser user) {
        this.user = user;
    }

    public static Boolean checkForLDAPUser(EntityManager em, String username, javax.naming.ldap.LdapContext ctx) {

        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"displayName"};

            constraints.setReturningAttributes(attrIDs);

            String name = (String) SystemOption.getOptionValueObject(em, "ldapContextName");
            NamingEnumeration answer = ctx.search(name, "SAMAccountName=" + username, constraints);

            if (!answer.hasMore()) { // Assuming only one match
                // LDAP user not found!
                return Boolean.FALSE;
            }
        } catch (NamingException ex) {
            System.out.println(ex);
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Boolean validateUser(EntityManager em, String username, String password) {
        Boolean userValidated = false;
        InitialLdapContext ctx;

        try {
            List<jm.com.dpbennett.business.entity.LdapContext> ctxs = jm.com.dpbennett.business.entity.LdapContext.findAllActiveLdapContexts(em);

            for (jm.com.dpbennett.business.entity.LdapContext ldapContext : ctxs) {
                ctx = ldapContext.getInitialLDAPContext(username, password);

                if (checkForLDAPUser(em, username, ctx)) {
                    // user exists in LDAP                    
                    userValidated = true;
                    break;
                }
            }

            // get the user if one exists
            if (userValidated) {
                System.out.println("User validated.");

                return true;

            } else {
                System.out.println("User NOT validated!");

                return false;
            }

        } catch (Exception e) {
            System.err.println("Problem connecting to directory: " + e);
        }

        return false;
    }

    public void checkLoginAttemps() {

        ++loginAttempts;
        if (loginAttempts == 2) {
            //PrimeFaces.current().executeScript("PF('loginAttemptsDialog').show();");
            try {
                // Send email to system administrator alert if activated
                if ((Boolean) SystemOption.getOptionValueObject(getEntityManager(),
                        "developerEmailAlertActivated")) {
                    Utils.postMail(null, null, null,
                            "Failed user login",
                            "Username: " + username + "\nDate/Time: " + new Date(),
                            "text/plain",
                            getEntityManager());
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (loginAttempts > 2) {// tk # attempts to be made option
            //PrimeFaces.current().executeScript("PF('loginAttemptsDialog').show();");
        }

        username = "";
        password = "";
    }

    public void login() {
        login(getEntityManager());
    }

    public void login(EntityManager em) {

        setUserLoggedIn(false);

        try {
            // Find user and determine if authentication is required for this user
            user = JobManagerUser.findActiveJobManagerUserByUsername(em, username);

            if (user != null) {
                em.refresh(user);
                if (!user.getAuthenticate()) {
                    System.out.println("User will NOT be authenticated.");
                    logonMessage = "Please provide your login details below:";
                    username = "";
                    password = "";
                    setUserLoggedIn(true);

                    notifyLoginListeners();

                    //PrimeFaces.current().executeScript("PF('loginDialog').hide();");
                } else if (validateUser(em, username, password)) {
                    logonMessage = "Please provide your login details below:";
                    username = "";
                    password = "";
                    setUserLoggedIn(true);

                    notifyLoginListeners();

                    //PrimeFaces.current().executeScript("PF('loginDialog').hide();");
                } else {
                    checkLoginAttemps();
                    logonMessage = "Please enter a valid username and password.";
                }
            } else {
                logonMessage = "Please enter a registered username.";
                username = "";
                password = "";
            }

        } catch (Exception e) {
            System.out.println(e);
            logonMessage = "Login error occurred! Please try again or contact the System Administrator";
        }

    }

    public String getLogonMessage() {
        return logonMessage;
    }

    public void setLogonMessage(String logonMessage) {
        this.logonMessage = logonMessage;
    }

    public interface AuthenticationListener {

        public void completeLogin();
        
        public void completeLogout();
    }
}
