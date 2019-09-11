/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.wal.utils;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Desmond Bennett <info@dpbennett.com.jm at http//dpbennett.com.jm>
 */
public class FinancialUtils {

    /**
     * NB: Payment types to be put in database...
     *
     * @return
     */
    public static List getPaymentTypes() {
        ArrayList paymentTypes = new ArrayList();

        paymentTypes.add(new SelectItem("Cash", "Cash"));
        paymentTypes.add(new SelectItem("Cheque", "Cheque"));
        paymentTypes.add(new SelectItem("Credit Card", "Credit Card"));
        paymentTypes.add(new SelectItem("Debit Card", "Debit Card"));
        paymentTypes.add(new SelectItem("Other", "Other"));

        return paymentTypes;
    }

    /**
     * NB: Payment purposes to be put in database...
     *
     * @return
     */
    public static List getPaymentPurposes() {
        ArrayList paymentPurposes = new ArrayList();

        paymentPurposes.add(new SelectItem("Deposit", "Deposit"));
        paymentPurposes.add(new SelectItem("Intermediate", "Intermediate payment"));
        paymentPurposes.add(new SelectItem("Final", "Final payment"));
        paymentPurposes.add(new SelectItem("Other", "Other"));

        return paymentPurposes;
    }

    public static List getCostTypeList() {
        ArrayList costTypes = new ArrayList();

        costTypes.add(new SelectItem("--", "--"));
        costTypes.add(new SelectItem("FIXED", "Fixed"));
        costTypes.add(new SelectItem("HEADING", "Heading"));
        costTypes.add(new SelectItem("VARIABLE", "Variable"));
        costTypes.add(new SelectItem("SUBCONTRACT", "Subcontract"));

        return costTypes;
    }

    /**
     * Returns the discount type that can be applied to a payment/amount NB: To
     * be deprecated
     *
     * @return
     */
    public static List getDiscountTypes() {
        ArrayList discountTypes = new ArrayList();

        discountTypes.add(new SelectItem("Currency", "Currency: "));
        discountTypes.add(new SelectItem("Percentage", "Percentage: "));

        return discountTypes;
    }

}
