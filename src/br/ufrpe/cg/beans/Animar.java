package br.ufrpe.cg.beans;

import br.ufrpe.cg.TelaController;
import br.ufrpe.cg.parte1.Operacoes;

public class Animar implements Runnable{
	
	public static boolean parar;
	public static double angulo;

	@Override
	public void run() {
		while(parar) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Operacoes.fazTudo(TelaController.width, TelaController.height, TelaController.carregada, angulo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			angulo = angulo + 5;
			if(angulo >= 360) {
				angulo = 0;
			}
		}
		
	}

}
