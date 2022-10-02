package ru.teslo.study.matrix;

public class MatrixActions {

    public static void sum(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }

        System.out.println("Итоговая матрица:");
        for (int[] matrix : resultMatrix) {
            for (int i : matrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void subtraction(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
            }
        }

        System.out.println("Итоговая матрица:");
        for (int[] matrix : resultMatrix) {
            for (int i : matrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void multiplicationByNumber(int[][] matrix, int number) {
        int[][] resultMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resultMatrix[i][j] = matrix[i][j] * number;
            }
        }

        System.out.println("Итоговая матрица:");
        for (int[] resMatrix : resultMatrix) {
            for (int i : resMatrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void multiplication(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] resultMatrix = new int[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = multiplyMatricesCell(firstMatrix, secondMatrix, i, j);
            }
        }

        System.out.println("Итоговая матрица:");
        for (int[] matrix : resultMatrix) {
            for (int i : matrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    private static int multiplyMatricesCell(int[][] firstMatrix, int[][] secondMatrix, int i, int j) {
        int cell = 0;
        for (int k = 0; k < secondMatrix.length; k++) {
            cell += firstMatrix[i][k] * secondMatrix[k][j];
        }
        return cell;
    }

    public static void transposition(int[][] matrix) {
        int[][] resultMatrix = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resultMatrix[j][i] = matrix[i][j];
            }
        }

        System.out.println("Итоговая матрица:");
        for (int[] resMatrix : resultMatrix) {
            for (int i : resMatrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
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
                temporary = new int[matrix.length-1][matrix[0].length-1];
                for (int j = 1; j < matrix.length; j++) {
                    for (int k = 0; k < matrix[0].length; k++) {
                        if (k < i) {
                            temporary[j-1][k] = matrix[j][k];
                        } else if (k > i) {
                            temporary[j-1][k-1] = matrix[j][k];
                        }
                    }
                }

                result += matrix[0][1] * Math.pow(-1, i) * determinant(temporary);
            }
        }

        return result;
    }

    public static void rank(int[][] matrix) {
        //А эта фигня неправильно считается, умничка какая
        int rank = Math.min(matrix.length, matrix[0].length);
        int[][] tempMatrix = new int[matrix.length][matrix[0].length];

        for (int row = 0; row < rank; row++) {
            if (tempMatrix[row][row] != 0) {
                for (int col = 0; col < matrix.length; col++) {
                    if (col != row) {
                        int multi = tempMatrix[col][row] / tempMatrix[row][row];
                        for (int i = 0; i < rank; i++) {
                            tempMatrix[col][i] -= multi * tempMatrix[row][i];
                        }
                    }
                }
            } else {
                boolean reduce = true;

                for (int i = row + 1; i < matrix.length; i++) {
                    if (tempMatrix[i][row] != 0) {
                        swap(tempMatrix, row, i, rank);
                        reduce = false;
                        break;
                    }
                }

                if (reduce) {
                    rank--;

                    for (int i = 0; i < matrix.length; i++) {
                        tempMatrix[i][row] = tempMatrix[i][rank];
                    }

                    row--;
                }
            }
        }

        System.out.println("Итог: "+rank);
    }
    
    private static void swap(int[][] matrix, int row1, int row2, int column) {
        for (int i = 0; i < column; i++) {
            int temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }

    public static void inverse(int[][] matrix) {
        int determinant = determinant(matrix);
        //Тут были попытки сделать инверсию. К сожалению, они не увенчались успехом.
    }

}
