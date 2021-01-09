package agh.cs.projekt;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public int[] genotype;

    public Genotype(int[] givenGene) {
        this.genotype = rightGene(givenGene);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Arrays.equals(this.genotype, genotype.genotype);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genotype);
    }

    private int[] rightGene(int[] gene) {   // mało czytelna nazwa
        int[] count = new int[8];
        for (int genome : gene) {   // raczej genom się składa z genów, niż odwrotnie
            count[genome]++;
        }
//  Dla każdego genomu, którego brakuje w genie wyszukujemy miejsce możliwe do jego wstawienia
//  poprzez podmienienie wartośći innego wystepującego więcej niż raz
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                boolean flag = true;
                while (flag) {
                    int draw = new Random().nextInt(32);    // nowy obiekt co obrót pętli
                    if (count[gene[draw]] > 1) {
                        gene[i] = i;
                        count[gene[draw]]--;
                        count[i]++;
                        flag = false;
                    }
                }
            }
        }
// następnie sortujemy genom:
        Arrays.sort(gene);
        return gene;
    }

    // a gdzie krzyżowanie?
}
