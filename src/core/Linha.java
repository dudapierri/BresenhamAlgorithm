package core;

import java.awt.*;

public class Linha {

    public Ponto inicio;
    public Ponto fim;

    public Linha(int xInicio, int yInicio, int xFim, int yFim){
        this.inicio = new Ponto(xInicio, yInicio);
        this.fim = new Ponto(xFim, yFim);
    }

    public void desenhase(Graphics2D g){

    }

}
