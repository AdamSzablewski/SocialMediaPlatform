package com.adamszablewski.service;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Upvote;
import com.adamszablewski.interfaces.Likeable;
import com.adamszablewski.model.Post;
import com.adamszablewski.exceptions.NoSuchCommentException;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.LikeRepository;
import com.adamszablewski.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public boolean checkIfUserAlreadyLiked(Likeable likeableObject, long userId){
        return likeableObject.getLikes().stream()
                .anyMatch(like -> like.getUserId() == userId);
    }
    public void removeLike(Likeable likeableObject, long userId){
        Upvote upvote =  likeableObject.getLikes().stream()
                .filter(l -> l.getUserId() == userId)
                .findFirst()
                .orElse(null);
        if (upvote != null){
            likeRepository.delete(upvote);
        }
        likeableObject.getLikes().remove(upvote);
    }
    @Transactional
    public void likePost(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);
        Upvote newLike = Upvote.builder()
                .post(post)
                .userId(userId)
                .build();
        boolean alreadyLiked = checkIfUserAlreadyLiked(post, userId);
        if(!alreadyLiked){
            post.getLikes().add(newLike);
        }
        likeRepository.save(newLike);
        postRepository.save(post);


    }
    @Transactional
    public void likeComment(long commentId, long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchPostException::new);
        Upvote newLike = Upvote.builder()
                .comment(comment)
                .userId(userId)
                .build();
        boolean alreadyLiked = checkIfUserAlreadyLiked(comment, userId);
        if(!alreadyLiked){
            comment.getLikes().add(newLike);
        }
        commentRepository.save(comment);
        likeRepository.save(newLike);
    }


    public void unLikePost(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);

        removeLike(post, userId);
        postRepository.save(post);

    }

    public void unLikeComment(long commentId, long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
        removeLike(comment, userId);
    }
}
