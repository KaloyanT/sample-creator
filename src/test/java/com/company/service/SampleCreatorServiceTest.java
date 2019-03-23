package com.company.service;

import com.company.util.ConstantsUtils;
import com.company.util.CsvUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class SampleCreatorServiceTest {

    private static final String FILENAME_FOR_TEST = ConstantsUtils.INPUT_FILE_TEST + ConstantsUtils.FILE_EXTENSION;

    private SampleCreatorService sampleCreatorService = new SampleCreatorService();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSampleInputFileNameIsNull() throws FileNotFoundException {
        this.sampleCreatorService.createSample(null);
    }

    @Test(expected = FileNotFoundException.class)
    public void testCreateSampleInputFileDoesntExist() throws FileNotFoundException {
        this.sampleCreatorService.createSample("non-existing-file");
    }

    @Test
    public void testCreateSampleReturnsSample() throws FileNotFoundException {
        this.sampleCreatorService.createSample(FILENAME_FOR_TEST);
        final var transactionList = CsvUtils.readCsvAndMapToTransaction(Paths.get(ConstantsUtils.OUTPUT_FILE + ConstantsUtils.FILE_EXTENSION));
        assertNotNull(transactionList);
        assertEquals(2, transactionList.size());
        assertEquals("user1", transactionList.get(0).getUserId());
        assertEquals("user2", transactionList.get(1).getUserId());
    }
}
