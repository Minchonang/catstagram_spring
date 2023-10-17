package com.example.insta.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SignInCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    log.debug("preHandle");
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute("id");
    if (user == null) {
      response.sendRedirect("/loginerror");
      return false;
    }
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request, HttpServletResponse response,
      Object handler, ModelAndView modelAndView) throws Exception {
    log.debug("postHandle");
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    log.debug("afterCompletion");
  }
}