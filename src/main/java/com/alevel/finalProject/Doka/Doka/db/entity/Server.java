package com.alevel.finalProject.Doka.Doka.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "servers")
@Data
@NoArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "server_id")
    private Integer server_id;

    @Column(name = "server_name", unique = true)
    private String server_name;

    public void addTextChannel(TextChannel channel){
        this.text_channels.add(channel);
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TextChannel> text_channels;

    @ToString.Exclude
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VoiceRoom> voice_rooms;


}
