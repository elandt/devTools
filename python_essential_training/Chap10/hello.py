#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

import sys


def main():
    try:
        x = int("foo")
        print(x)
    except ValueError as valerr:
        print(f"I caught a ValueError: {valerr}")
    except ZeroDivisionError:
        print("Don't divide by 0")
    except:
        # flake8 considers this (a blanket/general 'except') to be bad practice
        print(f"unknown error: {sys.exc_info()[1]}")
    else:
        print("No error occurred")
    finally:
        print("The finally block executes regardless of whether an error occurred")


if __name__ == "__main__":
    main()
