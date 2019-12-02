package com.acme.users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "User.findUsers",
                query = "SELECT u " +
                        "FROM User u"
        )
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
