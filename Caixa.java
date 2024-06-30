import java.util.Arrays;

public class Caixa implements Comparable<Caixa> {
    private int[] valores;

    public Caixa(int[] valores) {
        this.valores = valores;
        ordenarValores();
    }

    private void ordenarValores() {
        Arrays.sort(valores);
    }

    public boolean cabeDentro(Caixa outraCaixa) {
        for (int i = 0; i < 3; i++) {
            if (this.valores[i] >= outraCaixa.getValores()[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] getValores() {
        return valores;
    }

    public int compareTo(Caixa outra) {
        int[] valoresA = this.getValores();
        int[] valoresB = outra.getValores();
        if (valoresA[0] != valoresB[0]) return valoresA[0] - valoresB[0];
        if (valoresA[1] != valoresB[1]) return valoresA[1] - valoresB[1];
        return valoresA[2] - valoresB[2];
    }

    @Override
    public String toString() {
        return Arrays.toString(valores);
    }
}
