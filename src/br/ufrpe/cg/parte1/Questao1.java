package br.ufrpe.cg.parte1;

import br.ufrpe.cg.beans.Matriz;
import br.ufrpe.cg.beans.Ponto;
import br.ufrpe.cg.beans.Vetor;

public class Questao1 {
	
	public static String executar() {
		String res = "";
		for(int i = 0; i < 8; i++) {
			res += menu(i);
		}
		
		return res;
	}
	
	
	private static String printString(String s) {
		return "=======letra " + s + "==========\n"; 
		
	}
	
	private static String print() {
		return "\n========================\n";
	}
	
	
	private static String menu(int i) {
		String a = "ABCDEFGH";
		String v = printString("" + a.charAt(i));
		switch(i) {
			case 0:
				v += Questao1.letraA();
				v += print();
				break;
			case 1:
				v += Questao1.letraB();
				v += print();
				break;
			case 2:
				v += Questao1.letraC();
				v += print();
				break;
			case 3:
				v += Questao1.letraD();
				v += print();
				break;
			case 4:
				v += Questao1.letraE();
				v += print();
				break;
			case 5:
				v += Questao1.letraF();
				v += print();
				break;
			case 6:
				v += Questao1.letraG();
				v += print();
				break;
			case 7:
				v += Questao1.letraH();
				v += print();
		}
		
		return v;
	}
	
	public static String letraA() {
		Matriz a = new Matriz(2, 3);
		a.setIJ(0, 0, 1.5);
		a.setIJ(0, 1, 2.5);
		a.setIJ(0, 2, 3.5);
		a.setIJ(1, 0, 4.5);
		a.setIJ(1, 1, 5.5);
		a.setIJ(1, 2, 6.5);
		
		Matriz b = new Matriz(3, 2);
		b.setIJ(0, 0, 7.5);
		b.setIJ(0, 1, 8.5);
		b.setIJ(1, 0, 9.5);
		b.setIJ(1, 1, 10.5);
		b.setIJ(2, 0, 11.5);
		b.setIJ(2, 1, 12.5);
		
		Matriz c = Operacoes.multiplicar(a, b);
		
		String res = "Matriz\n";
		res += c;
		
		return res;
	}
	
	public static String letraB() {
		Ponto a = new Ponto();
		a.x = 3.5;
		a.y = 1.5;
		a.z = 2.0;
		
		Ponto b = new Ponto();
		b.x = 1.0;
		b.y = 2.0;
		b.z = 1.5;
		
		Vetor c = Operacoes.subtrairPontos(a, b);
		
		return "Vetor = " + c;
	}
	
	public static String letraC() {
		Vetor a = new Vetor();
		a.x = 3.5;
		a.y = 1.5;
		a.z = 2.0;
		
		Vetor b = new Vetor();
		b.x = 1.0;
		b.y = 2.0;
		b.z = 1.5;
		
		double c = Operacoes.produtoEscalar(a, b);
		
		return "Produto Escalar = " + c;
	}
	
	public static String letraD() {
		Vetor a = new Vetor();
		a.x = 3.5;
		a.y = 1.5;
		a.z = 2.0;
		
		Vetor b = new Vetor();
		b.x = 1.0;
		b.y = 2.0;
		b.z = 1.5;
		
		Vetor c = Operacoes.produtoVetorial(a, b);
		
		return "Vetor = " + c;
	}
	
	public static String letraE() {
		Vetor a = new Vetor();
		a.x = 3.5;
		a.y = 1.5;
		a.z = 2.0;
		
		double b = Operacoes.norma(a);
		
		return "Norma = " + b;
	}
	
	public static String letraF() {
		Vetor a = new Vetor();
		a.x = 3.5;
		a.y = 1.5;
		a.z = 2.0;
		
		Vetor b = Operacoes.normalizar(a);
		
		return "Vetor = " + b;
	}
	
	public static String letraG() {
		Ponto p = new Ponto();
		p.x = -0.25;
		p.y = 0.75;
		
		Ponto a = new Ponto();
		a.x = -1.0;
		a.y = 1.0;
		
		Ponto b = new Ponto();
		b.x = 0.0;
		b.y = -1.0;
		
		Ponto c = new Ponto();
		c.x = 1.0;
		c.y = 1.0;
		
		
		double[] v = Operacoes.coordenadaBaricentrica(p, a, b, c);
		
		String res = "coordenadas = (";
		for(int i = 0; i < v.length; i++) {
			if(i != v.length-1)
				res += v[i] + ", ";
			else
				res += v[i];
			
		}
		res += ")";
		
		return res;
	}

	public static String letraH() {
		double[] bari = new double[3];
		bari[0] = 0.5;
		bari[1] = 0.25;
		bari[2] = 0.25;
		
		Ponto a = new Ponto();
		a.x = -1.0;
		a.y = 1.0;
		
		Ponto b = new Ponto();
		b.x = 0.0;
		b.y = -1.0;
		
		Ponto c = new Ponto();
		c.x = 1.0;
		c.y = 1.0;
		
		Ponto v = Operacoes.coordenadaCartesianaBaricentrica(bari, a, b, c);
		
		return "Ponto = " + v;
	}
}
