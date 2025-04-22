package geral;

import core2d.*;
import core3d.*;
import obj3D.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.ArrayList;

public class PrototipoTela extends JPanel implements Runnable{
    //Definições da Tela
    private JFrame frame;
    private final int larguraTela = 1280;
    private final int alturaTela = 720;

	private String[] listaArquivos = {"AIM120D.obj", "11805_airplane_v2_L2.obj", "Ak-74Hi.obj", "Bench_LowRes.obj", "tank.obj", "uploads_files_2787791_Mercedes+Benz+GLS+580.obj"};

	private int objAtual = 0;

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
	private ArrayList<Obj3D> listaDeObj = new ArrayList<>();
	
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

	public void rotacoes(float angulo, float x, float y, float z){
		if(objAtual == -1){
			for (Obj3D o : listaDeObj) {
				o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
				o.rotacaoEixoQualquer(angulo, x, y, z); // eixo Y
				o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
			}
		}else{
			Obj3D o = listaDeObj.get(objAtual);
			o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
			o.rotacaoEixoQualquer(angulo, x, y, z); // eixo Y
			o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
		}
	}

	public void escalaMain(float a, float b, float c){
		if(objAtual == -1) {
			for (Obj3D o : listaDeObj) {
				o.escala(a, b, c);
			}
		}else{
			Obj3D o = listaDeObj.get(objAtual);
			o.escala(a, b, c);
		}
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
	public PrototipoTela() throws IOException {

        //Seta o tamanho do componente
		setSize(larguraTela, alturaTela);
		setFocusable(true);
		
		imageBuffer = new BufferedImage(larguraTela, alturaTela, BufferedImage.TYPE_4BYTE_ABGR);

		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
		System.out.println("Buffer SIZE " + bufferDeVideo.length);

		// Carregamento dos obj

		for(String s : listaArquivos){
			listaDeObj.add(ObjReader.loadObjFile("src/objFiles/" + s));
		}

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
				switch (e.getKeyCode()) {
					case KeyEvent.VK_0:
						objAtual = -1;
						break;
					case KeyEvent.VK_1:
						objAtual = 0;
						break;
					case KeyEvent.VK_2:
						objAtual = 1;
						break;
					case KeyEvent.VK_3:
						objAtual = 2;
						break;
					case KeyEvent.VK_4:
						objAtual = 3;
						break;
					case KeyEvent.VK_5:
						objAtual = 4;
						break;
					case KeyEvent.VK_6:
						objAtual = 5;
						break;
					case KeyEvent.VK_DOWN:
						escalaMain(0, 5, 0);
						break;
					case KeyEvent.VK_UP:
						escalaMain(0, -5, 0);
						break;
					case KeyEvent.VK_RIGHT:
						escalaMain(5, 0, 0);
						break;
					case KeyEvent.VK_LEFT:
						escalaMain(-5, 0, 0);
						break;
					case KeyEvent.VK_MINUS:
						escalaMain(0.8f, 0.8f, 0.8f);
						break;
					case KeyEvent.VK_PLUS:
						escalaMain(1.2f, 1.2f, 1.2f);
						break;
					case KeyEvent.VK_EQUALS:
						escalaMain(1.2f, 1.2f, 1.2f);
						break;
					case KeyEvent.VK_NUMPAD4:
						rotacoes(-5, 0, 1, 0);
						break;
					case KeyEvent.VK_NUMPAD6:
						rotacoes(+5, 0, 1, 0);
						break;
					case KeyEvent.VK_NUMPAD8:
						rotacoes(+5, 1, 0, 0);
						break;
					case KeyEvent.VK_NUMPAD2:
						rotacoes(-5, 1, 0, 0);
						break;
					case KeyEvent.VK_NUMPAD9:
						rotacoes(+5, 0, 0, 1);
						break;
					case KeyEvent.VK_NUMPAD7:
						rotacoes(-5, 0, 0, 1);
						break;
					default:
						break;
				}
				/*if(e.getKeyCode() == KeyEvent.VK_0){
					objAtual = -1;
				}else
				if(e.getKeyCode() == KeyEvent.VK_1){
					objAtual = 0;
				}else
				if(e.getKeyCode() == KeyEvent.VK_2){
					objAtual = 1;
				}else
				if(e.getKeyCode() == KeyEvent.VK_3){
					objAtual = 2;
				}else
				if(e.getKeyCode() == KeyEvent.VK_4){
					objAtual = 3;
				}else
				if(e.getKeyCode() == KeyEvent.VK_5){
					objAtual = 4;
				}else
				if(e.getKeyCode() == KeyEvent.VK_6){
					objAtual = 5;
				}else
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					//escalaMain(0, 5, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao(0, 5, 0);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao(0, 5, 0);
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_UP ){
					escalaMain(0, -5, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
						o.translacao(0, -5, 0);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao(0, -5, 0);
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					escalaMain(5, 0, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao(5, 0, 0);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao(5, 0, 0);
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT ){
					escalaMain(-5, 0, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao(-5, 0, 0);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao(-5, 0, 0);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					escalaMain(0.8f, 0.8f, 0.8f);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.escala(0.8f, 0.8f,0.8f);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.escala(0.8f, 0.8f,0.8f);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
					escalaMain(1.2f, 1.2f, 1.2f);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.escala(1.2f, 1.2f, 1.2f);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.escala(1.2f, 1.2f, 1.2f);
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
					rotacoes(-5, 0, 1, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
							o.rotacaoEixoQualquer(-5, 0, 1, 0); // eixo Y
							o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
						o.rotacaoEixoQualquer(-5, 0, 1, 0); // eixo Y
						o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
					rotacoes(+5, 0, 1, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
							o.rotacaoEixoQualquer(+5, 0, 1, 0); // eixo Y
							o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
						o.rotacaoEixoQualquer(+5, 0, 1, 0); // eixo Y
						o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
					rotacoes(+5, 1, 0, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
							o.rotacaoEixoQualquer(+5, 1, 0, 0); // eixo X
							o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
						o.rotacaoEixoQualquer(+5, 1, 0, 0); // eixo X
						o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
					rotacoes(-5, 1, 0, 0);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
							o.rotacaoEixoQualquer(-5, 1, 0, 0); // eixo X
							o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
						o.rotacaoEixoQualquer(-5, 1, 0, 0); // eixo X
						o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
					rotacoes(+5, 0, 0, 1);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
							o.rotacaoEixoQualquer(+5, 0, 0, 1); // eixo Z
							o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
						o.rotacaoEixoQualquer(+5, 0, 0, 1); // eixo Z
						o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
					rotacoes(-5, 0, 0, 1);
					/*if(objAtual == -1) {
						for (Obj3D o : listaDeObj) {
							o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
							o.rotacaoEixoQualquer(-5, 0, 0, 1); // eixo Z
							o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
						}
					}else{
						Obj3D o = listaDeObj.get(objAtual);
						o.translacao((float)-o.centroide.x,(float)-o.centroide.y,(float)-o.centroide.z);
						o.rotacaoEixoQualquer(-5, 0, 0, 1); // eixo Z
						o.translacao((float)o.centroide.x,(float)o.centroide.y,(float)o.centroide.z);
					}
				}*/
			}
		});
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

		/*
		for(int i = 0; i < listaDeLinhas.size();i++) {
			listaDeLinhas.get(i).desenhase(this);
		}
		for (Triangulo3D t : listaDeTriangulos) {
			t.desenhase(this);
		}*/
		g.setColor(Color.black);
		if(objAtual != -1) {
			listaDeObj.get(objAtual).desenhase(this);
		}else{
			for(Obj3D o : listaDeObj){
				o.desenhase(this);
			}
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