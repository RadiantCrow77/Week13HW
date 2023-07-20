package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

// Data copied from pet.store.entity > PetStore.Java
// DO NOT include annotations, have data and noargs as class lv
@Data
@NoArgsConstructor
public class PetStoreData {

	private Long petStoreId;
	
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private Long petStoreZip;
	private String petStorePhone;
	
	// constructor that takes a PetStore as a parameter. Set all matching fields in the PetStoreData class to the data in the PetStore class
	public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
	}
	
	// DTO inner class, must be public and static
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer {
		// copy/paste fields from Customer entity here:
		private Long customerId;
		
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		// this field is not needed, it removes recursion required from JPA:
//		@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
//		private Set<PetStore> petStores = new HashSet<>();
		
		// Customer Constructor and Getters
		public PetStoreCustomer(Customer customer){
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
			}
	}
	
	// PetStoreEmployee class is basically the same as customer :)
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee{
		// copied from entity:
		private Long EmployeeId;
		
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		
		// employee constructor
		public PetStoreEmployee(Employee employee) { 
			//getters:
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}
	
} // end main class
