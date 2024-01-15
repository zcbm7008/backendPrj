package webshop.domain;

import jakarta.persistence.*;
import lombok.*;
import webshop.User.domain.member.Member;
import webshop.common.model.Comment;
import webshop.common.jpa.CommentConverter;
import webshop.catalog.command.domain.product.Item;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

    @Id @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Item item;

    @Convert(converter = CommentConverter.class)
    private Comment comment;

    private int rating;

    public void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getReviews().add(this);
    }

}
