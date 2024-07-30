package br.com.joaobarbosadev.wolfcatalogv2.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.Instant;


@Data
@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Instant createdAt;

    public Category() {}

    public Category(Long id, String name, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
}
