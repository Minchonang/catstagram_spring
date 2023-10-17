package com.example.insta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.insta.model.UserList;

@Repository
public interface UserListRepository extends JpaRepository<UserList, Integer> {

  Optional<UserList> findByEmail(String e);

  Optional<UserList> findByIdString(String e);

  Optional<UserList> findByTel(String e);

}
