package com.adamszablewski.utils;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class CustomSortingUtil {

    public static final Comparator<Comment> COMPARE_COMMENT_AMOUNT =
            Comparator.<Comment>comparingInt(c -> c.getAnswers().size()).reversed();
    public static final Comparator<Comment> COMPARE_LIKE_AMOUNT =
            Comparator.<Comment>comparingInt(c-> c.getLikes().size()).reversed();
    public static final Comparator<Comment> COMPARE_DATE_TIME =
            Comparator.comparing(Comment::getDateTime, Comparator.reverseOrder());
    public static final Comparator<Post> COMPARE_DATE_TIME_POST =
            Comparator.comparing(Post::getCreationTime, Comparator.reverseOrder());

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

}
