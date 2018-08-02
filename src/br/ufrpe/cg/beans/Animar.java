package br.ufrpe.cg.beans;

import br.ufrpe.cg.TelaController;
import br.ufrpe.cg.parte1.Operacoes;

public class Animar implements Runnable{
	
	public static boolean parar;
	public static int rotacao;
	public static double angulo;
	public static int velocidade;
	private static Matriz a;

	@Override
	public void run() {
		while(parar) {
			try {
				Thread.sleep(velocidade);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				Operacoes.fazTudo(TelaController.width, TelaController.height, TelaController.carregada, a);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(rotacao == 0)
				setRotacaoX(angulo);
			else if(rotacao == 1)
				setRotacaoY(angulo);
			else
				setRotacaoZ(angulo);
			angulo = angulo + 10;
			angulo = angulo%360;
		}
		
	}
	
	public void setRotacaoX(double rotacao) {
		a = Operacoes.matrizRotacaoX(Math.toRadians(rotacao));
	}
	
	public void setRotacaoY(double rotacao) {
		a = Operacoes.matrizRotacaoY(Math.toRadians(rotacao));
	}
	
	public void setRotacaoZ(double rotacao) {
		a = Operacoes.matrizRotacaoZ(Math.toRadians(rotacao));
	}
	
	

}
