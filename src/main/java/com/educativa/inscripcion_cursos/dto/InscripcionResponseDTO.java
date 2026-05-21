package com.educativa.inscripcion_cursos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionResponseDTO {

    private Long inscripcionId;
    private String nombreEstudiante;
    private String emailEstudiante;
    private LocalDateTime fechaInscripcion;
    private List<CursoInscripcionDTO> cursos;
    private BigDecimal total;
}
