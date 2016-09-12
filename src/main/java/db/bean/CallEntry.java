package db.bean;

import javax.persistence.*;

@Entity
@Table(name = "CallEntry")
public class CallEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    public CallEntry(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CallEntry{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
