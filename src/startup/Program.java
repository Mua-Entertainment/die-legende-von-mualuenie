package startup;

import imports.OpenSimplex2S;

public class Program {
    public static void main(String[] args) {
        long seed = 0;

        for (int x = 0; x < 100_000f; x++) {
            float noise = OpenSimplex2S.noise2(seed, x / 10f, 0);
            System.out.println(noise);
        }
    }
}