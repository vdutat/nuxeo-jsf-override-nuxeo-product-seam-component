package org.nuxeo.ecm.webapp.helpers;

import static org.jboss.seam.annotations.Install.DEPLOYMENT;

import java.security.Principal;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.common.Environment;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

@Name("appNameFactory")
@Scope(ScopeType.STATELESS)
@Install(precedence = DEPLOYMENT)
public class CustomNuxeoProductNameFactory extends NuxeoProductNameFactory {

    private static final long serialVersionUID = 1L;

    @In(create = true, required = false)
    protected transient CoreSession documentManager;

    @In(create = true)
    protected transient UserManager userManager;

    @In(create = true)
    private transient Principal currentUser;

    @Factory(value = "nuxeoApplicationName", scope = ScopeType.APPLICATION)
    @Override public String getNuxeoProductName() {
        if (userManager.getPrincipal(currentUser.getName()).isAdministrator()) {
            return currentUser.getName() + "-" + Framework.getProperty(Environment.PRODUCT_NAME);
        } else {
            return super.getNuxeoProductName();
        }
    }

}
