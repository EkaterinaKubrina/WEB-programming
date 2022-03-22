package com.checker.onlinegame.repositories;

import com.checker.onlinegame.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
