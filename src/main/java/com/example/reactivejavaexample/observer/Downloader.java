package com.example.reactivejavaexample.observer;

import java.io.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    String[] url = {"https://bykowski.pl/http-communicator-z-resttemplate-vaadin-springboot/",
            "https://bykowski.pl/maven-i-gradle-porownanie-narzedzi-i-ich-wydajnosci/",
    "https://bykowski.pl/nowe-podejscie-do-budowy-obrazow-dockera-w-spring-boot-2-3/",
    "https://bykowski.pl/swagger-ui-przejrzysta-wizualizacja-zasobow-api/"};

    public Downloader(MyObservable myObservable) {
        int i=0;
        for (String s:url) {
            int finalI = i;
            executorService.submit(()->{
                try {
                    readWebsite(s, finalI +".html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            i++;
        }
        executorService.shutdown();
        myObservable.onFinish();
    }


    public static void readWebsite(String link, String fileName) throws IOException {
        URL otodom = new URL(link);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(otodom.openStream()));

        String inputLine;

        StringBuilder stringBuilder = new StringBuilder(); // mozna jeszcze StringBuffer, StringBuilder jest wolniejszy
        // ale "odporny" na wielowątkowość


        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator()); // dodaje znak nowej linii
        }
        in.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
        bw.write(stringBuilder.toString());
        bw.close();
    }
}
