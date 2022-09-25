package com.bridgelabz.bookstoreuserservice.repository;

import com.bridgelabz.bookstoreuserservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

	Optional<UserModel> findByemailId(String emailId);

}
