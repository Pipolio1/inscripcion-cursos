package com.educativa.inscripcion_cursos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponseDTO {

    private Long id;
    private String nombre;
    private String instructor;
    private Integer duracion;
    private BigDecimal costo;
}
