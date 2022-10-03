package ru.teslo.study.matrix.object;

public class Matrix {
    private double[][] doubleMatrix;

    public Matrix(int lines, int columns) {
        this.doubleMatrix = new double[lines][columns];
    }

    public Matrix(double[][] doubleMatrix) {
        this.doubleMatrix = doubleMatrix;
    }

    public void setCell(double number, int line, int column) {
        doubleMatrix[line][column] = number;
    }

    public double getCell(int line, int column) {
        return doubleMatrix[line][column];
    }

    public int lines() {
        return doubleMatrix.length;
    }

    public int columns() {
        return doubleMatrix[0].length;
    }

    public Matrix cloneMatrix() {
        return new Matrix(this.doubleMatrix);
    }

    @Deprecated
    public Matrix sum(double[][] matrix) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j) + matrix[i][j], i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix sum(Matrix matrix) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j) + matrix.getCell(i, j), i, j);
            }
        }

        return resultMatrix;
    }

    @Deprecated
    public Matrix sub(double[][] matrix) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j) - matrix[i][j], i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix sub(Matrix matrix) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j) - matrix.getCell(i, j), i, j);
            }
        }

        return resultMatrix;
    }

    @Deprecated
    public Matrix multi(double[][] matrix) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(multiplyMatricesCell(doubleMatrix, matrix, i, j), i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix multi(Matrix matrix) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(multiplyMatricesCell(this, matrix, i, j), i, j);
            }
        }

        return resultMatrix;
    }

    @Deprecated
    private int multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int i, int j) {
        int cell = 0;
        for (int k = 0; k < secondMatrix.length; k++) {
            cell += firstMatrix[i][k] * secondMatrix[k][j];
        }
        return cell;
    }

    private double multiplyMatricesCell(Matrix firstMatrix, Matrix secondMatrix, int i, int j) {
        double cell = 0;
        for (int k = 0; k < secondMatrix.lines(); k++) {
            cell += firstMatrix.getCell(i, k) * secondMatrix.getCell(k, j);
        }
        return cell;
    }

    public Matrix multi(int number) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j) * number, i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix multi(double number) {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j) * number, i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix transposition() {
        Matrix resultMatrix = new Matrix(lines(), columns());
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                resultMatrix.setCell(getCell(i, j), j, i);
            }
        }

        return resultMatrix;
    }

    public double determinant() {
        if (lines() != columns()) {
            throw new ArithmeticException("Not a square matrix");
        }
        double result = 0;
        int[][] temporary;
        if (lines() == 1) {
            result = getCell(0, 0);
        } else if (lines() == 2) {
            result = ((getCell(0, 0) * getCell(1, 1)) - (getCell(0, 1) * getCell(1, 0)));
        } else {
            for (int i = 0; i < columns(); i++) {
                result += doubleMatrix[0][i] * complement(0, i);
            }
        }
        return result;
    }

    public double complement(int i, int j){
        return Math.pow(-1, i+j+2) * minor(i, j);
    }

    public double minor(int i, int j) {
        double result = 0;
        Matrix tempMatrix = new Matrix(lines() - 1, columns() - 1);
        int flagRow = 0;
        int flagColumn = 0;
        for (int k = 0; k < tempMatrix.lines(); k++){
            flagColumn = 0;
            for (int l = 0; l < tempMatrix.columns(); l++) {
                if (l==j) {
                    flagColumn = 1;
                }
                if(k == i){
                    flagRow = 1;
                }
                tempMatrix.setCell(getCell(k+flagRow, l+flagColumn), k, l);
            }
        }
        result = tempMatrix.determinant();
        return result;
    }

    public Matrix inverse() {
        double determinant = determinant();
        if (determinant == 0) {
            throw new ArithmeticException("Determinant can't be 0");
        }
        if (lines() != columns()){
            throw new ArithmeticException("Not a square matrix");
        }
        Matrix result;
        Matrix compMatrix = new Matrix(lines(), columns());
        Matrix tempMatrix;
        for (int i = 0; i < lines(); i++) {
            for (int j = 0; j < columns(); j++) {
                compMatrix.setCell(complement(i, j), i, j);
            }
        }
        tempMatrix = compMatrix.transposition();
        result = tempMatrix.multi(1 / determinant);
        return result;
    }

    public double rank() {
        int _rank = lines();
        Matrix tempMatrix = this.cloneMatrix();
        for (int row = 0; row < _rank; row++) {
            if (tempMatrix.getCell(row, row) != 0) {
                for (int col = 0; col < lines(); col++) {
                    if (col != row) {
                        double multi = tempMatrix.getCell(col, row) / tempMatrix.getCell(row, row);
                        for (int i = 0; i < _rank; i++) {
                            tempMatrix.setCell(tempMatrix.getCell(col, i) - (multi * tempMatrix.getCell(row, i)), col, i);
                        }
                    }
                }
            } else {
                boolean reduce = true;
                for (int i = row + 1; i < lines(); i++) {
                    if (tempMatrix.getCell(i, row) != 0) {
                        swap(row, i, _rank);
                        reduce = false;
                        break;
                    }
                }
                if (reduce) {
                    _rank--;
                    for (int i = 0; i < lines(); i++) {
                        tempMatrix.setCell(tempMatrix.getCell(i, _rank), i, row);
                    }
                    row--;
                }
            }
        }
        return _rank;
    }

    public void swap(int row1, int row2, int column) {
        for (int i = 0; i < column; i++) {
            double temp = getCell(row1, i);
            setCell(getCell(row2, i), row1, i);
            setCell(temp, row2, i);
        }
    }

    public double[][] getSimpleMatrix() {
        return doubleMatrix;
    }
}
