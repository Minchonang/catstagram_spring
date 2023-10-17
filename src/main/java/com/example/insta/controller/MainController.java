package com.example.insta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MainController {

  @GetMapping()
  public String main() {
    return "main";
  }

  @GetMapping("/loginerror")
  public String loginErorr() {
    return "common/loginerror";
  }

}
