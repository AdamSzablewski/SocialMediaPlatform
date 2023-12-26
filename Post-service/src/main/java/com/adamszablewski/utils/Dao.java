package com.adamszablewski.utils;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Profile;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.exceptions.NoSuchProfileException;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Dao {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }
    public void deleteComment(List<Comment> comments){
        commentRepository.deleteAll(comments);
    }

    public void deletePost(long postId) {
        Post post = postRepository.findById(postId)
                        .orElseThrow(NoSuchPostException::new);
        removePostFromProfile(post);
        postRepository.deleteById(postId);
    }
    private void removePostFromProfile(Post post){
        Profile profile = profileRepository.findByUserId(post.getUserId())
                .orElse(null);
        if (profile == null) return;
        profile.getPosts().forEach(p -> {
            deleteComment(p.getComments());

        });
        profile.getPosts().remove(post);
        profileRepository.save(profile);
    }
}
