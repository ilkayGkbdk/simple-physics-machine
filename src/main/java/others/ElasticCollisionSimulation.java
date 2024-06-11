package others;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElasticCollisionSimulation extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int DIAMETER = 50;

    private double x1, y1, x2, y2;
    private double v1, v2;
    private double m1, m2;
    private Timer timer;

    public ElasticCollisionSimulation() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        // Başlangıç konumları ve hızlar
        x1 = 100;
        y1 = HEIGHT / 2 - DIAMETER / 2;
        x2 = 600;
        y2 = HEIGHT / 2 - DIAMETER / 2;

        v1 = 9;
        v2 = -6;

        m1 = 1;
        m2 = 2;

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Çemberleri çiz
        g2d.setColor(Color.RED);
        g2d.fillOval((int) x1, (int) y1, DIAMETER, DIAMETER);
        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) x2, (int) y2, DIAMETER, DIAMETER);

        // Hız bilgilerini çiz
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("v1 = %.2f m/s", v1), (int) x1, (int) y1 - 10);
        g2d.drawString(String.format("v2 = %.2f m/s", v2), (int) x2, (int) y2 - 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Hareket
        x1 += v1;
        x2 += v2;

        // Çarpışma kontrolü
        if (Math.abs((x1 + DIAMETER / 2) - (x2 + DIAMETER / 2)) < DIAMETER) {
            double v1Final = ((m1 - m2) * v1 + 2 * m2 * v2) / (m1 + m2);
            double v2Final = ((m2 - m1) * v2 + 2 * m1 * v1) / (m1 + m2);
            v1 = v1Final;
            v2 = v2Final;
        }

        // Kenarlara çarpma kontrolü
        if (x1 < 0 || x1 + DIAMETER > WIDTH) {
            v1 *= -1;
        }
        if (x2 < 0 || x2 + DIAMETER > WIDTH) {
            v2 *= -1;
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Elastic Collision Simulation");
        ElasticCollisionSimulation simulation = new ElasticCollisionSimulation();
        frame.add(simulation);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
