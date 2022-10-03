package ru.teslo.study.matrix;

import ru.teslo.study.matrix.object.Matrix;

import java.text.DecimalFormat;
import java.util.Scanner;

public class MatrixCalculator {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#####");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        runApp(scanner);
    }

    private static void runApp(Scanner scanner) {
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
                case 7 -> action = Action.RANK;
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

        Matrix firstMatrix = new Matrix(firstMatrixLines, firstMatrixColumns);
        System.out.println();
        System.out.println("Введите элементы первой матрицы по порядку.");
        for (int i = 0; i < firstMatrix.lines(); i++) {
            for (int j = 0; j < firstMatrix.columns(); j++) {
                System.out.print((i+1)+";"+(j+1)+": ");
                firstMatrix.setCell(scanner.nextInt(), i, j);
            }
        }

        System.out.println();

        System.out.println("Итоговая первая матрица:");
        printMatrix(firstMatrix);

        if (action == Action.RANK || action == Action.INVERSE_MATRIX || action == Action.DETERMINANT
                || action == Action.TRANSPOSITION) {
            //1 матрица
            try {
                System.out.println();
                System.out.println("Итог: ");
                switch (action) {
                    case RANK -> System.out.println(firstMatrix.rank());
                    case INVERSE_MATRIX -> printMatrix(firstMatrix.inverse());
                    case DETERMINANT -> System.out.println(firstMatrix.determinant());
                    case TRANSPOSITION -> printMatrix(firstMatrix.transposition());
                }
            }
            catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        } else if (action == Action.MULTIPLICATION_BY_NUMBER) {
            //1 матрица и 1 число
            System.out.print("Теперь введите число, на которое надо умножить матрицу: ");
            int number = scanner.nextInt();

            System.out.println();
            System.out.println("Итог: ");
            printMatrix(firstMatrix.multi(number));
        } else {
            //2 матрицы

            System.out.print("Введите количество строк второй матрицы: ");
            int secondMatrixLines = scanner.nextInt();
            System.out.print("Введите количество столбцов второй матрицы: ");
            int secondMatrixColumns = scanner.nextInt();

            Matrix secondMatrix = new Matrix(secondMatrixLines, secondMatrixColumns);

            System.out.println("Введите элементы второй матрицы по порядку.");
            for (int i = 0; i < secondMatrix.lines(); i++) {
                for (int j = 0; j < secondMatrix.columns(); j++) {
                    System.out.print((i+1)+";"+(j+1)+": ");
                    secondMatrix.setCell(scanner.nextInt(), i, j);
                }
            }

            System.out.println();

            System.out.println("Итоговая вторая матрица:");
            printMatrix(secondMatrix);
            System.out.println();

            System.out.println("Итог: ");
            switch (action) {
                case SUM -> printMatrix(firstMatrix.sum(secondMatrix));
                case SUBTRACTION -> printMatrix(firstMatrix.sub(secondMatrix));
                case MULTIPLICATION -> printMatrix(firstMatrix.multi(secondMatrix));
            }
        }

        System.out.print("Использовать калькулятор ещё раз? ");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("да") || answer.equalsIgnoreCase("true") || answer.equals("+") ||
                answer.equals("1") || answer.equalsIgnoreCase("yes")) {
            runApp(scanner);
        } else {
            System.out.println("Пока-пока!");
            System.exit(1);
        }
    }

    public static void printMatrix(Matrix matrix) {
        for (double[] doubles : matrix.getSimpleMatrix()) {
            for (double i : doubles) {
                System.out.print(decimalFormat.format(i) + " ");
            }
            System.out.println();
        }
    }

}
