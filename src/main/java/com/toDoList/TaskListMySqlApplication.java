package com.toDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.toDoList.Repositories.TaskRepository;
import com.toDoList.Repositories.UserRepository;

@SpringBootApplication
public class TaskListMySqlApplication {
	@Autowired 
	UserRepository userRepo;
	@Autowired
	TaskRepository taskRepo;

	public static void main(String[] args) {
		SpringApplication.run(TaskListMySqlApplication.class, args);
	}
	public void run(String... args) throws Exception {
		System.out.println("Application started");
	}
}
