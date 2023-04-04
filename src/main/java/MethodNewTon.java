import tools.MathAndPrintTools;

import static java.lang.Math.sqrt;

public class MethodNewTon {
    // x^2-2y=0
    // 3x-y^2+3=0
//    public static double[] dF_po_dx = {2.0, 0.0, 0.0};
//    public static double[] dF_po_dy = {0.0, 0.0, -2.0};
//    public static double[] dG_po_dx = {0.0, 0.0, 3.0};
//    public static double[] dG_po_dy = {0.0, -2.0, 0.0};
    // после перемножения якобиана матрицы дельт
    //2xdx-2dy=2y-x^2
    //3dx-2ydy=-3+y^2-3x

    // double[size = 3] -> index:
    // double[0] -> before x
    // double[1] -> before y
    // double[2] -> before free coef

    // x^2+y^2=4
    // y = 3x^2
//    public static double[] dF_po_dx = {2.0, 0.0, 0.0};
//    public static double[] dF_po_dy = {0.0, 0.0, -2.0};
//    public static double[] dG_po_dx = {0.0, 0.0, 3.0};
//    public static double[] dG_po_dy = {0.0, -2.0, 0.0};
    // после перемножения якобиана матрицы дельт
    //2xdx-2dy=2y-x^2
    //3dx-2ydy=-3+y^2-3x

    public static int number;
    public static double X_0;
    public static double X_1;
    public static double Y_0;
    public static double Y_1;
    public static double deltaX;
    public static double deltaY;
    public static double[][] matrix_for_lab_1 = new double[2][3];

    public static double precision;

    public static void search_dx_dy(int number) { //поиск корней по первой лабе
        if (MethodNewTon.number == 1) {
            matrix_for_lab_1[0][0] = 2*X_0;
            matrix_for_lab_1[0][1] = -2;
            matrix_for_lab_1[0][2] = 2*Y_0 - X_0*X_0;
            matrix_for_lab_1[1][0] = 3;
            matrix_for_lab_1[1][1] = -2*Y_0;
            matrix_for_lab_1[1][2] = -3 + Y_0*Y_0 - 3*X_0;
        }
        if(MethodNewTon.number == 2) {
            matrix_for_lab_1[0][0] = 2*X_0;
            matrix_for_lab_1[0][1] = 2*Y_0;
            matrix_for_lab_1[0][2] = -X_0*X_0 - Y_0*Y_0 + 4;
            matrix_for_lab_1[1][0] = -3*X_0*X_0;
            matrix_for_lab_1[1][1] = 1;
            matrix_for_lab_1[1][2] = X_0*X_0*X_0 - Y_0;
        }
    }

    public static void initSolve(
            double precision,
            double a, // начальные значения
            double b,  // начальные значения
            int i

    ) {
        double[] tmp = new double[2];
        X_0 = a;
        Y_0 = b;
        number = i;
        MethodNewTon.precision = precision;

        String header = "Начальные данные:\nX_0 = " + X_0 + "\nY_0 = " + Y_0;
        System.out.println(header);
        MathAndPrintTools.toFileResult.append(header);

        MatrixActionsFromLab1.SIZE = 2;
        int counter = 0;
        while (true) {
            counter++;

            search_dx_dy(number);
            MatrixActionsFromLab1.setMatrixAandB(matrix_for_lab_1);
            MatrixActionsFromLab1.initMatrixX1andX2();

            tmp = MatrixActionsFromLab1.startComputed();
            deltaX = tmp[0];
            deltaY = tmp[1];

            X_1 = X_0 + deltaX;
            Y_1 = Y_0 + deltaY;
            if (check() || counter > 20) {
                break;
            }


            X_0 = X_1;
            Y_0 = Y_1;
        }

//        String finalResult = "\n\nПрошло итераций = " + counter + "\n" +
//                "X = " + String.format("%1$8.3f", X_1) + "; \nY = " + String.format("%1$8.3f;", Y_1) + "\n" +
//                "Вектор погрешностей:" + "\n" +
//                "for X: " + String.format("%1$8.3f", (X_1 - X_0)) + "\n" +
//                "for Y: " + String.format("%1$8.3f", (Y_1 - Y_0)) + "\n";
        StringBuffer finalResult = new StringBuffer("\n\nПрошло итераций = " + counter + "\n" +
                "X = " + String.format("%1$8.3f", X_1) + "; \nY = " + String.format("%1$8.3f;", Y_1) + "\n" +
                "Вектор погрешностей:" + "\n" +
                "for X: " + String.format("%1$8.3f", (X_1 - X_0)) + "\n" +
                "for Y: " + String.format("%1$8.3f", (Y_1 - Y_0)) + "\n");
        if (number == 1) {
            finalResult.append("F1(x): ").append(String.format("%1$8.10f", X_1 * X_1 - 2 * Y_1)).append("\n").append("F2(x): ").append(String.format("%1$8.10f", 3 * X_1 + 3 - Y_0*Y_0)).append("\n");
//        } else if (number == 2 && X_0 > 0){
//            finalResult.append("F1(x): ").append(String.format("%1$8.10f", sqrt(4 - X_1 * X_1))).append("\n").append("F2(x): ").append(String.format("%1$8.10f", X_1 * X_1 * X_1)).append("\n");
        } else {
            finalResult.append("F1(x): ").append(String.format("%1$8.10f", X_1 * X_1 + Y_1 * Y_1 -4)).append("\n").append("F2(x): ").append(String.format("%1$8.10f", Y_1 - (X_1 * X_1 * X_1))).append("\n");
        }

        System.out.println(finalResult);
        MathAndPrintTools.toFileResult.append(finalResult);
    }

    public static boolean check() {
        return (Math.abs(X_1-X_0) <= precision || Math.abs(Y_1-Y_0) <= precision);
    }

}
