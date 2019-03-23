package com.company.util;

import com.company.model.Transaction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvUtils {

    private CsvUtils() {}

    private static Function<String, Transaction> mapToTransaction = (line) -> {
        String[] lineAsString = line.split(ConstantsUtils.CSV_SEPARATOR);
        Transaction currentTransaction = new Transaction();
        currentTransaction.setInvoiceId(Long.valueOf(lineAsString[0]));
        currentTransaction.setUserId(lineAsString[1]);
        currentTransaction.setCustomerId(lineAsString[2]);
        currentTransaction.setAmount(Double.valueOf(lineAsString[3]));
        return currentTransaction;
    };

    public static List<Transaction> readCsvAndMapToTransaction(Path filePath) {

        if (filePath == null) {
            return null;
        }

        List<Transaction> transactions = new ArrayList<>();

        // try-with-resources
        try(InputStream inputStream = new FileInputStream(filePath.toFile());
            var br = new BufferedReader(new InputStreamReader(inputStream))) {
            // skip  the header
            transactions = br.lines().skip(1).map(mapToTransaction).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public static void writeResultToCsv(Path path, List<Transaction> transactions) throws FileNotFoundException {

        if (path == null) {
            throw new FileNotFoundException("Filename fot output CSV not specified");
        }

        if (transactions == null) {
            throw new IllegalArgumentException("List of sample transactions is null. Cannot write it to file");
        }

        try(var writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write(ConstantsUtils.CSV_HEADERS);
            writer.newLine();

            for (Transaction t : transactions) {
                String line = CsvUtils.buildCsvLineForTransaction(t);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildCsvLineForTransaction(Transaction t) {
        var sb = new StringBuilder();
        sb.append(t.getInvoiceId());
        sb.append(ConstantsUtils.CSV_SEPARATOR);
        sb.append(t.getUserId());
        sb.append(ConstantsUtils.CSV_SEPARATOR);
        sb.append(t.getCustomerId());
        sb.append(ConstantsUtils.CSV_SEPARATOR);
        sb.append(t.getAmount());
        return sb.toString();
    }
}
