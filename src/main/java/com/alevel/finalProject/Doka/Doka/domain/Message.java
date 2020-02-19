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
    @Column(name = "messageId")
    private String messageId;

    @Column(name = "text")
    private String text;

    @Column(name = "senderId")
    private String senderId;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return String.format(
                "Message[message_id=%s, text='%s', senderId='%s']",
                messageId, text, senderId);
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
