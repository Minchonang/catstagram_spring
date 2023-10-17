package com.example.insta.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.insta.model.UserList;
import com.example.insta.repository.UserListRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

  @Autowired
  UserListRepository userListRepository;
  @Autowired
  HttpSession session;
  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/userhistory")
  public String userHistory(Model model) {
    String sid = (String) session.getAttribute("id");

    Optional<UserList> i = userListRepository.findByIdString(sid);
    UserList list = i.get();

    model.addAttribute("history", list);

    return "user/userhistory";
  }

  @GetMapping("/userhistory/update")
  public String userUpdate(Model model) {

    String sid = (String) session.getAttribute("id");
    Optional<UserList> i = userListRepository.findByIdString(sid);
    UserList list = i.get();
    model.addAttribute("history", list);

    return "user/userupdate";
  }

  @PostMapping("/userhistory/emailup")
  public String userEmUpdatePost(@ModelAttribute UserList userList) {

    String email = userList.getEmail();
    String id = userList.getIdString();
    UserList optlList = userListRepository.findByIdString(id).get();
    optlList.setEmail(email);
    userListRepository.save(optlList);

    return "redirect:/userhistory";

  }

  // @GetMapping("/userhistory/pwcheck")
  // @ResponseBody
  // public String userPwUpdate(@ModelAttribute UserList userList, String newPw,
  // String idstring) {
  // String oldPw = userList.getPw();

  // String id = userList.getIdString();
  // System.out.println(oldPw + id);

  // Boolean ismatch = passwordEncoder.matches(oldPw,
  // userListRepository.findByIdString(id).get().getPw());

  // if (ismatch) {
  // UserList db = userListRepository.findByIdString(id).get();
  // String np = passwordEncoder.encode(newPw);
  // db.setPw(np);
  // userListRepository.save(db);
  // } else {

  // return "fail";
  // }
  // return "success";

  // }

  @PostMapping("/userhistory/pwcheck")
  @ResponseBody
  public String userPwUpdatePost(@ModelAttribute UserList userList, String newPw) {
    String oldPw = userList.getPw();

    String id = userList.getIdString();

    Boolean ismatch = passwordEncoder.matches(oldPw,
        userListRepository.findByIdString(id).get().getPw());

    if (ismatch) {
      UserList db = userListRepository.findByIdString(id).get();
      String np = passwordEncoder.encode(newPw);
      db.setPw(np);
      userListRepository.save(db);
    } else {

      return "fail";
    }
    return "success";

  }
}
