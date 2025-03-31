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

    //Mouse
    private int clickX;
    private int clickY;
    private int mouseX;
    private int mouseY;

	//Codigo das regioes Clipping
	static final int INSIDE = 0; // 0000
	static final int LEFT = 1; // 0001
	static final int RIGHT = 2; // 0010
	static final int BOTTOM = 4; // 0100
	static final int TOP = 8; // 1000

	//Limites tela desenhavel
	private final int y_min = 50; // Superior e inferior é eixo Y
	private final int y_max = 550;
	private final int x_min = 50; // Esquerdo e direito é eixo X
	private final int x_max =  750;

    private Ponto p1linhadesenhando = null;
    private ArrayList<Linha> listaDeLinhas = new ArrayList<>();
	
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
				
				System.out.println("Ponto: " + clickX + " " + clickY);
				if(p1linhadesenhando == null) {
					p1linhadesenhando = new Ponto(clickX, clickY);
				}else {
					Ponto p2 = new Ponto(clickX, clickY);
					Linha linha = clipping(p1linhadesenhando.x,p1linhadesenhando.y, p2.x,p2.y);
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
	}

    //Desenha as linhas
    @Override
	public void paint(Graphics g) {
		
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

	// Função pra detectar se tem clipping, e em que região ocorre
	public int detectaClipping(int x, int y){
		int cod = INSIDE; // Por padrao considera que ta dentro da tela

		if (x < x_min) // Ve se ta na esquerda da tela
			cod |= LEFT;
		else if (x > x_max) //  Ve se ta na direita da tela
			cod |= RIGHT;
		if (y < y_min) //  Ve acima da tela
			cod |= BOTTOM;
		else if (y > y_max) // Ve se ta abaixo da tela
			cod |= TOP;

		return cod;
	}

	public Linha clipping(int x1, int y1, int x2, int y2){
		int cod1 = detectaClipping(x1,y1); // Ve se algum dos 2 pontos esta fora da tela
		int cod2 = detectaClipping(x2,y2);

		while(true){
			if(cod1 == INSIDE && cod2 == INSIDE){ // Ve se os dois ja tao dentro da tela
				return new Linha(x1,y1,x2,y2);
			}
			else if((cod1 & cod2) != INSIDE){ // Ve se os dois estão fora da tela e não passa por dentro dela
				return new Linha(-1,-1,-1,-1); // Retorna uma Linha invalida pra verificação no retorno
			}
			else{
				int codFora; // Codigo da região do ponto fora da tela
				int x = 0, y = 0;

				if(cod1 != INSIDE){
					codFora = cod1;
				}else{
					codFora = cod2;
				}

				if((codFora & TOP) != 0){ // Se estiver acima da tela
					x = x1 + (x2 - x1)* (y_max - y1) / (y2 - y1);
					y = y_max;
				}else if((codFora & BOTTOM) != 0){ // Se estiver abaixo
					x = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1);
					y = y_min;
				}else if((codFora & RIGHT) != 0){ // Se estiver a direita
					y = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1);
					x = x_max;
				}
				else if((codFora & LEFT) != 0){ // Se estiver a esquerda
					y = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1);
					x = x_min;
				}

				if (codFora == cod1) {
					x1 = x;
					y1 = y;
					cod1 = detectaClipping(x1, y1);
				}
				else {
					x2 = x;
					y2 = y;
					cod2 = detectaClipping(x2, y2);
				}
			}

		}

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