package com.educativa.inscripcion_cursos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InscripcionRequestDTO {

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String nombreEstudiante;

    @NotBlank(message = "El email del estudiante es obligatorio")
    private String emailEstudiante;

    @NotNull(message = "Debe seleccionar al menos un curso")
    @NotEmpty(message = "Debe seleccionar al menos un curso")
    private List<Long> cursoIds;
}
