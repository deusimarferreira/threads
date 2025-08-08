package com.example.threads.test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class ResultGoodString {

    /*
     * Complete the 'largestMagical' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING binString as parameter.
     */

    public static String largestMagical(String binString) {
        List<String> goods = new ArrayList<>();

        for (int i = 0; i < binString.length(); i++) {
            char c = binString.charAt(i);
            List<String> substrings = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(c);
            substrings.add(stringBuilder.toString());
            for (int j = i + 1; j < binString.length(); j++) {
                char c2 = binString.charAt(j);
                stringBuilder.append(c2);
                substrings.add(stringBuilder.toString());
            }

            for (String string : substrings) {
                int count0 = 0, count1 = 0;
                for (int n = 0; n < string.length(); n++) {
                    if (string.charAt(n) == '0') {
                        count0++;
                    } else {
                        count1++;
                    }
                }
    
                if (count0 == count1 && !string.equals(binString)) {
                    goods.add(string);
                    i = binString.indexOf(string) + string.length() - 1;
                    break;
                }
            }
        }
        
        Collections.sort(goods, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o2.length(), o1.length());
            }
        });
        return "";
    }

}

class StudentGradeProcessingManager {
 
    public Collector<Student, ?, Map<String, Double>> getSubjectAverageGrade() {
        // Write your code here
        return Collectors.groupingBy(
            Student::getSubject,
            Collectors.averagingDouble(Student::getGrade)
        );
    }
 
    public Predicate<Student> filterBySubjectGrade(String subject, double grade) {
        // Write your code here
        return student -> student.getSubject().equals(subject) && student.getGrade() > grade;
    }
 
    public Stream<Student> getTopStudents(Stream<Student> studentStream, String subject, int top) {
        // Write your code here
        return studentStream
            .filter(student -> student.getSubject().equals(subject))
            .sorted((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()))
            .limit(top);
    }
 
    public Collector<Student, ?, Map<String, Double>> getMedianForSubject() {
        // Write your code here
        return Collectors.groupingBy(
            Student::getSubject,
            Collectors.collectingAndThen(
                Collectors.mapping(Student::getGrade, Collectors.toList()),
                grades -> {
                    Collections.sort(grades);
                    int size = grades.size();
                    if (size % 2 == 0) {
                        return (grades.get(size / 2 - 1) + grades.get(size / 2)) / 2.0;
                    } else {
                        return grades.get(size / 2);
                    }
                }
            )
        );
    }
}

public class SolutionGoodString {
    public static void main(String[] args) throws IOException {
        // BufferedReader bufferedReader = new BufferedReader(new
        // InputStreamReader(System.in));
        // BufferedWriter bufferedWriter = new BufferedWriter(new
        // FileWriter(System.getenv("OUTPUT_PATH")));

        // String binString = bufferedReader.readLine();
        String binString = "11011000";
        // String binString = "1100";
        // String binString = "1101001100";

        String result = ResultGoodString.largestMagical(binString);
        System.out.println(result);

        // bufferedWriter.write(result);
        // bufferedWriter.newLine();

        // bufferedReader.close();
        // bufferedWriter.close();
    }
}
