package com.maxtrain.bootcamp.user;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Integer> {
	
	//this is used to provide a function to be able to find by username
	//when you start with findBy you are doing a query
	//there are many different method names you can use-Greg will provide a list
	//you will need to pass in two strings-username & password
	Optional<User> findByUsernameAndPassword(String username, String password);
	
}
