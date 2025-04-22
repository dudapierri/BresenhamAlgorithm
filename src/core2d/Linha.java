package core2d;

import core3d.Ponto3D;
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

    public Linha(Ponto3D inicio, Ponto3D fim){
        this.a = new Ponto((int)Math.round(inicio.x), (int)Math.round(inicio.y));
        this.b = new Ponto((int)Math.round(fim.x), (int)Math.round(fim.y));
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
    /*

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
    */

}
