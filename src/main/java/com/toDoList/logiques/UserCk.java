package com.toDoList.logiques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.toDoList.Repositories.UserRepository;
import com.toDoList.entities.User;

public class UserCk implements CheckUser {
	private UserRepository ur;
	@Override
	public boolean isValide(String name, String password) {
		if (name != null && name.length() > 0 && password != null && password.length() > 0)
			return true;
		else
			return false;
	}

	@Override
	public User findUser(String username, String password) {
		User user = new User();
		System.out.println("findUser, name = " + username + ", pss = " + password +"list user = "+ ur.findAll().size());
		user = ur.findUserByNameAndPassword(username, password);
		if (user != null)
			return user;
		else
			return null;
	}

}
