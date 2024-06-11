package math;

public class EquationSolver {
    private final EquationSystem system;
    private final double[] targets;
    private final int maxIterations;
    private final double tolerance;

    public EquationSolver(EquationSystem system, double[] targets, int maxIterations, double tolerance) {
        this.system = system;
        this.targets = targets;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
    }

    public double[] solve(double initialA, double initialB) {
        double a = initialA;
        double b = initialB;

        for (int i = 0; i < maxIterations; i++) {
            double[] values = system.evaluate(a, b);
            double f1 = values[0] - targets[0];
            double f2 = values[1] - targets[1];

            if (Math.abs(f1) < tolerance && Math.abs(f2) < tolerance) {
                return new double[] { a, b };
            }

            double[][] jacobian = system.jacobian(a, b);

            double det = jacobian[0][0] * jacobian[1][1] - jacobian[0][1] * jacobian[1][0];
            if (Math.abs(det) < 1e-10) {
                throw new IllegalStateException("Jacobi matrisinin determinantı sıfıra yakın, çözülemiyor.");
            }

            double invDet = 1.0 / det;
            double[][] invJacobian = {
                    { jacobian[1][1] * invDet, -jacobian[0][1] * invDet },
                    { -jacobian[1][0] * invDet, jacobian[0][0] * invDet }
            };

            double deltaA = -(invJacobian[0][0] * f1 + invJacobian[0][1] * f2);
            double deltaB = -(invJacobian[1][0] * f1 + invJacobian[1][1] * f2);
            a += deltaA;
            b += deltaB;
        }

        throw new IllegalStateException("Belirli bir tolerans içinde çözüm bulunamadı.");
    }
}
