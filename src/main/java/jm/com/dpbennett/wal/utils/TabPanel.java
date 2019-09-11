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

public class TabPanel implements Serializable {

    private String id;
    private String name;
    private String src;
   
    public TabPanel(
            String id,
            String name) {

        this.id = id;
        this.name = name;
        this.src = "";
    }
    
    public TabPanel(
            String id,
            String name,
            String src) {

        this.id = id;
        this.name = name;
        this.src = src;
    }

    public String getId() {
        if (id == null) {
            id = "";
        }

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
  
}
