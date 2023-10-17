package com.example.insta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserList {
  @Id
  @GeneratedValue
  int id;

  String email;
  String idString;
  String tel;
  String pw;
  String name;
  String time;

}
