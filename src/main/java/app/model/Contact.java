package app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String region;

    @Column
    private String avatar;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactActivity activity;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column
    private LocalDateTime updatedOn;
}
