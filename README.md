# Group 7 - Stratego

This is a coding project for implementing the board game Stratego. The project is developed using Java and JavaFX for the graphical user interface.

## Dependencies
The following python packages are required to run the machine learning
models in the background:
- pandas==2.1.4
- scikit-learn==1.3.2
- tensorflow==2.15

### You can install the required packages by running `pip install -r requirements.txt`


## Running the Project

The project uses Gradle as a build tool and for managing java dependencies. Gradle also handles the installation of JavaFX.

To run the project, use the following command in the terminal:

bash
./gradlew run

This command will start the Gradle run task, which will compile the project, install necessary dependencies including JavaFX, and start the application. If it doesn't work, run the build command first.

Alternatively, you can run the `Test.java` file to perform a faster simulation in the terminal.


## Game Rules

Stratego is a strategy board game for two players. The objective of the game is to find and capture the opponent's Flag, or to capture so many enemy pieces that the opponent cannot make any further moves.

