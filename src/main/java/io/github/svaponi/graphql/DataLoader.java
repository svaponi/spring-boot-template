package io.github.svaponi.graphql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public DataLoader(final PostRepository postRepository, final AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        log.info("ApplicationReadyEvent loading data ...");

        final Author sam = new Author("sam");
        authorRepository.save(sam);

        final Author tim = new Author("tim");
        authorRepository.save(tim);

        final Author carl = new Author("carl");
        authorRepository.save(carl);

        Stream.of("foo", "bar", "zip", "clap", "boom", "strap", "crash", "swoosh", "sbang", "crock", "push", "hey")
                .map(content -> new Post(content, randomize(sam, tim, carl)))
                .forEach(postRepository::save);

        log.info("Posts: {}", postRepository.findAll());
    }

    private <T> T randomize(final T... items) {
        return items.length == 0 ? null : items[new Random().nextInt(items.length)];
    }
}