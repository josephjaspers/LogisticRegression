/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logisticregression;

import java.util.ArrayList;

/**
 *
 * @author Joseph
 */
public class LogisticRegression {

    double learningRate = 0.0003;

    double[] constantArray;
    ArrayList<double[]> trainingData;  // the last double element is the Y of each Theta 
    String[] featureNames;
    int numbFeats;

    boolean cacheUpdated = false;

    public LogisticRegression(String[] featureNames) {
        this.featureNames = featureNames;
        numbFeats = featureNames.length;
        constantArray = new double[featureNames.length];
        trainingData = new ArrayList<>();
    }

    public void updateConstants() {
        for (int i = 0; i < trainingData.size(); ++i) {
            for (int j = 0; j < numbFeats; ++j) {
                costFunction(j, i);
            }
        }
    }

    //  -y(log(H(theta)(x)) - (1 - y)log(1-H(theta)(x))
    //Logistic Regression Coursera wk 3
    private double costFunction(int j, int i) { //cost function for single update 
        int m = trainingData.size();
        double constant = constantArray[j];

        double sigma = 0;
        //Logistic Regression
        sigma += -y(i) * Math.log(HypothesisTheta(i)) - (1 - y(i)) * Math.log(1 - HypothesisTheta(i));
   System.out.println(sigma);
        sigma *= learningRate;
        constant += sigma;

        constantArray[j] = constant;
        return constant;
    }

    private double HypothesisTheta(int index) {
        double total = 0;
        for (int i = 0; i < numbFeats; ++i) {
            total += trainingData.get(index)[i] * constantArray[i];
        }
        return total;
    }

    private double y(int index) {
        return trainingData.get(index)[numbFeats]; //Get the last element in trainingData which is the learningData
    }

    private double x(int index, int jIndex) {
        return trainingData.get(index)[jIndex];
    }

    public void addLearningData(double[] data) {
        if (data.length != numbFeats + 1) {
            throw new IllegalArgumentException("Must have all features + solution");
        }
        cacheUpdated = true;
        trainingData.add(data);
    }

    public double evaluate(double[] data) {
        if (data.length != numbFeats) {
            throw new IllegalArgumentException("invalid number of features");
        }

        if (cacheUpdated) {
            updateConstants();
        }

        double total = 0;
        for (int i = 0; i < data.length; ++i) {
            total += data[i] * constantArray[i];
        }
        return total;
    }

    public double[] getConstants() {
        return constantArray;
    }

    public static void main(String[] args) {
        LogisticRegression gd = new LogisticRegression(new String[]{"F1", "F2", "F3"});

        double c1 = Math.random() * 10 % 5;
        double c2 = Math.random() * 10 % 5;
        double c3 = Math.random() * 10 % 5;

        double f1;
        double f2;
        double f3;
        double ans;

        for (int i = 0; i < 400; i++) {
            f1 = Math.random() * 10 + 10;
            f2 = Math.random() * 10 + 10;
            f3 = Math.random() * 10 + 10;

            if (f1 + f2 + f3 > 20) {
                ans = 1;
            } else {
                ans = 0;
            }

            gd.addLearningData(new double[]{f1, f2, f3, ans});
        }

        System.out.println(gd.HypothesisTheta(0));

        f1 = Math.random() * 10 + 10;
        f2 = Math.random() * 10 + 10;
        f3 = Math.random() * 10 + 10;

        System.out.println("This test involves three variables, with respective constants - " + c1 + " " + c2 + " " + c3);
        System.out.println("Testing parameters " + f1 + " " + f2 + " " + f3 + " " + "\nevaluates to: " + gd.evaluate(new double[]{f1, f2, f3}) + "\n");
        System.out.println("The constants were evaluated to be ");

        double[] theConst = gd.getConstants();
        for (int i = 0; i < theConst.length; ++i) {
            System.out.println(theConst[i]);
        }

    }
}
