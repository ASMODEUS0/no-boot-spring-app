package org.aston.controller;

import lombok.RequiredArgsConstructor;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.service.LocalityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locality")
@RequiredArgsConstructor
public class LocalityController {

    private final LocalityService localityService;

    @PostMapping(path = "add")
    public ResponseEntity<Object> addLocality(@RequestBody LocalityCreateDto localityCreateDto){
        localityService.add(localityCreateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "update")
    public ResponseEntity<Object> updateLocality(@RequestBody LocalityUpdateDto localityUpdateDto){
        localityService.update(localityUpdateDto);
        return ResponseEntity.ok().build();
    }

}
