package com.udaybuilders.enquiry;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="enquiries")
public class Enquiry {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String customerName;
    @Column(nullable=false) private String mobileNumber;
    private String email; private Long projectId; private Long propertyId;
    @Column(nullable=false, length=4000) private String message;
    @Column(nullable=false, length=30) private String status = "NEW";
    private String adminRemarks; private Instant enquiryDate = Instant.now();
    protected Enquiry(){}
    public Enquiry(EnquiryRequest r){customerName=r.customerName(); mobileNumber=r.mobileNumber(); email=r.email(); projectId=r.projectId(); propertyId=r.propertyId(); message=r.message();}
    public Long getId(){return id;} public String getCustomerName(){return customerName;} public String getMobileNumber(){return mobileNumber;}
    public String getEmail(){return email;} public Long getProjectId(){return projectId;} public Long getPropertyId(){return propertyId;}
    public String getMessage(){return message;} public String getStatus(){return status;} public String getAdminRemarks(){return adminRemarks;}
    public Instant getEnquiryDate(){return enquiryDate;} public void update(String status,String remarks){this.status=status;this.adminRemarks=remarks;}
}
