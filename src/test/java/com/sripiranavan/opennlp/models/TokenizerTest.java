package com.sripiranavan.opennlp.models;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class TokenizerTest {

    @Test
    public void givenEnglishModel_whenTokenize_thenTokensAreDetected() {
        InputStream is = getClass().getResourceAsStream("/models/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
        try {
            TokenizerModel tokenizerModel = new TokenizerModel(is);
            TokenizerME tokenizerME = new TokenizerME(tokenizerModel);
//            Case sensitive
            String[] tokens = tokenizerME.tokenize("Java is a programming language.");
            assertThat(tokens).contains("Java", "is", "a", "programming", "language", ".");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenWhitespaceTokenizer_whenTokenize_thenTokensAreDetected() {
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
        //            Case sensitive
        String[] tokens = whitespaceTokenizer.tokenize("Java is a programming language.");
        assertThat(tokens).contains("Java", "is", "a", "programming", "language.");
    }

    @Test
    public void givenSimpleTokenizer_whenTokenize_thenTokensAreDetected() {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        //            Case sensitive
        String[] tokens = simpleTokenizer.tokenize("Java is a programming language.");
        assertThat(tokens).contains("Java", "is", "a", "programming", "language", ".");
    }
}