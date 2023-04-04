import graphs.Picture;
import tools.Executor;
import tools.MathAndPrintTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import org.jfree.*;

public class Main {
    public static double[] eq;
    public static int N;

    private static Scanner getInfo() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to enter params from file or console? (Write a file or console)");
        String str = scanner.nextLine();
        boolean isFile = false;
        while (true) {
            if (str.equalsIgnoreCase("file")) {
                isFile = true;
                break;
            }
            if (str.equalsIgnoreCase("console")) {
                break;
            }
            System.out.println("Incorrect input. Please repeat");
            str = scanner.nextLine();
        }

        if (isFile) {
            System.out.println("Enter path to file");
            File file = new File(scanner.nextLine());
            scanner = new Scanner(file);
        } else {
            System.out.println("Enter an accuracy, A and B");
        }
        return scanner;
    }
    public static void printResultToFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вывести результат в отдельный файл?(YES/NO)");
        String answer = scanner.nextLine();
        boolean toFile = false;
        if (answer.equalsIgnoreCase("yes")) {
            toFile = true;
        } else {
            System.out.println("Exiting...");
            return;
        }

        if (toFile) {
            System.out.println("Введите имя файла:");
            String filename = scanner.nextLine();
            MathAndPrintTools.printToFile(filename);
        }
    }

    public static void main(String[] args) {
        boolean transcendent = false;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose expression:\n" +
                "1)-2.4x^3 + 1.27x^2 + 8.63x + 2.31\n" +
                "2)x^3+2,28x^2-1,934x-3,907\n" +
                "3)x^2-x-sin(x)\n" +
                "4)x^3-x+4\n" +
                "5)x^2-2y=0\n  3x+3-y^2=0\n" +
                "6)x^2+y^2=4\n y=x^3\n" +
                "Input here:");

        try {
            N = scanner.nextInt();
            switch (N) {
                case 1:
                    eq = new double[4];
                    eq[0] = 2.31;
                    eq[1] = 8.63;
                    eq[2] = 1.27;
                    eq[3] = -2.4;
                    break;
                case 2:
                    eq = new double[4];
                    eq[0] = -3.907;
                    eq[1] = -1.934;
                    eq[2] = 2.28;
                    eq[3] = 1;
                    break;
                case 3:
                    eq = new double[3];
                    eq[0] = -1;
                    eq[1] = -1;
                    eq[2] = 1;
                    transcendent = true;
                    break;
                case 4:
                    eq = new double[4];
                    eq[0] = 4;
                    eq[1] = -1;
                    eq[2] = 0;
                    eq[3] = 1;
                    break;
                case 5:
                    Scanner sc5 = new Scanner(System.in);
                    System.out.println("Введите предварительные интервалы для изоляции корней:");
                    double a5 = sc5.nextDouble();
                    double b5 = sc5.nextDouble();
                    eq = new double[4];
                    eq[0] = 0.5;
                    eq[1] = 2;
                    eq[2] = 0;
                    eq[3] = 0;
                    MathAndPrintTools.fillMapSystem1(a5, b5, eq);

                    eq = new double[4];
                    eq[0] = 3;
                    eq[1] = 1;
                    eq[2] = 3;
                    eq[3] = 1;
                    MathAndPrintTools.fillMapSystem2(a5, b5, eq);
                    Picture picture1 = new Picture("graph");
                    picture1.graph(MathAndPrintTools.pointsSystem1,MathAndPrintTools.pointsSystem2);
                    System.out.println("Enter an accuracy, X0 and Y0");
                    double epsLocal = scanner.nextDouble();
                    double aLocal = scanner.nextDouble();
                    double bLocal = scanner.nextDouble();
                    int number1 = 1;
                    MethodNewTon.initSolve(epsLocal, aLocal, bLocal, number1);
                    printResultToFile();
                    System.exit(0);
                    break;
                case 6:
                    Scanner sc6 = new Scanner(System.in);
                    System.out.println("Введите предварительные интервалы для изоляции корней:");
                    double a6 = sc6.nextDouble();
                    double b6 = sc6.nextDouble();
                    eq = new double[4];
                    eq[0] = -1;
                    eq[1] = 2;
                    eq[2] = 4;
                    eq[3] = 1;
                    MathAndPrintTools.fillMapSystem1(a6, b6, eq);

                    eq = new double[4];
                    eq[0] = 1;
                    eq[1] = 3;
                    eq[2] = 0;
                    eq[3] = 0;
                    MathAndPrintTools.fillMapSystem2(a6, b6, eq);
                    Picture picture2 = new Picture("graph");
                    picture2.graph(MathAndPrintTools.pointsSystem1,MathAndPrintTools.pointsSystem2);
                    System.out.println("Enter an accuracy, X0 and Y0");
                    double epsLocal2 = scanner.nextDouble();
                    double aLocal2 = scanner.nextDouble();
                    double bLocal2 = scanner.nextDouble();
                    int number2 = 2;
                    MethodNewTon.initSolve(epsLocal2, aLocal2, bLocal2, number2);
                    printResultToFile();
                    System.exit(0);
                default:
                    System.out.println("incorrect input");
                    System.exit(12);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error with inputting. Exiting...");
            System.exit(13);
        } finally {
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Введите предварительные интервалы для изоляции корней:");
            double a1 = sc2.nextDouble();
            double b1 = sc2.nextDouble();
            MathAndPrintTools.fillMap(a1, b1, eq, transcendent);
            Picture picture2 = new Picture("graph");
            picture2.graph(MathAndPrintTools.pointsFirst, null);
        }

        try {
            scanner = getInfo();
        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found! Read from console");
            scanner = new Scanner(System.in);
        }

        double eps = scanner.nextDouble();
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double bCash = b;
        if(a > b) {
            b = a;
            a = bCash;
        }
        Executor rooter = new Executor(eq, eps, transcendent);
        StringBuffer buffer = rooter.rootFinder(a, b);
        while (buffer ==null || buffer.length() != 0) {
            if (buffer == null) {
                System.out.println("0 roots");
            } else {
                System.out.println(buffer.toString());
            }
            System.out.println("Enter new accuracy, A and B");
            eps = scanner.nextDouble();
            a = scanner.nextDouble();
            b = scanner.nextDouble();
            buffer = rooter.rootFinder(a, b);
        }
        System.out.println("We have 1 root on this interval. Continue..\n");

        // функция проверки на кол-во корней и если их несколько вывести примерные интервалы.
//        EquationSolver finder = new EquationSolver(eq, a, b, eps, transcendent);
//        List<Double> roots = finder.findRoots();
//
//        System.out.println("Найдены корни:");
//        for (Double root : roots) {
//            System.out.println(root);
//        }
        System.out.println("Choose method of solving:\n1)simpleIteration(iter)\n2)chordMethod(chord)\n3)Newton(newton)");
        scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int i;
        while (true) {
            if (str.equalsIgnoreCase("iter")) {
                i = 1;
                break;
            }
            if (str.equalsIgnoreCase("chord")) {
                i = 2;
                break;
            }
            if (str.equalsIgnoreCase("newton")) {
                i = 3;
                break;
            }
            System.out.println("Incorrect input. Please repeat");
            str = scanner.nextLine();
        }
        switch (i) {
            case 1:
                MethodSimpleIterations.initSolve(eq, a, b, eps, transcendent);
                break;
            case 2:
                MethodChord method = new MethodChord(eq, eps, transcendent);
                method.findRightSolution(a, b);
                break;
            case 3:
                MethodNewtonForEquation.initSolve(eq, a, b, eps, transcendent);
                break;
        }

        Picture picture = new Picture("graph");
        picture.graph(MathAndPrintTools.points, null);

        printResultToFile();


    }
}
