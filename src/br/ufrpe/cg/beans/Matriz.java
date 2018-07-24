package br.ufrpe.cg.beans;

public class Matriz {
	private double matriz[][];
	private double transposta[][];
	private double inversa[][];
	private double cofatores[][];
	private Double det;
	

	public Matriz(int linha, int coluna) {
		matriz = new double[linha][coluna];
	}
	
	public Matriz(double[][] matriz) {
		this.matriz = matriz;
	}
	
	public double[][] getMatriz() {
		return matriz;
	}
	
	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}
	
	public double getIJ(int i, int j) {
		return matriz[i][j];
	}
	
	public void setIJ(int i, int j, double num) {
		this.matriz[i][j] = num;
	}
	
	public int getNumLinhas() {
		return this.matriz.length;
	}
	
	public int getNumColunas() {
		return this.matriz[0].length;
	}
	
	public double[][] getTransposta() {
		if(transposta == null) {
			transposta = new double[getNumColunas()][getNumLinhas()];
			for(int i = 0; i < getNumLinhas(); i++) {
				for(int j = 0; j < getNumColunas(); j++) {
					transposta[j][i] = this.matriz[i][j];
				}
			}
		}
		
		return transposta;
	}
	
	public double getDeterminante() {
		Double det = null;
		if(det == null) {
			if(matriz.length == 1) {
				det = matriz[0][0];
			}
			else if(matriz.length == 2) {
				det = matriz[0][0]*matriz[1][1] - (matriz[0][1]*matriz[1][0]);
			}
			else if(matriz.length == 3) {
				det = ((matriz[0][0]*matriz[1][1]*matriz[2][2] + matriz[0][1]*matriz[1][2]*matriz[2][0] + matriz[0][2]*matriz[1][0]*matriz[2][1])
						- 
					  (matriz[0][2]*matriz[1][1]*matriz[2][0] + matriz[0][0]*matriz[1][2]*matriz[2][1] + matriz[0][1]*matriz[1][0]*matriz[2][2]));
			}
			else {
				det = 0.0;
				double[][] aux;
                int i_aux, j_aux, linha, coluna, i;            
 
                for(i = 0; i < matriz.length; i++){
 
                    if(matriz[0][i] != 0){
                        aux = new double[matriz.length - 1][matriz.length - 1];
                        i_aux = 0;
                        j_aux = 0;
 
                        for(linha = 1; linha < matriz.length; linha++){
                            for(coluna = 0; coluna < matriz.length; coluna++){
                                if(coluna != i){
                                    aux[i_aux][j_aux] = matriz[linha][coluna];
                                    j_aux++;
                                }
                            }
 
                            i_aux++;
                            j_aux = 0;
                        }
                        Matriz a = new Matriz(aux);
                        det += Math.pow(-1, i)*matriz[0][i]*a.getDeterminante();
                    }
 
                }
            }
		}
		
		return det;
	}
	
	public double[][] getCofatores() {
		if(cofatores == null) {
		
			cofatores = new double[matriz.length][matriz.length];
	
			for(int i = 0; i < matriz.length; i++) {
				for(int j = 0; j < matriz.length; j++) {
					double[][] aux = new double[matriz.length-1][matriz.length-1];
					int i_aux = 0, j_aux = 0;
					boolean ok = false;
					
					for(int linha = 0; linha < matriz.length; linha++) {
						for(int coluna = 0; coluna < matriz.length; coluna++) {
							if(linha != i && coluna != j) {
								aux[i_aux][j_aux] =  matriz[linha][coluna];
								j_aux++;
								ok = true;
							}
						}
						if(ok) {
							i_aux++;
							j_aux = 0;
							ok = false;
						}
						
					}
					Matriz a = new Matriz(aux);
					double cofator = Math.pow(-1, (i+1)+(j+1))*a.getDeterminante(); 
					if(cofator == -0)
						cofatores[i][j] = 0;
					else
						cofatores[i][j] = cofator;
				}
			}
			
		}
		return cofatores;
    }
	
	public double[][] getInversa() {
		if(inversa == null) {
			if(getNumLinhas() == getNumColunas()) {
				det = getDeterminante();
				if(det != 0) { 
					inversa = new double[getNumLinhas()][getNumColunas()];
					if(matriz.length == 2) {
						double valor = matriz[1][1]/det;
						
						if(valor == -0)
							inversa[0][0] = 0;
						else
							inversa[0][0] = matriz[1][1]/det;
						
						valor = matriz[0][0]/det;
						if(valor == -0)
							inversa[1][1] = 0;
						else
							inversa[1][1] = matriz[0][0]/det;
						
						valor = -matriz[0][1]/det;
						if(valor == -0)
							inversa[0][1] = 0;
						else
							inversa[0][1] = -matriz[0][1]/det;
						
						valor = -matriz[1][0]/det;
						if(valor == -0)
							inversa[1][0] = 0;
						else
							inversa[1][0] = -matriz[1][0]/det;	
					}
					else if(matriz.length == 3) {
						Matriz cofatores = new Matriz(getCofatores());
						Matriz transposta = new Matriz(cofatores.getTransposta());
						
						for(int i = 0; i <  inversa.length; i++) {
							for(int j = 0; j < inversa.length; j++) {
								double valor = (1/det)*transposta.getIJ(i, j);
								if(valor == -0)
									inversa[i][j] = 0;
								else
									inversa[i][j] = valor;
							}
						}
					}
				}
			}
			
		}
		
		return inversa;
	}
	
	public String toString() {
		String res = "";
		
		for(int i = 0; i < getNumLinhas(); i++) {
			for(int j = 0; j < getNumColunas(); j++) {
				res += getIJ(i, j) + "\t";
			}
			res += "\n";
		}
		
		return res;
	}

}
