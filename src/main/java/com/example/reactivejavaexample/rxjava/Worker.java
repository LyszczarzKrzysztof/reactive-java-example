package com.example.reactivejavaexample.rxjava;

import rx.Observable;
import rx.Observer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.reactivejavaexample.observer.Downloader.readWebsite;

public class Worker {

    static String[] url = {"https://bykowski.pl/http-communicator-z-resttemplate-vaadin-springboot/",
            "https://bykowski.pl/maven-i-gradle-porownanie-narzedzi-i-ich-wydajnosci/",
            "https://bykowski.pl/nowe-podejscie-do-budowy-obrazow-dockera-w-spring-boot-2-3/",
            "https://bykowski.pl/swagger-ui-przejrzysta-wizualizacja-zasobow-api/"};

    public static void main(String[] args) {
        Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Finish!");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            int i = 0;

            int finalI = i;

            @Override
            public void onNext(String s) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(() -> {
                    try {
                        readWebsite(s, finalI + ".html");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                executorService.shutdown();
                System.out.println("Zakończono pracę nad elementem: "+finalI);
                finalI++;

            }
        };

        Observable.from(url).subscribe(stringObserver); // połaczenie tablicy url (musi być statyczna bo main jest statyczny)
                                                        // z observerem tzw subskrybcja
                                                        // tu już chyba czuje że tablica i się iteruje bez użycia petli
    }
}
