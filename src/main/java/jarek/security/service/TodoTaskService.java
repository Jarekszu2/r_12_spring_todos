package jarek.security.service;

import jarek.security.model.Account;
import jarek.security.model.TaskStatus;
import jarek.security.model.TodoTask;
import jarek.security.repository.AccountRepository;
import jarek.security.repository.TodoTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoTaskService {

    @Autowired
    private TodoTaskRepository todoTaskRepository;
    @Autowired
    private AccountRepository accountRepository;

    public void addTask(TodoTask task, Principal principal) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
        if (optionalAccount.isPresent()) {
            Account userAccount = optionalAccount.get();

            task.setAccount(userAccount);
            task.setTaskStatus(TaskStatus.TODO);

            todoTaskRepository.save(task);
        }
    }

    public Set<TodoTask> getAllCurrent(String userName) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(userName);
        if (optionalAccount.isPresent()) {
            Account userAccount = optionalAccount.get();

            return userAccount
                    .getTasks()
                    .stream()
                    .filter(task -> task.getTaskStatus() != TaskStatus.ARCHIVED)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public Set<TodoTask> getAllArchived(String userName) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(userName);
        if (optionalAccount.isPresent()) {
            Account userAccount = optionalAccount.get();

            return userAccount
                    .getTasks()
                    .stream()
                    .filter(task -> task.getTaskStatus() == TaskStatus.ARCHIVED)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public void setDone(Long doneId, String userName) {
        if (!userIsOwnerOf(userName, doneId)){
            return;
        }

        Optional<TodoTask> optionalTodoTask = todoTaskRepository.findById(doneId);
        if (optionalTodoTask.isPresent() && optionalTodoTask.get().getTaskStatus() != TaskStatus.ARCHIVED) {
            TodoTask todoTask = optionalTodoTask.get();
            todoTask.setTaskStatus(TaskStatus.DONE);
            todoTask.setDateFinished(LocalDate.now());

            todoTaskRepository.save(todoTask);
        }
    }

    public void setArchive(Long doneId, String userName) {
        if (!userIsOwnerOf(userName, doneId)){
            return;
        }

        Optional<TodoTask> optionalTodoTask = todoTaskRepository.findById(doneId);
        if (optionalTodoTask.isPresent() && optionalTodoTask.get().getTaskStatus() == TaskStatus.DONE) {
            TodoTask todoTask = optionalTodoTask.get();
            todoTask.setTaskStatus(TaskStatus.ARCHIVED);

            todoTaskRepository.save(todoTask);
        }
    }

    public void setToDo(Long doneId, String userName) {
        if (!userIsOwnerOf(userName, doneId)){
            return;
        }

        Optional<TodoTask> optionalTodoTask = todoTaskRepository.findById(doneId);
        if (optionalTodoTask.isPresent() && optionalTodoTask.get().getTaskStatus() == TaskStatus.DONE) {
            TodoTask todoTask = optionalTodoTask.get();
            todoTask.setTaskStatus(TaskStatus.TODO);
            todoTask.setDateFinished(null);

            todoTaskRepository.save(todoTask);
        }
    }

    private boolean userIsOwnerOf(String userName, Long taskId){
        Optional<Account> optionalAccount = accountRepository.findByUsername(userName);
        if (optionalAccount.isPresent()){
            Account user = optionalAccount.get();

            return user.getTasks()
                    .stream()
                    .anyMatch(task -> task.getId() == taskId);
        }
        return false;
    }
}
