package ru.teslo.study.matrix;

import static java.lang.Math.pow;

public class MatrixActions {

    public static int[][] sum(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }

        return resultMatrix;
    }

    public static int[][] subtraction(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
            }
        }

        return resultMatrix;
    }

    public static int[][] multiplicationByNumber(int[][] matrix, int number) {
        int[][] resultMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resultMatrix[i][j] = matrix[i][j] * number;
            }
        }

        return resultMatrix;
    }
    public static double[][] multiplicationByNumber(int[][] matrix, double number) {
        double[][] resultMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resultMatrix[i][j] = matrix[i][j] * number;
            }
        }

        return resultMatrix;
    }

    public static int[][] multiplication(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = multiplyMatricesCell(firstMatrix, secondMatrix, i, j);
            }
        }

        return resultMatrix;
    }

    private static int multiplyMatricesCell(int[][] firstMatrix, int[][] secondMatrix, int i, int j) {
        int cell = 0;
        for (int k = 0; k < secondMatrix.length; k++) {
            cell += firstMatrix[i][k] * secondMatrix[k][j];
        }
        return cell;
    }

    public static int[][] transposition(int[][] matrix) {
        int[][] resultMatrix = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resultMatrix[j][i] = matrix[i][j];
            }
        }

        return resultMatrix;
    }

    public static int determinant(int[][] matrix) {
        int result = 0;
        int[][] temporary;
        
        if (matrix.length == 1) {
            result = matrix[0][0];
        } else if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
        } else {
            for (int i = 0; i < matrix[0].length; i++) {
                System.out.println(1*(int)pow(-1, i+1));
                result += matrix[0][i] * complement(matrix, 0, i);
            }
        }

        return result;
    }

    public static int rank(int[][] matrix) {
        //А эта фигня неправильно считается, умничка какая
        int _rank = Math.min(matrix.length, matrix[0].length);
        int[][] tempMatrix = new int[matrix.length][matrix[0].length];

        for (int row = 0; row < _rank; row++) {
            if (tempMatrix[row][row] != 0) {
                for (int col = 0; col < matrix.length; col++) {
                    if (col != row) {
                        int multi = tempMatrix[col][row] / tempMatrix[row][row];
                        for (int i = 0; i < _rank; i++) {
                            tempMatrix[col][i] -= multi * tempMatrix[row][i];
                        }
                    }
                }
            } else {
                boolean reduce = true;

                for (int i = row + 1; i < matrix.length; i++) {
                    if (tempMatrix[i][row] != 0) {
                        swap(tempMatrix, row, i, _rank);
                        reduce = false;
                        break;
                    }
                }

                if (reduce) {
                    _rank--;

                    for (int i = 0; i < matrix.length; i++) {
                        tempMatrix[i][row] = tempMatrix[i][_rank];
                    }

                    row--;
                }
            }
        }

        return _rank;
    }
    
    private static void swap(int[][] matrix, int row1, int row2, int column) {
        for (int i = 0; i < column; i++) {
            int temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }

    public static int complement(int[][] matrix, int i, int j){
        return (int)pow(-1, i+j+2) * minor(matrix, i, j);
    }

    public static int minor(int[][] matrix, int i, int j) {
        int result = 0;
        int[][] tempMatrix = new int[matrix.length - 1][matrix[0].length - 1];
        int flagRow = 0;
        int flagColumn = 0;
        for (int k = 0; k < tempMatrix.length; k++){
            flagColumn = 0;
            for (int l = 0; l < tempMatrix.length; l++) {
                if (l==j) {
                    flagColumn = 1;
                }
                if(k == i){
                    flagRow = 1;
                }
                tempMatrix[k][l] = matrix[k+flagRow][l+flagColumn];
            }
        }
        result = determinant(tempMatrix);
        return result;
    }

    public static double[][] inverse(int[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        int determinant = determinant(matrix);
        System.out.println(determinant);
        int[][] compMatrix = new int[matrix.length][matrix[0].length];
        int[][] tempMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                compMatrix[i][j] = complement(matrix, i, j);
            }
        }
        tempMatrix = transposition(compMatrix);
        result = multiplicationByNumber(tempMatrix, 1 / (double)determinant);
        return result;
    }

}
