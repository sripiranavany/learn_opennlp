package com.sripiranavan.opennlp.models;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class SentenceDetectionTest {

    @Test
    public void givenEnglishModel_whenDetect_thenSentencesAreDetected() {
        String paragraph = "This is a statement. This is another statement. Now is an abstract word for time, "
                + "that is always flying. And my email address is google@gmail.com.";
        InputStream is = getClass().getResourceAsStream("/models/opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin");
        try {
            SentenceModel sentenceModel = new SentenceModel(is);
            SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(sentenceModel);
            String sentences[] = sentenceDetectorME.sentDetect(paragraph);
            assertThat(sentences).contains("This is a statement.", "This is another statement.", "Now is an abstract word for time, that is always flying.", "And my email address is google@gmail.com.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}