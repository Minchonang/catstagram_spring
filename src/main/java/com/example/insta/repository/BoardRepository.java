package com.example.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.insta.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
