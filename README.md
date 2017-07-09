# HealthIQ-Simulator

This project [simulates](https://docs.google.com/document/d/1OzP-DplILiBx1MNc9ucSNr_1rHLOiDYfP5ViJhtTy-w/edit) the effect 
that food and exercise have on blood sugar levels and glycation throughout one day.

Sample input files (in CSV format) for the food database, exercise database and input file are provided.

To build the application, run the following command from the root directory:
```
mvn clean install
```

To run the application, execute this command from the root directory:
```
java -jar target/simulator-1.0-SNAPSHOT.jar com.healthiq.Simulator -e exerciseDB.csv -f foodDB.csv -i inputActivity.csv
```

The application will print (to stdout) the total blood sugar levels and glycation at that point in the day (in 15-minute increments).

Example:
```
Timestamp,Blood Sugar,Glycation
...
06:15Z,69.99999999999993,0
06:30Z,59.99999999999991,0
06:45Z,49.99999999999994,0
07:00Z,39.99999999999998,0
07:15Z,39.99999999999998,0
07:30Z,39.99999999999998,0
07:45Z,39.99999999999998,0
08:00Z,39.99999999999998,0
08:15Z,39.99999999999998,0
```