package com.example.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.insta.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
