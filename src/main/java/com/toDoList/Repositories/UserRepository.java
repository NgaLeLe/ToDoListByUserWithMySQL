package com.toDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.toDoList.entities.User;

@CrossOrigin("*")
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from user u where u.username like :x and u.password like :y")
	public User findUserByNameAndPassword(@Param("x") String username, @Param("y") String pwd);

}
