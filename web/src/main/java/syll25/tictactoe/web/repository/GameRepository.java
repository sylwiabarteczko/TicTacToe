package syll25.tictactoe.web.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT g.id " +
            "FROM Game g " +
            "WHERE g.isAi = true " +
            "and g.gameOver = false " +
            "and g.currentPlayer = g.player2Name")
    List<Long> findIdsNeedingAiMove();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g from Game g WHERE g.id = :id")
    Game lockById(@Param("id") Long id);
}