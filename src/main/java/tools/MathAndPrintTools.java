package tools;

import graphs.Point;

import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MathAndPrintTools {
    public static double solveDerivateSinus(double[] coefficients, double x) {
        double solve = 0; // Малое число для вычисления производной
        solve += 3*x - 1 - Math.cos(x);
        return solve;
        //return (solvePointSinus(coefficients, x + h) - solvePointSinus(coefficients, x - h)) / (2 * h);
    }

    public static double solveSecondDerivateSinus(double[] coefficients, double x) {
        double solve = 0; // Малое число для вычисления производной
        solve += 3 + Math.sin(x);
        return solve;
        //return (solvePointSinus(coefficients, x + h) - solvePointSinus(coefficients, x - h)) / (2 * h);
    }

    public static double solveSecondPointDerivate(double[] coefficients, double x, boolean trenc) {
        if (trenc) {
            return solveSecondDerivateSinus(coefficients, x);
        } else {
            double[] derivative = solveDerivativePolinom(coefficients, trenc);
            return solvePoint(derivative, x);
        }
    }
    public static double solvePointDerivate(double[] coefficients, double x, boolean trenc) {
        if (trenc) {
            return solveDerivateSinus(coefficients, x);
        } else {
            double[] derivative = solveDerivativePolinom(coefficients, trenc);
            return solvePoint(derivative, x);
        }
    }

        public static double solvePointSinus(double[] expression, double point) {
        double solve = 0;

        solve += point*point-point-Math.sin(point);

        return solve;
    }

    public static double solvePoint(double[] expression, double point) {
        double solve = 0;

        for (int size = expression.length - 1; size >= 0; size--) {
            solve += expression[size]*Math.pow(point, size);
        }

        return solve;
    }

    public static double[] solveDerivativePolinom(double[] expression, boolean trenc) {
        int size = expression.length;
        if(trenc) {
            double[] derivativeSin;
            return derivativeSin = new double[]{-1, -1, 2};
        } else {
            double[] derivative = new double[size - 1];

            for (int i = 1; i < size; i++) {
                derivative[i-1] = expression[i] * (i);
            }

            return derivative;
        }
    }

    public static void print(double[] a, int count) {
        StringBuilder s = new StringBuilder(String.format(" %1$03d|", count));
        for (int i = 0; i < a.length; i++) {
            s.append(String.format("%1$8.3f | ", a[i]));
        }
        s.append("\n");
        System.out.print(s);
        toFileResult.append(s );
    }

    public static StringBuilder toFileResult = new StringBuilder();
    public static void printToFile(String filename) {
        try(FileWriter writer = new FileWriter("src/main/java/output-files/" + filename + ".txt", false))
        {
            // запись всей строки
            writer.write(toFileResult.toString());

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }


    public static List<Point> points = new ArrayList<>();
    public static List<Point> pointsFirst = new ArrayList<>();
    public static List<Point> pointsSystem1 = new ArrayList<>();
    public static List<Point> pointsSystem2 = new ArrayList<>();
//    y=x^2/2
//    y=+-sqrt(3x+3)
//
//    y=+-sqrt(4-x^2)
//    y=3x^2
//
//    eq[0] - к перед x
//    eq[1] - степ x
//    eq[2] - своб член
//    eq[3] - есть ли корень
    public static void fillMapSystem1(double a, double b, double[] eq){
        if (eq[3]!=0){
            for (double temp = b + (b-a)*0.1; temp >= a - (b-a)*0.1; temp -= (b-a)*1.2/10000) {
                double val;
                if(eq[1]!=0){
                    val = Math.pow(temp, eq[1]);
                } else{
                    val = temp;
                }
                val = val*eq[0] + eq[2];

                val = Math.sqrt(val);
                pointsSystem1.add(new Point(temp, val));
            }
            for (double temp = a - (b-a)*0.1; temp <= b + (b-a)*0.1; temp += (b-a)*1.2/10000) {
                double val;
                if(eq[1]!=0){
                    val = Math.pow(temp, eq[1]);
                } else{
                    val = temp;
                }
                val = val*eq[0] + eq[2];

                val = -Math.sqrt(val);
                pointsSystem1.add(new Point(temp, val));
            }
        } else {
            for (double temp = b + (b-a)*0.1; temp >= a - (b-a)*0.1; temp -= (b-a)*1.2/10000) {
                double val;
                if(eq[1]!=0){
                    val = Math.pow(temp, eq[1]);
                } else{
                    val = temp;
                }
                val = val*eq[0] + eq[2];
                pointsSystem1.add(new Point(temp, val));
            }
        }

    }
    public static void fillMapSystem2(double a, double b, double[] eq){
        if (eq[3]!=0){
            for (double temp = b + (b-a)*0.1; temp >= a - (b-a)*0.1; temp -= (b-a)*1.2/10000) {
                double val;
                if(eq[1]!=0){
                    val = Math.pow(temp, eq[1]);
                } else{
                    val = temp;
                }
                val = val*eq[0] + eq[2];

                val = Math.sqrt(val);
                pointsSystem2.add(new Point(temp, val));
            }
            for (double temp = a - (b-a)*0.1; temp <= b + (b-a)*0.1; temp += (b-a)*1.2/10000) {
                double val;
                if(eq[1]!=0){
                    val = Math.pow(temp, eq[1]);
                } else{
                    val = temp;
                }
                val = val*eq[0] + eq[2];

                val = -Math.sqrt(val);
                pointsSystem2.add(new Point(temp, val));
            }
        } else {
            for (double temp = b + (b-a)*0.1; temp >= a - (b-a)*0.1; temp -= (b-a)*1.2/10000) {
                double val;
                if(eq[1]!=0){
                    val = Math.pow(temp, eq[1]);
                } else{
                    val = temp;
                }
                val = val*eq[0] + eq[2];
                pointsSystem2.add(new Point(temp, val));
            }
        }

    }
    public static void fillMap(double a, double b, double[] eq, boolean trenc) {
        for (double temp = a - (b-a)*0.1; temp < b + (b-a)*0.1; temp += (b-a)*1.2/10000) {
            pointsFirst.add(new Point(temp, trenc ? solvePointSinus(eq, temp) : solvePoint(eq, temp)));
        }
    }


}
