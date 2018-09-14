package io.github.svaponi.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Query implements GraphQLQueryResolver {

    private final PostRepository postRepository;

    public Query(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getRecentPosts(final int size) {
        final Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return postRepository.findAll(pageable).getContent();
    }
}