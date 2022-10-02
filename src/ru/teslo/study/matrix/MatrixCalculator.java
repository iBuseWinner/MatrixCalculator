package ru.teslo.study.matrix;

import java.util.Scanner;

public class MatrixCalculator {

    public static void main(String[] args) {
        int[][] matrix = {{-1, 2, -2}, {2, -1, 5}, {3, -2, 4}};
        double[][] result = MatrixActions.inverse(matrix);
        for (int i = 0; i < result.length; i++){
            for (int j = 0; j < result[0].length; j++){
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void runApp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Доступные действия для матриц(ы):");
        System.out.println();
        System.out.println("1) Сумма матриц");
        System.out.println("2) Разность матриц");
        System.out.println("3) Умножение матрицы на число");
        System.out.println("4) Умножение матрицы на матрицу");
        System.out.println("5) Транспонирование матрицы");
        System.out.println("6) Определитель матрицы");
        System.out.println("7) Ранг матрицы");
        System.out.println("8) Обратная матрица");
        System.out.println();
        System.out.print("Выберите действие: ");
        String actionString = scanner.next();

        Action action = null;
        try {
            int actionNumber = Integer.parseInt(actionString);
            switch (actionNumber) {
                case 1 -> action = Action.SUM;
                case 2 -> action = Action.SUBTRACTION;
                case 3 -> action = Action.MULTIPLICATION_BY_NUMBER;
                case 4 -> action = Action.MULTIPLICATION;
                case 5 -> action = Action.TRANSPOSITION;
                case 6 -> action = Action.DETERMINANT;
                case 7 -> action = Action.RANG;
                case 8 -> action = Action.INVERSE_MATRIX;
            }
        } catch (NumberFormatException e) {
            action = Action.valueOf(actionString.toUpperCase());
        }
        if (action == null) {
            action = Action.MULTIPLICATION_BY_NUMBER;
        }
        System.out.println();
        System.out.print("Введите количество строк первой матрицы: ");
        int firstMatrixLines = scanner.nextInt();
        System.out.print("Введите количество столбцов первой матрицы: ");
        int firstMatrixColumns = scanner.nextInt();

        int[][] firstMatrix = new int[firstMatrixLines][firstMatrixColumns];
        System.out.println();
        System.out.println("Введите элементы первой матрицы по порядку.");
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                System.out.print((i+1)+";"+(j+1)+": ");
                firstMatrix[i][j] = scanner.nextInt();
            }
        }

        System.out.println();

        System.out.println("Итоговая первая матрица:");
        for (int[] matrix : firstMatrix) {
            for (int i : matrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        if (action == Action.RANG || action == Action.INVERSE_MATRIX || action == Action.DETERMINANT
                || action == Action.TRANSPOSITION) {
            //1 матрица
            switch (action) {
                case RANG -> MatrixActions.rank(firstMatrix);
                case INVERSE_MATRIX -> MatrixActions.inverse(firstMatrix);
                case DETERMINANT -> System.out.println("Итог: "+ MatrixActions.determinant(firstMatrix));
                case TRANSPOSITION -> MatrixActions.transposition(firstMatrix);
            }
        } else if (action == Action.MULTIPLICATION_BY_NUMBER) {
            //1 матрица и 1 число
            System.out.print("Теперь введите число, на которое надо умножить матрицу: ");
            int number = scanner.nextInt();

            MatrixActions.multiplicationByNumber(firstMatrix, number);
        } else {
            //2 матрицы

            System.out.print("Введите количество строк второй матрицы: ");
            int secondMatrixLines = scanner.nextInt();
            System.out.print("Введите количество столбцов второй матрицы: ");
            int secondMatrixColumns = scanner.nextInt();

            int[][] secondMatrix = new int[secondMatrixLines][secondMatrixColumns];

            System.out.println("Введите элементы второй матрицы по порядку.");
            for (int i = 0; i < secondMatrix.length; i++) {
                for (int j = 0; j < secondMatrix[i].length; j++) {
                    System.out.print((i+1)+";"+(j+1)+": ");
                    secondMatrix[i][j] = scanner.nextInt();
                }
            }

            System.out.println("Итоговая вторая матрица:");
            for (int[] matrix : secondMatrix) {
                for (int i : matrix) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }

            switch (action) {
                case SUM -> MatrixActions.sum(firstMatrix, secondMatrix);
                case SUBTRACTION -> MatrixActions.subtraction(firstMatrix, secondMatrix);
                case MULTIPLICATION -> MatrixActions.multiplication(firstMatrix, secondMatrix);
            }
        }

        System.out.print("Использовать калькулятор ещё раз? ");
        String answer = scanner.next();
        scanner.close();
        if (answer.equalsIgnoreCase("да") || answer.equalsIgnoreCase("true") || answer.equals("+") || answer.equals("1") || answer.equalsIgnoreCase("yes")) {
            //Исправить ошибку
            runApp();
        } else {
            System.out.println("Пока-пока!");
            System.exit(1);
        }
    }

}
