# Sample Creator Application

Sample creator Java application written for an interview take home assignment.

## Approach for the solution

Read the CSV file, map every line to a Transaction object and put all objects in an ArrayList. Sort the list by transaction amount. Then, iterate through the sorted list and group the transactions in a HashMap with the user_id and customer_id forming the Key. The value for each Key will be an ArrayList containing all the transactions for this user and customer. After that, iterate trough the HashMap and get the samples. As described in the task, a sample for each User-Customer pair is basically 5% of the transactions with the highest amount. Since the Transactions were sorted by their amount, it is enough to get the a sublist containing the first 5% of the total Transactions for each User-Customer pair. As further specified in the task, if there are less than 2 transactions for each User-Customer pair, a sample is not created. At the end,just before saving the data as a CSV file, another sorting of the sample Transactions by their ID is performed. This step is not necessary and it is only performed, so the IDs are sequential. Finally, the ArrayList containing the sample Transactions is saved to a CSV file "sample-data.csv" with the same format as the original file.

## How to start the program

Simply run "java -jar sample-creator-1.0.jar" from the command line in the directory, in which the JAR file is contained. As described in the task, the program searches for a file in the same directory with a name "source-data.csv" and creates a file "sample-data.csv". Additionally, the name of the file containing the source data can be provided as the first argument of the program, e.g. "example.csv", resulting in "java -jar sample-creator-1.0.jar example.csv".

Alternatively, the project can be imported as a Maven project in your favourite IDE and started from there. For the build, a simple "mvn clean package" or "mvn clean install" can be run from the root directory of the project, containing the "pom.xml" file.

## Dependencies

The only dependencies for the project are JUnit 4 and Mockito 2.25.1. The use of Maven is also not necessary, but it is used for easier packaging. The Maven project itself was created from the Maven quickstart architype in IntelliJ IDEA Ultimate.

## Runtime

The program takes about 150 ms to run, when started from IntelliJ IDEA Ultimate with the provided "source-data.csv" file and about 20 ms less, if the sorting by ID is not performed. Running the program from the Windows 10 PowerShell with "java -jar" takes about 100 ms with sorting. Removing the sorting doesn't influence the runtime on my machine. 

The runtime complexity is O(n) for reading the CSV file, then O(nlogn) for sorting the transactions by amount, O(n) for grouping them and O(n) for creating the sample. The optional second sort also has a complexity of O(nlogn). In total, the program has a runtime complexity of O(nlogn) and a Space Complexity of O(n).

## Side note

Please keep in mind that the program might fail, if the Locale of your System is different than the Locale used to format the CSV file. The reason for that is mainly the decimal separator symbol used in the CSV file. On my machine the locale is set to "en_GB". If the program fails on your machine, the solution for this problem is to replace the dot (".") decimal separator in the file with a comma (","), which is the decimal separator for some locales. Figuring out the used decimal separator was not part of the task, so the program doesn't perform any checks for that. Also, the CSV Separator for the file is ";", which unlike the decimal separator, is harcoded as a constant in the "ConstantUtils" class.