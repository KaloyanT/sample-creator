package com.company;

import com.company.service.SampleCreatorService;
import com.company.util.ConstantsUtils;

import java.io.FileNotFoundException;

public class SampleCreator {

    public static void main(String[] args) {

        String fileName = null;

        if (args.length == 0) {
            System.out.println("File name not specified. Trying with sample-data.csv");
            fileName = ConstantsUtils.INPUT_FILE + ConstantsUtils.FILE_EXTENSION;
        } else {
            fileName = args[0].trim();
        }

        var sampleCreatorService = new SampleCreatorService();
        try {
            sampleCreatorService.createSample(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
