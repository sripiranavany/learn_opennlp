package com.sripiranavan.opennlp.models;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ChunkerTest {

    @Test
    public void givenChunkerModel_whenChunk_thenChunksAreDetected() {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = simpleTokenizer.tokenize("He reckons the current account deficit will narrow to only 8 billion.");

        InputStream isPOSTagger = getClass().getResourceAsStream("/models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
        try {
            POSModel posModel = new POSModel(isPOSTagger);
            POSTaggerME posTaggerME = new POSTaggerME(posModel);
            String[] tags = posTaggerME.tag(tokens);

            InputStream isChunker = getClass().getResourceAsStream("/models/en-chunker.bin");
            ChunkerModel chunkerModel = new ChunkerModel(isChunker);
            ChunkerME chunkerME = new ChunkerME(chunkerModel);
            String[] chunks = chunkerME.chunk(tokens, tags);
            assertThat(chunks).contains("B-NP", "B-VP", "B-NP", "I-NP", "I-NP", "I-NP", "B-VP", "I-VP", "B-PP", "B-NP", "I-NP", "I-NP", "O");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}