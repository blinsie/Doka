package com.alevel.finalProject.Doka.Doka.db.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "voice_rooms")
public class VoiceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int voice_room_id;

    @Column(name = "voice_room_name")
    private String voice_room_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private Server server;
}
