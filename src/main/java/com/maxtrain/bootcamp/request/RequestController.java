package com.maxtrain.bootcamp.request;

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

public class RequestController {
	
	@Autowired
	private RequestRepository reqRepo;

	@GetMapping
	public ResponseEntity <Iterable<Request>> getRequests(){
		var requests = reqRepo.findAll();
		return new ResponseEntity <Iterable<Request>>(requests, HttpStatus.OK); 		
	}
	
	@GetMapping ("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) { //if the id you type in is not found, you will see the not found message
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}                          //otherwise it will return the customer by the id you specify in postman
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	//adding this method to get all requests in REVIRW status
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Request>> getRequetsInReview(){
		var requests = reqRepo.findByStatus("REVIEW");
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	//adding this method to set status to NEW
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request){
		if(request == null || request.getId() !=0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);				
		}
		request.setStatus("NEW"); //this was added today 3/10/22
		var req = reqRepo.save(request);		
		return new ResponseEntity<Request>(req,HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewRequest(@PathVariable int id, @RequestBody Request request) {
		var statusValue = (request.getTotal() <= 50) ? "APPROVED" : "REVIEW";
		request.setStatus(statusValue);
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("REJECTED");
		return putRequest(id, request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping ("approve/{id}")
	public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request) {
		request.setStatus("APPROVED");
		return putRequest(id, request);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@PutMapping ("{id}") 
	public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
		if(request == null || request.getId() == 0) {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		var req = reqRepo.findById(request.getId());
		if(req.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.delete(request.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
