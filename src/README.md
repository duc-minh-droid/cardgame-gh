## Run the test suite
To run the full test suite, compile the `src` folder and then run the `CardGameTestSuite` class. This will automatically run the individual test files for each class.

### Command line instructions for Mac/Linux
1. Compile the source code to bytecode
```cmd
javac -cp lib/junit-4.13.2.jar:src src/CardGameTestSuite.java

```
2. Run the tests with JUnit
```cmd
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CardGameTestSuite

```

To run on windows replace all colons(:) with semi-colons(;)
