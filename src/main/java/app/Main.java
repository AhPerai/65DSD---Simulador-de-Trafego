package app;

import utils.MatrixUtils;

import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException {
        MatrixUtils mUtil = new MatrixUtils();
        
        mUtil.generateMapFromFile("src/main/resources/casefiles/malha-exemplo-1.txt");

    }
}

