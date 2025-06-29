## Scratch Game

### Pre-requisites

* Java 23
* Maven 3.9.9

### Build Instructions

To install all the dependencies, run the following command:

`mvn clean install`

To skip the tests run:

`mvn clean install -DskipTests`

To package the jar file, run the following command:

`mvn package`

### Running the application

To run application, execute the following command in a terminal window from the current directory:

```
    java -jar target/take-home-assignment-0.0.1-SNAPSHOT.jar --config config.json --betting-amount 100
```

This will print the result to the standard output and look something like:

```json
{
    "matrix" : [ [ "+1000", "D", "E", "F" ], [ "B", "E", "F", "F" ], [ "D", "D", "D", "E" ], [ "F", "F", "D", "C" ] ],
    "reward" : 2120,
    "applied_winning_combinations" : {
        "D" : [ "same_symbol_5_times", "horizontally_linear_symbols" ],
        "E" : [ "same_symbol_3_times" ],
        "F" : [ "same_symbol_5_times" ]
    },
    "applied_bonus_symbol" : "+1000"
}
```

Additional Information 

The `--config` is located in `src/main/resources` directory in the `config.json` file.

The `--betting-amount` must be greater than `0`

### Issues and Resolutions

If you get the following error when running the application
```
java.lang.InternalError: platform encoding not initialized
```

Execute the following command in the terminal window

```
export MAVEN_OPTS="-Dfile.encoding=UTF-8"
```
