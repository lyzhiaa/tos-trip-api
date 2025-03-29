package co.istad.tostripv1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String placeUuid;
    @Column(nullable = false)
    private String userUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private User user;


    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;
    @Column
    private String review;
    @Column(nullable = false, updatable = false)
    private String createdAt;
}
