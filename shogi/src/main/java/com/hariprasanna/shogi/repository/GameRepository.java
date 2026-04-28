package com.hariprasanna.shogi.repository;

import com.hariprasanna.shogi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // That's it. Leave it completely empty!
    // By extending JpaRepository, you instantly get:
    // .save(), .findById(), .findAll(), .deleteById()
}