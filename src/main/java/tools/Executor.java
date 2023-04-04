package tools;

import static tools.MathAndPrintTools.solvePoint;
import static tools.MathAndPrintTools.solvePointSinus;

public class Executor {
    private final double[] equation;
    private final int n;
    private final double eps;
    private boolean transcendent;

    public Executor(double[] equation, double accuracy, boolean transcendent) {
        this.equation = equation;
        this.eps = accuracy;
        n = equation.length;
        this.transcendent = transcendent;
    }

    public StringBuffer rootFinder(double a, double b) {
        StringBuffer res = new StringBuffer();
        int count = 0;
        double xi = a;

        for (double i = a; i < b; i += eps) {
            if (execute(xi) * execute(xi + eps) < 0) {
                res.append("for root" + (count+1) + " you can use a = ").append(xi - eps*10).append(" b = ").append(xi + eps*10).append("\n");
                count++;
            }
            xi += eps;
        }
        if (count == 0){
            res = null;
        } else if (count == 1) {
            res.setLength(0);
//            res.append("We have 1 root on this interval. Continue..\n");
        }

        return res;
    }

    public double execute(double x) {
        double ans = 0;
        for (int i = 0; i < n; i++) {
            ans += transcendent ? solvePointSinus(equation, x) : solvePoint(equation, x);
        }
        return ans/n;
    }

}
