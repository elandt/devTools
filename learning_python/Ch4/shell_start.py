#
# Example file for working with filesystem shell methods
#
import os
from os import path
import shutil
from zipfile import ZipFile


def main():
    # make a duplicate of an existing file
    if path.exists("learning_python/ch4/textfile.txt"):
        # get the path to the file in the current directory
        # src = path.realpath("learning_python/ch4/textfile.txt")

        # let's make a backup copy by appending "bak" to the name
        # dst = src + ".bak"

        # copy over the permissions, modification times, and other info
        # copy(src, dst)

        # rename the original file
        # old_name = "learning_python/ch4/textfile.txt"
        # new_name = "learning_python/ch4/newfile.txt"
        # rename(old_name, new_name)

        # now put things into a ZIP archive
        # root_dir, tail = path.split(src)
        # shutil.make_archive("learning_python/ch4/archive", "zip", root_dir)

        # more fine-grained control over ZIP files
        with ZipFile("learning_python/ch4/testzip.zip", "w") as newzip:
            newzip.write("learning_python/ch4/textfile.txt")
            newzip.write("learning_python/ch4/textfile.txt.bak")


def copy(src, dest):
    shutil.copy(src, dest)
    shutil.copystat(src, dest)


def rename(old_name, new_name):
    os.rename(old_name, new_name)


if __name__ == "__main__":
    main()
