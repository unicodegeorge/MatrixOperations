package cz.educanet.tranformations;

import kotlin.NotImplementedError;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Arrays;

public class Matrix implements IMatrix {

    private final double[][] rawArray;

    public Matrix(double[][] rawArray) {
        this.rawArray = rawArray;
    }

    @Override
    public int getRows() {
        return rawArray.length;
    }

    @Override
    public int getColumns() {
        if (getRows() > 0)
            return rawArray[0].length;

        return 0;
    }

    public boolean isMatrixValid(IMatrix matrix) {
        return matrix.getColumns() == this.getRows();
    }

    @Override
    public IMatrix times(IMatrix matrix) {
        if (isMatrixValid(matrix)) {
            IMatrix transposedMatrix = matrix.transpose();
            double[][] resultMatrix = new double[transposedMatrix.getRows()][transposedMatrix.getColumns()];
            for (int i = 0; i < this.getRows(); i++) {
                for (int p = 0; p < this.getColumns(); p++) {
                    int operationResult = 0;
                    for (int c = 0; c < this.getRows(); c++) {
                        operationResult += this.rawArray[i][c] * transposedMatrix.get(p, c);
                    }
                    resultMatrix[i][p] = operationResult;
                }
            }
            return MatrixFactory.create(resultMatrix);
        } else {
            return null;
        }

    }

    @Override
    public IMatrix times(Number scalar) {
        double[][] resultMatrix = new double[this.getRows()][this.getColumns()];
        for(int i = 0; i < this.getRows(); i++) {
            for(int y = 0; y < this.getColumns(); y++) {
                resultMatrix[i][y] = this.rawArray[i][y] * scalar.doubleValue();
            }
        }
        return MatrixFactory.create(resultMatrix);
    }

    @Override
    public IMatrix add(IMatrix matrix) {
        if(this.isMatrixValid(matrix)){
            double[][] resultMatrix = new double[this.getRows()][this.getColumns()];
            for(int i = 0; i < this.getRows(); i++) {
                for(int x =0; x < this.getColumns(); x++) {
                    resultMatrix[i][x] = this.rawArray[i][x] + matrix.get(i, x);
                }
            }
            return MatrixFactory.create(resultMatrix);
        } else {
            return null;
        }
    }

    @Override
    public double get(int n, int m) {
        return rawArray[n][m];
    }

    //region Optional
    @Override
    public IMatrix transpose() {
        Matrix matrix = new Matrix(new double[this.getRows()][this.getColumns()]);
        for(int x = 0; x < this.getRows(); x++) {
            for(int i = 0; i < this.getColumns(); i++) {
                matrix.rawArray[x][i] = this.rawArray[i][x];
            }
        }
        return matrix;
    }

    @Override
    public double determinant() {
        return 0;
    }

    //endregion
    //region Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.equals(rawArray, matrix.rawArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rawArray);
    }
    //endregion
}
