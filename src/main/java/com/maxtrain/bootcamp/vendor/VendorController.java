package com.maxtrain.bootcamp.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin  
@RestController //this signifies that your controller will be sending and receiving info through JSON data
@RequestMapping("/api/vendor")

public class VendorController {
	
	@Autowired
	private VendorRepository vendorRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors(){
		var vendors = vendorRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);		
	}	
	
	@GetMapping("{id}")  //this method will return one item by id 
	public ResponseEntity<Vendor> getVendor(@PathVariable int id) {
		var vendor = vendorRepo.findById(id);
		if(vendor.isEmpty()) { //if the id you type in is not found, you will see the not found message
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}                          //otherwise it will return the customer by the id you specify in postman
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor){
		if(vendor == null || vendor.getId() !=0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);				
		}
		var vend = vendorRepo.save(vendor);		
		return new ResponseEntity<Vendor>(vend,HttpStatus.CREATED);	
	}
	
	@SuppressWarnings("rawtypes") //this means we can use an instance of a class that is generic but we will not put a generic type in there
	@PutMapping("{id}") 
	public ResponseEntity putVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		if(vendor == null || vendor.getId() == 0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		var vend = vendorRepo.findById(vendor.getId());
		if(vend.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendorRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteVendor(@PathVariable int id) {
		var vendor = vendorRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendorRepo.delete(vendor.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	

}

