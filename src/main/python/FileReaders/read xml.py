import xml.etree.ElementTree as ET
import os
import csv
directory = r'path where the data is at'   #you gotta change the directory to run this
counter = 0
xml_files = [f for f in os.listdir(directory) if f.endswith('.xml') and 'barrage' not in f.lower()]
field = [f"Col{i}" for i in range(1, 481)]
print("reading")
with open('trainingDataKonnie.csv', 'a', newline = '') as file:
    field.append("Class")
    writer = csv.writer(file)
    writer.writerow(field)


input = ""
finalInput = []
for xml_file in xml_files:
    file_path = os.path.join(directory, xml_file)  
    try:
        tree = ET.parse(file_path)
        print(file_path)
        counter = counter + 1
        print(counter)
        root = tree.getroot()

        field_element = root.find(".//field")
        field_content = field_element.get("content")
        count = 0
        for i in field_content:
            
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

        
        with open('trainingDataKonnie.csv', 'a', newline = '') as file:
            finalInput.append(1)
            writer = csv.writer(file)
            writer.writerow(finalInput)
            finalInput = []

        count = 0
        for i in reversed(field_content):
            count = count + 1
            if count < 41:
                ake = 1
            else:
                break
            if i == "O":
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
            elif i == "P":
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

            elif i == "Q":
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

            elif i == "R":
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

            elif i == "S":
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

            elif i == "T":
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

            elif i == "U":
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

            elif i == "V":
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

            elif i == "W":
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

            elif i == "X":
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

            elif i == "Y":
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

            elif i == "N":
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

            
        
        with open('trainingDataKonnie.csv', 'a', newline = '') as file:
            finalInput.append(1)
            writer = csv.writer(file)
            writer.writerow(finalInput)
            finalInput = []
                
    except ET.ParseError as e:
        print(f"Error parsing {xml_file}: {e}")
    except FileNotFoundError as e:
        print(f"File not found: {e}")
print("done")