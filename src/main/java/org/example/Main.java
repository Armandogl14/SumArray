
package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final int ARRAY_SIZE = 1000000;
    private static final int THREAD_COUNT = 6;
    private static final List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("numbers.txt"))) {
            Random random = new Random();
            for (int i = 0; i < ARRAY_SIZE; i++) {
                int number = 1 + random.nextInt(10_000);
                writer.write(Integer.toString(number));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTime = System.nanoTime();
        int sequentialSum = sequentialSum(numbers);
        long endTime = System.nanoTime();
        double elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double sequentialTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Suma secuencial: " + sequentialSum);
        System.out.println("Tiempo secuencial: " + elapsedTimeInSeconds + " segundos");

        startTime = System.nanoTime();
        int parallelSum = parallelSum(numbers, THREAD_COUNT);
        endTime = System.nanoTime();
        elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double parallelTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Suma paralela: " + parallelSum);
        System.out.println("Tiempo paralelo con "+ THREAD_COUNT + " hilos: " + elapsedTimeInSeconds + " segundos");


        //implementacion con Ley de Amdah
        double speedup = sequentialTimeInSeconds / parallelTimeInSeconds;
        double efficiency = speedup / THREAD_COUNT;

        System.out.println("Speedup: " + speedup);
        System.out.println("Eficiencia: " + efficiency);
        return;
    }

    private static int sequentialSum(List<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    private static int parallelSum(List<Integer> numbers, int threadCount) {
        Thread[] threads = new Thread[threadCount];
        int[] sums = new int[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int start = i * (ARRAY_SIZE / threadCount);
            int end = (i + 1) * (ARRAY_SIZE / threadCount);
            final int index = i;
            threads[i] = new Thread(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    sum += numbers.get(j);
                }
                sums[index] = sum;
            });
            threads[i].start();
        }

        int totalSum = 0;
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
                totalSum += sums[i];
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return totalSum;
    }
}