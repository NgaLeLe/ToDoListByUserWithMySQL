package com.toDoList.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "task")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Task implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_task;
	@NotNull
	private String title;
	private String category;
	private String label;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	public Task(String title, String category, String label, User user) {
		this.title = title;
		this.category = category;
		this.label = label;
		this.user = user;
	}

}
