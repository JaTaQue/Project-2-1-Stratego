import numpy as np 

# leaky relu actiavtion function 

def leaky(x):
    f = max(0.01*x , x)
    
    return f