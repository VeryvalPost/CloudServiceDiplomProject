package netology.cloudserverdiplom.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(generator = "increment")
    private int id;
    @Column
    private String authToken;
    @Column
    private String filename;
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}