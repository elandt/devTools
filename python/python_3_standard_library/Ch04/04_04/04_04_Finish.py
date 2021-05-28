# Create a Timer with the Time module

import time

run = input("Start? yes/no: ")

seconds = 0

# counts from 0 to 10, inclusive
if run == "yes":
    while seconds != 10:
        print("> ", seconds)
        time.sleep(1)
        seconds += 1
    print("> ", seconds)
