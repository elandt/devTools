# Files and File Writing

# Open a file
with open("scores.txt", "w") as myFile:

    # w --> write
    # r --> read
    # r+ --> read and write
    # a --> append
    # Show attributes and properties of that file
    print("Name " + myFile.name)
    print("Mode " + myFile.mode)

    # Write to a file
    myFile.write("GBJ : 100\nKHD : 99\nBBB : 89")

# Read the file
with open("scores.txt", "r") as myFile:
    # Reads the first 10 characters, and moves 'seek' pointer
    print("Reading... " + myFile.read(10))
    # Starts reading from the 'seek' pointer
    print("Reading again... " + myFile.read(10))
