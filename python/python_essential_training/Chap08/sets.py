#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

# Sets are MUTABLE - add, delete, or change values
# They use {}, and unordered list of UNIQUE values
# Typically used for checking set membership


def main():
    a = set("We're gonna need a bigger boat.")
    b = set("I'm sorry, Dave. I'm afraid I can't do that.")
    print_set(a)
    # you can sort using sorted()
    print_set(sorted(b))
    # in set a, but not set b
    print_set(a - b)
    # in set b, but not set a
    print_set(b - a)
    # in set a, set b, or both
    print_set(a | b)
    # in set a, set b, but NOT both
    print_set(a ^ b)
    # in set a AND set b
    print_set(a & b)


def print_set(o):
    print('{', end='')
    for x in o:
        print(x, end='')
    print('}')


if __name__ == '__main__':
    main()
