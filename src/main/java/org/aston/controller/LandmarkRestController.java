package org.aston.controller;

import lombok.extern.slf4j.Slf4j;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.request.LandmarkGetRequest;
import org.aston.service.LandmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/landmark")
public class LandmarkRestController {

    private final LandmarkService landmarkService;


    public LandmarkRestController(LandmarkService landmarkService) {
        this.landmarkService = landmarkService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addLandmark(@RequestBody LandmarkCreateDto landmarkCreateDto) {
        log.debug("Received /landmark/add request with body: " + landmarkCreateDto.toString());
        landmarkService.save(landmarkCreateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/getAll")
    public List<LandmarkReadDto> getLandmarks(@RequestBody LandmarkGetRequest landmarkRequest) {
        log.debug("Received /landmark/getAll request with body: " + landmarkRequest.toString());
        return landmarkService.getAll(landmarkRequest);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<Object> updateLandmark(@RequestBody LandmarkUpdateDto landmarkUpdateDto) {
        log.debug("Received /landmark/update request with body: " + landmarkUpdateDto.toString());
        landmarkService.update(landmarkUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<Object> deleteLandmark(@RequestParam(name = "id") Long id) {
        log.debug("Received /landmark/delete request with body: " + id.toString());
        landmarkService.delete(id);
        return ResponseEntity.ok().build();
    }


}
