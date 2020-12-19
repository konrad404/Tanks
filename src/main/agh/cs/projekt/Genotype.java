package agh.cs.projekt;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public int[] gene;
    public Genotype(int[] givenGene){
        this.gene = rightGene(givenGene);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Arrays.equals(gene, genotype.gene);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(gene);
    }

    private int[] rightGene(int[] gene){
        int[] count = new int[8];
        for (int i =0; i< gene.length;i++){
            count[gene[i]]++;
        }
//  Dla każdego genomu którego brakuje w genie wyszukujemy miejsce możliwe do jego wstawienia
//  poprzez podmienienie wartośći innego wystepującego więcej niż raz
        for (int i =0; i< count.length;i++){
            if (count[i] ==0){
                boolean flag = true;
                while(flag){
                    int draw = new Random().nextInt(32);
                    if ( count[gene[draw]] > 1){
                        gene[i] = i;
                        count[gene[draw]] --;
                        count[i]++;
                        flag =false;
                    }
                }
            }
        }
// następnie sortujemy genom:
        Arrays.sort(gene);
        return gene;
    }
}
