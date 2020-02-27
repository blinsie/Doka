package com.alevel.finalProject.Doka.Doka.db.repos;

import com.alevel.finalProject.Doka.Doka.db.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
    public Message findByText(String text);
}
