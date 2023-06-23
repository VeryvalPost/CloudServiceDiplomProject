package netology.cloudserverdiplom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "increment")
    private int id;
    @Column
    private String login;
    @Column
    private String password;
    @Column(name = "auth_token")
    private String authToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<File> files;

    public User() {
    }

    public User(String login, String password, String authToken) {
        this.login = login;
        this.password = password;
        this.authToken = authToken;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, String authToken) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authToken = authToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
