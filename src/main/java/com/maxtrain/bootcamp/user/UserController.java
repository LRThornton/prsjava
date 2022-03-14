package com.maxtrain.bootcamp.user;

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
@RequestMapping("/api/users")

public class UserController {
		
	@Autowired
	private UserRepository userRepo;
	

	@GetMapping("username/{password}")
	public ResponseEntity<User> findByUsernameAndPassword(@PathVariable String username, @PathVariable String password) {
		var user = userRepo.findByUsernameAndPassword(username, password);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}

	
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers(){
		var users = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);		
		
	}
	
	@GetMapping("{id}")  //this method will return one item by id 
	public ResponseEntity<User> getUser(@PathVariable int id) {
		var user = userRepo.findById(id);
		if(user.isEmpty()) { //if the id you type in is not found, you will see the not found message
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}                          //otherwise it will return the customer by the id you specified in postman
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user){
		if(user == null || user.getId() !=0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);				
		}
		var use = userRepo.save(user);		
		return new ResponseEntity<User>(use,HttpStatus.CREATED);	
	}
	

	@SuppressWarnings("rawtypes") //this means we can use an instance of a class that is generic but we will not put a generic type in there
	@PutMapping("{id}") 
	public ResponseEntity putUser(@PathVariable int id, @RequestBody User user) {
		if(user == null || user.getId() == 0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		var use =userRepo.findById(user.getId());
		if(use.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		var user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.delete(user.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
		
	
}
