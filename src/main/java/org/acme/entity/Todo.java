package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends PanacheEntity {
    private String title;

    public static List<Todo> search(String title) {
        return find("title like ?1", "%" + title + "%").list();
    }

    @Override
    public String toString() {
        return "{\"title\":\"" + title + "\"}";
    }
}
