package org.example;

import model.MatrizCase;

import java.io.IOException;

public class Main  extends MatrizCase {

    public static void main(String[] args) throws IOException {
        int[][] roadCase1 = reader("src/main/java/CaseFiles/malha-exemplo-1.txt");
        System.out.println(roadCase1);
    }
}

