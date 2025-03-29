package co.istad.tostripv1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "places")
@Setter
@Getter
@RequiredArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String uuid;
    @Column( length = 255)
    private String name;

    @Column(nullable = false)
    private String categoryUuid;

    @Column(columnDefinition = "TEXT")
    private String description;

//    @Column( nullable = false )
    private String openHours  = ""; // Default value

    @Column(precision = 10)
    private Double entryFee = 0.0; // Default value

    @Column(length = 255)
    private String location;

    @Column(precision = 9)
    private Double latitude;

    @Column(precision = 9)
    private Double longitude;

    @ElementCollection
    private List<String> imageUrls;

    @Column( unique = true, columnDefinition = "INT DEFAULT 0")
    private Integer viewsCount;

    @Column(updatable = false)
    private String createdAt;

    private String updatedAt;

    Boolean disabled;
    Boolean enabled;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Category category;
}