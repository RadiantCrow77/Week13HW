package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.EmployeeDao;
import pet.store.dao.CustomerDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired // spring injects
	private PetStoreDao petStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;

	// creates a pet store
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		// copyPetStoreFields takes a PetStore obj and PetStoreData obj as params
		copyPetStoreFields(petStore, petStoreData);

		// call the PetStoreDao method save(petStore) ...
		return new PetStoreData(petStoreDao.save(petStore));

		// return a new PetStoreData obj created from return val of save() method
//		return new PetStoreData (savedPetStore);
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		// instantiate variables
//		PetStore petStore;

		// method returns new PetStore obj if ID is null
		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return findPetStoreById(petStoreId); // if not null, call findPetStoreById, ...
		}
//		return petStore; // ... which returns a PetStore obj w/ matching ID, if no match is found, throw exception in findPetStoreById

	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet Store With ID: " + petStoreId + " was not found."));
	}

	// HW 15 -- essentially same as savePetStore method
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		// fields
		PetStore petStore = findPetStoreById(petStoreId);

		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);

		copyEmployeeFields(employee, petStoreEmployee);

		// set pet store in employee
		employee.setPetStore(petStore);

		// add employee to pet store list of employees
		petStore.getEmployees().add(employee);

		// save employee by calling save method in employee DAO
		Employee dbEmployee = employeeDao.save(employee);

		// convert employee obj returned by save method to PetStoreEmployee obj and
		// return
		return new PetStoreEmployee(dbEmployee);
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) { // if employeeID = null
			return new Employee(); // return new employee
		} else {
			return findEmployeeById(petStoreId, employeeId); // else return the employee and the pet store by IDs
		}
	}

	// Add Employee
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException("The Employee with Id = " + employeeId + " was not found. "));
		// Note: findById returns an Opt
		// if Opt = empty, throw a NoSuchElExc
		// if Opt != empty, an employee is returned
		// How this works: ^^^
		// if employee pet store ID = petSToreId,
		// return employee
		// else throw exception with msg

		// if pet store ID in employ obj's PetSTore var != ID, throw exc
		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalStateException(
					"Employee with ID = " + employeeId + " does not work at pet store containing ID = " + petStoreId);
		}
		return employee;

	}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		// copy first nm, Id, job title, last nm, phone from petStoreEmployee as
		// setters/getters:
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		;
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		;

	}

	// findOrCreateCustomer, same as findOrCreateEmployee
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		} else {
			return findCustomerById(petStoreId, customerId);
		}
	}

	// Add Customer, almost carbon copy of Add Employee section except ..
	private Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException("The Customer with Id = " + customerId + " was not found. "));

		boolean storeFound = false;

		// ... have to loop through petstore because it's a set
		for (PetStore petStore : customer.getPetStores()) {
			if (petStore.getPetStoreId() == petStoreId) {
				storeFound = true;
				break;
			} else {
				if (!storeFound) {
					throw new IllegalStateException("Employee with ID = " + customerId
							+ " does not work at pet store containing ID = " + petStoreId);
				}
			}

		}
		return customer;
	}

	// Save Customer, same as Employee
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);

		copyCustomerFields(customer, petStoreCustomer);
		customer.getPetStores().add(petStore); 
		petStore.getCustomers().add(customer);

		Customer dbCustomer = customerDao.save(customer); // saving a customer
		
		// return
		return new PetStoreCustomer(dbCustomer);
		
	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
	// fields are from  customer entity:
//		private Long customerId;
//		
//		private String customerFirstName;
//		private String customerLastName;
//		private String customerEmail;
		
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	@Transactional(readOnly = false)
	public List<PetStoreData> retrieveAllPetStores() {
	List<PetStore> petStores = petStoreDao.findAll(); // call the findAll method in DAO
	List<PetStoreData> result = new LinkedList<>(); // convert List of PetStore objects to a list of PetStoreData objects
	
	for(PetStore petStore : petStores) {
		PetStoreData psd = new PetStoreData(petStore);
		
		psd.getCustomers().clear(); // remove all customer objects
		psd.getEmployees().clear(); // and employee objects, in ea PetSToreData obj
		
		result.add(psd);
	}
	return result; // return summary list
	}

	//
} // end service class
