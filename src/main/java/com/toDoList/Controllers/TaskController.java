package com.toDoList.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.toDoList.Repositories.TaskRepository;
import com.toDoList.Repositories.UserRepository;
import com.toDoList.entities.Task;
import com.toDoList.entities.User;
import com.toDoList.logiques.CheckUser;
import com.toDoList.logiques.UserCk;

@Controller
public class TaskController {
	@Autowired
	UserRepository ur;
	@Autowired
	TaskRepository tr;
	private CheckUser ckUser = new UserCk();

	@GetMapping({ "/", "/index" })
	public String getLoginForm() {
		return "loginForm";
	}

	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "iduserconnect") Long id_user) {
		User userConnect = ur.getOne(id_user);
		return postLoginForm(model, userConnect.getUsername(), userConnect.getPassword());
	}

	@PostMapping({ "/login" })
	public String postLoginForm(Model model, 
			@RequestParam String username, 
			@RequestParam String password) {
		if (ckUser.isValide(username, password)) {
			System.out.println("username = " + username + "pass = " + password);
			User userConnect = new User();
			userConnect = ur.findUserByNameAndPassword(username, password);
			if (userConnect != null) {
				List<Task> tasks = tr.findTaskByIdUser(userConnect.getId_user());
				model.addAttribute("user", userConnect);
				model.addAttribute("tasks", tasks);
				return "taskUser";
			} else // user n'existe pas dans DB
				return "/login";// ?error";
		} else // user ou pass n'est pas rempli
			return "/login";
	}

	@GetMapping("/ajouter")
	public String getAddNewTaskForm(Model model, 
			@RequestParam(name = "iduserconnect") Long id_client) {
		User userConnect = ur.getOne(id_client);
		if (userConnect != null) {
			Task task = new Task();
			model.addAttribute("task", task);
			model.addAttribute("user", userConnect);
			return "newTaskForm";
		}
		return "login";
	}

	@PostMapping("/saveNvTache")
	public String saveNouveauTask(Model model, 
			@RequestParam(name = "iduserconnect") Long id_user, 
			Task task) {
		User userconnect = ur.getOne(id_user);
		System.out.println("userconnect = " + userconnect.getId_user() + " --- " + userconnect.getUsername());
		System.out.println("task nv = " + task.getTitle() + " --- " + task.getCategory() + " --- " + task.getLabel());
		if (task.getCategory() != null & !task.getCategory().isEmpty() && task.getTitle() != null
				&& !task.getTitle().isEmpty()) {
			task.setUser(userconnect);
			tr.save(task);
			model.addAttribute("user", userconnect);
			model.addAttribute("tasknouveau", task);
			return "taskNouveauDetail";
		} else
			return getAddNewTaskForm(model, userconnect.getId_user());
	}

	@PostMapping("/chercher")
	public String chercherByMotCle(Model model,
			@RequestParam(name = "iduserconnect") Long id_user,  
			@RequestParam(name="motcle") String motcle) {
		User userconnect = ur.getOne(id_user);
		String key = motcle;
		List<Task> tasksResult = tr.findTaskByIdUser(id_user);
		if (key.length() == 0 && key.equals(null))
			model.addAttribute("tasks", tasksResult);
		else {
			key = "%"+ key +"%";
			tasksResult = tr.findTaskByIdUserByKey(id_user, key);
			model.addAttribute("tasks", tasksResult);
		}
		model.addAttribute("user", userconnect);
		return "taskUser";
	}

	@GetMapping("/supprimerTache")
	public String supprimerTache(Model model, 
			@RequestParam(name = "idUserConnect") Long id_user,
			@RequestParam(name = "idtask") Long id_task) {
		User userConnect = ur.findById(id_user).get(); // récupère user connecte
		List<Task> listNv = tr.findTaskByIdUser(userConnect.getId_user()); // list de tache proprietaire
		Task taskFind = tr.getOne(id_task); // chercher tache à supprimer dans repo
		if (taskFind.getUser().getId_user() == userConnect.getId_user()) { // verifier id_user de tache trouvée est
																			// égale id-user connecté
			tr.delete(taskFind); // supprime ce tache
			listNv = tr.findTaskByIdUser(userConnect.getId_user()); // recharge sa list de tache
		}
		model.addAttribute("user", userConnect); // envoye user et sa liste à model
		model.addAttribute("tasks", listNv);
		return "taskUser";
	}

	@RequestMapping(value = "/modifierTache", method = RequestMethod.GET)
	public String modifierTache(Model model, 
			@RequestParam(name = "idUserConnect") Long id_user,
			@RequestParam(name = "idtask") Long id_task) {
		User userConnect = ur.findById(id_user).get();
		Task taskModif = tr.findById(id_task).get();
		model.addAttribute("user", userConnect);
		model.addAttribute("task", taskModif);
		return "editTaskForm";
	}

	@PostMapping("/savemodif")
	public String saveModifTask(Model model, 
			Task task,
			@RequestParam(name = "idtask") Long id_task, 
			@RequestParam(name = "iduserconnect") Long id_user) {
		Task taskFind = tr.findById(id_task).get();
		User userConnect = ur.findById(id_user).get();
		task.setId_task(taskFind.getId_task());
		task.setUser(taskFind.getUser());
		taskFind = task;
		tr.save(taskFind);
		model.addAttribute("saveTaskModif", taskFind);
		model.addAttribute("user", userConnect);
		return "taskModifDetail";
	}

//	@GetMapping("/trierBy")
//	public String sortBy(Model model, @RequestParam(name = "iduserconnect") Long id_user, String sortBy) {
//		User userConnect = ur.findById(id_user).get();	
//		String colSortBy = sortBy;
//		System.out.println(" colSortBy " + colSortBy);
//		List<Task> lisTask = tr.findTaskByIdUser(userConnect.getId_user(), colSortBy);
//		model.addAttribute("user", userConnect);
//		model.addAttribute("task", lisTask);
//		if (userConnect != null) { 
//			
//		}
//		return "taskUser";
//	}
}