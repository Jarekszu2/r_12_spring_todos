package jarek.security.controller;

import jarek.security.model.Account;
import jarek.security.model.TodoTask;
import jarek.security.service.AccountService;
import jarek.security.service.TodoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping(path = "/admin/task")
@PreAuthorize(value = "hasRole('ADMIN')")
public class AdminTaskController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TodoTaskService todoTaskService;

    @GetMapping("/list") // listujemy użytkowników
    public String getTaskListForm(Model model) {
        model.addAttribute("atr_userList", accountService.getAll());

        return "user-chooser";
    }

    @PostMapping("/list")
    public String getTasksOfUser(Model model, String chosenUsername){
        Set<TodoTask> taskSet = todoTaskService.getAllCurrent(chosenUsername);
        taskSet.addAll(todoTaskService.getAllArchived(chosenUsername));

        model.addAttribute("atr_tasks", taskSet);

        return "task-list";
    }
}
