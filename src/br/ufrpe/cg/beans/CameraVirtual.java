package br.ufrpe.cg.beans;

import br.ufrpe.cg.parte1.Operacoes;

public class CameraVirtual {
	public static Ponto C;
	public static Vetor N;
	public static Vetor V;
	public static double D;
	public static double Hx;
	public static double Hy;

	public CameraVirtual() {
		C = new Ponto();
		N = new Vetor();
		V = new Vetor();
	}
	
	public CameraVirtual(Ponto c, Vetor n, Vetor v, double d, double hx, double hy) {
		C = c;
		N = n;
		V = v;
		D = d;
		Hx = hx;
		Hy = hy;
	}
	
	public static CameraVirtual getCamera() {
		return new CameraVirtual(C, N, V, D, Hx, Hy);
	}
	
	public String toString() {
		String res = "N = " + N + "\n" + "V = " + V + "\n";
		res += "U = " + Operacoes.obterU(N, V) + "\n" + "d = " + D + "\n";
		res += "hx = " + Hx + "\n" + "hy = " + Hy;
		
		return res;
	}
	
	
	
	

}
