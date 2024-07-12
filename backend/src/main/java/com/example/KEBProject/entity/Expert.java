package com.example.KEBProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Expert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expert {

    @Id
    @Column(name = "engineer_id", nullable = false, length = 20)
    private String engineerId;

    @NonNull
    @Column(name = "customer_id", nullable = false, length = 20)
    private String customerId;

    @Column(name = "engineer_career")
    private Integer engineerCareer;

    @Column(name = "engineer_picture", length = 80)
    private String engineerPicture;

    @Column(name = "engineer_certification", length = 80)
    private String engineerCertification;

    @Column(name = "engineer_profile", length = 200)
    private String engineerProfile;

    @NonNull
    @Column(name = "rating_average", nullable = false)
    private Float ratingAverage = 0.0f;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @PrePersist
    protected void onPrePersist() {
        if (this.ratingAverage == null) {
            this.ratingAverage = 0.0f;
        }
    }
}
