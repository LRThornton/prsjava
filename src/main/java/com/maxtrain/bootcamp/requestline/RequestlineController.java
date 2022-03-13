package com.maxtrain.bootcamp.requestline;

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
@RestController
@RequestMapping("/api/request")

public class RequestlineController {
	
	
	@Autowired
	private RequestlineRepository RLRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Requestline>> getRequestline(){
		var requestlines = RLRepo.findAll();
		return new ResponseEntity<Iterable<Requestline>>(requestlines, HttpStatus.OK);		
	}	
	
	@GetMapping("{id}")  //this method will return one item by id 
	public ResponseEntity<Requestline> getRequestline(@PathVariable int id) {
		var requestline = RLRepo.findById(id);
		if(requestline.isEmpty()) { //if the id you type in is not found, you will see the not found message
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}                          //otherwise it will return the customer by the id you specify in postman
		return new ResponseEntity<Requestline>(requestline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Requestline> postRequestline(@RequestBody Requestline requestline){
		if(requestline == null || requestline.getId() !=0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);				
		}
		var reql = RLRepo.save(requestline);		
		return new ResponseEntity<Requestline>(reql,HttpStatus.CREATED);	
	}
	
	@SuppressWarnings("rawtypes") //this means we can use an instance of a class that is generic but we will not put a generic type in there
	@PutMapping("{id}") 
	public ResponseEntity putRequestline(@PathVariable int id, @RequestBody Requestline requestline) {
		if(requestline == null || requestline.getId() == 0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		var reql = RLRepo.findById(requestline.getId());
		if(reql.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		RLRepo.save(requestline);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleterequestline(@PathVariable int id) {
		var requestline = RLRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	RLRepo.delete(requestline.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
