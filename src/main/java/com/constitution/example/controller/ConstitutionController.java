package com.constitution.example.controller;

import com.constitution.example.service.ConstitutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ConstitutionController {

    private final ConstitutionService constitutionService;

    public ConstitutionController(ConstitutionService constitutionService) {
        this.constitutionService = constitutionService;
    }

    @GetMapping
    public ResponseEntity<String> simplify(@RequestParam(value = "question", defaultValue = "List all Articles in the Turkey Constitution")
                           String question) {

        var simplify =  constitutionService.simplify(question);

        return ResponseEntity.ok(simplify);
    }
}
