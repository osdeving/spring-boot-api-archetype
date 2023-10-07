package it.pkg.controller;

import it.pkg.dto.DemoDto;
import it.pkg.service.DemoService;
import it.pkg.service.impl.DemoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class DemoController {
    private final DemoService demoService;

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<DemoDto> list(
            @PathVariable("id") String id,
            @RequestHeader Map<String, String> headers) {
        log.info("### list");
        return ResponseEntity.ok(demoService.find(id));
    }
}
