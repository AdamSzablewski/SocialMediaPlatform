package com.adamszablewski.utils;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Component
public class CustomSortingUtil {

    private static final Comparator<Comment> COMPARE_COMMENT_AMOUNT =
            Comparator.<Comment>comparingInt(c -> c.getAnswers().size()).reversed();
    private static final Comparator<Comment> COMPARE_LIKE_AMOUNT =
            Comparator.<Comment>comparingInt(c-> c.getLikes().size()).reversed();
    private static final Comparator<Comment> COMPARE_DATE_TIME =
            Comparator.comparing(Comment::getDateTime, Comparator.reverseOrder());

    public void sortComments(Post post){

        if (post.getComments() != null && post.getComments().size() > 1){
            List<Comment> comments = post.getComments().stream()
                    .sorted(COMPARE_COMMENT_AMOUNT
                            .thenComparing(COMPARE_LIKE_AMOUNT)
                                .thenComparing(COMPARE_DATE_TIME))
                    .toList();
            post.setComments(comments);
        }

    }
    private void sortBasedOnCommentAmtThenLikeAmt(){

    }

}
