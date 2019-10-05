#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

# variable length KEYWORD arguments example


def main():
    kitten(Buffy='meow', Zilla='grr', Angel='rawr')
    x = dict(Goose='hello', Maverick='world')
    # kitten(x) # this is invalid - x is a positional argument in this case
    # kitten(*x) # this is invalid - x's contents are positional arguments here
    kitten(**x)
    y = {'Albert': 'ROAR'}
    kitten(**y)


def kitten(**kwargs):
    if len(kwargs):
        for k in kwargs:
            print('Kitten {} says {}'.format(k, kwargs[k]))
    else:
        print('Meow.')


if __name__ == '__main__':
    main()
