package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Введите путь до файла .gz");
        String inputGZFile = new Scanner(System.in).nextLine();

        System.out.println("Введите путь, где хотите создать новый файл");
        String outputCsvFile = new Scanner(System.in).nextLine();


	    decompressGZ(inputGZFile,outputCsvFile+"finalCSV.csv");
        parseLogFile("C:\\IdeaProjects\\finalCSV.csv","C:\\IdeaProjects\\exampleReady.csv");
    }

    private static void decompressGZ(String gzipFile, String newFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(gzipFile);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gzipInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.close();
            gzipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void parseLogFile(String noParseCSVFile, String newFilePath) throws IOException {
        FileWriter fileWriter = new FileWriter(newFilePath);
        fileWriter.write("DATE;SERVER;SYSTEM;DATE_1;CODE;IP;TYPE;CLOB\n");
        try (BufferedReader br = new BufferedReader(new FileReader(noParseCSVFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] substr = line.replaceAll("[\\s]{2,}", " ").replaceAll(" - ", " ").split(" ", 9);
                for (int i = 0; i < 3; i++) {
                    if (i == 2) {
                        fileWriter.write(substr[i] + ";");
                    } else
                        fileWriter.write(substr[i] + "-");
                }
                for (int i = 3; i < 9; i++) {
                    fileWriter.write(substr[i] + ";");
                }
                fileWriter.write("\n");
                fileWriter.flush();
            }
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
