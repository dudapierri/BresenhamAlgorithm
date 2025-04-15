package core2d;

public class Matriz3x3 {
    double mat[][] = new double[3][3];

    public Matriz3x3() { // construtor inicializa a matriz como identidade por padrão
        setIdentity();
    }

    public void setIdentity() { // não altera o ponto ao multiplicar
        zera();
        mat[0][0] = 1;
        mat[1][1] = 1;
        mat[2][2] = 1;
    }

    public void zera() { // zera todos os elementos da matriz
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                mat[y][x] = 0;
            }
        }
    }

    public void setTranslate(double a, double b) { //matriz de translação (move um ponto no espaço)
        zera();
        mat[0][0] = 1; // mantem escala em X
        mat[1][1] = 1; // mantem escala em Y
        mat[2][2] = 1; // coordenada homogenea
        mat[0][2] = a; // deslocamento no eixo x
        mat[1][2] = b; // deslocamento no eixo y
    }

    public void setSacale(double a, double b) { // matriz de escala (aumenta ou reduz o tamanho de um objeto)
        zera();
        mat[0][0] = a; //fator de escala no eixo X
        mat[1][1] = b; //fator de escala no eixo Y
        mat[2][2] = 1; // coordenada homogênea
    }

    public void setShear(double shx, double shy) { // matriz shear ( inclina objetos horizontal e vertical)
        setIdentity(); // inicia com a matriz identidade
        mat[0][1] = shx; // shear horizontal
        mat[1][0] = shy; // shear vertical
    }

    public void setRotate(double theta) { // matriz de rotação em torno da origem
        zera();
        mat[0][0] = Math.cos(theta); // componente de rotação em x
        mat[0][1] = -Math.sin(theta); // componente de rotação em x
        mat[1][0] = Math.sin(theta); // componente de rotação em y
        mat[1][1] = Math.cos(theta); // componente de rotação em Y
        mat[2][2] = 1; // coordenada homogênea
    }
}
