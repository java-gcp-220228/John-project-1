package com.revature.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.revature.dao.TicketDao;
import com.revature.dto.EmployeeAddTicketDTO;
import com.revature.dto.ResolveTicketDTO;
import com.revature.dto.UserDTO;
import com.revature.model.Ticket;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.UploadedFile;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    public List<Ticket> getAllTickets() throws SQLException {
        return this.ticketDao.getAllTickets();
    }
    public List<Ticket> getEmployeeTickets(int id) throws SQLException {
        return this.ticketDao.getTicketFromEmployeeId(id);
    }

    public EmployeeAddTicketDTO addEmployeeTicket(EmployeeAddTicketDTO newTicket, UploadedFile image) throws SQLException, IOException {
        if (newTicket.getAmount() <= 0) throw new BadRequestResponse("Invalid reimbursement amount");
        if (!(newTicket.getType().equalsIgnoreCase("LODGING") || newTicket.getType().equalsIgnoreCase("TRAVEL")
            || newTicket.getType().equalsIgnoreCase("FOOD") || newTicket.getType().equalsIgnoreCase("OTHER")))
                throw new BadRequestResponse("Invalid reimbursement type");
        if (image != null) {
            Tika tika = new Tika();
            InputStream img = image.getContent();
            String mimeType = tika.detect(img);
            if (!(mimeType.equals("image/jpeg") || mimeType.equals("image/png") || mimeType.equals("image/gif"))){
                throw new BadRequestResponse("Image must be JPEG, PNG or GIF");
            }
            String fileName = image.getFilename();
            newTicket.setReceiptLink(uploadToCloud(fileName, img));
        }
        return this.ticketDao.createTicketByEmployeeId(newTicket);
    }

    private String uploadToCloud(String fileName, InputStream img) throws IOException {
        String projectId = "hopeful-altar-343717";
        String bucketName = "revature-project-1";
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.createFrom(blobInfo, img);
        return "http://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

    public ResolveTicketDTO resolveTicket(String ticketId, String status, UserDTO resolver) throws SQLException {
        if (!(status.equalsIgnoreCase("APPROVED") ||
                status.equalsIgnoreCase("DENIED"))) throw new BadRequestResponse("Ticket must be APPROVED or DENIED");
        try {
            int id = Integer.parseInt(ticketId);
            ResolveTicketDTO dto = new ResolveTicketDTO();
            dto.setId(id);
            dto.setStatus(status);
            dto.setResolver(resolver);
            return ticketDao.patchTicket(dto);
        } catch (IllegalArgumentException e) {
            throw new BadRequestResponse("Id must be valid integer");
        }
    }
}
