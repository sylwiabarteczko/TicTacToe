package syll25.tictactoe.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import syll25.tictactoe.web.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByUsername(String username);

}