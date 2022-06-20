package jarek.security.controller;

import jarek.security.model.Account;
import jarek.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/admin/account")
@PreAuthorize(value = "hasRole('ADMIN')")
public class AdminAccountController {

    private AccountService accountService;

    @Autowired
    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/list")
    public String getUserList(Model model) {
        model.addAttribute("atr_accountsList", accountService.getAll());

        return "account-list";
    }

    @GetMapping(path = "/toggleLock")
    public String toggleLock(@RequestParam(name = "accountId") Long accountId) {
        accountService.toggleLock(accountId);

        return "redirect:/admin/account/list";
    }

    @GetMapping(path = "/remove")
    public String remove(@RequestParam(name = "accountId") Long deletedId) {
        accountService.remove(deletedId);

        return "redirect:/admin/account/list";
    }
}
