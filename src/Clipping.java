public class Clipping {

    //Codigo das regioes Clipping
    static final int INSIDE = 0; // 0000
    static final int LEFT = 1; // 0001
    static final int RIGHT = 2; // 0010
    static final int BOTTOM = 4; // 0100
    static final int TOP = 8; // 1000

    // Função pra detectar se tem clipping, e em que região ocorre
    public static int detectaClipping(int x, int y, PrototipoTela tela){
        int cod = INSIDE; // Por padrao considera que ta dentro da tela

        if (x < tela.getX_min()) // Ve se ta na esquerda da tela
            cod |= LEFT;
        else if (x > tela.getX_max()) //  Ve se ta na direita da tela
            cod |= RIGHT;
        if (y < tela.getY_min()) //  Ve acima da tela
            cod |= BOTTOM;
        else if (y > tela.getY_max()) // Ve se ta abaixo da tela
            cod |= TOP;

        return cod;
    }

    public static Linha clipping(int x1, int y1, int x2, int y2, PrototipoTela tela){
        int cod1 = detectaClipping(x1,y1, tela); // Ve se algum dos 2 pontos esta fora da tela
        int cod2 = detectaClipping(x2,y2, tela);

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
                    x = x1 + (x2 - x1)* (tela.getY_max() - y1) / (y2 - y1);
                    y = tela.getY_max();
                }else if((codFora & BOTTOM) != 0){ // Se estiver abaixo
                    x = x1 + (x2 - x1) * (tela.getY_min() - y1) / (y2 - y1);
                    y = tela.getY_min();
                }else if((codFora & RIGHT) != 0){ // Se estiver a direita
                    y = y1 + (y2 - y1) * (tela.getX_max() - x1) / (x2 - x1);
                    x = tela.getX_max();
                }
                else if((codFora & LEFT) != 0){ // Se estiver a esquerda
                    y = y1 + (y2 - y1) * (tela.getX_min() - x1) / (x2 - x1);
                    x = tela.getX_min();
                }

                if (codFora == cod1) {
                    x1 = x;
                    y1 = y;
                    cod1 = detectaClipping(x1, y1, tela);
                }
                else {
                    x2 = x;
                    y2 = y;
                    cod2 = detectaClipping(x2, y2, tela);
                }
            }

        }

    }
}
