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
package jm.com.dpbennett.jmts;

import java.util.List;
import javax.faces.model.ListDataModel;
import jm.com.dpbennett.business.entity.jmts.JobSample;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author dbennett
 */
public class JobSampleDataModel extends ListDataModel<JobSample> implements SelectableDataModel<JobSample> {

    private List<JobSample> list;

    public JobSampleDataModel() {
    }

    public JobSampleDataModel(List<JobSample> list) {
        super(list);
        this.list = list;
    }

    @Override
    public Object getRowKey(JobSample jobSample) {
        return jobSample.getId();
    }

    @Override
    public JobSample getRowData(String rowKey) {
        for (JobSample jobSample : list) {
            if (jobSample.getId().toString().equals(rowKey)) {
                return jobSample;
            }
        }

        return null;
    }

}
