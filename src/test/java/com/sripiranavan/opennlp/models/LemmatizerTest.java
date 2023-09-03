package com.sripiranavan.opennlp.models;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class LemmatizerTest {

    @Test
    public void givenEnglishDictionary_whenLemmatize_thenLemmasAreDetected() {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = simpleTokenizer.tokenize("John has a sister named Penny.");

        InputStream isPOSTagger = getClass().getResourceAsStream("/models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
        try {
            POSModel posModel = new POSModel(isPOSTagger);
            POSTaggerME posTaggerME = new POSTaggerME(posModel);
            String[] tags = posTaggerME.tag(tokens);

            InputStream isDictLema = getClass().getResourceAsStream("/models/en-lemmatizer.dict");
            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(isDictLema);
            String[] lemmas = lemmatizer.lemmatize(tokens, tags);

            assertThat(lemmas).contains("O", "have", "a", "sister", "name", "O", "O");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}