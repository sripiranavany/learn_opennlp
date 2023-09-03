package com.sripiranavan.opennlp.models;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NameEntityRecognitionTest {

    @Test
    public void givenEnglishPersonModel_whenNER_thenPersonsAreDetected() {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = simpleTokenizer.tokenize("John is 26 years old. His best friend's "
                + "name is Leonard. He has a sister named Penny and a brother named Rogger");

        InputStream is = getClass().getResourceAsStream("/models/en-ner-person.bin");
        try {
            TokenNameFinderModel tokenNameFinderModel = new TokenNameFinderModel(is);
            NameFinderME nameFinderME = new NameFinderME(tokenNameFinderModel);
            List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
            assertThat(spans.toString()).isEqualTo("[[0..1) person, [13..14) person, [20..21) person, [25..26) person]");
            List<String> names = new ArrayList<>();
            int k = 0;
            for (Span s : spans) {
                names.add("");
                for (int index = s.getStart(); index < s.getEnd(); index++) {
                    names.set(k, names.get(k) + tokens[index]);
                }
                k++;
            }
            assertThat(names).contains("John", "Leonard", "Penny", "Rogger");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}