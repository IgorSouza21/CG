package br.ufrpe.cg.beans;

public class Iluminacao {
	public static Vetor Iamb;
	public static double Ka;
	public static Vetor Il;
	public static Ponto Pl;
	public static Vetor Kd;
	public static Vetor Od;
	public static double Ks;
	public static double Eta;
	
	public Iluminacao() {
		Iamb = new Vetor();
		Il = new Vetor();
		Pl = new Ponto();
		Kd = new Vetor();
		Od = new Vetor();
	}
	
	public Iluminacao(Vetor iamb, double ka, Vetor il, Ponto pl, Vetor kd, Vetor od, double ks, double eta) {
		Iamb = iamb;
		Ka = ka;
		Il = il;
		Pl = pl;
		Kd = kd;
		Od = od;
		Ks = ks;
		Eta = eta;
	}
	
	public static Iluminacao getIluminacao() {
		return new Iluminacao(Iamb, Ka, Il, Pl, Kd, Od, Ks, Eta);
	}
	
	
}
