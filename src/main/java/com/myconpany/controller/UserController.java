package com.myconpany.controller;

import com.myconpany.repository.UserRepository;
import com.myconpany.service.UserNotFoundException;
import com.myconpany.service.UserService;
import com.myconpany.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserlist(Model model) {
        List<User> userList = service.listAll();
        model.addAttribute("userList", userList);
        model.addAttribute("pageTitle", "Gerenciamento de Usuários");
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Registrar um novo Usuário");
        return "form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {

        List<User> userList = service.listAll();
        for (User u : userList) {
            if (u.getId().equals(user.getId())) {
                User user1 = new User();
                user1.setId(user.getId());
                user1.setEmail(user.getEmail());
                user1.setFirstName(user.getFirstName());
                user1.setLastName(user.getLastName());
                user1.setEnable(user.isEnable());
                user1.setPassword(user.getPassword());
                service.save(user1);
                ra.addFlashAttribute("message", "O usuário foi alterado com sucesso!");
                return "redirect:/users";
            }
        }
        service.save(user);
        ra.addFlashAttribute("message", "O usuário foi cadastrado com sucesso!");
        return "redirect:/users";
    }


    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) throws UserNotFoundException {

        List<User> userList = service.listAll();
        for (User u : userList) {
            if (u.getId().equals(id) && u.isEnable()) {
                User user = service.get(id);
                model.addAttribute("user", user);
                model.addAttribute("pageTitle", "Alterar o Usuário com id: " + id);
                return "form";
            }
        }
        ra.addFlashAttribute("message", "O usuário não pode ser modificado!");
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) throws UserNotFoundException {

        List<User> userList = service.listAll();
        for (User u : userList) {
            if (u.getId().equals(id) && u.isEnable()) {
                service.delete(id);
                ra.addFlashAttribute("message", "O usuário foi deletado com sucesso!");
                return "redirect:/users";
            }
        }
        ra.addFlashAttribute("message", "O usuário não pode ser deletado!");
        return "redirect:/users";

    }


}