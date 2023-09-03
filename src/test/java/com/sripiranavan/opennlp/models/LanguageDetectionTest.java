package com.sripiranavan.opennlp.models;

import opennlp.tools.langdetect.*;
import opennlp.tools.util.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class LanguageDetectionTest {

    @Test
    public void givenLanguageDictionary_whenLanguageDetect_thenLanguageIsDetected() {
        try {
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("src/main/resources/models/DoccatSample.txt"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            LanguageDetectorSampleStream sampleStream = new LanguageDetectorSampleStream(lineStream);
            TrainingParameters parameters = new TrainingParameters();
            parameters.put(TrainingParameters.ITERATIONS_PARAM, 100);
            parameters.put(TrainingParameters.CUTOFF_PARAM, 5);
            parameters.put("DataIndexer", "TwoPass");
            parameters.put(TrainingParameters.ALGORITHM_PARAM, "NAIVEBAYES");

            LanguageDetectorModel model = LanguageDetectorME.train(sampleStream, parameters, new LanguageDetectorFactory());

            LanguageDetector languageDetector = new LanguageDetectorME(model);
            Language[] languages = languageDetector.predictLanguages("estava em uma marcenaria na Rua Bruno");

            assertThat(Arrays.asList(languages)).extracting("lang", "confidence").contains(tuple("pob", 0.9999999950605625), tuple("ita", 4.939427661578518E-9), tuple("spa", 9.665954064662948E-15), tuple("fra", 8.25034992488302E-25));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}