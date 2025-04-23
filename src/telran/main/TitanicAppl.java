package telran.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TitanicAppl {
    public static void main(String[] args) {
        double totalFare = 0;
        double[] fareByClass = new double[3];
        int[] countByClass = new int[3];
        int survived = 0, notSurvived = 0;
        int survivedMen = 0, notSurvivedMen = 0;
        int survivedWoman = 0, notSurvivedWoman = 0;
        int survivedChildren = 0, notSurvivedChildren = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("train.csv"))) {
            String str = br.readLine();
            str = br.readLine();

            while (str != null) {
                String[] cell = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);
                if(cell.length<12){
                   // System.out.println("Invalid line: " + str);
                    str =br.readLine();
                    continue;
                }
                int isSurvived = Integer.parseInt(cell[1]);
                int pClass = Integer.parseInt(cell[2]);
                boolean isMale = cell[4].equals("male");
                double age = cell[5].isEmpty() ? -1 : Double.parseDouble(cell[5]);
                double fare = Double.parseDouble(cell[9]);

                totalFare += fare;
                fareByClass[pClass - 1] += fare;
                countByClass[pClass - 1]++;

                if (isSurvived == 1) {
                    survived++;
                    if (isMale) survivedMen++;
                    else survivedWoman++;
                    if (age >= 0 && age < 18) survivedChildren++;
                } else {
                    notSurvived++;
                    if (isMale) notSurvivedMen++;
                    else notSurvivedWoman++;
                    if (age >= 0 && age < 18) notSurvivedChildren++;
                }

                str = br.readLine();
            }
            System.out.println(" Total Fare: " + totalFare);
            System.out.println(" Average Fare by Class:");
            for (int i = 0; i < 3; i++) {
                System.out.println("   Class " + (i + 1) + ": " + (countByClass[i] > 0 ? fareByClass[i] / countByClass[i] : 0));
            }
            System.out.println("   Total Quantity of Survived and Not Survived Passengers:");
            System.out.println("   Survived: " + survived);
            System.out.println("   Not Survived: " + notSurvived);
            System.out.println("   Total Quantity of Survived and Not Survived Men, Women, and Children (<18):");
            System.out.println("   Survived Men: " + survivedMen);
            System.out.println("   Not Survived Men: " + notSurvivedMen);
            System.out.println("   Survived Women: " + survivedWoman);
            System.out.println("   Not Survived Women: " + notSurvivedWoman);
            System.out.println("   Survived Children (<18): " + survivedChildren);
            System.out.println("   Not Survived Children (<18): " + notSurvivedChildren);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}