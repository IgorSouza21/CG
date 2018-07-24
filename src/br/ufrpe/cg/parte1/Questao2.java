package br.ufrpe.cg.parte1;

import java.io.IOException;

import br.ufrpe.cg.beans.Ponto;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Questao2 {

	private static Ponto projecoes[];
	private static Ponto normais[];
	
	public static void executar(String s, double WIDTH, double HEIGHT, int tamPixel, Canvas c) throws IOException{
		Operacoes.carregarPontosTriangulos(s);
		pegarProjecoes();
		double[] minMax = pegarMinMax();
		normalizarProjecoes(minMax, WIDTH, HEIGHT, tamPixel);
		pinta(c);
	}
	
	private static void pinta(Canvas c) {
		for(int i = 0; i < getNormais().length; i++) {
			Operacoes.paint(c.getGraphicsContext2D(),(int) Questao2.getNormais()[i].x, 
					(int) Questao2.getNormais()[i].y, Color.WHITE);	
		}
	}
	
	private static void pegarProjecoes() {
		projecoes = new Ponto[Operacoes.pontos.length];
		for(int i = 0; i < projecoes.length; i++) {
			projecoes[i] = new Ponto();
			projecoes[i].x = Operacoes.pontos[i].x;
			projecoes[i].y = Operacoes.pontos[i].y;
		}
	}
	
	private static double[] pegarMinMax() {
		double xmin = projecoes[0].x;
		double ymin = projecoes[0].y;
		double xmax = projecoes[0].x;
		double ymax = projecoes[0].y;
		
		for(int i = 1; i < projecoes.length; i++) {
			if(projecoes[i].x > xmax) {
				xmax = projecoes[i].x;
			}
			if(projecoes[i].y > ymax) {
				ymax = projecoes[i].y;
			}
			if(projecoes[i].x < xmin) {
				xmin = projecoes[i].x;
			}
			if(projecoes[i].y < ymin) {
				ymin = projecoes[i].y;
			}
		}
		
		double[] res = new double[4];
		res[0] = xmax;
		res[1] = ymax;
		res[2] = xmin;
		res[3] = ymin;
		
		return res;
		
	}
	
	private static void normalizarProjecoes(double[] minMax, double WIDTH, double HEIGHT, int tamPixel) {
		double xmaxMin = minMax[0] - minMax[2];
		double ymaxMin = minMax[1] - minMax[3];
		
		normais = new Ponto[projecoes.length];
		for(int i = 0; i < normais.length; i++) {
			normais[i] = new Ponto();
			double v = ((projecoes[i].x - minMax[2])/ xmaxMin) * (WIDTH - tamPixel);
			normais[i].x = v;
			v = ((projecoes[i].y - minMax[3])/ ymaxMin) * (HEIGHT - tamPixel);
			normais[i].y = v;
		}
	}

	public static Ponto[] getProjecoes() {
		return projecoes;
	}

	public static Ponto[] getNormais() {
		return normais;
	}
}
