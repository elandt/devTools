# Iterative Files
with open("scores.txt", "r") as myFile:

    # Read one line at a time
    print("My one line: " + myFile.readline())
    myFile.seek(0)

    # Iterate through each line of a file
    for line in myFile:
        # NOTE: this replace does NOT impact the data in the file
        newHighScorer = line.replace("BBB", "PDJ")
        print(newHighScorer)
