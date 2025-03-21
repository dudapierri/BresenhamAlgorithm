import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        /*List<int[]> pontosLinha = Bresenham.bresenham(1, 1, 4, 10);
        for (int[] ponto : pontosLinha) {
            System.out.println("(" + ponto[0] + ", " + ponto[1] + ")");
        }*/

        PrototipoTela meuCanvas = new PrototipoTela();

        JFrame f = new JFrame();
        f.setSize(800, 650);
        f.setVisible(true);
        f.getContentPane().add(meuCanvas);


        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        meuCanvas.start();
    }

}
