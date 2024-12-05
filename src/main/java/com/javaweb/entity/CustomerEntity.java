package com.javaweb.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class CustomerEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "companyname")
    private String companyName;

    @Column(name = "demand")
    private String demand;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active")
    private String isActive;

    @OneToMany(mappedBy = "customers",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AssignmentCustomerEntity> assignmentCustomerEntity = new ArrayList<>();

    @OneToMany(mappedBy = "customers",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionEntity = new ArrayList<>();

    public List<AssignmentCustomerEntity> getAssignmentCustomerEntity() {
        return assignmentCustomerEntity;
    }

    public void setAssignmentCustomerEntity(List<AssignmentCustomerEntity> assignmentCustomerEntity) {
        this.assignmentCustomerEntity = assignmentCustomerEntity;
    }

    public List<TransactionEntity> getTransactionEntity() {
        return transactionEntity;
    }

    public void setTransactionEntity(List<TransactionEntity> transactionEntity) {
        this.transactionEntity = transactionEntity;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
