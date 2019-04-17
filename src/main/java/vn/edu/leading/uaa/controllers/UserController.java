package vn.edu.leading.uaa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.uaa.models.RoleModel;
import vn.edu.leading.uaa.models.UserModel;
import vn.edu.leading.uaa.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("roles", userService.findAll());
        return "users/list";
    }

    @GetMapping("users/search")
    public String search(@RequestParam("term") String term, Model model) {
        if (StringUtils.isEmpty(term)) {
            return "redirect:/users";
        }
        model.addAttribute("users", userService.search(term));
        return "users/list";
    }

    @GetMapping("/users/add")
    public String add(Model model) {
        model.addAttribute("userModel", new RoleModel());
        return "users/form";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userModel", userService.findById(id));
        return "users/form";
    }

    @PostMapping("/users/save")
    public String save(@Valid UserModel user, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "users/form";
        }
        userService.save(user);
        redirect.addFlashAttribute("successMessage", "Saved customer successfully!");
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (userService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted customer successfully!");
            return "redirect:/users";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            return "redirect:/users";
        }
    }

    @PostMapping("/register")
    public String register(@Valid UserModel userModel) throws Exception {
        userService.register(userModel);
        return "redirect:/login";
    }
}
