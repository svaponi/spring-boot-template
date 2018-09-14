package io.github.svaponi.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Service;

@Service
public class Mutation implements GraphQLMutationResolver {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public Mutation(final PostRepository postRepository, final AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public Post writePost(final String content, final String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null)
            author = new Author(authorName);
        return postRepository.save(new Post(content, author));
    }
}