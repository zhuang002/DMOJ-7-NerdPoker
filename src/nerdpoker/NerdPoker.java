/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nerdpoker;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class NerdPoker {

    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Double> getProbabilityBackup = new HashMap();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 0; i < 1; i++) {
            doTestCase();
        }
    }

    private static void doTestCase() {
        int n = sc.nextInt();
        int s = sc.nextInt();

        System.out.println((int) expectedRolls(n, s));
    }

    private static double expectedRolls(int n, int s) {
        if (s == 1) {
            return 1;
        }

        double probability = 0;

        double avg = 0.0;

        int i = 1;
        double a;
        double threashhold = 0;
        double maxa=0;
        boolean goingDown=false;
        do {
            probability = getProbability(i, n, s);
            if (threashhold == 0) {
                threashhold = probability;
            }
            a = (double) i * probability;
            if (maxa<a) maxa=a;
            else if (maxa!=0) goingDown=true;
            avg += a;
            i++;
        } while (!(goingDown && a <= 10e-5));

        return Math.ceil(avg);
    }

    private static double getProbability(int rolls, int dices, int s) {
        double p = 1.0 / (double) s;
        double probability = 0;
        if (s == 1) {
            return 0;
        }

        if (dices == 1) {
            if (rolls == 1) {
                return p;
            }
        }
        if (rolls == 1) {
            probability=Math.pow(p, dices);
            return probability;
        }

        String key = rolls + "-" + dices + "-" + s;
        if (getProbabilityBackup.containsKey(key)) {
            return getProbabilityBackup.get(key);
        }

        for (int i = 0; i < dices; i++) {
            double p1 = combination(i, dices) * Math.pow(p, i) * (Math.pow(1.0 - p, dices - i));
            double p2 = getProbability(rolls - 1, dices - i, s);
            probability += p1 * p2;
        }
        getProbabilityBackup.put(key, probability);
        return probability;
    }

    private static double combination(int m, int n) {
        if (m == 0 || m == n) {
            return 1;
        }
        double ret = 1;
        for (int k = 0; k < m; k++) {
            ret *= (double) (n - k);
        }
        for (int k = 0; k < m; k++) {
            ret /= (double) (k + 1);
        }
        return ret;
    }

}
