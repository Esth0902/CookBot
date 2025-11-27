package com.development.cookbot.dto.setting;

import com.development.cookbot.entity.Language;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SettingDto {
    private Boolean darkMode;
    private Language language;
    private Integer nbPeople;
    private LocalDate endedTrialDate;
    private boolean isTrial;
}
