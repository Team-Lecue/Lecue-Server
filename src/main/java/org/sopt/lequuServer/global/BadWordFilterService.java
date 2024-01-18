package org.sopt.lequuServer.global;

import com.vane.badwordfiltering.BadWordFiltering;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.domain.log.model.BadWordLog;
import org.sopt.lequuServer.domain.log.repository.BadWordLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class BadWordFilterService {

    private final BadWordFiltering badWordFiltering;
    private final HashSet<String> badWords;
    private final String[] symbols;
    private final BadWordLogRepository badWordLogRepository;

    public BadWordFilterService(BadWordLogRepository badWordLogRepository) throws IOException {
        badWordFiltering = new BadWordFiltering("♡");
        symbols = new String[]{"!", "@", "#", "$", "%", "^", "&", "*", "_"};

        // 비속어 필터링을 제외할 단어 설정 가능
        String[] enableList = new String[]{"똥", "바보"};
        for (String s : enableList) {
            badWordFiltering.remove(s);
        }

        // ArrayList 대신 HashSet 사용하면 중복된 단어는 제외할 수 있음
        badWords = new HashSet<>();

        readBadWords("BadWord.txt");
        badWordFiltering.addAll(badWords);

        this.badWordLogRepository = badWordLogRepository;
    }

    @Transactional
    public String badWordChange(Long memberId, String string) {
        String changedWord = badWordFiltering.change(string, symbols);

        log.info("check-test");
        if (!changedWord.equals(string)) {
            log.warn("change-check");
            badWordLogRepository.save(BadWordLog.of(memberId, string));

            log.warn("save-check ");
            String[] split = changedWord.split(" ");
            List<String> result = new ArrayList<>();

            for (String s : split) {
                if (s.contains("♡")){
                    result.add(s.replaceAll(".", "♡"));
                    continue;
                }
                result.add(s);
            }
            return String.join(" ", result);
        }

        return changedWord;
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