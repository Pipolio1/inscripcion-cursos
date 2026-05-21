package com.educativa.inscripcion_cursos.controller;

import com.educativa.inscripcion_cursos.dto.CursoRequestDTO;
import com.educativa.inscripcion_cursos.dto.CursoResponseDTO;
import com.educativa.inscripcion_cursos.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursosDisponibles());
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> crearCurso(@Valid @RequestBody CursoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.crearCurso(requestDTO));
    }
}
