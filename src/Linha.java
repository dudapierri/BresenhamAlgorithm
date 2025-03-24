import java.util.List;

public class Linha {

    public Ponto a;
    public Ponto b;
    public List<int[]> pixeisBresenham = null; // Lista de pixel que o algoritmo de Bresenham definiu que precisa ser pintado

    public Linha(int xInicio, int yInicio, int xFim, int yFim){
        this.a = new Ponto(xInicio, yInicio);
        this.b = new Ponto(xFim, yFim);
    }

    public Linha(Ponto inicio, Ponto fim){
        this.a = inicio;
        this.b = fim;
    }

    public void desenhase(PrototipoTela p) { // Aqui eu quero fazer um for no array de pixeisBresenham e usar a função desenha pixel la do PrototipoTela
        for(int[] pixel : this.pixeisBresenham){
            if(!detectaClipping(p, pixel)){ // so desenha se nao detectar o clipping
                p.desenhaPixel(pixel[0], pixel[1], 0, 0, 0);
            }
        }
    }

    public boolean detectaClipping(PrototipoTela p, int[] pixel){
        if(p.getLimiteEsquerdo() > pixel[0] || pixel[0] > p.getLimiteDireito()){ // Se ele for mais a esquerda que o limite esquerdo ou mais a direita que o limite direito
            return true; // detecta clipping
        } else if (p.getLimiteSuperior() > pixel[1] || pixel[1] > p.getLimiteInferior()) { // Se ele for mais acima que o limite superior ou mais abaixo que o limite inferior
            return true; // detecta clipping
        }
        return false; // sem clipping
    }

}
