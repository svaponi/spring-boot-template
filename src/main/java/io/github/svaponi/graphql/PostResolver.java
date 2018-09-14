package io.github.svaponi.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Service;

@Service
public class PostResolver implements GraphQLResolver<Post> {

    private final AuthorRepository authorRepository;

    public PostResolver(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(final Post post) {
        return authorRepository.getOne(post.getAuthorId());
    }
}