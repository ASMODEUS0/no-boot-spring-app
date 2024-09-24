package org.aston.controller;

import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.request.LandmarkGetRequest;
import org.aston.service.LandmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landmark")
public class LandmarkRestController {

    private final LandmarkService landmarkService;


    public LandmarkRestController(LandmarkService landmarkService) {
        this.landmarkService = landmarkService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addLandmark(@RequestBody LandmarkCreateDto landmarkCreateDto) {
        landmarkService.save(landmarkCreateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/getAll")
    public List<LandmarkReadDto> getLandmarks(@RequestBody LandmarkGetRequest request) {
        return landmarkService.getAll(request);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<Object> updateLandmark(@RequestBody LandmarkUpdateDto updateDto) {
        landmarkService.update(updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<Object> deleteLandmark(@RequestParam(name = "id") Long id) {
        landmarkService.delete(id);
        return ResponseEntity.ok().build();
    }


}
