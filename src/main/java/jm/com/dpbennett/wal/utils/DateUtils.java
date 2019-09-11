/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.wal.utils;

import java.util.ArrayList;
import javax.faces.model.SelectItem;

/**
 *
 * @author Desmond Bennett <info@dpbennett.com.jm at http//dpbennett.com.jm>
 */
public class DateUtils {

    public static ArrayList getDateSearchFields() {
        ArrayList dateSearchFields = new ArrayList();

        dateSearchFields.add(new SelectItem("dateAndTimeEntered", "Date entered"));
        dateSearchFields.add(new SelectItem("dateReceived", "Date received"));
        dateSearchFields.add(new SelectItem("dateSubmitted", "Date submitted"));
        dateSearchFields.add(new SelectItem("dateCostingApproved", "Date costing approved"));
        dateSearchFields.add(new SelectItem("dateOfCompletion", "Date completed"));
        dateSearchFields.add(new SelectItem("expectedDateOfCompletion", "Exp'ted date of completion"));
        dateSearchFields.add(new SelectItem("dateSamplesCollected", "Date sample(s) collected"));
        dateSearchFields.add(new SelectItem("dateDocumentCollected", "Date document(s) collected"));

        return dateSearchFields;
    }

    public static ArrayList getDateSearchFields(String category) {
        ArrayList dateSearchFields = new ArrayList();

        switch (category) {
            case "Job":
                dateSearchFields.add(new SelectItem("dateAndTimeEntered", "Date entered"));
                dateSearchFields.add(new SelectItem("dateSubmitted", "Date submitted"));
                dateSearchFields.add(new SelectItem("dateCostingApproved", "Date costing approved"));
                dateSearchFields.add(new SelectItem("dateOfCompletion", "Date completed"));
                dateSearchFields.add(new SelectItem("expectedDateOfCompletion", "Exp'ted date of completion"));
                dateSearchFields.add(new SelectItem("dateSamplesCollected", "Date sample(s) collected"));
                dateSearchFields.add(new SelectItem("dateDocumentCollected", "Date document(s) collected"));
            case "Legal":
                dateSearchFields.add(new SelectItem("dateReceived", "Date received"));
                dateSearchFields.add(new SelectItem("dateOfCompletion", "Date delivered"));
                dateSearchFields.add(new SelectItem("expectedDateOfCompletion", "Agreed delivery date"));
                break;
            case "All":
                return getDateSearchFields();
            default:
                return getDateSearchFields();
        }

        return dateSearchFields;
    }
}
