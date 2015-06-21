package com.xihuani.system.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xihuani.system.common.Constants;
import com.xihuani.system.model.System;
import com.xihuani.system.model.Role;
import com.xihuani.system.model.User;
import com.xihuani.system.service.RoleService;
import com.xihuani.system.service.SystemService;
import com.xihuani.system.service.UserService;

@Controller
public class SystemController {
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping("/login")
	public String login(Map<String, Object> map) {
		return Constants.View.SYSTEM_LOGIN;
	}
	
	@RequestMapping("/")
	public String home(Map<String, Object> map) {
		return "redirect:" + Constants.View.SYSTEM_SYSTEM;
	}
	
	@RequestMapping(value = "/system", method = RequestMethod.GET)
	public String listSystems(Map<String, Object> map) {
		map.put("system", new System());
		map.put("systemList", systemService.getSystemList());
		map.put("systems_nav_class","active");
		map.put("system_board","active");
		
		return Constants.View.SYSTEM_HOME_JSP;
	}
	
	@RequestMapping(value = "/system", method = RequestMethod.POST)
	public String addSystem(@ModelAttribute("system") System system, BindingResult result) {
		systemService.addSystem(system);
		return "redirect:" + Constants.View.SYSTEM_SYSTEM;
	}
	
	@RequestMapping(value = "/system/{systemId}/role", method = RequestMethod.GET)
    public String listSystemRoles(@PathVariable("systemId") Integer systemId, Map<String, Object> map) {
		map.put("role", new Role());
		map.put("roleList", systemService.getSystemRoles(systemId));
		map.put("systems_nav_class","active");
		map.put("role_board","active");
		
		return Constants.View.SYSTEM_HOME_JSP;
    }
	
	@RequestMapping(value = "/system/{systemId}/role", method = RequestMethod.POST)
	public String addRole(@PathVariable("systemId") Integer systemId, @ModelAttribute("role") Role role, BindingResult result) {
		systemService.addRole(systemId, role);
		return "redirect:" + Constants.View.SYSTEM_SYSTEM + "/" + systemId + Constants.View.SYSTEM_ROLE;
	}
	
	@RequestMapping("system/{systemId}/role/{roleId}/delete")
	public String deleteRole(@PathVariable("systemId") Integer systemId, @PathVariable("roleId") Integer roleId) {
		systemService.deleteRole(systemId, roleId);
		return "redirect:" + Constants.View.SYSTEM_SYSTEM + "/" + systemId + Constants.View.SYSTEM_ROLE;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public String listPeople(Map<String, Object> map) {
		
		map.put("user", new User());
		map.put("userList", userService.getUserList());
		map.put("users_nav_class","active");
		map.put("user_board","active");
		
		return Constants.View.SYSTEM_HOME_JSP;
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, BindingResult result) {
		userService.addUser(user);
		return "redirect:" + Constants.View.SYSTEM_USER;
	}
	
	@RequestMapping("/user/{userId}/delete")
	public String deleteUser(@PathVariable("userId") Integer userId) {
		userService.deleteUser(userId);
		return "redirect:" + Constants.View.SYSTEM_USER;
	}
	
	@RequestMapping("/access_level_search")
    public String listAccessLevelSearch(HttpSession session,  @RequestParam(value = "name", required = false) String name, Map<String, Object> map) {
		name = name != null ? name : (String)session.getAttribute("name");
		session.setAttribute("name", name);
		map.put("name", name);
		map.put("roleList", roleService.getRoleList());
		map.put("userList", userService.getUserList());
		map.put("systems_nav_class","active");
		map.put("access_level_board","active");
		
		return Constants.View.SYSTEM_HOME_JSP;
    }
	
	@RequestMapping("system/{systemId}/access_level")
    public String listAccessLevel(@PathVariable("systemId") Integer systemId, Map<String, Object> map) {
		map.put("roleList", systemService.getSystemRoles(systemId));
		map.put("userList", userService.getUserList());
		map.put("systems_nav_class","active");
		map.put("access_level_board","active");
		
		return Constants.View.SYSTEM_HOME_JSP;
    }
	
	@RequestMapping(value = "system/{systemId}/access_level/{userId}", method = RequestMethod.POST)
	public String editRoles(@PathVariable("systemId") Integer systemId, @PathVariable("userId") Integer userId, @RequestParam(value = "roleId", required = false) Integer []roleId) {
		User user = userService.findUser(userId);
		List<Role> roles = roleService.findRole(roleId);
		Set<Role> set = new HashSet<Role>(roles);
		user.setRoles(set);
		userService.save(user);
		return "redirect:" + Constants.View.SYSTEM_SYSTEM + "/" + systemId + Constants.View.SYSTEM_ACCESS_LEVEL;
	}
}
