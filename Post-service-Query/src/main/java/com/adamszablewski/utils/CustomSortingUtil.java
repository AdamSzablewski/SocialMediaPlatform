package com.adamszablewski.utils;

import com.adamszablewski.interfaces.Likeable;
import com.adamszablewski.model.Commentable;
import com.adamszablewski.model.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;

@Component
public class CustomSortingUtil {

    public static final Comparator<Commentable> COMPARE_COMMENT_AMOUNT =
            Comparator.<Commentable>comparingInt(c -> c.getComments().size()).reversed();
    public static final Comparator<Likeable> COMPARE_LIKE_AMOUNT =
            Comparator.<Likeable>comparingInt(c-> c.getLikes().size()).reversed();
public static final Comparator<Post> COMPARE_DATE_TIME_POST =
        Comparator.comparing(
                post -> {
                    LocalDateTime creationTime = post.getCreationTime();
                    return (creationTime != null) ? creationTime.toLocalDate() : null;
                },
                Comparator.nullsLast(Comparator.reverseOrder())
        );
    public static final Comparator<Post> COMPARE_DAY_LIKE_COMMENT =
            COMPARE_DATE_TIME_POST
                .thenComparing(COMPARE_LIKE_AMOUNT)
                    .thenComparing(COMPARE_COMMENT_AMOUNT);

}
