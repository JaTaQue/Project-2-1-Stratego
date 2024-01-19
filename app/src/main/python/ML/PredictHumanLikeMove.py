import pandas as pd
import tensorflow as tf
from sklearn.model_selection import train_test_split
import os
import os

class PredictHumanLikeMove:
    @staticmethod
    def predict():
        # Load the TensorFlow model
        model_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'model_parameters.h5')
        model = tf.keras.models.load_model(model_path)
        
        # Build the full path to the CSV file
        data_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'trainingDataKonnie.csv')

        # Now read the CSV file
        data = pd.read_csv(data_file)
        # Separate the features and target variables if necessary
        if 'Class' in data.columns:
            data = data.drop('Class', axis=1)
            print("Dropped 'Class' column from DataFrame. Continuing...")
        else:
            print("'Class' column not found in DataFrame. Continuing...")
        
        # Make predictions using the loaded model
        prediction = model.predict(data)

        return prediction[0][1]

PredictHumanLikeMove.predict()