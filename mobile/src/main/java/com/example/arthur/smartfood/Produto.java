package com.example.arthur.smartfood;

import java.io.Serializable;

public class Produto implements Serializable{
String nome;
String imagem;
String descricao;
long id;
boolean favorito;
// Construtor usado no banco
public Produto(long id, String nome,  String descricao,String imagem) {
	this(nome, descricao, imagem);
	this.id = id;
	
}
// Construtor usado no parser
public Produto(String nome,  String imagem, String descricao) {
	
	this.nome = nome;
	this.imagem = imagem;
	this.descricao = descricao;
}

@Override
public String toString() {
	return nome;

}}
