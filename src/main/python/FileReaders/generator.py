import random


for i in range(0, 10):
    pieceArray = ["B", "B", "B", "B", "B", "B", "C", "D", "D", "D", "D", "D", "D", "D", "D", "E", "E", "E", "E", "E", "F", "F", "F", "F", "G", "G", "G", "G", "H", "H", "H", "H", "I", "I", "I", "J", "J", "K", "L", "M"] # len 40
    RandomPosArray = [] # len 40
    for i in range(0, len(pieceArray)):
        x = random.random()
        if(pieceArray[i] == "C"): 
            x = x/2
        RandomPosArray.append(x)
    lister = list(zip(RandomPosArray, pieceArray))
    lister.sort()
    
    numbers = [item[0] for item in lister]
    letters = [item[1] for item in lister]

    letters_string = ''.join(letters)

    file_path = 'formations.txt'  
    with open(file_path, 'a') as file:
        file.write(letters_string + "\n")