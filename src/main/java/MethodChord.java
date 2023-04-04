import graphs.Point;
import tools.Executor;

import java.awt.geom.Point2D;

import static tools.MathAndPrintTools.*;

public class MethodChord {

    private final double[] equation;
    private final int n;
    private final double accuracy;
    private boolean transcendent;

    public MethodChord(double[] equation, double accuracy, boolean transcendent) {
        this.equation = equation;
        this.accuracy = accuracy;
        n = equation.length;
        this.transcendent = transcendent;

    }

    public void findRightSolution(double a, double b) {
        Executor executor = new Executor(equation, accuracy, transcendent);
        for (double temp = a - 2.0; temp < b + 2.0; temp += 0.1) {
            points.add(new Point(temp, transcendent ? solvePointSinus(equation, temp) : solvePoint(equation, temp)));
        }

        // print header of table
        String header = "   №|\t\t a| \t    b|\t\t   x| \t   f(a)| \t  f(b)|\t\t f(x)| \t |Xn+1-Xn||\n" +
                "+---+---------+----------+----------+----------+----------+----------+----------+\n";
        System.out.println(header);
        toFileResult.append(header);

        double[] tmp = new double[7];
        tmp[0] = a;
        tmp[1] = b;
        calc(tmp, executor);
        int cnt = 0;
        //while (Math.abs(tmp[2] - tmp[6]) > accuracy && Math.abs(tmp[5]) > accuracy) {
        while (tmp[6] > accuracy || Math.abs(tmp[5]) > accuracy) {
            print(tmp, cnt);
            cnt++;
            double x = tmp[0] - executor.execute(tmp[0]) * ((tmp[1] - tmp[0]) / (executor.execute(tmp[1]) - executor.execute(tmp[0])));
            double fx = executor.execute(x);
            if (executor.execute(tmp[0]) * fx > 0) {
                tmp[0] = x;
            } else {
                tmp[1] = x;
            }
            tmp[3] = executor.execute(tmp[0]);
            tmp[4] = executor.execute(tmp[1]);
            tmp[2]= tmp[0]-((tmp[1]-tmp[0])/(tmp[4]-tmp[3]))*tmp[3];
            tmp[5] = executor.execute(tmp[2]);
            tmp[6] = Math.abs(tmp[2]-x);
        }
        print(tmp, cnt);

        // print results
        String results = "Count iterations: " + cnt + "\n" +
                "Final x = " + tmp[2] + "\nValue =" + tmp[5];
        System.out.println(results);
        toFileResult.append(results);
    }

    private void calc(double[] tmp, Executor executor) {
        tmp[3] = executor.execute(tmp[0]);
        tmp[4] = executor.execute(tmp[1]);
        tmp[2] = tmp[0]-((tmp[1]-tmp[0])/(tmp[4]-tmp[3]))*tmp[3];
        tmp[5] = executor.execute(tmp[2]);
        tmp[6] = Math.abs(tmp[2] - (tmp[0]));
    }
}