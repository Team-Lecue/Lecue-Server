package org.sopt.lequuServer.global;

import com.vane.badwordfiltering.BadWordFiltering;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;


@Service
public class BadWordFilterService {

    private final BadWordFiltering badWordFiltering;
    private final HashSet<String> badWords;

    public BadWordFilterService() throws IOException {
        badWordFiltering = new BadWordFiltering();

        // ArrayList 대신 HashSet 사용하면 중복된 단어는 제외할 수 있음
        badWords = new HashSet<>();

        readBadWords("BadWord.txt");
        badWordFiltering.addAll(badWords);
    }

    public String badWordChange(String string) {
        String[] change = new String[]{"*"};

        return badWordFiltering.change(string, change);
    }

    private void readBadWords(String fileName) throws IOException {
        // ClassLoader를 통해 resource 폴더의 경로를 가져옴 (ClassLoader를 사용해야 resource폴더에 있는 badword.txt가져올 수 있음)
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource(fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceUrl.openStream()))) {
            String line;

            // , 을 기준으로 분리
            while ((line = reader.readLine()) != null) {
                String[] splitWords = line.split(", ");
                Collections.addAll(badWords, splitWords);
            }
        }
    }
}