import os

import pandas as pd
import tensorflow as tf
from sklearn.model_selection import train_test_split


class PredictHumanLikeMove:

    def __init__(self):
        """
        Initializes the PredictHumanLikeMove class.
        
        Loads the TensorFlow model from the specified model path.
        """
        # Load the TensorFlow model
        model_path = 'src/main/python/ML/model_parameters.h5'
        self.model = tf.keras.models.load_model(model_path)


    def predict(self):
        """
        Predicts the target variable using the loaded model.

        Returns:
            float: The predicted value for the target variable.
        """
        # Build the full path to the CSV file
        # data_file = 'app/src/main/java/AI/RandomGuess.csv'
        data_file = 'src/main/java/AI/RandomGuess.csv'

        # Now read the CSV file
        data = pd.read_csv(data_file)
        if data.empty:
            print("Data is empty. Cannot make a prediction.")
            return
        
        # Separate the features and target variables if necessary
        if 'Class' in data.columns:
            data = data.drop('Class', axis=1)
            print("Dropped 'Class' column from DataFrame. Continuing...")
        else:
            print("'Class' column not found in DataFrame. Continuing...")
        
        # Make predictions using the loaded model
        prediction = self.model.predict(data)
        #print(prediction.tolist())
        return prediction[0][1]


    def evaluate(self):
        """
        This method is used to evaluate the performance of the model by testing it on a separate test dataset.
        It reads the training data from a CSV file, splits it into features and target variables, and then splits it into training and test sets.
        The model is compiled and evaluated on both the training and test sets, and the accuracy is printed.
        """
        script_dir = os.path.dirname(os.path.abspath(__file__))

        # Build the full path to the CSV file
        data_file = os.path.join(script_dir, 'ANNTrainingData.csv')

        script_dir = os.path.dirname(os.path.abspath(__file__))

        # Now read the CSV file
        data = pd.read_csv(data_file)

        # Separate the features and target variables
        X = data.drop('Class', axis=1)
        y = data['Class']

        # Split the data into training and test sets
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.34, random_state=42)

        self.model.compile(loss='sparse_categorical_crossentropy', metrics=['accuracy'])

        train_loss, train_accuracy = self.model.evaluate(X_train, y_train)
        test_loss, test_accuracy = self.model.evaluate(X_test, y_test)
        print("Training Accuracy: {:.2f}%".format(train_accuracy * 100))
        print("Test Accuracy: {:.2f}%".format(test_accuracy * 100))
