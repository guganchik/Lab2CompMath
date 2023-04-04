import graphs.Point;

import static tools.MathAndPrintTools.*;
import static tools.MathAndPrintTools.toFileResult;

public class MethodNewtonForEquation {
    public static double[] exprLocal;
    public static double intervalA;
    public static double intervalB;
    public static double epsilonLocal;

    public static double X_i;
    public static double X_iPrev;

    public static boolean transcendentLocal = false;

    public static void solveAll() {
        String header = "   №|\t     X|      f(X)|   f'(X)  |  Xn+1   | |Xn+1-Xn||\n" +
                "+---+---------+----------+---------+----------+----------+\n";
        System.out.println(header);
        toFileResult.append(header);

        int counter = 1;
        double[] tmp = new double[5];
        while (Math.abs(X_i - X_iPrev) > epsilonLocal) {
            X_iPrev = X_i;
            if (!transcendentLocal) {
                X_i = X_iPrev - solvePoint(exprLocal, X_iPrev) / solvePointDerivate(exprLocal, X_iPrev, transcendentLocal);
                tmp[1] = solvePoint(exprLocal, X_iPrev);
                tmp[2] = solvePointDerivate(exprLocal, X_iPrev, transcendentLocal);
            } else {
                // Для тригонометрических функций необходимо использовать другую функцию для вычисления производной.
                X_i = X_iPrev - solvePointSinus(exprLocal, X_iPrev) / solvePointDerivate(exprLocal, X_iPrev, transcendentLocal);
                tmp[1] = solvePointSinus(exprLocal, X_iPrev);
                tmp[2] = solvePointDerivate(exprLocal, X_iPrev, transcendentLocal);
            }

            tmp[0] = X_iPrev;
            tmp[3] = X_i;
            tmp[4] = Math.abs(X_i - X_iPrev);
            print(tmp, counter);
            counter++;
        }
        System.out.print("\n");
    }

    public static boolean checkStartInterval() {
        if (transcendentLocal) {
            if (solvePointSinus(exprLocal, intervalA) * solvePointDerivate(exprLocal, X_iPrev, transcendentLocal) > 0) {
                return true;
            } else if (solvePointSinus(exprLocal, intervalB) * solvePointDerivate(exprLocal, X_iPrev, transcendentLocal) > 0) {
                return true;
            }
        } else {
            if (solvePoint(exprLocal, intervalA) * solvePointDerivate(exprLocal, X_iPrev, transcendentLocal) > 0) {
                return true;
            } else if (solvePoint(exprLocal, intervalB) * solvePointDerivate(exprLocal, X_iPrev, transcendentLocal) > 0) {
                return true;
            }
        }
        return false;
    }

    public static void initSolve(
            double[] expr,
            double a,
            double b,
            double eps,
            boolean transcendent
    ) {
        exprLocal = expr;
        intervalA = a;
        intervalB = b;
        epsilonLocal = eps;
        transcendentLocal = transcendent;

        for (double temp = a - 2.0; temp < b + 2.0; temp += 0.1) {
            points.add(new Point(temp, transcendent ? solvePointSinus(exprLocal, temp) : solvePoint(exprLocal, temp)));
        }

        if (!checkStartInterval()) {
            System.out.println("Incorrect input");
            System.exit(10);
        }
        if ( solveSecondPointDerivate(exprLocal, intervalA, transcendentLocal) * (transcendentLocal ? solvePointSinus(exprLocal, intervalA) :solvePoint(exprLocal, intervalA)) > 0) {
           X_i = intervalA;
//            X_iPrev = intervalB;
//            X_i = X_iPrev - solvePoint(exprLocal, X_iPrev) / solvePointDerivate(exprLocal, X_iPrev, transcendentLocal);
        } else {
            X_i = intervalB;
//            X_iPrev = intervalA;
        }
        System.out.println("Начальный X0 =" + X_i);

        solveAll();
        String result = "Point = " + String.format("%1$8.6f", X_i) + "\nValue = " + String.format("%1$8.6f", (transcendentLocal ? solvePointSinus(exprLocal, X_i) : solvePoint(exprLocal, X_i)));
        System.out.println(result);
        toFileResult.append(result);
    }
}
