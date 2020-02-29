package com.alevel.finalProject.Doka.Doka.db.repos;

import com.alevel.finalProject.Doka.Doka.db.entity.TextChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextChannelRepository extends JpaRepository<TextChannel, Integer> {
}