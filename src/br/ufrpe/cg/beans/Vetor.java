package br.ufrpe.cg.beans;

public class Vetor {
	public double x;
	public double y;
	public double z;

	public Vetor() {
		
	}
	
	public Vetor(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getNorma() {
		double norma = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);	
		norma = Math.sqrt(norma);
		
		return norma;
	}
	
	public Vetor multiplicarPorEscalar(double k) {
		Vetor a = new Vetor(x, y, z);
		a.x = a.x * k;
		a.y = a.y * k;
		a.z = a.z * k;
		
		return a;
	}

	@Override
	public String toString() {
		return "Vetor [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	
	
	

}
