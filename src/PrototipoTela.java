import javax.swing.*;
import core.*;

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

    private Ponto p1linhadesenhando = null;
    private ArrayList<Linha> listaDeLinhas = new ArrayList<>();
	
	private boolean ativo = true;
	
	private BufferedImage imageBuffer;
	private byte[] bufferDeVideo;

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
	public void PrototipoTela(){

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
					Linha linha = new Linha(p1linhadesenhando.x,p1linhadesenhando.y, p2.x,p2.y);
					listaDeLinhas.add(linha);
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
		
		g.setColor(Color.black);
		for(int i = 0; i < listaDeLinhas.size();i++) {
			listaDeLinhas.get(i).desenhase((Graphics2D)g);
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
			bufferDeVideo[pospix+2] = (byte)0;
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
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)255;
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