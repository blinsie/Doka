package com.alevel.finalProject.Doka.Doka.db.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "text_channels")
public class TextChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int text_channel_id;

    @EqualsAndHashCode.Include
    @ToString.Exclude
    @Column(name = "text_channel_name")
    private String text_channel_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private Server server;
}
