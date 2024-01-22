import csv
import random

def gene():
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

    return letters_string

countas = 0
directory = r'put the path here'  #you gotta change the directory to run this
counter = 0

field = [f"Col{i}" for i in range(1, 481)]
field.append("Class")
print("reading")

with open('txtDataComrad.csv', 'a', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(field)

for i in range(0, 80000):
        print(i)
        data = gene()
        count = 0
        finalInput = []
        for i in data:
            
            count = count + 1
            if count < 41:
                aka = 2
            else:
                break
            if i == "C":
                input = "100000000000"
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
            elif i == "D":
                input = "010000000000"
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "E":
                input = "001000000000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "F":
                input = "000100000000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "G":
                input = "000010000000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "H":
                input = "000001000000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "I":
                input = "000000100000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "J":
                input = "000000010000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "K":
                input = "000000001000"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "L":
                input = "000000000100"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")
                finalInput.append("0")

            elif i == "M":
                input = "000000000010"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")
                finalInput.append("0")

            elif i == "B":
                input = "000000000001"
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("0")
                finalInput.append("1")

        with open('txtDataComrad.csv', 'a', newline='') as file:
            writer = csv.writer(file)
            finalInput.append(0)
            writer.writerow(finalInput)