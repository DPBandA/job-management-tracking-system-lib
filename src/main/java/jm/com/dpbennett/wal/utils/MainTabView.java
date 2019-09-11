/*
Job Management & Tracking System (JMTS) 
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
package jm.com.dpbennett.wal.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jm.com.dpbennett.business.entity.JobManagerUser;
import org.primefaces.PrimeFaces;

/**
 *
 * @author desbenn
 */
public class MainTabView implements Serializable {

    private Boolean render;
    private Integer tabIndex;
    private List<TabPanel> tabs;
    private JobManagerUser user;

    public MainTabView(JobManagerUser user) {
        this.user = user;
        tabs = new ArrayList<>();
        tabIndex = 0;
        render = false;
    }

    public void setTabName(String tabId, String name) {
        TabPanel tab = findTab(tabId);
        if (tab != null) {
            tab.setName(name);
        }
    }

    public Boolean getRender() {
        return render;
    }

    public void setRender(Boolean render) {
        this.render = render;
    }

    public void update(String componentId) {

        PrimeFaces.current().ajax().update(componentId);
    }

    public void update(String tabId, String componentId, String componentVar) {
        TabPanel tab = findTab(tabId);

        if (tab != null) {
            PrimeFaces.current().ajax().update(componentId);
            select(componentVar, true);
        }
    }

    public void select(int tabIndex) {

        this.tabIndex = tabIndex < 0 ? 0 : tabIndex;

        PrimeFaces.current().executeScript("PF('mainTabViewVar').select(" + this.tabIndex + ");");

    }

    public void select(String tabId) {
        select(getTabIndex(tabId));
    }

    public void select(String componentVar, int tabIndex) {

        this.tabIndex = tabIndex < 0 ? 0 : tabIndex;

        PrimeFaces.current().executeScript("PF('" + componentVar + "')" + ".select(" + this.tabIndex + ");");

    }

    public void select(Boolean wasTabAdded) {

        if (wasTabAdded) {
            PrimeFaces.current().executeScript("PF('mainTabViewVar').select(" + tabIndex + ");");
        } else {
            PrimeFaces.current().executeScript("PF('mainTabViewVar').select(" + ((tabIndex - 1) < 0 ? 0 : (tabIndex - 1)) + ");");
        }
    }

    public void select(String componentVar, Boolean wasTabAdded) {

        if (wasTabAdded) {
            PrimeFaces.current().executeScript("PF('" + componentVar + "')" + ".select(" + tabIndex + ");");
        } else {
            PrimeFaces.current().executeScript("PF('" + componentVar + "')" + ".select(" + ((tabIndex - 1) < 0 ? 0 : (tabIndex - 1)) + ");");
        }
    }

    public TabPanel findTab(String tabId) {
        tabIndex = 0;

        for (TabPanel tab : tabs) {
            if (tab.getId().equals(tabId)) {
                return tab;
            }
            ++tabIndex;
        }

        return null;
    }

    public Boolean isTabRendered(String tabId) {
        return !(findTab(tabId) == null);
    }

    public int getTabIndex(String tabId) {
        TabPanel tab = findTab(tabId);
        if (tab != null) {
            return tabIndex;
        }

        return -1;
    }

    public void openTab(String tabId) {
        addTab(tabId, true);
        select(tabId);
    }

    public void closeTab(String tabId) {
        addTab(tabId, false);
    }

    private void addTab(
            String tabId,
            Boolean render) {

        TabPanel tab = findTab(tabId);

        if (tab != null && !render) {
            // TabPanel is being removed so remove and update the tab view          
            tabs.remove(tab);
            update("mainTabViewForm:mainTabView");           
                select(render);            
        } else if (tab != null && render) {
            // TabPanel already added so just update and select
            update("mainTabViewForm:mainTabView");            
            select(render);
        } else if (tab == null && !render) {
            // TabPanel is not be added            
        } else if (tab == null && render) {
            // TabPanel is to be added so add and render
            tabs.add(new TabPanel(tabId, tabId));
            update("mainTabViewForm:mainTabView");
            select(render);
        }

    }

    public void removeAllTabs() {
        tabs.clear();
    }

    public void reset(JobManagerUser user) {
        this.user = user;

        removeAllTabs();

        setRender(true);
    }

    public List<TabPanel> getTabs() {

        return tabs;
    }

    public void setTabs(ArrayList<TabPanel> tabs) {
        this.tabs = tabs;
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

    public Integer getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(Integer tabIndex) {
        this.tabIndex = tabIndex;
    }
}
