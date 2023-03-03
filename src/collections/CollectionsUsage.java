package collections;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class CollectionsUsage {
    public static void main(String[] args) {
        // Read clients from input file and store in a list
        List<String> clients = readClientsFromFile();

        // Sort clients in alphabetical order
        sortClients(clients);

        // Extract distinct first names of employees
        List<String> firstNames = extractFirstNames(clients);

        // Output the sorted list of clients and distinct first names to console
        System.out.println("Sorted List of Clients: ");
        for (String client : clients) {
            System.out.println(client);
        }

        System.out.println("\nDistinct First Names of Employees: ");
        for (String firstName : firstNames) {
            System.out.println(firstName);
        }

        // Append date of birth and age to input file and store in a new file
        appendDateOfBirthAndAge(clients, "src/resources/client-input.txt", "src/resources/client-final.txt");
    }

    private static List<String> readClientsFromFile() {
        List<String> clients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/client-input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                clients.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading clients from file: " + e.getMessage());
        }

        return clients;
    }

    private static void sortClients(List<String> clients) {
        clients.sort(Comparator.comparing(c -> c.split("\\|")[0]));
    }

    private static List<String> extractFirstNames(List<String> clients) {
        List<String> firstNames = new ArrayList<>();
        Set<String> distinctFirstNames = new HashSet<>();

        for (String client : clients) {
            String[] clientDetails = client.split("\\|");
            String firstName = clientDetails[0];
            String lastName = clientDetails[1];

            if (lastName.equals("employee") && !distinctFirstNames.contains(firstName)) {
                firstNames.add(firstName);
                distinctFirstNames.add(firstName);
            }
        }

        return firstNames;
    }

    private static void appendDateOfBirthAndAge(List<String> clients, String inputFilename, String outputFilename) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename))) {
            BufferedReader br = new BufferedReader(new FileReader(inputFilename));

            String line;
            while ((line = br.readLine()) != null) {
                String[] clientDetails = line.split("\\|");
                String firstName = clientDetails[0];
                String lastName = clientDetails[1];
                String dobString = clientDetails[2];

                LocalDate dob = LocalDate.parse(dobString, formatter);
                int age = Period.between(dob, LocalDate.now()).getYears();

                String newLine = line + "|" + formatter.format(dob) + "|" + age;

                bw.write(newLine);
                bw.newLine();
            }

            br.close();
        } catch (IOException e) {
            System.err.println("Error appending date of birth and age to file: " + e.getMessage());
        }
    }
}
