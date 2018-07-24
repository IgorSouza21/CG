package br.ufrpe.cg.beans;

import java.util.ArrayList;

public class Ponto {
	public double x;
	public double y;
	public double z;
	public ArrayList<Integer> triangulos = new ArrayList<>();
	public Vetor normal;
	
	public Ponto() {
		
	}
	
	public Ponto(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		triangulos = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Ponto3D [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ponto other = (Ponto) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
	public Ponto copy() {
		Ponto v = new Ponto();
		v.x = x;
		v.y = y;
		v.z = z;
		v.normal = normal;
		v.triangulos = triangulos;
		
		return v;
	}

}
