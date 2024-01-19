package com.adamszablewski.utils;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.Profile;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class Dao {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    @Transactional
    public void deleteUserData(long userId){
        profileRepository.deleteByUserId(userId);
    }
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }
    public void deleteComment(List<Comment> comments){
        commentRepository.deleteAll(comments);
    }
    public void deletePost(Post post) {
        removePostFromProfile(post);
        postRepository.delete(post);
    }
    public void deletePostById(long postId) {
        Post post = postRepository.findById(postId)
                        .orElseThrow(NoSuchPostException::new);
        deletePost(post);
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
