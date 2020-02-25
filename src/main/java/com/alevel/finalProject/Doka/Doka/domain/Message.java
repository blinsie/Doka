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

    @Column(name = "autor")
    private String autor;

    @Column(name = "text_channel_id")
    private Integer text_channel_id;

    @Column(name = "to")
    private String to;

    public Message(String text, String autor) {
        this.text = text;
        this.autor = autor;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return String.format(
                "Message[message_id=%s, autor='%s', text='%s', text_channel_id='%s', receiver='%s']",
                message_id, autor, text, text_channel_id, to);
    }

}
