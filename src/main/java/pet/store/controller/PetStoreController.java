package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController // tell spring its a REST contr, expects + returns JSON in req body
@RequestMapping // tell spring that URI for every req is mapped to a method in controller class that must start with /pet_store
@Slf4j
public class PetStoreController {
	
@Autowired // injects :
private PetStoreService petStoreService;

//CREATE / POST req to /pet_store
	@PostMapping("/pet_store") // post request to pet_park/contributor
	public PetStoreData insertPetStore(
			@RequestBody PetStoreData petStoreData) {
		log.info("Creating Pet Store {}", petStoreData); // logs req
		
		return petStoreService.savePetStore(petStoreData); // returns PetStoreData obj, calls a method to service class (savePetStore) that inserts/modifies data
	}
	
}
