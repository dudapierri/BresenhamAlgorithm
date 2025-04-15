package core2d;

import geral.*;

import java.util.List;


public class Linha {

    public Ponto a;
    public Ponto b;
    public List<int[]> pixeisBresenham = null; // Lista de pixel que o algoritmo de geral.Bresenham definiu que precisa ser pintado

    public Linha(int xInicio, int yInicio, int xFim, int yFim){
        this.a = new Ponto(xInicio, yInicio);
        this.b = new Ponto(xFim, yFim);
    }

    public Linha(Ponto inicio, Ponto fim){
        this.a = inicio;
        this.b = fim;
    }

    public void desenhase(PrototipoTela p) { // Aqui eu quero fazer um for no array de pixeisBresenham e usar a função desenha pixel la do geral.PrototipoTela
        for(int[] pixel : this.pixeisBresenham){
                p.desenhaPixel(pixel[0], pixel[1], 0, 0, 0);
        }
    }

    public void aplicaTransformacao(Matriz3x3 matriz, PrototipoTela tela) { // aplica uma transformação (matriz 3x3) nos dois pontos da linha e recalcula os pixels
        System.out.println("func aplicaTransformacao - Linha");
        a.multiplicaMat(matriz);
        b.multiplicaMat(matriz);
        recalculaPixels(tela);
    }

    public void recalculaPixels(PrototipoTela tela){
        pixeisBresenham.clear();

        // Aplica o algoritmo de clipping corretamente
        Linha linhaClipada = Clipping.clipping(a.x, a.y, b.x, b.y, tela);

        if (linhaClipada.a.x != -1) {
            pixeisBresenham = Bresenham.bresenham(linhaClipada.a.x, linhaClipada.a.y, linhaClipada.b.x, linhaClipada.b.y);
        }
    }

}
