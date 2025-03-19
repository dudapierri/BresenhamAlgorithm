import java.util.ArrayList;
import java.util.List;
public class Bresenham {
        /**
         * Implementação do algoritmo de Bresenham para desenhar uma linha entre dois pontos.
         *
         * @param xInicial Coordenada X do ponto inicial
         * @param yInicial Coordenada Y do ponto inicial
         * @param xFinal Coordenada X do ponto final
         * @param yFinal Coordenada Y do ponto final
         * @return Lista de pontos (x, y) que representam a linha.
         */
        public static List<int[]> bresenham(int xInicial, int yInicial, int xFinal, int yFinal) {
            List<int[]> pontos = new ArrayList<>();

            int deltaX = Math.abs(xFinal - xInicial); // Diferença absoluta entre as coordenadas X
            int deltaY = Math.abs(yFinal - yInicial); // Diferença absoluta entre as coordenadas Y
            boolean inclinacaoMaiorQueUm = deltaY > deltaX; // Verifica se a inclinação é maior que 1

            // Se a inclinação for maior que 1, trocamos as coordenadas X e Y para manter a precisão
            if (inclinacaoMaiorQueUm) {
                int temp;
                temp = xInicial; xInicial = yInicial; yInicial = temp;
                temp = xFinal; xFinal = yFinal; yFinal = temp;
                temp = deltaX; deltaX = deltaY; deltaY = temp;
            }

            int p = 2 * deltaY - deltaX; // Valor inicial do parâmetro de decisão
            int x = xInicial, y = yInicial; // Define o ponto inicial
            int passoX = (xFinal > xInicial) ? 1 : -1; // Define a direção do movimento no eixo X
            int passoY = (yFinal > yInicial) ? 1 : -1; // Define a direção do movimento no eixo Y

            for (int i = 0; i <= deltaX; i++) {
                if (inclinacaoMaiorQueUm) {
                    pontos.add(new int[]{y, x});
                } else {
                    pontos.add(new int[]{x, y});
                }

                x += passoX; // Atualiza X dependendo da direção
                if (p < 0) {
                    p += 2 * deltaY; // Atualiza o parâmetro de decisão sem alterar Y
                } else {
                    y += passoY; // Ajusta Y dependendo da direção
                    p += 2 * (deltaY - deltaX); // Atualiza o parâmetro de decisão
                }
            }

            return pontos; // Retorna a lista de pontos da linha
        }

        public static void main(String[] args) {
            List<int[]> pontosLinha = bresenham(1, 1, 4, 10);
            for (int[] ponto : pontosLinha) {
                System.out.println("(" + ponto[0] + ", " + ponto[1] + ")");
            }
        }
    }

