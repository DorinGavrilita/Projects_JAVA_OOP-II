package collections;

import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionsUsage {

    public static void main(String[] args) {
        String inputFileName = "D:\\Dorin\\USARB\\POO II\\Java\\Project_1\\src\\resources\\client-input.txt";
        String outputFileName = "D:\\Dorin\\USARB\\POO II\\Java\\Project_1\\src\\resources\\client-final.txt";
        listOfClientNames(inputFileName);
        addClientsAge(inputFileName, outputFileName);
    }

    private static void listOfClientNames(String fileName) {
        ArrayList<String> clients = readClientsFromFile(fileName);
        Collections.sort(clients);
        System.out.println("Clients sorted in alphabetical order:");
        for (String client : clients) {
            System.out.println(client);
        }
        System.out.println();
        ArrayList<String> firstNames = extractFirstNames(clients);
        System.out.println("Distinct first names of employees:");
        for (String firstName : firstNames) {
            System.out.println(firstName);
        }
    }

    private static void addClientsAge(String inputFileName, String outputFileName) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> originalLines = readClientsFromFile(inputFileName);
        Collections.sort(originalLines);
        ArrayList<String> modifiedLines = new ArrayList<>();
        System.out.print("Enter the date of birth (dd/MM/yyyy) for:");
        for (String line : originalLines) {
            String[] name = line.split(" ");
            System.out.print("\n\t" + name[0] + " " + name[1] + " ");
            String dateString = scanner.nextLine();
            LocalDate birthDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Period age = Period.between(birthDate, LocalDate.now());
            String modifiedLine = line + "|" + birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "|" + age.getYears();
            modifiedLines.add(modifiedLine);
        }
        writeClientData(modifiedLines, outputFileName);
    }


    private static ArrayList<String> readClientsFromFile(String fileName) {
        ArrayList<String> clients = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                clients.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", fileName);
            e.printStackTrace();
        }
        return clients;
    }

    private static ArrayList<String> extractFirstNames(ArrayList<String> clients) {
        /*ArrayList<String> firstNames = new ArrayList<>();
        for (String client : clients) {
            String[] tokens = client.split(" ");
            String firstName = tokens[0];
            if (!firstNames.contains(firstName)) {
                firstNames.add(firstName);
            }
        }*/
        // Alternatively, use a HashSet to store distinct first names:
        HashSet<String> firstNamesSet = new HashSet<>();
        for (String client : clients) {
            String[] tokens = client.split(" ");
            firstNamesSet.add(tokens[0]);
        }
        ArrayList<String> firstNames = new ArrayList<>(firstNamesSet);
        Collections.sort(firstNames);
        return firstNames;
    }

    private static void writeClientData(ArrayList<String> clients, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String client : clients) {
                writer.write(client);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
