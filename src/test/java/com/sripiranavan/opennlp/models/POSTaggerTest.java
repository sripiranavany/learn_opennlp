package com.sripiranavan.opennlp.models;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class POSTaggerTest {

    @Test
    public void givenPOSModel_whenPOSTagging_thenPOSAreDetected() {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = simpleTokenizer.tokenize("John has a sister named Penny.");

        InputStream is = getClass().getResourceAsStream("/models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
        try {
            POSModel posModel = new POSModel(is);
            POSTaggerME posTaggerME = new POSTaggerME(posModel);
            String[] tags = posTaggerME.tag(tokens);
            assertThat(tags).contains("PROPN", "AUX", "DET", "NOUN", "VERB", "PROPN", "PUNCT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}