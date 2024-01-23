package com.adamszablewski.dao;

import com.adamszablewski.events.CommentEvent;
import com.adamszablewski.events.PostEvent;
import com.adamszablewski.events.ProfileEvent;
import com.adamszablewski.events.UpvoteEvent;
import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.Profile;
import com.adamszablewski.model.Upvote;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.LikeRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Dao {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ProfileRepository profileRepository;
    public void consumePostEvent(PostEvent event) {
        switch (event.getEventType()){
            case CREATE, UPDATE -> savePost(event.getPost());
            case DELETE -> deletePost(event.getPost());
        }
    }

    private void deletePost(Post post) {
        postRepository.deleteById(post.getId());
    }

    private void savePost(Post post){
        postRepository.save(post);
    }

    public void consumeCommentEvent(CommentEvent event) {
        switch (event.getEventType()){
            case CREATE, UPDATE -> saveComment(event.getComment());
            case DELETE -> deleteComment(event.getComment());
        }
    }
    private void deleteComment(Comment comment) {
        commentRepository.deleteById(comment.getId());
    }

    private void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public void consumeUpvoteEvent(UpvoteEvent event) {
        switch (event.getEventType()){
            case CREATE, UPDATE -> saveUpvote(event.getUpvote());
            case DELETE -> deleteUpvote(event.getUpvote());
        }
    }
    private void deleteUpvote(Upvote upvote) {
        likeRepository.deleteById(upvote.getId());
    }

    private void saveUpvote(Upvote upvote){
        likeRepository.save(upvote);
    }

    public void consumeProfileEvent(ProfileEvent event) {
        switch (event.getEventType()){
            case CREATE, UPDATE -> saveProfile(event.getProfile());
            case DELETE -> deleteProfile(event.getProfile());
        }
    }
    private void deleteProfile(Profile profile) {
        profileRepository.deleteById(profile.getId());
    }

    private void saveProfile(Profile profile){
        profileRepository.save(profile);
    }
}
