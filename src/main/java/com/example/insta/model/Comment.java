package com.example.insta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Comment {

  @Id
  @GeneratedValue
  int id;
  String writer;
  String content;
  String time;

  @ManyToOne
  Board board;

}
