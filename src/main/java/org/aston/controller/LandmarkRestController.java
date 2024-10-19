package org.aston.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.request.LandmarkGetRequest;
import org.aston.service.LandmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/landmarks")
@RequiredArgsConstructor
public class LandmarkRestController {

    private final LandmarkService landmarkService;

    @PostMapping
    public ResponseEntity<LandmarkReadDto> addLandmark(@RequestBody LandmarkCreateDto landmarkCreateDto) {
        log.debug("Received Post /landmark  request with body: " + landmarkCreateDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(landmarkService.save(landmarkCreateDto));
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<List<LandmarkReadDto>> getLandmarks(@RequestBody LandmarkGetRequest landmarkRequest) {
        log.debug("Received Post /landmark/getAll request with body: " + landmarkRequest.toString());
       return  ResponseEntity.ok(landmarkService.getAll(landmarkRequest));
    }

    @PutMapping
    public ResponseEntity<LandmarkReadDto> updateLandmark(@RequestBody LandmarkUpdateDto landmarkUpdateDto) {
        log.debug("Received Put /landmark request with body: " + landmarkUpdateDto.toString());
        return ResponseEntity.ok(landmarkService.update(landmarkUpdateDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLandmark(@RequestParam(name = "id") Long id) {
        log.debug("Received Delete /landmark request with body: " + id.toString());
       return  landmarkService.delete(id) ?
               ResponseEntity.ok().build() :
               ResponseEntity.notFound().build();
    }

}
