package others;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

class Boid {
    public Vector position;
    public Vector velocity;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public Boid(double x, double y) {
        position = new Vector(x, y);
        velocity = new Vector(Math.random() * 2 - 1, Math.random() * 2 - 1);
    }

    public void update(ArrayList<Boid> boids) {
        Vector separation = separate(boids);
        Vector alignment = align(boids);
        Vector cohesion = cohere(boids);

        separation = separation.scale(1.5);
        alignment = alignment.scale(1.0);
        cohesion = cohesion.scale(1.0);

        velocity = velocity.add(separation).add(alignment).add(cohesion);
        velocity = velocity.limit(2);

        position = position.add(velocity);
        borderBounce();
    }

    private void borderBounce() {
        if (position.x < 0) {
            position.x = 0;
            velocity.x *= -1;
        }
        if (position.x > WIDTH) {
            position.x = WIDTH;
            velocity.x *= -1;
        }
        if (position.y < 0) {
            position.y = 0;
            velocity.y *= -1;
        }
        if (position.y > HEIGHT) {
            position.y = HEIGHT;
            velocity.y *= -1;
        }
    }

    private Vector separate(ArrayList<Boid> boids) {
        double desiredSeparation = 25.0;
        Vector steer = new Vector(0, 0);
        int count = 0;

        for (Boid other : boids) {
            double distance = position.distance(other.position);
            if (distance > 0 && distance < desiredSeparation) {
                Vector diff = position.subtract(other.position);
                diff = diff.normalize();
                diff = diff.scale(1 / distance);
                steer = steer.add(diff);
                count++;
            }
        }

        if (count > 0) {
            steer = steer.scale(1.0 / count);
        }

        if (steer.magnitude() > 0) {
            steer = steer.normalize();
            steer = steer.scale(2);
            steer = steer.subtract(velocity);
            steer = steer.limit(0.03);
        }

        return steer;
    }

    private Vector align(ArrayList<Boid> boids) {
        double neighborDist = 50;
        Vector sum = new Vector(0, 0);
        int count = 0;

        for (Boid other : boids) {
            double distance = position.distance(other.position);
            if (distance > 0 && distance < neighborDist) {
                sum = sum.add(other.velocity);
                count++;
            }
        }

        if (count > 0) {
            sum = sum.scale(1.0 / count);
            sum = sum.normalize();
            sum = sum.scale(2);
            Vector steer = sum.subtract(velocity);
            return steer.limit(0.03);
        }

        return new Vector(0, 0);
    }

    private Vector cohere(ArrayList<Boid> boids) {
        double neighborDist = 50;
        Vector sum = new Vector(0, 0);
        int count = 0;

        for (Boid other : boids) {
            double distance = position.distance(other.position);
            if (distance > 0 && distance < neighborDist) {
                sum = sum.add(other.position);
                count++;
            }
        }

        if (count > 0) {
            sum = sum.scale(1.0 / count);
            return steerTo(sum);
        }

        return new Vector(0, 0);
    }

    private Vector steerTo(Vector target) {
        Vector desired = target.subtract(position);
        desired = desired.normalize();
        desired = desired.scale(2);
        Vector steer = desired.subtract(velocity);
        return steer.limit(0.03);
    }

    public void draw(Graphics2D g2d) {
        g2d.fillOval((int)position.x, (int)position.y, 5, 5);
    }
}

public class Vector {
    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }

    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y);
    }

    public Vector scale(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector normalize() {
        double magnitude = magnitude();
        if (magnitude > 0) {
            return scale(1 / magnitude);
        } else {
            return new Vector(0, 0);
        }
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector limit(double max) {
        if (magnitude() > max) {
            return normalize().scale(max);
        } else {
            return this;
        }
    }

    public double distance(Vector v) {
        double dx = this.x - v.x;
        double dy = this.y - v.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

class BoidsSimulation extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private ArrayList<Boid> boids = new ArrayList<>();
    private Timer timer;

    public BoidsSimulation() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        for (int i = 0; i < 100; i++) {
            boids.add(new Boid(Math.random() * WIDTH, Math.random() * HEIGHT));
        }

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Boid boid : boids) {
            boid.update(boids);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Boid boid : boids) {
            boid.draw(g2d);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Boids Simulation");
        BoidsSimulation simulation = new BoidsSimulation();
        frame.add(simulation);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
