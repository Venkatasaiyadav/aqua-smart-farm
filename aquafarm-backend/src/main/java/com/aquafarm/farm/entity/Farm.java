// src/main/java/com/aquafarm/farm/entity/Farm.java
package com.aquafarm.farm.entity;

import com.aquafarm.pond.entity.Pond;
import com.aquafarm.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farm_name", nullable = false, length = 100)
    private String farmName;

    @Column(length = 255)
    private String location;

    /*
     📚 LEARN: @OneToOne
     One Farm has One Owner
     @JoinColumn = "which column in THIS table links to User"
    */
    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /*
     📚 LEARN: @OneToMany
     One Farm has Many Ponds
     mappedBy = "the field in Pond class that points back to Farm"
     cascade = "if Farm is saved, save its Ponds too"
    */
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    @Builder.Default
    private List<Pond> ponds = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}