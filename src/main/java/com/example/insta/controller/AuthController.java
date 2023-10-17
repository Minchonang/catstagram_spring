package com.example.insta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.insta.model.UserList;
import com.example.insta.repository.UserListRepository;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  UserListRepository userListRepository;

  @Autowired
  HttpSession session;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/signup")
  public String signup() {
    session.removeAttribute("isIdCheck");

    return "auth/signup";
  }

  @GetMapping("/errorpage")
  public String errorPage() {
    return "common/errorpage";
  }

  @PostMapping("/signup")

  public String signupPost(@ModelAttribute UserList userList) {

    // String idString = userList.getIdString();
    Boolean isIdCheck = (Boolean) session.getAttribute("isIdCheck");

    if (isIdCheck == null) {
      System.out.println("isIdCheck is null");
      return "redirect:/auth/signup";

    } else if (isIdCheck) {
      Date date = new Date();
      SimpleDateFormat SDFAdd = new SimpleDateFormat("yy-MM-dd HH mm ss z");
      String toDayFormatAdd = SDFAdd.format(date);
      userList.setTime(toDayFormatAdd);
      String enPw = passwordEncoder.encode(userList.getPw());

      userList.setPw(enPw);

      userListRepository.save(userList);

    } else {
      System.out.println("isIdCheck is false");
      return "redirect:/auth/errorpage";
    }

    return "auth/signin";

  }

  @GetMapping("/signin")
  public String signin() {

    return "/auth/signin";
  }

  @PostMapping("/signin")
  public String signinPost(@ModelAttribute UserList userList) {

    String all = userList.getIdString();

    Optional<UserList> optId = userListRepository.findByIdString(all);

    Optional<UserList> optEm = userListRepository.findByEmail(all);

    Optional<UserList> optTel = userListRepository.findByTel(all);

    String dbPw = "";
    String pw = "";
    String dbEm = "";
    String dbId = "";

    if (optId.isPresent() || optEm.isPresent() || optTel.isPresent()) {

      pw = userList.getPw();

      if (optId.isPresent()) {
        dbPw = optId.get().getPw();
        dbEm = optId.get().getEmail();
        dbId = optId.get().getIdString();
      } else if (optEm.isPresent()) {
        dbPw = optEm.get().getPw();
        dbEm = optEm.get().getEmail();
        dbId = optEm.get().getIdString();
      } else if (optTel.isPresent()) {
        dbPw = optTel.get().getPw();
        dbEm = optTel.get().getEmail();
        dbId = optTel.get().getIdString();
      } else {
        System.out.println("id is not exsist");
        return "auth/signinfail";
      }

      if (passwordEncoder.matches(pw, dbPw)) {

        session.setAttribute("email", dbEm);
        session.setAttribute("id", dbId);
        return "redirect:/";

      } else {
        System.out.println("not equals");
        return "auth/signinfail";
      }

    }

    return "auth/signinfail";

  }

  @GetMapping("/signout")

  public String signOut() {
    session.removeAttribute("id");
    session.removeAttribute("pw");
    session.removeAttribute("email");

    return "auth/signout";
  }

  // ajax영역입니다. idcheck, checkEmail, checkTel
  @GetMapping("/idcheck")
  @ResponseBody
  public String idCheck(@ModelAttribute UserList userList) {
    session.setAttribute("isIdCheck", false);

    String idString = userList.getIdString();

    if (idString.isEmpty()) {// 비어있을 경우 가입불가
      return "가입불가";
    }
    Optional<UserList> opt = userListRepository.findByIdString(idString);

    if (opt.isPresent()) { // 값 있음, 아이디가 있음, 가입 불가
      return "가입불가";
    } else { // 값 없음, 아이디가 없음, 가입 가능
      session.setAttribute("isIdCheck", true);
      return "가입가능";
    }

  }

  @GetMapping("/emailcheck")
  @ResponseBody
  public String emailCheck(@ModelAttribute UserList userList) {
    session.setAttribute("emailcheck", false);

    String email = userList.getEmail();

    if (email.isEmpty()) {// 비어있을 경우 가입불가
      return "가입불가";
    }
    Optional<UserList> opt = userListRepository.findByEmail(email);

    if (opt.isPresent()) { // 값 있음, 아이디가 있음, 가입 불가
      return "가입불가";
    } else { // 값 없음, 아이디가 없음, 가입 가능
      session.setAttribute("emailcheck", true);
      return "가입가능";
    }

  }

  @GetMapping("/telcheck")
  @ResponseBody
  public String telCheck(@ModelAttribute UserList userList) {
    session.setAttribute("telcheck", false);

    String tel = userList.getTel();

    if (tel.isEmpty()) {// 비어있을 경우 가입불가
      return "가입불가";
    }
    Optional<UserList> opt = userListRepository.findByTel(tel);

    if (opt.isPresent()) { // 값 있음, 아이디가 있음, 가입 불가
      return "가입불가";
    } else { // 값 없음, 아이디가 없음, 가입 가능
      session.setAttribute("telcheck", true);
      return "가입가능";
    }

  }

}
