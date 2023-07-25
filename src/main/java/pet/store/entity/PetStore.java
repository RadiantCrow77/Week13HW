package pet.store.entity;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;



@Entity
@Data
public class PetStore {
	// instance vars in snake case
		// Id annotation before Ids
		@Id
		 @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long petStoreId;
		
		private String petStoreName;
		private String petStoreAddress;
		private String petStoreCity;
		private String petStoreState;
		private Long petStoreZip;
		private String petStorePhone;
		
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		
		// set customer annotations and relationship
		@ManyToMany(cascade = CascadeType.PERSIST)
		@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
		private Set<Customer> customers = new HashSet<>();
		
		//  set employee annotations and relationship
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
		private Set<Employee> employees = new HashSet<>();
	}