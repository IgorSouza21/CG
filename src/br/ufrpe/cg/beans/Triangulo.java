package br.ufrpe.cg.beans;

import br.ufrpe.cg.parte1.Operacoes;

public class Triangulo {
	public Ponto v1;
	public Ponto v2;
	public Ponto v3;
	public Vetor normal;
	public Ponto p1Original;
	public Ponto p2Original;
	public Ponto p3Original;
	
	public Triangulo() {
		normal = new Vetor();
	}
	
	public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
		v1 = p1;
		v2 = p2;
		v3 = p3;
	}
	
	public double calcularBaricentro() {
		return (v1.z + v2.z + v3.z) / 3;
	}
	
	@Override
	public String toString() {
		return "Triangulo [v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
	}

	public void normalTriangulo() {
		Vetor temp = Operacoes.subtrairPontos(v2, v1);
		Vetor temp1 = Operacoes.subtrairPontos(v3, v1);
		normal = Operacoes.normalizar(Operacoes.produtoVetorial(temp, temp1));
	}
	
	public Triangulo copy() {
		Triangulo t = new Triangulo();
		t.v1 = v1.copy();
		t.v2 = v2.copy();
		t.v3 = v3.copy();
		t.normal = normal;
		t.p1Original = p1Original.copy();
		t.p2Original = p2Original.copy();
		t.p3Original = p3Original.copy();
		
		return t;
	}

}
