package com.company.service;

import com.company.model.Transaction;
import com.company.model.UserCustomerPair;
import com.company.util.ConstantsUtils;
import com.company.util.CsvUtils;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleCreatorService {

    public void createSample(String inputFileName) throws FileNotFoundException {

        if (inputFileName == null || inputFileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Specified file name is null or empty. Exiting the program");
        }

        Path path = Paths.get(inputFileName);
        if (path == null || Files.notExists(path)) {
            throw new FileNotFoundException("Specified file " + inputFileName + " doesn't exist. Exiting the program");
        }

        List<Transaction> transactions = CsvUtils.readCsvAndMapToTransaction(path);
        transactions.sort(Comparator.comparing(Transaction::getAmount).reversed());

        List<Transaction> sampleTransactions = this.extractSampleTransactions(transactions);
        // Not necessary. Perform this sort only so the IDs in the resulting file are sequential
        sampleTransactions.sort(Comparator.comparing(Transaction::getInvoiceId));

        CsvUtils.writeResultToCsv(Paths.get(ConstantsUtils.OUTPUT_FILE + ConstantsUtils.FILE_EXTENSION), sampleTransactions);
    }

    private List<Transaction> extractSampleTransactions(List<Transaction> transactions) {
        Map<UserCustomerPair, List<Transaction>> userCustomerMap = this.groupTransactionsByUserAndCustomer(transactions);
        List<Transaction> sampleTransactions =
                this.getSampleTransactionsForUserAndCustomerGroup(userCustomerMap, ConstantsUtils.SAMPLE_SIZE_PERCENT, ConstantsUtils.MIN_SAMPLE_SIZE);
        return sampleTransactions;
    }

    private Map<UserCustomerPair, List<Transaction>> groupTransactionsByUserAndCustomer(List<Transaction> transactions) {

        Map<UserCustomerPair, List<Transaction>> userCustomerMap = new HashMap<>();
        for (Transaction t : transactions) {
            var pair = new UserCustomerPair(t.getUserId(), t.getCustomerId());
            if (userCustomerMap.containsKey(pair)) {
                var currentTransactionList = userCustomerMap.get(pair);
                currentTransactionList.add(t);
                userCustomerMap.put(pair, currentTransactionList);
            } else {
                userCustomerMap.put(pair, new ArrayList<>(Arrays.asList(t)));
            }
        }

        return userCustomerMap;
    }

    private List<Transaction> getSampleTransactionsForUserAndCustomerGroup(
            Map<UserCustomerPair, List<Transaction>> userCustomerMap, int sampleSizePercent, int minSampleSize) {

        List<Transaction> sampleTransactions = new ArrayList<>();
        for (var entry : userCustomerMap.entrySet()) {
            if (entry.getValue().size() < minSampleSize) {
                continue;
            }

            int currentSampleSize = (int) Math.ceil((sampleSizePercent / 100d) * entry.getValue().size());
            sampleTransactions.addAll(entry.getValue().subList(0, currentSampleSize));
        }

        return sampleTransactions;
    }
}
