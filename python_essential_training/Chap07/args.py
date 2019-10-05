#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


def main():
    kitten('meow', 'grrr', 'purr')
    x = ('meow', 'grrr', 'purr', 'hello', 'world')
    kitten(x)
    kitten(*x)


def kitten(*args):    # this is an example of variable length argument list
    if len(args):
        for s in args:
            print(s)
    else:
        print('Meow.')


if __name__ == '__main__':
    main()
