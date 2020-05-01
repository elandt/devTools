#
# Read and write files using the built-in Python file methods
#


def main():
    # Open a file for writing (w) and create it if it doesn't exist (+)
    # f = open("learning_python/ch4/textfile.txt", "w+")

    # Open the file for appending (a) text to the end
    # f = open("learning_python/ch4/textfile.txt", "a")

    # write some lines of data to the file
    # for i in range(10):
    #     f.write(f"This is line {i}\n")

    # close the file when done
    # (this step is unnecessary if using "with open() as ...")
    # f.close()

    # Open the file back up and read (r) the contents
    f = open("learning_python/ch4/textfile.txt", "r")
    if f.mode == "r":
        # contents =  f.read()
        fl = f.readlines()
        for x in fl:
            print(x)
        # print(contents)


if __name__ == "__main__":
    main()
