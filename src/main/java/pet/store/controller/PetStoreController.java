package pet.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController // tell spring its a REST contr, expects + returns JSON in req body
@RequestMapping("/pet_store") // tell spring that URI for every req is mapped to a method in controller class
								// that must start with /pet_store
@Slf4j // allows logs
public class PetStoreController {

	@Autowired // injects :
	private PetStoreService petStoreService;

//CREATE / POST req to /pet_store
	@PostMapping // post request to pet_park/contributor
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) { // tell spring that JSON is in the body
		log.info("Creating Pet Store {}", petStoreData); // logs req

		return petStoreService.savePetStore(petStoreData); // returns PetStoreData obj, calls a method to service class
															// (savePetStore) that inserts/modifies data
	}

	// UPDATE / PUT req to /pet_store
	@PutMapping("/{petStoreId}") // post request to pet_park/contributor
	public PetStoreData updatePetStore(
			@PathVariable Long petStoreId,
			@RequestBody PetStoreData petStoreData) {
		
		petStoreData.setPetStoreId(petStoreId); // sets the id
		
		log.info("Updating Pet Store {}", petStoreData); // logs req
		
		return petStoreService.savePetStore(petStoreData); // returns PetStoreData obj, calls a method to service class (savePetStore) that inserts/modifies data
	}

	// Wk 15
	// 1. Create method that will add employee to employee table
	// CREATE
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED) // stat 201
	public PetStoreEmployee addEmployee(
			@PathVariable Long petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee
			) {
		log.info("Added an employee with name {}", petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
		
	}
	
	// CREATE customer
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addCustomer(
			@PathVariable Long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer
			) {
		log.info("Added customer with name {}", petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	// List all pet Stores
	// GET/READ  all pet stores
	@GetMapping
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieve all pet stores...");
		List<PetStoreData> petStores = petStoreService.retrieveAllPetStores();
		return petStores;
	}
		
	} // ends file
