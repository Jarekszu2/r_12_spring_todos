package jarek.security.controller;

import jarek.security.model.TodoTask;
import jarek.security.service.TodoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping(path = "/task/")
public class TaskController {

    @Autowired
    private TodoTaskService todoTaskService;

    @GetMapping("/add")
    public String addForm(Model model, TodoTask task) {
        model.addAttribute("atr_taskObject", task);

    return "task-form";
    }

    @GetMapping("/list")
    public String taskList(Model model, Principal principal) {
        Set<TodoTask> taskSet = todoTaskService.getAll(principal.getName());
        model.addAttribute("atr_tasks", taskSet);

        return "task-list";
    }

    @PostMapping("/add")
    public String addForm(TodoTask task, Principal principal) {
        todoTaskService.addTask(task, principal);

        return "redirect:/task/list";
    }
}
