package com.example.insta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = { "comments" })
public class Board {
  @Id
  @GeneratedValue
  int id;

  String title;
  String content;
  String writer;
  String time;
  String ip;

  @JsonIgnore
  @OneToMany(mappedBy = "board")
  List<Comment> comments = new ArrayList<>();

}
