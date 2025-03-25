package syll25.tictactoe.web.repository;

import org.springframework.data.jpa.repository.Query;
import syll25.tictactoe.web.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g " +
            "FROM Game g " +
            "WHERE g.gameOver = false " +
            "ORDER BY g.createdDate DESC")
    List<Game> findByActiveGame();

}