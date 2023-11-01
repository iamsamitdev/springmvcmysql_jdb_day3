package com.itgenius.springmvcmysql_jdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itgenius.springmvcmysql_jdb.model.User;
import com.itgenius.springmvcmysql_jdb.service.UserService;

@Controller
public class FrontendController {

	@Autowired
	private UserService userService;

    String viewpath = "frontend/";

    @GetMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("homeMenuActive", true);
		return viewpath + "index";
	}

    @GetMapping(value = "/about")
	public String about(Model model) {
		model.addAttribute("aboutMenuActive", true);
		return viewpath + "about";
	}

	@GetMapping(value = "/service")
	public String service(Model model) {
		model.addAttribute("serviceMenuActive", true);
		return viewpath + "service";
	}

	@GetMapping(value = "/contact")
	public String contact(Model model) {
		model.addAttribute("contactMenuActive", true);
		return viewpath + "contact";
	}

	@GetMapping(value = "/login")
	public String login(Model model) {
		model.addAttribute("loginMenuActive", true);
		return viewpath + "login";
	}

	@GetMapping(value = "/register")
	public String register(Model model) {
		model.addAttribute("registerMenuActive", true);
		return viewpath + "register";
	}

	@GetMapping(value="/users")
	public ModelAndView users(Model model, @RequestParam(defaultValue="0") int page) {
		int pageSize = 2;
		model.addAttribute("userMenuActive", true);
		Page<User> listUser = userService.getAllUser(page, pageSize);
		return new ModelAndView(viewpath + "user", "listUser", listUser);
	}

	@GetMapping(value = "/adduser")
	public String adduser(ModelMap map) {
		User user = new User();
		map.addAttribute("user", user);
		return viewpath + "adduser";
	}

	// Save User
	@PostMapping(value = "/saveuser")
	public String saveuser(
		@Validated
		@ModelAttribute("user") User user,
		BindingResult result,
		RedirectAttributes redirectAttributes
	) {
		// if validation has error
		if(result.hasErrors()){
			return viewpath + "adduser";
		}else{
			userService.save(user);
			return "redirect:/users";
		}
	}

	// Load Edit User Form
	@GetMapping(value = "/edituser/{id}")
	public String edituser(@PathVariable int id, ModelMap map) {
		User user = userService.findById(id).get();
		map.addAttribute("user",user);
		return viewpath + "edituser";
	}

	// Submit Edit User
	@PostMapping(value = "/saveedituser")
	public String saveedituser(@Validated @ModelAttribute("user") User user, BindingResult result)
	{
		if(result.hasErrors()){
			return viewpath + "edituser";
		}else{
			User u = userService.findById(user.getId()).get();
			
			u.setFirstname(user.getFirstname());
			u.setLastname(user.getLastname());
			u.setEmail(user.getEmail());

			userService.save(u);

			return "redirect:/users";
		}
	}

	// Delete User
	@GetMapping(value = "/deleteuser/{id}")
	public ModelAndView deleteuser(@PathVariable int id){
		User user = userService.findById(id).get();
		userService.deleteById(user);
		return new ModelAndView("redirect:/users");
	}
}
