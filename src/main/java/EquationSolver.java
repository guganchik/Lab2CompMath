import java.util.ArrayList;
import java.util.List;

import static tools.MathAndPrintTools.*;

public class EquationSolver {
    private final double[] equation;
    private final double a;
    private final double b;
    private final double eps;
    private final boolean transcendent;

    public EquationSolver(double[] equation, double a, double b, double eps, boolean transcendent) {
        this.equation = equation;
        this.a = a;
        this.b = b;
        this.eps = eps;
        this.transcendent = transcendent;
    }

    public List<Double> findRoots() {
        List<Double> roots = new ArrayList<>();
        double step = 0.1; // шаг для перебора точек
        double prevValue = 0;
        double prevX = a;
        for (double x = a + step; x <= b; x += step) {
            double currValue = transcendent ? solvePointSinus(equation, x) : solvePoint(equation, x);
            if (currValue == 0) { // если значение в точке равно 0, значит это корень
                roots.add(x);
            } else if (prevValue != 0 && prevValue * currValue < 0) { // если значение на подотрезке поменяло знак, значит на нем есть корень
                roots.add(findRootOnInterval(prevX, x, eps));
            }
            prevValue = currValue;
            prevX = x;
        }
        return roots;
    }

    private double findRootOnInterval(double a, double b, double eps) {
        double x0, x1, f0, f1;
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        x0 = a;
        x1 = b;
        f0 = transcendent ? solvePointSinus(equation, x0) : solvePoint(equation, x0);
        f1 = transcendent ? solvePointSinus(equation, x1) : solvePoint(equation, x1);

        while (Math.abs(x1 - x0) > eps) {
            double x2 = x1 - f1 * (x1 - x0) / (f1 - f0);
            double f2 = transcendent ? solvePointSinus(equation, x2) : solvePoint(equation, x2);
            if (f1 * f2 < 0) {
                x0 = x1;
                f0 = f1;
                x1 = x2;
                f1 = f2;
            } else {
                x1 = x2;
                f1 = f2;
            }
        }
        return (x0 + x1) / 2;
    }
}