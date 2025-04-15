package geral;

import core2d.*;
import core3d.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class PrototipoTela extends JPanel implements Runnable{
    //Definições da Tela
    private JFrame frame;
    private final int larguraTela = 1280;
    private final int alturaTela = 720;

	private boolean is3d = true;

    //Mouse
    private int clickX;
    private int clickY;
    private int mouseX;
    private int mouseY;



	//Limites tela desenhavel
	private final int y_min = 50; // Superior e inferior é eixo Y
	private final int y_max = 550;
	private final int x_min = 50; // Esquerdo e direito é eixo X
	private final int x_max =  750;

    private Ponto p1linhadesenhando = null;
    private ArrayList<Linha> listaDeLinhas = new ArrayList<>();
	private ArrayList<Triangulo3D> listaDeTriangulos = new ArrayList<>();
	
	private boolean ativo = true;
	
	private BufferedImage imageBuffer;
	private byte[] bufferDeVideo;

	// Getter Variaveis de limite de area desenhavel
	public int getY_min() {
		return y_min;
	}

	public int getY_max() {
		return y_max;
	}

	public int getX_min() {
		return x_min;
	}

	public int getX_max() {
		return x_max;
	}

	//Config teste de tela
    public void tela(){
        this.frame = new JFrame("Prototipo de Tela");
        this.frame.setSize(800, 600);
        this.frame.setLayout(null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(3);
    }

    //Construtor da class, carrega tudo
	public PrototipoTela(){

        //Seta o tamanho do componente
		setSize(larguraTela, alturaTela);
		setFocusable(true);
		
		imageBuffer = new BufferedImage(larguraTela, alturaTela, BufferedImage.TYPE_4BYTE_ABGR);

		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
		System.out.println("Buffer SIZE " + bufferDeVideo.length);

        //Ações do mouse
        addMouseListener(new MouseListener() {

            //MouseListener precisa que essa função esteja aqui, mesmo que esteja vazia
            @Override
            public void mouseClicked(MouseEvent e) {}

            //Pega a posição do click do mouse
            @Override
            public void mousePressed(MouseEvent e) {
                clickX = e.getX();
				clickY = e.getY();
				
				System.out.println("core2d.Ponto: " + clickX + " " + clickY);
				if(p1linhadesenhando == null) {
					p1linhadesenhando = new Ponto(clickX, clickY);
				}else {
					Ponto p2 = new Ponto(clickX, clickY);
					Linha linha = Clipping.clipping(p1linhadesenhando.x,p1linhadesenhando.y, p2.x,p2.y, PrototipoTela.this);
					if(linha.a.x != -1){ // Verifica se a linha é invalida (2 pontos fora da area desenhavel e não passa por dentro em nenhum momento)
						linha.pixeisBresenham = Bresenham.bresenham(linha.a.x,linha.a.y, linha.b.x, linha.b.y);
						listaDeLinhas.add(linha);
					}
					p1linhadesenhando = null;
				}
            }

            //MouseListener precisa que essas funções estejam aqui, mesmo que estejam vazias
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });

        //Alguma parada do mouse, não sei bem ainda
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
            
        });

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_1){
					is3d = false;
				}else
				if(e.getKeyCode() == KeyEvent.VK_2){
					is3d = true;
				}
				if(!is3d){
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						transladaLinhas(5, 0);
					} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						transladaLinhas(-5, 0);
					} else if(e.getKeyCode() == KeyEvent.VK_UP) {
						transladaLinhas(0, -5);
					} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
						transladaLinhas(0, 5);
					} else if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
						escalaLinhas(1.02, 1.02);
					} else if(e.getKeyCode() == KeyEvent.VK_MINUS) {
						escalaLinhas(0.98, 0.98);
					} else if(e.getKeyCode() == KeyEvent.VK_R) {
						rotacionaLinhas(Math.toRadians(5));
					} else if(e.getKeyCode() == KeyEvent.VK_T) {
						rotacionaLinhas(Math.toRadians(-5));
					} else if(e.getKeyCode() == KeyEvent.VK_E) {
						shearLinhas(0.02, 0);
					} else if(e.getKeyCode() == KeyEvent.VK_W) {
						shearLinhas(0, 0.02);
					}
				}else{
					if(e.getKeyCode() == KeyEvent.VK_DOWN){
						for(int i = 0; i < listaDeTriangulos.size(); i++){
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.translacao(0, 5, 0);
						}
					}
					else if(e.getKeyCode() == KeyEvent.VK_UP ){
						for(int i = 0; i < listaDeTriangulos.size(); i++){
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.translacao(0, -5, 0);
						}
					}
					else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
						for(int i = 0; i < listaDeTriangulos.size(); i++){
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.translacao(5, 0, 0);
						}
					}
					else if(e.getKeyCode() == KeyEvent.VK_LEFT ){
						for(int i = 0; i < listaDeTriangulos.size(); i++){
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.translacao(-5, 0, 0);
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_MINUS) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.escala(0.8f, 0.8f,0.8f);
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.escala(1.2f, 1.2f, 1.2f);
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.rotacaoY(-5);
						}

					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.rotacaoY(+5);
						}

					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.rotacaoX(+5);
						}

					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.rotacaoX(-5);
						}

					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.rotacaoZ(+5);
						}

					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
						for (int i = 0; i < listaDeTriangulos.size(); i++) {
							Triangulo3D tri = listaDeTriangulos.get(i);
							tri.rotacaoZ(-5);
						}

					}
				}
			}
		});

		criaCubo(100,100,0,100,100,100);
		criaCubo(300,200,0,100,200,100);
	}

	private void criaCubo(float x,float y, float z, float lx,float ly, float lz) {
		Ponto3D p1 = new Ponto3D(x, y, z);
		Ponto3D p2 = new Ponto3D(x+lx, y, z);
		Ponto3D p3 = new Ponto3D(x+lx, y+ly, z);
		Ponto3D p4 = new Ponto3D(x, y+ly, z);

		Ponto3D p5 = new Ponto3D(x, y, z+lz);
		Ponto3D p6 = new Ponto3D(x+lx, y, z+lz);
		Ponto3D p7 = new Ponto3D(x+lx, y+ly, z+lz);
		Ponto3D p8 = new Ponto3D(x, y+ly, z+lz);

		listaDeTriangulos.add(new Triangulo3D(p1,p2,p3));
		listaDeTriangulos.add(new Triangulo3D(p3,p4,p1));

		listaDeTriangulos.add(new Triangulo3D(p5,p6,p7));
		listaDeTriangulos.add(new Triangulo3D(p7,p8,p5));

		listaDeTriangulos.add(new Triangulo3D(p1,p4,p5));
		listaDeTriangulos.add(new Triangulo3D(p4,p8,p5));

		listaDeTriangulos.add(new Triangulo3D(p2,p3,p6));
		listaDeTriangulos.add(new Triangulo3D(p3,p7,p6));

		listaDeTriangulos.add(new Triangulo3D(p1,p2,p6));
		listaDeTriangulos.add(new Triangulo3D(p1,p6,p5));

		listaDeTriangulos.add(new Triangulo3D(p4,p3,p7));
		listaDeTriangulos.add(new Triangulo3D(p6,p7,p8));
	}

	public void transladaLinhas(double tx, double ty) { //// aplica translação em todas as linhas da tela
		Matriz3x3 matriz = new Matriz3x3();
		matriz.setTranslate(tx, ty);
		for(Linha linha : listaDeLinhas) {
			System.out.println("func translada linhas Prototipo tela");
			linha.aplicaTransformacao(matriz, this);
		}
	}

	public void escalaLinhas(double sx, double sy) { //// Aplica escala (zoom) em torno do ponto central da área desenhável
		Matriz3x3 matrizTranslateOrigem = new Matriz3x3();
		matrizTranslateOrigem.setTranslate(-400, -300);

		Matriz3x3 matrizEscala = new Matriz3x3();
		matrizEscala.setSacale(sx, sy);

		Matriz3x3 matrizTranslateVolta = new Matriz3x3();
		matrizTranslateVolta.setTranslate(400, 300);

		for(Linha linha : listaDeLinhas) {
			linha.aplicaTransformacao(matrizTranslateOrigem, this);
			linha.aplicaTransformacao(matrizEscala, this);
			linha.aplicaTransformacao(matrizTranslateVolta, this);
		}
	}

	public void rotacionaLinhas(double theta) { // aplica rotação em torno do centro da tela
		Matriz3x3 matrizTranslateOrigem = new Matriz3x3();
		matrizTranslateOrigem.setTranslate(-400, -300);

		Matriz3x3 matrizRotacao = new Matriz3x3();
		matrizRotacao.setRotate(theta);

		Matriz3x3 matrizTranslateVolta = new Matriz3x3();
		matrizTranslateVolta.setTranslate(400, 300);

		for(Linha linha : listaDeLinhas) {
			linha.aplicaTransformacao(matrizTranslateOrigem, this);
			linha.aplicaTransformacao(matrizRotacao, this);
			linha.aplicaTransformacao(matrizTranslateVolta, this);
		}
	}

	public void shearLinhas(double shx, double shy) { // aplica shear nas linhas
		Matriz3x3 matrizShear = new Matriz3x3();
		matrizShear.setShear(shx, shy);
		for(Linha linha : listaDeLinhas) {
			linha.aplicaTransformacao(matrizShear, this);
		}
	}


    //Desenha as linhas
    @Override
	public void paint(Graphics g) {

		Graphics2D g2d = imageBuffer.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, larguraTela, alturaTela);
		g2d.dispose();

		// Continua com o resto da pintura normal
		g.setColor(Color.white);
		g.fillRect(0, 0, larguraTela, alturaTela);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, larguraTela, alturaTela);

		//Desenha borda usando os limites definidos la em cima de parametro
		desenhaLinhaHorizontal(x_min, y_min, x_max - x_min);
		desenhaLinhaHorizontal(x_min, y_max, x_max - x_min);
		desenhaLinhaVertical(x_min, y_min, y_max - y_min);
		desenhaLinhaVertical(x_max, y_min, y_max - y_min);
		
		g.setColor(Color.black);
		for(int i = 0; i < listaDeLinhas.size();i++) {
			listaDeLinhas.get(i).desenhase(this);
		}
		for (Triangulo3D t : listaDeTriangulos) {
			t.desenhase(this);
		}
		
		g.setColor(Color.red);
		if(p1linhadesenhando!=null) {
			g.drawLine((int)p1linhadesenhando.x, (int)p1linhadesenhando.y, mouseX, mouseY);
		}
		
		g.drawImage(imageBuffer,0,0,null);
		
		g.setColor(Color.black);
	}
	
    //Não foi implementado ainda, professor que fez
	public void desenhaLinhaHorizontal(int x, int y,int w) {
		int pospix = y * (larguraTela * 4) + x * 4;
		
		for(int i = 0; i < w;i++) {
			
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)255;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
		}
	}
	
    //Não foi implementado ainda, professor que fez
	public void desenhaLinhaVertical(int x, int y,int h) {
		int pospix = y * (larguraTela * 4) + x * 4;
		
		for(int i = 0; i < h;i++) {
			
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)255;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix += (larguraTela * 4);
		}
	}
	
    //Não foi implementado ainda, professor que fez
	public void desenhaPixel(int x, int y,int r,int g,int b) {
		int pospix = y * (larguraTela * 4) + x * 4;
			
		bufferDeVideo[pospix] = (byte)255;
		bufferDeVideo[pospix+1] = (byte)(b&0xff);
		bufferDeVideo[pospix+2] = (byte)(g&0xff);
		bufferDeVideo[pospix+3] = (byte)(r&0xff);
	
	}


	
    //Aqui é onde é iniciado la pelo main
	public void start(){
  		Thread runner;
		runner = new Thread(this);
		runner.start();
	}
	
	@Override
	public void run() {
		while(ativo){
			paintImmediately(0, 0, larguraTela, alturaTela);
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}