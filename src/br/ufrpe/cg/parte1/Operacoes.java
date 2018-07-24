package br.ufrpe.cg.parte1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.ufrpe.cg.beans.CameraVirtual;
import br.ufrpe.cg.beans.Iluminacao;
import br.ufrpe.cg.beans.Matriz;
import br.ufrpe.cg.beans.Ponto;
import br.ufrpe.cg.beans.Triangulo;
import br.ufrpe.cg.beans.Vetor;
import br.ufrpe.cg.beans.Z;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Operacoes {
	public static Ponto[] pontos;
	public static Triangulo[] triangulos;
	public static Triangulo[] triangulosVista;
	public static Z[][] zBuffer;
	public static Canvas canvas;

	private static double mul(double[] linha, double[] coluna) {
		double res = 0;
		
		for(int i = 0; i < linha.length; i++) {
			res += linha[i]*coluna[i];
		}
		
		return res;
	}
	
	public static Matriz multiplicar(Matriz A, Matriz B) {
		Matriz c = null;
		if(A.getNumColunas() == B.getNumLinhas()) {
			
			c = new Matriz(A.getNumLinhas(), B.getNumColunas());
			int k = 0;
			int l = 0;
			
			for(int i = 0; i < c.getNumLinhas(); i++) {
				for(int j = 0; j < c.getNumColunas(); j++) {
					c.setIJ(i, j, mul(A.getMatriz()[k], B.getTransposta()[l]));
					l++;
				}
				k++;
				l = 0;
			}
		}
		
		return c;
	}
	
	public static Vetor subtrairPontos(Ponto a, Ponto b) {
		Vetor c = new Vetor();
		c.x = b.x - a.x;
		c.y = b.y - a.y;
		c.z = b.z - a.z;
				
		return c;
	}
	
	public static double produtoEscalar(Vetor a, Vetor b) {
		double res = a.x*b.x + a.y*b.y + a.z*b.z;
		
		return res;
	}
	
	public static Vetor produtoVetorial(Vetor a, Vetor b) {
		Vetor c = new Vetor();
		c.x = a.y*b.z - a.z*b.y;
		c.y = a.z*b.x - a.x*b.z;
		c.z = a.x*b.y - a.y*b.x;
		
		return c;
	}
	
	public static double norma(Vetor a) {
		return a.getNorma();
	}
	
	public static Vetor normalizar(Vetor a) {
		Vetor n = new Vetor();
		n.x = a.x/a.getNorma();
		n.y = a.y/a.getNorma();
		n.z = a.z/a.getNorma();
				
		return n;
	}
	
	public static double[] coordenadaBaricentrica(Ponto p, Ponto a, Ponto b, Ponto c) {
		Matriz m1 = new Matriz(2,2);
		
		m1.setIJ(0, 0, a.x - c.x);
		m1.setIJ(0, 1, b.x - c.x);
		m1.setIJ(1, 0, a.y - c.y);
		m1.setIJ(1, 1, b.y - c.y);
		
		Matriz m2 = new Matriz(2, 1);
		
		m2.setIJ(0, 0, p.x - c.x);
		m2.setIJ(1, 0, p.y - c.y);
		
		Matriz alfaBeta = multiplicar(new Matriz(m1.getInversa()), m2);
		
		double alfa = alfaBeta.getIJ(0, 0);
		double beta = alfaBeta.getIJ(1, 0);
		double gama = 1 - alfa - beta;
		
		double[] res = new double[3];
		res[0] = alfa;
		res[1] = beta;
		res[2] = gama;
		
		return res;
	}
	
	public static Ponto coordenadaCartesianaBaricentrica(double[] coordBaricentrica, Ponto a, Ponto b, Ponto c) {
		Ponto cartesiana = new Ponto();
		
		cartesiana.x = a.x*coordBaricentrica[0] + b.x*coordBaricentrica[1] + c.x*coordBaricentrica[2];
		cartesiana.y = a.y*coordBaricentrica[0] + b.y*coordBaricentrica[1] + c.y*coordBaricentrica[2];
		cartesiana.z = a.z*coordBaricentrica[0] + b.z*coordBaricentrica[1] + c.z*coordBaricentrica[2];
		
		return cartesiana;
	}
	
	public static void carregarPontosTriangulos(String s) throws IOException {
			BufferedReader reader = new BufferedReader(new FileReader(s));
			String linha = reader.readLine();
			Ponto[] p;
			Triangulo[] t;
			String nk[] = linha.split(" ");
			p = new Ponto[Integer.parseInt(nk[0])];
			t = new Triangulo[Integer.parseInt(nk[1])];
			for(int i = 0; i < p.length;i++) {
				linha = reader.readLine();
				nk = linha.split(" ");
				p[i] = new Ponto();
				p[i].x = Double.parseDouble(nk[0]);
				p[i].y = Double.parseDouble(nk[1]);
				p[i].z = Double.parseDouble(nk[2]);
			}
			for(int i = 0; i < t.length;i++) {
				linha = reader.readLine();
				nk = linha.split(" ");
				t[i] = new Triangulo(p[Integer.parseInt(nk[0])-1], p[Integer.parseInt(nk[1])-1], p[Integer.parseInt(nk[2])-1]);
				p[Integer.parseInt(nk[0])-1].triangulos.add(i);
				p[Integer.parseInt(nk[1])-1].triangulos.add(i);
				p[Integer.parseInt(nk[2])-1].triangulos.add(i);
				t[i].p1Original = p[Integer.parseInt(nk[0])-1];
				t[i].p2Original = p[Integer.parseInt(nk[1])-1];
				t[i].p3Original = p[Integer.parseInt(nk[2])-1];
			}
			
			reader.close();
			pontos = p;
			triangulos = t;
	}
	
	public static Vetor obterU(Vetor N, Vetor V) {
		Vetor U = null;
		if(N != null && V != null) {
			U = Operacoes.produtoVetorial(N, V);
		}
		
		return U;
	}
	
	public static void ortogonalizarV(Vetor V, Vetor N) {
		Vetor N1 = new Vetor();
		N1.x = N.x;
		N1.y = N.y;
		N1.z = N.z;
		double a = Operacoes.produtoEscalar(V,N1) / Operacoes.produtoEscalar(N1,N1);
		
		Vetor temp = N1.multiplicarPorEscalar(a);
		
		V.x = V.x - temp.x;
		V.y = V.y - temp.y;
		V.z = V.z - temp.z;
		
	}
	
	public static Ponto getCoordenadasVista(Vetor U, Vetor V, Vetor N, Ponto m) {
		
		Matriz A = new Matriz(3,3);
		A.setIJ(0, 0, U.x);
		A.setIJ(0, 1, U.y);
		A.setIJ(0, 2, U.z);
		A.setIJ(1, 0, V.x);
		A.setIJ(1, 1, V.y);
		A.setIJ(1, 2, V.z);
		A.setIJ(2, 0, N.x);
		A.setIJ(2, 1, N.y);
		A.setIJ(2, 2, N.z);
		
		Vetor temp = Operacoes.subtrairPontos(m, CameraVirtual.C);
		Matriz B = new Matriz(3, 1);
		B.setIJ(0, 0, temp.x);
		B.setIJ(1, 0, temp.y);
		B.setIJ(2, 0, temp.z);
		
		Matriz v = Operacoes.multiplicar(A, B);
		
		Ponto f = new Ponto();
		f.x = v.getIJ(0, 0);
		f.y = v.getIJ(1, 0);
		f.z = v.getIJ(2, 0);
		
		return f;
		
	}
	
	public static Ponto getProjecaoPerspectiva(Ponto p) {
		Ponto f = new Ponto();
		
		f.x = CameraVirtual.D*(p.x/p.z);
		f.y = CameraVirtual.D*(p.y/p.z);
		
		return f;
	}
	
	public static Ponto getCoordenadasNormalizadas(Ponto p) {
		
		p.x = p.x/CameraVirtual.Hx;
		p.y = p.y/CameraVirtual.Hy;
		
		return p;
		
	}
	
	public static Ponto getCoordenadasTela(double resX, double resY, Ponto p) {
		double temp1 = Math.floor(((p.x + 1)/2)*resX + 0.5);
		double temp2 = Math.floor(resY - (((p.y + 1)/2)*resY + 0.5));
		p.x = temp1;
		p.y = temp2;
		
		return p;
	}
	
	public static void scanLine(Triangulo t, int k) {
		Ponto[] vs = new Ponto[]{t.v1,t.v2,t.v3};
		quickSort(vs, 0, 2, 0);
		t.v1 = vs[0];
		t.v2 = vs[1];
		t.v3 = vs[2];
		System.out.println("fez quicksort");
		if(t.v2.y == t.v3.y) {
			preencherTrianguloSuperior(t, t, k);
		}
		else if(t.v1.y == t.v2.y) {
			preencherTrianguloInferior(t, t, k);
		}
		else {
			Ponto p = new Ponto();
			p.x = Math.floor((t.v1.x + ((t.v2.y - t.v1.y) / (t.v3.y - t.v1.y)) * (t.v3.x - t.v1.x) + 0.5));
			p.y = t.v2.y;
			Triangulo um, dois;
			if(k == 0) {
				um = new Triangulo();
				um.v1 = t.v1;
				um.v2 = t.v2;
			}
			else
				um = t.copy();
			um.v3 = p;
			if(k == 0) {
				dois = new Triangulo();
				dois.v1 = t.v2;
				dois.v3 = t.v3;
			}
			else
				dois = t.copy();
			
			dois.v2 = p;
			
 
			preencherTrianguloSuperior(um, t, k);
			preencherTrianguloInferior(dois, t, k);
		}
	}
	
	private static void preencherTrianguloInferior(Triangulo t, Triangulo maior, int k) {
		double a1, a2;
		if (t.v1.x < t.v2.x) {
			a1 = (t.v3.x - t.v1.x) / (t.v3.y - t.v1.y);
			a2 = (t.v3.x - t.v2.x) / (t.v3.y - t.v2.y);
		} else {
			a1 = (t.v3.x - t.v2.x) / (t.v3.y - t.v2.y);
			a2 = (t.v3.x - t.v1.x) / (t.v3.y - t.v1.y);
		}
		
		double xMin = t.v3.x;
		double xMax = t.v3.x;
		
		for(int yScan = (int) t.v3.y; yScan >= t.v1.y; yScan--) {
			for(int x = (int) xMin; x <= (int) xMax; x++) {
				if(k == 0) {
					paint(canvas.getGraphicsContext2D(), x, yScan, Color.WHITE);
				}
				else
					calcularCor(x, yScan, maior);
			}
			xMin -= a1;
			xMax -= a2;
		}
			
	}
	
	private static void preencherTrianguloSuperior(Triangulo t, Triangulo maior, int k) {
		double a1, a2;
		if (t.v2.x < t.v3.x) {
			a1 = (t.v2.x - t.v1.x) / (t.v2.y - t.v1.y);
			a2 = (t.v3.x - t.v1.x) / (t.v3.y - t.v1.y);
		} else {
			a1 = (t.v3.x - t.v1.x) / (t.v3.y - t.v1.y);
			a2 = (t.v2.x - t.v1.x) / (t.v2.y - t.v1.y);
		}

		double xMin = t.v1.x;
		double xMax = t.v1.x;
		for(int yScan = (int) t.v1.y; yScan <= t.v2.y; yScan++) { 
			for(int x = (int) xMin; x <= (int) xMax; x++) {
				if(k == 0) {
					paint(canvas.getGraphicsContext2D(), x, yScan, Color.WHITE);
				}
				else
					calcularCor(x, yScan, maior);
			}
			xMin += a1;
			xMax += a2;
		}
	}
	
	private static int particionar1(Object[] a, int p, int r){
		Ponto[] A;
		if(a instanceof Ponto[]) {
			A = (Ponto[]) a;
			int i = p - 1;
			double y = A[r].y;
			int j;
			for(j = p; j <= r-1; j++){
				if(A[j].y <= y){
					i++;
					Ponto temp;
					temp = A[i];
					A[i] = A[j];
					A[j] = temp;
				}
			}
			Ponto temp;
			temp = A[i+1];
			A[i+1] = A[r];
			A[r] = temp;
	
			return i+1;
		}
		
		return -1;
	}
	
	private static int particionar2(Object[] a, int p, int r){
		Triangulo[] A;
		if(a instanceof Triangulo[]) {
			A = (Triangulo[]) a;
			int i = p - 1;
			double z = A[r].calcularBaricentro();
			int j;
			for(j = p; j <= r-1; j++){
				if(A[j].calcularBaricentro() <= z){
					i++;
					Triangulo temp;
					temp = A[i];
					A[i] = A[j];
					A[j] = temp;
				}
			}
			Triangulo temp;
			temp = A[i+1];
			A[i+1] = A[r];
			A[r] = temp;
	
			return i+1;
		}
		
		return -1;
	}

	public static void quickSort(Object[] A, int p, int r, int part){
		int q = -1;
		if(p < r){
			if(part == 0)
				q = particionar1(A, p, r);
			if(part == 1)
				q = particionar2(A, p, r);
			quickSort(A, p, q-1, part);
			quickSort(A, q+1, r, part);
		}
		
	}

	public static Vetor somaVetores(Vetor A, Vetor B) {
		Vetor temp = new Vetor();
		temp.x = A.x + B.x;
		temp.y = A.y + B.y;
		temp.z = A.z + B.z;
			
		return temp;
	}
	
	public static Z[][] inicializarZBuffer(int width, int height) {
		if(zBuffer == null)
			zBuffer = new Z[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				zBuffer[i][j] = new Z();
				zBuffer[i][j].z = Double.NEGATIVE_INFINITY;
				zBuffer[i][j].c = Color.BLACK;
			}
		}
		
		return zBuffer;
		
	}
	
	public static void fazTudo(int width, int height, String s) throws Exception {
		Operacoes.carregarPontosTriangulos(s);
		Operacoes.carregarParametrosCamera();
		Operacoes.carregarParametrosIluminacao();
		
		Operacoes.ortogonalizarV(CameraVirtual.V, CameraVirtual.N);
		Vetor V = Operacoes.normalizar(CameraVirtual.V);
		Vetor N = Operacoes.normalizar(CameraVirtual.N);
		Vetor U = Operacoes.obterU(N, V);
		
		triangulosVista = new Triangulo[triangulos.length];
		for(int i = 0; i < triangulos.length; i++) {
			triangulosVista[i] = triangulos[i].copy();
			triangulosVista[i].v1 = Operacoes.getCoordenadasVista(U, V, N, triangulos[i].v1);
			triangulosVista[i].v2 = Operacoes.getCoordenadasVista(U, V, N, triangulos[i].v2);
			triangulosVista[i].v3 = Operacoes.getCoordenadasVista(U, V, N, triangulos[i].v3);
			triangulosVista[i].normalTriangulo();
		}
		
		for(int i = 0; i < triangulosVista.length; i++) {
			calcularNormaisVertices(triangulosVista[i], triangulosVista);
		}
		
		Operacoes.quickSort(triangulosVista, 0, triangulosVista.length-1, 1);
		
		inicializarZBuffer(width, height);
		
		for (int i = 0; i < triangulosVista.length; i++) {
			coordenadasTela(triangulosVista[i], width, height, 1);
			System.out.println("chegou aqui: " + i);
			Operacoes.scanLine(triangulosVista[i], 1);
			System.out.println("fez scanline");
		}
		pintaZBuffer(canvas.getGraphicsContext2D());
	}
	
	public static Ponto pOriginal(double[] coord, Triangulo t) {

		Ponto r = Operacoes.coordenadaCartesianaBaricentrica(coord, t.p1Original, t.p2Original, t.p3Original);
		Ponto pt = new Ponto();
		pt.x = r.x;
		pt.y = r.y;
		pt.z = r.z;
		return pt;
	}
	
	public static Vetor calculaNormalPelaBaricentrica(Ponto p, Triangulo t, double[] coord) {
		Vetor n1 = new Vetor();
		n1.x = t.p1Original.normal.x;
		n1.y = t.p1Original.normal.y;
		n1.z = t.p1Original.normal.z; 
		n1 = n1.multiplicarPorEscalar(coord[0]);
		
		Vetor n2 = new Vetor();
		n2.x = t.p2Original.normal.x;
		n2.y = t.p2Original.normal.y;
		n2.z = t.p2Original.normal.z; 
		n2 = n2.multiplicarPorEscalar(coord[1]);
		
		Vetor n3 = new Vetor();
		n3.x = t.p3Original.normal.x;
		n3.y = t.p3Original.normal.y;
		n3.z = t.p3Original.normal.z; 
		n3 = n3.multiplicarPorEscalar(coord[2]);

		Vetor temp = Operacoes.somaVetores(n1, n2); 
		temp = Operacoes.somaVetores(temp, n3);
		temp = Operacoes.normalizar(temp);
		
		return temp;
		
	}

	public static Z[][] getZBuffer() {
		return zBuffer;
	}
	
	public static void calcularCor(int x, int y, Triangulo t) {
		double[] coord = Operacoes.coordenadaBaricentrica(new Ponto(x,y,0),	t.v1, t.v2, t.v3);
		
		Ponto p = pOriginal(coord, t);
		if(p.z > zBuffer[x][y].z) {
			Vetor N = calculaNormalPelaBaricentrica(p, t, coord);
			Vetor V = Operacoes.encontrarV(p);
			Vetor L = Operacoes.encontrarL(p);
			Vetor R = Operacoes.encontrarR(N, L);
			Vetor cor = Operacoes.iluminacaoPhong(N, V, L, R);
			zBuffer[x][y].c = Color.color(cor.x, cor.y, cor.z);
		}
	}
	
	public static Vetor calcularNormalVertice(Ponto v,Triangulo[] T) {
		Vetor normal = new Vetor();
		for(int i= 0; i < v.triangulos.size(); i++) {
			normal = Operacoes.somaVetores(normal, T[v.triangulos.get(i)].normal);
		}
		
		normal = Operacoes.normalizar(normal);
		return normal;
		
	}
	
	public static void calcularNormaisVertices(Triangulo t, Triangulo[] T) {
		t.p1Original.normal = calcularNormalVertice(t.p1Original, T);
		t.p2Original.normal = calcularNormalVertice(t.p2Original, T);
		t.p3Original.normal = calcularNormalVertice(t.p3Original, T);
	}
	
	public static Vetor luzAmbiente() {
		Vetor Ia = new Vetor();
		Ia.x = Iluminacao.Iamb.x;
		Ia.y = Iluminacao.Iamb.y;
		Ia.z = Iluminacao.Iamb.z;
		
		return Ia.multiplicarPorEscalar(Iluminacao.Ka);
		
	}
	
	public static Vetor reflexaoDifusa(Vetor N, Vetor V, Vetor L) {
		double cosTeta = Operacoes.produtoEscalar(N, L);
		Vetor temp = new Vetor();
		temp.x = Iluminacao.Kd.x;
		temp.y = Iluminacao.Kd.y;
		temp.z = Iluminacao.Kd.z;
		
		Vetor Id = temp.multiplicarPorEscalar(cosTeta);
		Id = multiplicacaoComponenteComponente(Id, Iluminacao.Od);
		Id = multiplicacaoComponenteComponente(Id, Iluminacao.Il);
		
		return Id;
	}
	
	public static Vetor reflexaoEspecular(Vetor R, Vetor V) {
		double RV = Math.pow(Operacoes.produtoEscalar(R, V), Iluminacao.Eta)*Iluminacao.Ks;
		Vetor temp = new Vetor();
		temp.x = Iluminacao.Il.x;
		temp.y = Iluminacao.Il.y;
		temp.z = Iluminacao.Il.z;
	
		Vetor Is = temp.multiplicarPorEscalar(RV);
		
		return Is;
	}
	
	public static Vetor iluminacaoPhong(Vetor N, Vetor V, Vetor L, Vetor R) {
		Vetor Ia = luzAmbiente();
		
		if(Operacoes.produtoEscalar(N,L) < 0) {
			if(Operacoes.produtoEscalar(V,N) < 0) {
				N.x = -N.x;
				N.y = -N.y;
				N.z = -N.z;
			}
			else {
				if(Ia.x > 255)
					Ia.x = 255;
				if(Ia.y > 255)
					Ia.y = 255;
				if(Ia.z > 255)
					Ia.z = 255;
				return Ia;
			}
		}
		
		Vetor Id = reflexaoDifusa(N, V, L);
	
		if(Operacoes.produtoEscalar(V, R) < 0) {
			Vetor I = new Vetor();
			I = Operacoes.somaVetores(Ia, Id);
			
			if(I.x > 255)
				I.x = 255;
			if(I.y > 255)
				I.y = 255;
			if(I.z > 255)
				I.z = 255;
			return I;
		}
		
		Vetor Is = reflexaoEspecular(R, V);
		
		Vetor I = new Vetor();
		I = Operacoes.somaVetores(Ia, Id);
		I = Operacoes.somaVetores(I, Is);
		
		if(I.x > 255)
			I.x = 255;
		if(I.y > 255)
			I.y = 255;
		if(I.z > 255)
			I.z = 255;
		
		return I;	
	}
	
	private static Vetor multiplicacaoComponenteComponente(Vetor A, Vetor B) {
		Vetor c = new Vetor();
		c.x = A.x * B.x;
		c.y = A.y * B.y;
		c.z = A.z * B.z;
		
		return c;
	}
	
	public static Vetor encontrarL(Ponto p) {
		return Operacoes.normalizar(Operacoes.subtrairPontos(Iluminacao.Pl, p));
		
	}
	
	public static Vetor encontrarV(Ponto p) {
		Vetor v = new Vetor();
		v.x = -p.x;
		v.y = -p.y;
		v.z = -p.z;
		return Operacoes.normalizar(v);
		
	}
	
	public static Vetor encontrarR(Vetor N, Vetor L) {
		double k = 2*Operacoes.produtoEscalar(N, L);
		Vetor temp = new Vetor();
		temp.x = N.x;
		temp.y = N.y;
		temp.z = N.z;
		temp = temp.multiplicarPorEscalar(k);
		
		Vetor R = new Vetor();
		R.x = temp.x - L.x;
		R.y = temp.y - L.y;
		R.z = temp.z - L.z;
		
		return R;
	}
	
	public static void carregarParametrosIluminacao() throws IOException {
		String[] b = new String[8];
		b[0] = "Iamb = ";
		b[1] = "Ka = ";
		b[2] = "Il = ";
		b[3] = "Pl = ";
		b[4] = "Kd = ";
		b[5] = "Od = ";
		b[6] = "Ks = ";
		b[7] = "Eta = ";

		BufferedReader r = new BufferedReader(new FileReader("Iluminacao.txt"));
		Double[] v = new Double[18];
		int k = 0;
		for(int i = 0; i < 8; i++) {
			String linha = r.readLine();
			String x[] = linha.split(b[i]);
			x = x[1].split(" ");
			for(int j = 0; j < 3; j++) {
				v[k] = Double.parseDouble(x[j]);
				k++;
				if(i == 1 || i == 6 || i == 7)
					break;
			}
			
		}
		Vetor x = new Vetor();
		x.x = v[0];
		x.y = v[1];
		x.z = v[2];
		Iluminacao.Iamb = x;
		
		Iluminacao.Ka = v[3];
		
		x.x = v[4];
		x.y = v[5];
		x.z = v[6];
		Iluminacao.Il = x;
		
		Ponto y = new Ponto();
		y.x = v[7];
		y.y = v[8];
		y.z = v[9];
		Iluminacao.Pl = y;
		
		x.x = v[10];
		x.y = v[11];
		x.z = v[12];
		Iluminacao.Kd = x;
		
		x.x = v[13];
		x.y = v[14];
		x.z = v[15];
		Iluminacao.Od = x;
		
		Iluminacao.Ks = v[16];
		Iluminacao.Eta = v[17];
		
		
		r.close();
	}
	
	public static void carregarParametrosCamera() throws IOException {
		String[] b = new String[6];
		b[0] = "N = ";
		b[1] = "V = ";
		b[2] = "d = ";
		b[3] = "hx = ";
		b[4] = "hy = ";
		b[5] = "C = ";

		BufferedReader r = new BufferedReader(new FileReader("Camera.txt"));
		Double[] v = new Double[12];
		int k = 0;
		for(int i = 0; i < 6; i++) {
			String linha = r.readLine();
			String x[] = linha.split(b[i]);
			x = x[1].split(" ");
			for(int j = 0; j < 3; j++) {
				v[k] = Double.parseDouble(x[j]);
				k++;
				if(i == 2 || i == 3 || i == 4)
					break;
			}
			
		}
		Vetor x = new Vetor();
		x.x = v[0];
		x.y = v[1];
		x.z = v[2];
		CameraVirtual.N = x;
		
		x = new Vetor();
		x.x = v[3];
		x.y = v[4];
		x.z = v[5];
		CameraVirtual.V = x;
		
		CameraVirtual.D = v[6];
		CameraVirtual.Hx = v[7];
		CameraVirtual.Hy = v[8];
		
		Ponto y = new Ponto();
		y.x = v[9];
		y.y = v[10];
		y.z = v[11];
		CameraVirtual.C = y;
		
		r.close();
	}
	
	public static void coordenadasTela(Triangulo t, int width, int height, int k) {
		Triangulo c;
		if(k == 0) {
			c = new Triangulo();
			c.v1 = t.v1;
			c.v2 = t.v2;
			c.v3 = t.v3;
		}
		else
			c = t.copy();
		c.v1 = Operacoes.getProjecaoPerspectiva(c.v1);
		c.v2 = Operacoes.getProjecaoPerspectiva(c.v2);
		c.v3 =	Operacoes.getProjecaoPerspectiva(c.v3);
		
		c.v1 = Operacoes.getCoordenadasNormalizadas(c.v1);
		c.v2 = Operacoes.getCoordenadasNormalizadas(c.v2);
		c.v3 =	Operacoes.getCoordenadasNormalizadas(c.v3);
		
		c.v1 = Operacoes.getCoordenadasTela(width, height, c.v1);
		c.v2 = Operacoes.getCoordenadasTela(width, height, c.v2);
		c.v3 = Operacoes.getCoordenadasTela(width, height, c.v3);
		
		t.v1 = c.v1;
		t.v2 = c.v2;
		t.v3 = c.v3;
	}

	public static void pintaZBuffer(GraphicsContext gc) {
		for(int i = 0; i < Operacoes.getZBuffer().length; i++) {
			for(int j = 0; j < Operacoes.getZBuffer().length; j++) {
				paint(gc, i, j, Operacoes.getZBuffer()[i][j].c);
			}
		}
	}
	
	public static void paint(GraphicsContext g, int x, int y, Color c){
		g.setFill(c);
		g.fillRect(x-1, y, 1, 1);
	}

	public static Canvas pintaProjecaoOrtogonal(GraphicsContext gc, int width, int height, String s) throws Exception{
		Operacoes.carregarPontosTriangulos(s);
		Operacoes.carregarParametrosCamera();
	    
	    Operacoes.ortogonalizarV(CameraVirtual.V, CameraVirtual.N);
		Vetor V = Operacoes.normalizar(CameraVirtual.V);
		Vetor N = Operacoes.normalizar(CameraVirtual.N);
		Vetor U = Operacoes.obterU(N, V);
	 
	    Ponto[] pTela = new Ponto[triangulos.length]; 
	 
	    for(int i = 0; i < pTela.length; i++) {
	      pTela[i] = new Ponto();
	      pTela[i] = Operacoes.getCoordenadasVista(U, V, N, pontos[i]);
	      pTela[i] = Operacoes.getProjecaoPerspectiva(pTela[i]);
	      pTela[i] = Operacoes.getCoordenadasNormalizadas(pTela[i]);
	      pTela[i] = Operacoes.getCoordenadasTela(width, height, pTela[i]);
	 
	      paint(gc, (int) pTela[i].x, (int) pTela[i].y, Color.WHITE);
	 
	    }
	 
	    return gc.getCanvas();
	 
	}
	
	 public static Canvas pintaScanLine(GraphicsContext gc, int width, int height, String s) throws Exception{
		Operacoes.carregarPontosTriangulos(s);
		Operacoes.carregarParametrosCamera();
		   
		Operacoes.ortogonalizarV(CameraVirtual.V, CameraVirtual.N);
		Vetor V = Operacoes.normalizar(CameraVirtual.V);
		Vetor N = Operacoes.normalizar(CameraVirtual.N);
		Vetor U = Operacoes.obterU(N, V);
	 
	    Triangulo[] pTela = new Triangulo[triangulos.length]; 
		 
		    for(int i = 0; i < pTela.length; i++) {
		      pTela[i] = new Triangulo();
		      pTela[i].v1 = getCoordenadasVista(U, V, N, triangulos[i].v1);
		      pTela[i].v2 = getCoordenadasVista(U, V, N, triangulos[i].v2);
		      pTela[i].v3 = getCoordenadasVista(U, V, N, triangulos[i].v3);
		      
		      coordenadasTela(pTela[i], width, height, 0);
		 
		      scanLine(pTela[i], 0);
		 
		    }
		    return gc.getCanvas();
	 }
		 
}