package com.toDoList.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.toDoList.entities.Task;



@CrossOrigin("*")
public interface TaskRepository extends JpaRepository<Task, Long> {
	@Query("select t from task t where t.user.id_user = :x ")
	public List<Task> findTaskByIdUser(@Param("x") Long id_user);

	//or t.title like :y or t.label like :y
	@Query("select t from task t where t.user.id_user = :x  and (t.category like :y or t.title like :y or t.label like :y)")
	public List<Task> findTaskByIdUserByKey(@Param("x") Long id_user, @Param("y") String key);

//	@Query("select t from task t where t.user.id_user = :x sort by t.:y")
//	public List<Task> findTaskByIdUser(@Param("x") Long id_user, @Param("y") String keySort);
}
