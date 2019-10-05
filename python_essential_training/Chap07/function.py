#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

# When passing an immutable object as an arg to a function, it is passed by
# value, and any change to that value creates a new object.
# When passing a mutable object as an arg to a function, it is passed by
# reference, and any change to that will be reflected in the original object


def main():
    x = kitten(3)   # all functions return a value, even is that value is None
    print(type(x), x)


def kitten(a, b=1, c=0):    # example of function with default arg values
    print('Meow.')
    return 42 if a == 3 else ['Hello', 'World'] if b == 1 else {'Ello': 'Moto'}


if __name__ == '__main__':    # '__main__' is returned when this is the main
    main()                    # unit of execution
