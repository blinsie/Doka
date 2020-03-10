package com.alevel.finalProject.Doka.Doka.db.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "voice_rooms")
public class VoiceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int voice_room_id;

    @Column(name = "voice_room_name")
    private String voice_room_name;

    @EqualsAndHashCode.Include
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private Server server;
}
