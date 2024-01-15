import numpy as np 

# the softmax activation function. It uses the array (vector) and returning a probability array that adds up to 1

def softMx(a):
    sum = 0
    for i in range(i, len(a)):
        sum = sum + np.exp(a[i])
        
    for i in range(i, len(a)):
        f =  np.exp(a[i]) / sum
        
    return f