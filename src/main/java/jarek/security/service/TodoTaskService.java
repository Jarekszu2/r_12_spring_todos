package jarek.security.service;

import jarek.security.model.Account;
import jarek.security.model.TaskStatus;
import jarek.security.model.TodoTask;
import jarek.security.repository.AccountRepository;
import jarek.security.repository.TodoTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

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

    public Set<TodoTask> getAll(String userName) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(userName);
        if (optionalAccount.isPresent()) {
            Account userAccount = optionalAccount.get();

            return userAccount.getTasks();
        }
        return new HashSet<>();
    }
}
