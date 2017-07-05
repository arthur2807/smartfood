package com.example.arthur.smartfood;

import java.io.Serializable;

/**
 * Created by Marcos on 29/06/2017.
 */

public class Turma implements Serializable {
    long    cd_turma;
    String  dc_turma;
    Curso cd_curso;
    String  dc_horario_turma;
    boolean  favorito;


    // Construtor usado no banco
    public Turma(long cd_turma, String dc_turma, Curso cd_curso, String dc_horario_turma) {
        this(dc_turma,cd_curso, dc_horario_turma);
        this.cd_turma = cd_turma;
    }
    // Construtor usado no parser
    public Turma(String dc_turma, Curso cd_curso, String dc_horario_turma) {
        this.dc_turma = dc_turma;
        this.cd_curso = cd_curso;
        this.dc_horario_turma = dc_horario_turma;
    }
}
