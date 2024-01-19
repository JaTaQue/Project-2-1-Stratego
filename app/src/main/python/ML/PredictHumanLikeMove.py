import pandas as pd
import tensorflow as tf
from sklearn.model_selection import train_test_split
import os

class PredictHumanLikeMove:

    def __init__(self):
        # Load the TensorFlow model
        model_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'model_parameters.h5')
        self.model = tf.keras.models.load_model(model_path)


    def predict(self):
        # Build the full path to the CSV file
        data_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'PredictTest.csv')

        # Now read the CSV file
        data = pd.read_csv(data_file)
        # Separate the features and target variables if necessary
        if 'Class' in data.columns:
            data = data.drop('Class', axis=1)
            print("Dropped 'Class' column from DataFrame. Continuing...")
        else:
            print("'Class' column not found in DataFrame. Continuing...")
        
        # Make predictions using the loaded model
        prediction = self.model.predict(data)
        print(prediction.tolist())
        print("------------------------------------------------------------------------------------------------------\n")
        return prediction[0][1]


    def test(self):
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

phlm = PredictHumanLikeMove()
phlm.test()
