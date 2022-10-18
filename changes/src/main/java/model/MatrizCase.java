package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MatrizCase {

        protected static int[][] reader(String mPath) throws IOException {
            //Recuperando o documento e o listando todas as linhas


            Path path = Paths.get(mPath);
            List<String> lines = Files.readAllLines(path);

            int[][] road = new int[Integer.parseInt(lines.get(0))][Integer.parseInt(lines.get(1))];
            StringBuilder strRoad = new StringBuilder();


            for (int i = 2; i < lines.size(); i++) {
                String[] line = lines.get(i).split("\t")[0].split(" ");


                for (int j = 0; j < line.length; j++) {
                    road[i - 2][j] = Integer.parseInt(line[j]);
                    strRoad.append(line[j] + " ");
                }
                strRoad.append("\n");
            }

            System.out.println(strRoad);

            return road;
        }
}
