package com.example.arthur.smartfood;

import java.io.Serializable;

public class Produto implements Serializable{
String nome;
String imagem;
long id;
boolean favorito;


// Construtor usado no banco
public Produto(long id, String nome,String imagem) {
	this(nome, imagem);
	this.id = id;
	
}
// Construtor usado no parser
public Produto(String nome,  String imagem) {
	this.nome = nome;
	this.imagem = imagem;
}

@Override
public String toString() {
	return nome;
}}
