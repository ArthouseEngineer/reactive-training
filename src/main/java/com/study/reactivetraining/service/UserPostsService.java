package com.study.reactivetraining.service;


import com.study.reactivetraining.dto.Post;
import com.study.reactivetraining.dto.User;
import com.study.reactivetraining.dto.UserPosts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserPostsService {

    private final PostsService postsService;
    private final UsersService usersService;


    public Mono<UserPosts> getAllPostsByUserId(Long id) {
        var userMono = usersService.user(id);

        var postsMono = postsService.postsByUserId(id)
                .collectList();

        return userMono.zipWith(postsMono)
                .map(p -> {
                    User user = p.getT1();
                    List<Post> posts = p.getT2();
                    UserPosts userPost = new UserPosts();
                    userPost.setUser(user);
                    userPost.setPosts(posts);
                    return userPost;
                });
    }


    public Mono<List<UserPosts>> getAllUsersWithPosts() {
        return usersService.users()
                .flatMap(user -> postsService.postsByUserId(user.getId())
                        .collectList()
                        .map(userPost -> new UserPosts(user, userPost)))
                .collectList();

    }

}
