package processor;

import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);

    public static void printMenu() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
    }

    public static void printTransposeMenu() {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical diagonal");
        System.out.println("4. Horizontal diagonal");
    }

    public static void printMatrix(double[][] matrix) {
        System.out.println("The result is");
        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                System.out.printf("%f ", aDouble);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static double[][] readMatrix(String number) {
        System.out.printf("Enter size of %s: ", number);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        double[][] matrix = new double[n][m];
        System.out.printf("Enter %s: \n", number);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }

    public static double[][] addMatrices(double[][] A, double[][] B) {
        if (A.length != B.length && A[0].length != B[0].length) {
            System.out.println("ERROR");
            return new double[0][0];
        }
        double[][] sum = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                sum[i][j] = A[i][j] + B[i][j];
            }
        }
        return sum;
    }

    public static double[][] mulMatrixConst(double[][] matrix, double constant) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] *= constant;
            }
        }
        return matrix;
    }

    public static double[][] mulMatrices(double[][] matrixA, double[][] matrixB) {
        if (matrixA[0].length != matrixB.length) {
            System.out.println("The operation cannot be performed.");
            return new double[0][0];
        }
        double[][] matrixProduct = new double[matrixA.length][matrixB[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[i].length; j++) {
                for (int k = 0; k < matrixA[i].length; k++) {
                    matrixProduct[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixProduct;
    }

    public static void transposeMainDiagonal(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j > i) {
                    double temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
    }

    public static void transposeSideDiagonal(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (row - i - 1 > j) {
                    double temp = matrix[i][j];
                    matrix[i][j] = matrix[col - 1 - j][row - 1 - i];
                    matrix[col - 1 - j][row - 1 - i] = temp;
                }
            }
        }
    }

    public static void transposeVerticalDiagonal(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col / 2; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[i][col - 1 - j];
                matrix[i][col - 1 - j] = temp;
            }
        }
    }

    public static void transposeHorizontalDiagonal(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row / 2; i++) {
            for (int j = 0; j < col; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[row - 1 - i][j];
                matrix[row - 1 - i][j] = temp;
            }
        }
    }

    public static double getDeterminant(double[][] matrix) {
        if (matrix.length == 2 && matrix[0].length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        double determinant = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            determinant += matrix[0][i] * getDeterminant(getSubMatrix(matrix, 0, i)) * Math.pow(-1, i);
        }
        return determinant;
    }

    public static double[][] getSubMatrix(double[][] matrix, int row, int col) {
        double[][] subMatrix = new double[matrix.length - 1][ matrix.length - 1];
        int is = 0, js = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != row && j != col) {
                    subMatrix[is][js] = matrix[i][j]; js++;
                }
            }
            if (i != row) {
                is++; js = 0;
            }
        }
        return subMatrix;
    }

    public static double[][] getCofactors(double[][] matrix) {
        double[][] cofactors = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                cofactors[i][j] = Math.pow(-1, i + j) * getDeterminant(getSubMatrix(matrix, i, j));
            }
        }
        return cofactors;
    }

    public static void main(String[] args) {
        int option = 1;
        while (option != 0) {
            printMenu();
            System.out.print("Your choice: ");
            option = scanner.nextInt();
            if (option == 1) {

                double[][] matrixA = readMatrix("first matrix");
                double[][] matrixB = readMatrix("second matrix");
                printMatrix(addMatrices(matrixA, matrixB));
            } else if (option == 2) {

                double[][] matrix = readMatrix("matrix");
                System.out.print("Enter constant: ");
                double constant = scanner.nextDouble();
                printMatrix(mulMatrixConst(matrix, constant));
            } else if (option == 3) {

                double[][] matrixA = readMatrix("first matrix");
                double[][] matrixB = readMatrix("second matrix");
                printMatrix(mulMatrices(matrixA, matrixB));
            } else if (option == 4) {

                printTransposeMenu();
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();
                double[][] matrix = readMatrix("matrix");
                if (choice == 1) transposeMainDiagonal(matrix);
                if (choice == 2) transposeSideDiagonal(matrix);
                if (choice == 3) transposeVerticalDiagonal(matrix);
                if (choice == 4) transposeHorizontalDiagonal(matrix);
                printMatrix(matrix);
            } else if (option == 5) {
                
                double[][] matrix = readMatrix("matrix");
                System.out.println("The result is:\n" + getDeterminant(matrix) + "\n");
            } else if (option == 6) {

                double[][] matrix = readMatrix("matrix");
                double[][] cofactors = getCofactors(matrix);
                transposeMainDiagonal(cofactors);
                double[][] inverse = mulMatrixConst(cofactors, (1 / getDeterminant(matrix)));
                printMatrix(inverse);
            }
        }
    }
}
