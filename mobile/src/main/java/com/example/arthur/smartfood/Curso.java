package com.example.arthur.smartfood;

import java.io.Serializable;

public class Curso implements Serializable{

	String dc_curso;
	long cd_curso;
	String imglink;
boolean favorito;
// Construtor usado no banco
public Curso(long cd_curso, String dc_curso, String imglink) {
	this( dc_curso, imglink);
	this.cd_curso = cd_curso;
}


// Construtor usado no parser
public Curso(String dc_curso, String imglink) {
	
	this.dc_curso = dc_curso;
	this.imglink = imglink;

}

@Override
public String toString() {
	return dc_curso;

}}
