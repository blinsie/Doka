package com.alevel.finalProject.Doka.Doka.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Getter
@Setter
@Document(collection = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private String message_id;

    @Column(name = "text")
    private String text;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return String.format(
                "Message[message_id=%s, text='%s']",
                message_id, text);
    }

}
