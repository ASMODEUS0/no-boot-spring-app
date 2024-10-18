package org.aston.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LandmarkUpdateDto implements UpdateDto {
   public Long id;
   private String description;
}
