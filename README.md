## Run the test suite
To run the full test suite, compile the `src` folder and then run the `CardGameTestSuite` class. This will automatically run the individual test files for each class.

### Command line instructions for Mac/Linux
1. The tests must be run from the `src` directory. To switch to it from the root folder, use:
```cmd
cd src
```
2. Compile the source code to bytecode
```cmd
javac -cp .:..lib/junit-4.13.2.jar:src CardGameTestSuite.java
```
3. Run the tests with JUnit
```cmd
javac -cp .:..lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:bin:.:src org.junit.runner.JUnitCore CardGameTestSuite
```

To run on windows replace all colons(:) with semi-colons(;)
