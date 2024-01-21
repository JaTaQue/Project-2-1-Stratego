# Group 7 - Stratego

This is a coding project for implementing the board game Stratego. The project is developed using Java and JavaFX for the graphical user interface.

## Dependencies

The project requires Python 3.8 or higher to run. Please note that Python 3.12 is not supported.
The project requires Java 11 or higher to run.
The project uses Gradle as a build tool and for managing java dependencies.
Gradle also handles the installation of JavaFX.

The following python packages are required to run the machine learning
models in the background:
- pandas==2.1.4
- scikit-learn==1.3.2
- tensorflow==2.15
- jep==4.2.0
- numpy==1.24.3

## Building the project

Gradle will automatically create a Python virtual environment and install these packages. It also sets the `java.library.path` to include the path to the Jep native library. This setup is cross-platform and should work on both Windows and Unix-like systems.

```bash
./gradlew build
```

If you want to manually install the required packages, you can run `pip install -r requirements.txt`.

## Running the Project

The project uses Gradle as a build tool and for managing java dependencies. Gradle also handles the installation of JavaFX.

To run the project, use the following command in the terminal:

```bash
./gradlew run
```
This command will start the Gradle run task, which will compile the project, install necessary dependencies including JavaFX, and start the application. If it doesn't work, run the build command first.  Alternatively, you can run the Test.java file to perform a faster simulation in the terminal.  
## Game Rules
Stratego is a strategy board game for two players. The objective of the game is to find and capture the opponent's Flag, or to capture so many enemy pieces that the opponent cannot make any further moves.