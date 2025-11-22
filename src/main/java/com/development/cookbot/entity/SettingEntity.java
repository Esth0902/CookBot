package com.development.cookbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "settings")
public class SettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean darkMode;
    @Enumerated(EnumType.STRING)
    private Language language;
    private int nbPeople;
    private int requestQuantity;
    private LocalDate lastRequestDate;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;
}
