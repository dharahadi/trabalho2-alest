import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "teste10000.txt";
        List<Caixa> caixas = new ArrayList<>();
        long start = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] valor = line.split(" ");
                int[] valores = new int[]{
                        Integer.parseInt(valor[0]),
                        Integer.parseInt(valor[1]),
                        Integer.parseInt(valor[2])
                };
                Arrays.sort(valores);
                caixas.add(new Caixa(valores));
            }
        }

        List<Caixa> maiorSeq = calculaMaiorSeq(caixas);
        System.out.println("Caminho mais longo: ");
        for (Caixa caixa : maiorSeq) {
            System.out.println(caixa.toString());
        }
        System.out.println("A maior sequência de caixas é: " + maiorSeq.size());
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Dot_" + fileName + ".dot");
        toDot(maiorSeq, sb1.toString());


        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução: " + elapsed);
    }


    public static void toDot(List<Caixa> caixas, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                StringBuilder sb = new StringBuilder();
                sb.append("digraph Caixas {").append(System.lineSeparator());
                sb.append("rankdir = LR;").append(System.lineSeparator());
                sb.append("node [shape = box];").append(System.lineSeparator());

                for (int i = 0; i < caixas.size(); i++) {
                    Caixa caixa1 = caixas.get(i);
                    for (int j = 0; j < caixas.size(); j++) {
                        Caixa caixa2 = caixas.get(j);
                        if (caixa1.cabeDentro(caixa2)) {
                            sb.append("Caixa_").append(i).append(" -> Caixa_").append(j).append(";").append(System.lineSeparator());
                        }
                    }
                }

                sb.append("}").append(System.lineSeparator());

                bw.write(sb.toString());
            }

            System.out.println("Arquivo criado com sucesso.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Caixa> calculaMaiorSeq(List<Caixa> caixas) {
        int n = caixas.size();
        Caixa[] caixasSeq = caixas.toArray(new Caixa[0]);
        Arrays.sort(caixasSeq);

        Caixa[][] seqs = new Caixa[n][];
        for (int i = 0; i < n; i++) {
            seqs[i] = new Caixa[0];
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (caixasSeq[j].cabeDentro(caixasSeq[i]) && seqs[j].length > seqs[i].length) {
                    seqs[i] = Arrays.copyOf(seqs[j], seqs[j].length);
                }
            }
            seqs[i] = Arrays.copyOf(seqs[i], seqs[i].length + 1);
            seqs[i][seqs[i].length - 1] = caixasSeq[i];
        }

        Caixa[] longestSequence = new Caixa[0];
        for (Caixa[] seq : seqs) {
            if (seq.length > longestSequence.length) {
                longestSequence = seq;
            }
        }

        return Arrays.asList(longestSequence);
    }


}
