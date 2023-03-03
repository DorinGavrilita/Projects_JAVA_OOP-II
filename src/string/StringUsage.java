package string;

import java.util.*;
import java.io.*;


public class StringUsage {
    public static void main(String[] args) throws IOException {
     /*   // Создаем объект Scanner для чтения с клавиатуры
        Scanner scanner = new Scanner(System.in);
*/

        try {
            // Чтение фразы из файла input.txt
            BufferedReader reader = new BufferedReader(new FileReader("src/resources/input.txt"));
            String phrase = reader.readLine();
            reader.close();

            // Преобразование фразы...
            String resultUpperCase = toUpperCase(phrase);
            String resultLowerCase = toLowerCase(phrase);
            int resultLength = length(phrase);
//            int resultCountCaV = countConsonantsAndVowels(phrase);
            int resultVowels = countVowels(phrase);
            int resultCons = countConsonants(phrase);
            int resultNumberOfWords = countTheNumberOfWords(phrase);
            String resultMinMax = maxMinWord(phrase);
            FindDuplicateWords(phrase);
            allInclusive();

            // Запись результата в файл output.txt
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/output.txt"));
            writer.write(resultUpperCase + "\n \n");
            writer.write(resultLowerCase + "\n \n");
            writer.write("Length of the string = " + resultLength + "\n\r");
            writer.write("Vowels: " + resultVowels + "\n");
            writer.write("Consonants: " + resultCons + "\n \n");
            writer.write("Number of words in a phrase: " + resultNumberOfWords + "\n \n");
            writer.write(resultMinMax + "\n \n");
            writer.close();

            System.out.println("Преобразование выполнено успешно!");

        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
 /*       // Считываем строку с клавиатуры
        System.out.print("Введите строку: ");
        String input = scanner.nextLine();
*/
    }

    public static String toUpperCase(String phrase) {
        return phrase.toUpperCase();
    }

    public static String toLowerCase(String phrase) {
        return phrase.toLowerCase();
    }

    public static int length(String phrase) {
        System.out.println("\nLength of the string = " + phrase.length());
        return phrase.length();
    }


    public static int countVowels(String input) {
        String vowels = "aeiouAEIOU";
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (vowels.indexOf(input.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

    public static int countConsonants(String input) {
        String consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (consonants.indexOf(input.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

   /* public static int countConsonantsAndVowels(String input) {
        String line = input;
        int vowels = 0, consonants = 0, digits = 0, spaces = 0;

        line = line.toLowerCase();
        for (int i = 0; i < line.length(); ++i) {
            char ch = line.charAt(i);

            // check if character is any of a, e, i, o, u
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                ++vowels;
            }

            // check if character is in between a to z
            else if ((ch >= 'a' && ch <= 'z')) {
                ++consonants;
            }

           *//* // check if character is in between 0 to 9
            else if (ch >= '0' && ch <= '9') {
                ++digits;
            }

            // check if character is a white space
            else if (ch == ' ') {
                ++spaces;
            }*//*
        }

        System.out.println("\nVowels: " + vowels);
        System.out.println("Consonants: " + consonants);
        *//*System.out.println("Digits: " + digits);
        System.out.println("White spaces: " + spaces);*//*
        return consonants;
    }*/

    public static int countTheNumberOfWords(String input) {
        int countWords = input.split("\\s").length;
        System.out.println("\nNumber of words in a phrase: " + countWords + "\n");
        return countWords;
    }

    public static String maxMinWord(String input) {
        String[] words = input.split("\\s");
        String maxWord = "";
        String minWord = words[0];
        for (String w : words) {
            // this will override temp only if the word is longer
            if (w.length() > maxWord.length()) {
                maxWord = w;
            }

            if (w.length() < minWord.length()) {
                minWord = w;
            }
        }
        // don't print hardcoded "temp", but concatenate the value of the variable temp
        System.out.println("The longest word is: " + maxWord);
        System.out.println("The shortest word is: " + minWord);
        return "The longest word is: " + maxWord + "\nThe shortest word is: " + minWord;
    }


    public static void FindDuplicateWords(String input) {
        // Разбиваем строку на слова
        String[] words = input.split(" ");

        // Создаем Map, чтобы хранить количество вхождений каждого слова в строке
        Map<String, Integer> wordCount = new HashMap<>();

        // Проходим по каждому слову в строке
        for (String word : words) {
            if (wordCount.containsKey(word)) {
                // Если слово уже было встречено, увеличиваем соответствующее значение в Map
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                // Если слово встречается впервые, добавляем его в Map с начальным значением 1
                wordCount.put(word, 1);
            }
        }

        // Проходим по Map и выводим все слова, которые встречаются более одного раза
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " - occurs: " + entry.getValue() + " times.");
            }
        }

    }

    public static void allInclusive() {
        File myObj = new File("resources/input.txt");
        if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Absolute path: " + myObj.getAbsolutePath());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
        } else {
            System.out.println("The file does not exist.");
        }
    }
}