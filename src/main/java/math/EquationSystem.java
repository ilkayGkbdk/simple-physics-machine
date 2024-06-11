package math;

public interface EquationSystem {
    double[] evaluate(double a, double b);
    double[][] jacobian(double a, double b);
}
