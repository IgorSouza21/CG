package br.ufrpe.cg.beans;

import br.ufrpe.cg.TelaController;
import br.ufrpe.cg.parte1.Operacoes;

public class Animar implements Runnable{
	
	public static boolean parar;
	public static double angulo;
	public static int rotacao;
	public static int velocidade;

	@Override
	public void run() {
		while(parar) {
			try {
				Thread.sleep(velocidade);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Operacoes.fazTudo(TelaController.width, TelaController.height, TelaController.carregada, angulo, rotacao, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			angulo = angulo + 5;
			angulo = angulo%360;
		}
		
	}

}
