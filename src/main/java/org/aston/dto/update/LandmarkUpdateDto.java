package org.aston.dto.update;

import lombok.Getter;

@Getter
public class LandmarkUpdateDto implements UpdateDto {
   public Long id;
   private String description;
}
