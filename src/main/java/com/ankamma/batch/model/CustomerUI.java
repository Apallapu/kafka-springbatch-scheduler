package com.ankamma.batch.model;

public class CustomerUI  {
 
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String firstName;
     private String lastName;
     
     private String status;
     public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected CustomerUI() {
	}
 
	public CustomerUI(Long id,String firstName, String lastName,String status) {
		this.id=id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status=status;
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
	
	public String toString() {
		return String.format("Customer[firstName='%s', lastName='%s']", firstName, lastName);
	}
}