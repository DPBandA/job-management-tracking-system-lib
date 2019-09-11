/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template uploadedFile, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.wal.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import jm.com.dpbennett.business.entity.Attachment;
import jm.com.dpbennett.business.entity.SystemOption;
import jm.com.dpbennett.wal.utils.PrimeFacesUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desmond Bennett <info@dpbennett.com.jm at http//dpbennett.com.jm>
 */
public class FileUploadManager {

    @PersistenceUnit(unitName = "JMTSPU")
    private EntityManagerFactory EMF;
    private UploadedFile uploadedFile;
    private List<Attachment> attachments;
    
    public EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    
    public void upload() {
        if (uploadedFile != null) {
            FacesMessage message = new FacesMessage("Succesful", uploadedFile.getFileName() + " was uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            OutputStream outputStream;
            
            // Save file
            File fileToSave = 
                    new File(SystemOption.getOptionValueObject(getEntityManager(), 
                            "purchReqUploadFolder") + 
                            event.getFile().getFileName());
            outputStream = new FileOutputStream(fileToSave);
            outputStream.write(event.getFile().getContents());
            outputStream.close();
            
            // Create attachment
            
            PrimeFacesUtils.addMessage("Succesful!", event.getFile().getFileName() + " was uploaded.", FacesMessage.SEVERITY_INFO);

        } catch (IOException ex) {
            Logger.getLogger(FileUploadManager.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void okAttachment() {
        //tk
        // The return of this dialog should be handled by "dialogReturn" event?
        // for now just close the dialog.
        System.out.println("ok attachment...");
        PrimeFacesUtils.closeDialog(null);

    }

    public void closeDialog() {
        PrimeFacesUtils.closeDialog(null);
    }
}
