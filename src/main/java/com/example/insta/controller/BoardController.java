package com.example.insta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.insta.model.Board;
import com.example.insta.model.Comment;
import com.example.insta.repository.BoardRepository;
import com.example.insta.repository.CommentRepository;
import com.example.insta.repository.UserListRepository;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Autowired
  HttpSession session;

  @Autowired
  BoardRepository boardRepository;
  @Autowired
  CommentRepository commentRepository;
  @Autowired
  UserListRepository userListRepository;

  @GetMapping({ "/list", "", "/" })
  public String list(Model model, @RequestParam(defaultValue = "1") int p) {

    Direction dic = Direction.DESC;
    Sort sort = Sort.by(dic, "id");
    Pageable page = PageRequest.of(p - 1, 5, sort);

    Page<Board> boardList = boardRepository.findAll(page);
    // model.addAttribute("sessionId", sessionId);
    model.addAttribute("boardList", boardList);

    return "board/list";
  }

  @GetMapping("/write")
  public String write() {

    return "board/write";
  }

  @PostMapping("/write")
  @Transactional
  public String writePost(@ModelAttribute Board board,

      HttpServletRequest request, String idString) {

    String clientIP = "";

    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null) {
      clientIP = xForwardedFor.split(",")[0];
    } else {
      // 헤더가 없으면 getRemoteAddr() 사용
      clientIP = request.getRemoteAddr();
    }

    // headers.forEach((key, value) -> {
    // System.out.println(String.format("Header '%s' = %s", key, value));
    // });
    // System.out.println(headers);

    Date date = new Date();
    SimpleDateFormat SDFAdd = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
    String toDayFormatAdd = SDFAdd.format(date);
    board.setTime(toDayFormatAdd);
    board.setIp(clientIP);
    board.setWriter(idString);
    boardRepository.save(board);

    return "redirect:/board/list";
  }

  @GetMapping("/detail")
  public String detail(
      @RequestParam(defaultValue = "1") int id,

      Model model) {

    Optional<Board> i = boardRepository.findById(id);
    Board detail = i.get();

    model.addAttribute("detail", detail);
    return "board/detail";
  }

  @GetMapping("/update")
  public String update(int id, Model model) {
    Optional<Board> i = boardRepository.findById(id);
    Board result = i.get();
    model.addAttribute("update", result);

    return "board/update";

  }

  // @GetMapping("/comment")
  // @ResponseBody
  // public String comment() {
  // return "asdf";
  // }
  @Transactional
  @PostMapping("/comment")
  public String commentPost(@ModelAttribute Comment comment,
      Model model,
      int board, String writer) {

    int id = comment.getBoard().getId();

    Date date = new Date();
    SimpleDateFormat SDFAdd = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
    String toDayFormatAdd = SDFAdd.format(date);
    comment.setTime(toDayFormatAdd);
    comment.setWriter(writer);
    commentRepository.save(comment);

    // model.addAttribute("comment", i);

    return "redirect:/board/detail?id=" + id;
  }

  @GetMapping("/comment/remove")
  public String commentRemove(@ModelAttribute Comment comment, int detailId) {
    commentRepository.delete(comment);
    return "redirect:/board/detail?id=" + detailId;
  }

}
