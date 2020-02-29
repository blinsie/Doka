package com.alevel.finalProject.Doka.Doka.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "server_name")
    private String server_name;


/*
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "users",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "server_id")}
    )
    private List<User> users;
*/


    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TextChannel> text_channels;

/*
    private List<Integer> call_channels_id;
*/

}
