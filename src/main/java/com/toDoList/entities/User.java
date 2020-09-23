package com.toDoList.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_user;
	@NotNull
	@Column (unique = true)
	private String username;
	@NotNull
	private String password;
	private String email;
	private String name;
	private Date birthday;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Task> tasks = new ArrayList<Task>();

	public User(String username, String password, String email, String name, Date birthday, List<Task> tasks) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.birthday = birthday;
		this.tasks = tasks;
	}

}
