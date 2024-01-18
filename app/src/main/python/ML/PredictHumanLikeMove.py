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
        data_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'PredictTest.csv')

        # Now read the CSV file
        data = pd.read_csv(data_file)
        data = data.drop('Class', axis=1)
        
        # Make predictions using the loaded model
        prediction = model.predict(data)
        print(type(prediction))
        print(prediction[1000][1])
        
        return prediction[0][1]

PredictHumanLikeMove.predict()