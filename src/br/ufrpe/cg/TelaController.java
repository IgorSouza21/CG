package br.ufrpe.cg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.ufrpe.cg.beans.CameraVirtual;
import br.ufrpe.cg.beans.Iluminacao;
import br.ufrpe.cg.beans.Ponto;
import br.ufrpe.cg.beans.Vetor;
import br.ufrpe.cg.parte1.Operacoes;
import br.ufrpe.cg.parte1.Questao1;
import br.ufrpe.cg.parte1.Questao2;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaController extends Application implements Initializable{
	@FXML private TextField Cx;
	@FXML private TextField Cy;
	@FXML private TextField Cz;
	@FXML private TextField D;
	@FXML private TextField Hx;
	@FXML private TextField Hy;
	@FXML private TextField Vx;
	@FXML private TextField Vy;
	@FXML private TextField Vz;
	@FXML private TextField Nx;
	@FXML private TextField Ny;
	@FXML private TextField Nz;
	@FXML private Canvas canvas;
	@FXML private TextArea questao1;
	@FXML private ComboBox<String> imagem;
	@FXML private TextField IambR;
	@FXML private TextField IambG;
	@FXML private TextField IambB;
	@FXML private TextField Ka;
	@FXML private TextField IlR;
	@FXML private TextField IlG;
	@FXML private TextField IlB;
	@FXML private TextField PlX;
	@FXML private TextField PlY;
	@FXML private TextField PlZ;
	@FXML private TextField KdR;
	@FXML private TextField KdG;
	@FXML private TextField KdB;
	@FXML private TextField OdR;
	@FXML private TextField OdG;
	@FXML private TextField OdB;
	@FXML private TextField Ks;
	@FXML private TextField Eta;
	
	private int tela;
	private String carregada;
	private CameraVirtual virtual;
	private Iluminacao luz;
	public static int width;
	public static int height;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tela = 4;
		Operacoes.canvas = canvas;
		width = (int) canvas.getWidth();
		height = (int) canvas.getHeight();
		try {
			Operacoes.carregarParametrosCamera();
			Operacoes.carregarParametrosIluminacao();
			preencheCampos();
		} catch (IOException e) {
			erro("Camera.txt ou Iluminacao.txt");
		}
		ArrayList<String> im = new ArrayList<>();
		im.add("");
		im.add("calice2");
		im.add("maca");
		im.add("maca2");
		im.add("piramide");
		im.add("triangulo");
		im.add("vaso");
		imagem.setItems(FXCollections.observableArrayList(im));
		imagem.getSelectionModel().select("");
		imagem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2 != "") {
					carregada = arg2 + ".byu";
					pintaImagem();
				}
				
			}
			
		});
	}
	
	public void pintaImagem() {
		if(carregada != null && carregada != "") {
			if(tela == 0) {
				canvas.setVisible(false);
				questao1.setVisible(true);
				questao1.setText(Questao1.executar());
			}
			else if(tela == 1) {
				canvas.getGraphicsContext2D().setFill(Color.BLACK);
				canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				canvas.setVisible(true);
				questao1.setVisible(false);
				if(carregada != null && !carregada.equals("")) {
					try {
						Questao2.executar(carregada, canvas.getWidth(), canvas.getHeight(), 1, canvas);
					} catch (IOException e) {
						erro(carregada);
					}
				}
			}
			else if(tela == 2) {
				canvas.getGraphicsContext2D().setFill(Color.BLACK);
				canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				canvas.setVisible(true);
				questao1.setVisible(false);
				try {
					Operacoes.pintaProjecaoOrtogonal(canvas.getGraphicsContext2D(),(int) canvas.getWidth(),(int) canvas.getHeight(), 
							carregada);
				} catch (Exception e) {
					erro(carregada);
				}			
			}
			else if(tela == 3) {
				canvas.getGraphicsContext2D().setFill(Color.BLACK);
				canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				canvas.setVisible(true);
				questao1.setVisible(false);
				try {
					Operacoes.pintaScanLine(canvas.getGraphicsContext2D(), width, height, carregada);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(tela == 4) {
				try {
					Operacoes.fazTudo(width, height, carregada);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void handleCarregar() throws IOException {
		if (carregada != null) {
			File camera = new File("Camera.txt");
			BufferedWriter w = new BufferedWriter(new FileWriter(camera));
			File luz = new File("Iluminacao.txt");
			BufferedWriter wr = new BufferedWriter(new FileWriter(luz));
			Ponto c = new Ponto();
			c.x = Double.parseDouble(Cx.getText());
			c.y = Double.parseDouble(Cy.getText());
			c.z = Double.parseDouble(Cz.getText());
			Vetor n = new Vetor();
			n.x = Double.parseDouble(Nx.getText());
			n.y = Double.parseDouble(Ny.getText());
			n.z = Double.parseDouble(Nz.getText());
			Vetor v = new Vetor();
			v.x = Double.parseDouble(Vx.getText());
			v.y = Double.parseDouble(Vy.getText());
			v.z = Double.parseDouble(Vz.getText());
			Vetor Iamb = new Vetor();
			Iamb.x = Double.parseDouble(IambR.getText());
			Iamb.y = Double.parseDouble(IambG.getText());
			Iamb.z = Double.parseDouble(IambB.getText());
			Vetor Il = new Vetor();
			Il.x = Double.parseDouble(IlR.getText());
			Il.y = Double.parseDouble(IlG.getText());
			Il.z = Double.parseDouble(IlB.getText());
			Ponto Pl = new Ponto();
			Pl.x = Double.parseDouble(PlX.getText());
			Pl.y = Double.parseDouble(PlY.getText());
			Pl.z = Double.parseDouble(PlZ.getText());
			Vetor Kd = new Vetor();
			Kd.x = Double.parseDouble(KdR.getText());
			Kd.y = Double.parseDouble(KdG.getText());
			Kd.z = Double.parseDouble(KdB.getText());
			Vetor Od = new Vetor();
			Od.x = Double.parseDouble(OdR.getText());
			Od.y = Double.parseDouble(OdG.getText());
			Od.z = Double.parseDouble(OdB.getText());
			CameraVirtual x = new CameraVirtual(c, n, v, Double.parseDouble(D.getText()),
					Double.parseDouble(Hx.getText()), Double.parseDouble(Hy.getText()));
			if (!virtual.equals(x)) {
				w.write("N = " + Nx.getText() + " " + Ny.getText() + " " + Nz.getText());
				w.newLine();
				w.write("V = " + Vx.getText() + " " + Vy.getText() + " " + Nz.getText());
				w.newLine();
				w.write("d = " + D.getText());
				w.newLine();
				w.write("hx = " + Hx.getText());
				w.newLine();
				w.write("hy = " + Hy.getText());
				w.newLine();
				w.write("C = " + Cx.getText() + " " + Cy.getText() + " " + Cz.getText());
				w.close();
				Operacoes.carregarParametrosCamera();
			}
			Iluminacao y = new Iluminacao(Iamb, Double.parseDouble(Ka.getText()), Il, Pl, Kd, Od,
					Double.parseDouble(Ks.getText()), Double.parseDouble(Eta.getText()));
			if (!this.luz.equals(y)) {
				wr.write("Iamb = " + IambR.getText() + " " + IambG.getText() + " " + IambB.getText());
				wr.newLine();
				wr.write("Ka = " + Ka.getText());
				wr.newLine();
				wr.write("Il = " + IlR.getText() + " " + IlG.getText() + " " + IlB.getText());
				wr.newLine();
				wr.write("Pl = " + PlX.getText() + " " + PlY.getText() + " " + PlZ.getText());
				wr.newLine();
				wr.write("Kd = " + KdR.getText() + " " + KdG.getText() + " " + KdB.getText());
				wr.newLine();
				wr.write("Od = " + OdR.getText() + " " + OdG.getText() + " " + OdB.getText());
				wr.newLine();
				wr.write("Ks = " + Ks.getText());
				wr.newLine();
				wr.write("Eta = " + Eta.getText());
				wr.close();
				Operacoes.carregarParametrosIluminacao();
			}
			try {
				Operacoes.fazTudo(width, height, carregada);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void handleCarrega(KeyEvent k) throws IOException {
		if(k.getCode() == KeyCode.ENTER)
			if(carregada != null)
				handleCarregar();
		if(k.getCode() ==  KeyCode.F5) {
			preencheCampos();
			try {
				Operacoes.fazTudo(width, height, carregada);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}
	
	private void preencheCampos() {
		Cx.setText("" + CameraVirtual.C.x);
		Cy.setText("" + CameraVirtual.C.y);
		Cz.setText("" + CameraVirtual.C.z);
		D.setText("" + CameraVirtual.D);
		Hx.setText("" + CameraVirtual.Hx);
		Hy.setText("" + CameraVirtual.Hy);
		Vx.setText("" + CameraVirtual.V.x);
		Vy.setText("" + CameraVirtual.V.y);
		Vz.setText("" + CameraVirtual.V.z);
		Nx.setText("" + CameraVirtual.N.x);
		Ny.setText("" + CameraVirtual.N.y);
		Nz.setText("" + CameraVirtual.N.z);
		IambR.setText("" + Iluminacao.Iamb.x);
		IambG.setText("" + Iluminacao.Iamb.y);
		IambB.setText("" + Iluminacao.Iamb.z);
		Ka.setText("" + Iluminacao.Ka);
		IlR.setText("" + Iluminacao.Il.x);
		IlG.setText("" + Iluminacao.Il.y);
		IlB.setText("" + Iluminacao.Il.z);
		PlX.setText("" + Iluminacao.Pl.x);
		PlY.setText("" + Iluminacao.Pl.y);
		PlZ.setText("" + Iluminacao.Pl.z);
		KdR.setText("" + Iluminacao.Kd.x);
		KdG.setText("" + Iluminacao.Kd.y);
		KdB.setText("" + Iluminacao.Kd.z);
		OdR.setText("" + Iluminacao.Od.x);
		OdG.setText("" + Iluminacao.Od.y);
		OdB.setText("" + Iluminacao.Od.z);
		Ks.setText("" + Iluminacao.Ks);
		Eta.setText("" + Iluminacao.Eta);
		virtual = CameraVirtual.getCamera();
		luz = Iluminacao.getIluminacao();
	}
	
	private void erro(String s) {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Erro");
		a.setContentText("Ocorreu um erro em " + s);
		a.showAndWait();
	}
	
	public void questao1() {
		tela = 0;
		pintaImagem();
	}
	public void questao2() {
		tela = 1;
		pintaImagem();
	}
	public void projecaoPerspectiva() {
		tela = 2;
		pintaImagem();
	}
	public void scanLine() {
		tela = 3;
		pintaImagem();
	}
	public void iluminacao() {
		tela = 4;
		pintaImagem();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("tela.fxml"))));
			stage.setResizable(false);
			stage.setTitle("Computa��o Gr�fica");
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
