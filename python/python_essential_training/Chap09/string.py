#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


class RevStr(str):
    # this extends the built in class 'str' and overrides its '__str__' method
    # this particualr example reverses the string when printing
    def __str__(self):
        return self[::-1]


def main():
    hello = RevStr("Hello, World.")
    print(hello)


if __name__ == "__main__":
    main()
