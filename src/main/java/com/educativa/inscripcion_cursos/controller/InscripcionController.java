package com.educativa.inscripcion_cursos.controller;

import com.educativa.inscripcion_cursos.dto.InscripcionRequestDTO;
import com.educativa.inscripcion_cursos.dto.InscripcionResponseDTO;
import com.educativa.inscripcion_cursos.service.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> inscribirEstudiante(@Valid @RequestBody InscripcionRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionService.inscribirEstudiante(requestDTO));
    }
}
