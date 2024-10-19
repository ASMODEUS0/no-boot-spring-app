package org.aston.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.read.LocalityReadDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.service.LocalityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("locality")
@RequiredArgsConstructor
public class LocalityRestController {

    private final LocalityService localityService;

    @PostMapping
    public ResponseEntity<LocalityReadDto> addLocality(@RequestBody LocalityCreateDto localityCreateDto){
        log.debug("Received Post /locality request with body: " + localityCreateDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(localityService.save(localityCreateDto));
    }

    @PutMapping
    public ResponseEntity<LocalityReadDto> updateLocality(@RequestBody LocalityUpdateDto localityUpdateDto){
        log.debug("Received Put /locality request with body: " + localityUpdateDto.toString());
        return ResponseEntity.ok(localityService.update(localityUpdateDto));
    }
}
