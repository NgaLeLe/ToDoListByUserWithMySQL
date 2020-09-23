package com.toDoList.logiques;

import com.toDoList.entities.User;

public interface CheckUser {
	
	public boolean isValide(String name, String password) ;
	public User findUser(String name, String password);

}
