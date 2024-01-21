import pandas as pd
import tensorflow as tf
from sklearn.model_selection import train_test_split
import os
# Get the absolute path of the script
script_dir = os.path.dirname(os.path.abspath(__file__))

# Build the full path to the CSV file
data_file = 'src/main/python/ML/ANNTrainingData.csv'

# Now read the CSV file
data = pd.read_csv(data_file)

# Separate the features and target variables
X = data.drop('Class', axis=1)
y = data['Class']

# Split the data into training and test sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.34, random_state=42)

# Define the number of nodes in each layer
input_nodes = 480 
hidden_nodes = 150
output_nodes = 2

# Create the model
model = tf.keras.models.Sequential([
    tf.keras.layers.Dense(hidden_nodes, activation=tf.nn.leaky_relu, input_shape=(input_nodes,)),
    tf.keras.layers.Dense(output_nodes, activation=tf.nn.softmax)
])

# Compile the model
model.compile(optimizer='SGD', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

# Train the model
model.fit(X_train, y_train, epochs=100)
train_loss, train_accuracy = model.evaluate(X_train, y_train)
test_loss, test_accuracy = model.evaluate(X_test, y_test)
print("Training Accuracy: {:.2f}%".format(train_accuracy * 100))
print("Test Accuracy: {:.2f}%".format(test_accuracy * 100))
model.save('model_parameters.h5')