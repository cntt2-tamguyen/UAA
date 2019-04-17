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
import vn.edu.leading.uaa.services.RoleService;

import javax.validation.Valid;

@Controller
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String list(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "roles/list";
    }

    @GetMapping("roles/search")
    public String search(@RequestParam("term") String term, Model model) {
        if (StringUtils.isEmpty(term)) {
            return "redirect:/roles";
        }
        model.addAttribute("roles", roleService.search(term));
        return "roles/list";
    }

    @GetMapping("/roles/add")
    public String add(Model model) {
        model.addAttribute("roleModel", new RoleModel());
        return "roles/form";
    }

    @GetMapping("/roles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roleModel", roleService.findById(id));
        return "roles/form";
    }

    @PostMapping("/roles/save")
    public String save(@Valid RoleModel role, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "roles/form";
        }
        roleService.save(role);
        redirect.addFlashAttribute("successMessage", "Saved customer successfully!");
        return "redirect:/roles";
    }

    @GetMapping("/roles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (roleService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted customer successfully!");
            return "redirect:/roles";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            return "redirect:/roles";
        }
    }
}
