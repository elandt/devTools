#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


def main():
    # Defaults to read mode (r), and text mode (t), these
    # could also be based as the second arg to open(<filename>, 'rt')
    f = open("lines.txt")
    for line in f:
        print(line.rstrip())


if __name__ == "__main__":
    main()
