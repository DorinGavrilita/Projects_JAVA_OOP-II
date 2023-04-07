package collections;

import java.io.*;
import java.text.*;
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
        int i = 1;
        for (String firstName : firstNames) {
            System.out.println(i + " \t" + firstName);
            i++;
        }
        System.out.println('\n');
    }

    private static void addClientsAge(String inputFileName, String outputFileName) {
        ArrayList<String> clients = readClientsFromFile(inputFileName);
        Collections.sort(clients);
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<String> modifiedClients = new ArrayList<>();
        System.out.print("Enter the date of birth (dd/MM/yyyy) for:");
        for (String client : clients) {
            System.out.print("\t" + client + " ");
            String dateStr = scanner.nextLine();
            try {
                Date dob = dateFormat.parse(dateStr);
                int age = calculateAge(dob);
                String modifiedClient = client + " " + dateFormat.format(dob) + "| " + age;
                modifiedClients.add(modifiedClient);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Skipping client " + client);
            }
        }
        writeClientData(modifiedClients, outputFileName);
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
        System.out.println("Client-final.txt file has been updated successfully.");
    }

    private static int calculateAge(Date dob) {
        Date now = new Date();
        int age = now.getYear() - dob.getYear();
        if (now.getMonth() < dob.getMonth() || (now.getMonth() == dob.getMonth() && now.getDay() < dob.getDay())) {
            age--;
        }
        return age;
    }

}
