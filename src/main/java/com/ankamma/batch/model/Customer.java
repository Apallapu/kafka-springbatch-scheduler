package com.ankamma.batch.model;

import java.io.Serializable;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@NamedQueries({ @NamedQuery(name = "Customer.findAll", query = "select a from Customer a"),
	@NamedQuery(name = "Customer.findById", query = "select v from Customer v where v.id=:id") })
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	private static final long serialVersionUID = -2343243243242432341L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	@Column(name = "firstname")
	private String firstName;
 
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "status")
	private String status;
	
	@Temporal(TemporalType.DATE)
    private Date effectiveDate;
 
 
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected Customer() {
	}
 
	public Customer(String firstName, String lastName,String status,Date effectiveDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status=status;
		this.effectiveDate=effectiveDate;
	}
 
	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s,status='%s'']", id, firstName, lastName);
	}
	public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}