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
package jm.com.dpbennett.wal.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import jm.com.dpbennett.business.entity.PurchaseRequisition;
import jm.com.dpbennett.business.entity.utils.BusinessEntityUtils;

/**
 *
 * @author Desmond Bennett
 */
@FacesValidator("purchaseReqNumberValidator")
public class PurchaseReqNumberValidator extends ValidatorAdapter {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String prNumber = (String) value;
        Long selectedPurchaseReqId = (Long) component.getAttributes().get("selectedPurchaseReqId");
        Boolean autoGeneratePRNumber = (Boolean) component.getAttributes().get("autoGeneratePRNumber");

        // Check for valid PR number
        if (!BusinessEntityUtils.validateText(prNumber.trim())) {
            throw new ValidatorException(getMessage(component.getId()));
        }

        // Check if PR number is unique
        PurchaseRequisition existingPR = PurchaseRequisition.findByPRNumber(getEntityManager(), prNumber);
        if (existingPR != null) {
            long current_prId = selectedPurchaseReqId != null ? selectedPurchaseReqId : -1L;
            if (existingPR.getId() != current_prId) {
                throw new ValidatorException(getMessage("exist"));
            }
        }

        // Validate PR number text 
        if (autoGeneratePRNumber) {
            if (!validatePRNumber(prNumber, autoGeneratePRNumber)) {
                throw new ValidatorException(getMessage("invalid"));
            }
        }

    }

    private FacesMessage getMessage(String componentId) {
        switch (componentId) {
            case "prNumberEdit":
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Purhcase Requisition Number", "Please enter a valid purchase requisition number.");            
            case "invalid":
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Purhcase Requisition Number", "This purhcase requisition cannot be saved because a valid purhcase requisition number was not entered.");
            case "exist":
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Purhcase Requisition Not Unique", "This purhcase requisition cannot be saved because the purhcase requisition number is not unique.");
            default:
                return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Field Value Required", "Please enter all required fields.");
        }
    }

    /**
     * Validates a purchase requisition number. Expected form is PR/year/####.
     * 
     * @param prNumber
     * @param auto
     * @return 
     */
    public Boolean validatePRNumber(String prNumber, Boolean auto) {       
        Integer year = 0;
        Long sequenceNumber = 0L;

        String parts[] = prNumber.split("/");
        if (parts != null) {
            // Check for correct number of parts
            if (parts.length == 3) {
                // Year and sequence number valid integers/long?
                try {
                   
                    year = Integer.parseInt(parts[1]);
                    if (auto && parts[2].equals("?")) {
                        // This means the complete PR number has not yet
                        // been generate. Ignore for now.
                    } else {
                        sequenceNumber = Long.parseLong(parts[2]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e);
                    return false;
                }
                
                // Year has valid ranges?               
                if (year < 1970) {
                    return false;
                }
                if (auto && parts[2].equals("?")) {
                    // This means the complete PR number has not yet
                    // been generate. Ignore for now.
                } else if (sequenceNumber < 1L) {
                    return false;
                }
                               
                // All is well here
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
