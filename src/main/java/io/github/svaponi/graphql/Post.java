package io.github.svaponi.graphql;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity
@ToString(of = {"content"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private Long authorId;

    private Instant timestamp;

    public Post(final String content, final Author author) {
        Assert.hasText(content, "invalid content");
        this.content = content;
        Assert.notNull(author, "null author");
        this.authorId = author.getId();
        author.getPosts().add(this);
        this.timestamp = Instant.now();
    }

    public void update(final String content) {
        Assert.hasText(content, "invalid content");
        this.content = content;
        this.timestamp = Instant.now();
    }
}
