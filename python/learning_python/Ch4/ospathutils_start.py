#
# Example file for working with os.path module
#
import os
from os import path
import datetime
from datetime import date, time, timedelta
import time


def main():
    # Print the name of the OS
    print(os.name)

    # Check for item existence and type
    path_name = "learning_python/ch4/textfile.txt"
    print(f"Item exists: {path.exists(path_name)}")
    print(f"Item is a file: {path.isfile(path_name)}")
    print(f"Item is a directory: {path.isdir(path_name)}")

    # Work with file paths
    print(f"Item path: {path.realpath(path_name)}")
    print(f"Item path and name: {path.split(path.realpath(path_name))}")

    # Get the modification time
    # ctime converts the modification time to a real time
    # getmtime returns the modification time
    last_mod_time = time.ctime(path.getmtime(path_name))
    print(last_mod_time)
    last_mod_time = datetime.datetime.fromtimestamp(path.getmtime(path_name))
    print(last_mod_time)

    # Calculate how long ago the item was modified
    time_since_last_mod = datetime.datetime.now() - last_mod_time
    print(f"It has been {time_since_last_mod} since the file was modified")
    print(f"Or, {time_since_last_mod.total_seconds()} seconds")


if __name__ == "__main__":
    main()
