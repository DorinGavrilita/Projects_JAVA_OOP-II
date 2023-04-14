package collections;

import java.io.*;
import java.text.*;
import java.util.*;

public class CollectionsUsage {

    public static void main(String[] args) {
        String clientNameInputFile = "D:\\Dorin\\USARB\\POO II\\Java\\Project_1\\src\\resources\\client-input.txt";
        String clientNameOutputFile = "D:\\Dorin\\USARB\\POO II\\Java\\Project_1\\src\\resources\\client-final.txt";
        String productsInput = "D:\\Dorin\\USARB\\POO II\\Java\\Project_1\\src\\resources\\products-input.txt";
        String productsOutput = "D:\\Dorin\\USARB\\POO II\\Java\\Project_1\\src\\resources\\products-output.txt";

        Scanner scanner = new Scanner(System.in);
        while (true) {


            System.out.println("\n1. Actions on the list of clients.");
            System.out.println("2. Actions on the age of clients.");
            System.out.println("3. Actions on the product list.\n");
            System.out.println("0. Exit.\n");
            System.out.println("What action do you want to take?\n(select a number)");
            int action = scanner.nextInt();

            switch (action) {
                case 1 -> listOfClientNames(clientNameInputFile);
                case 2 -> addClientsAge(clientNameInputFile, clientNameOutputFile);
                case 3 -> ProductsProcessor(productsInput, productsOutput);
            }

            if (action == 0) break;
        }

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
        System.out.println("\nClient-final.txt file has been updated successfully.");
    }

    private static int calculateAge(Date dob) {
        Date now = new Date();
        int age = now.getYear() - dob.getYear();
        if (now.getMonth() < dob.getMonth() || (now.getMonth() == dob.getMonth() && now.getDay() < dob.getDay())) {
            age--;
        }
        return age;
    }

    private static void ProductsProcessor(String inputFilePath, String outputFilePath) {
        HashMap<String, String> products = new HashMap<>();

        // Read products from file and store in HashMap
        try (Scanner scanner = new Scanner(new File(inputFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                products.put(parts[0].trim(), parts[1].trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        // Add new products
        products.put("p1234", "Banana");
        products.put("p6789", "Peach");
        products.put("p5432", "Apple");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter product code (or type 'stop' to finish adding products): ");
            String code = scanner.nextLine();

            if (code.equals("stop")) break;

            System.out.println("Enter product name: ");
            String value = scanner.nextLine();

            if (!code.isEmpty() && !value.isEmpty()) {
                products.put(code, value);
            } else {
                System.out.println("Error: code and name cannot be empty. Please try again.");
            }

            System.out.println("Do you want to add another product? (y/n): ");
            String answer = scanner.nextLine();

            if (answer.equals("n")) break;
        }


        // Update product name for the last added product
        ArrayList<String> codes = new ArrayList<>(products.keySet());
        String lastCode = codes.get(codes.size() - 1);
        System.out.println("Enter the product name for the last added product: ");
        String update = scanner.nextLine();
        products.put(lastCode, update);

        // Write products to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            for (String key : products.keySet()) {
                writer.printf("%s| %s%n", key, products.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
