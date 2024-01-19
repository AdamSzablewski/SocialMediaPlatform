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

    private boolean checkIfUserAlreadyLiked(Likeable likeableObject, long userId){
        return likeableObject.getLikes().stream()
                .anyMatch(like -> like.getUserId() == userId);
    }
    private Upvote removeLike(Likeable likeableObject, long userId){
        return likeableObject.getLikes().stream()
                .filter(l -> l.getUserId() == userId)
                .findFirst()
                .orElse(null);

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

        Upvote like = removeLike(post, userId);
        postRepository.save(post);
        if(like != null){
            likeRepository.save(like);
        }
    }

    public void unLikeComment(long commentId, long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);

        Upvote like = removeLike(comment, userId);
        commentRepository.save(comment);
        if(like != null){
            likeRepository.save(like);
        }
    }
}
