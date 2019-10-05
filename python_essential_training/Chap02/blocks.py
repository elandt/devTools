#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

# Blocks don't define scope.

x = 42
y = 73

if x < y and y == 73:
    print('x < y: x is {} and y is {}'.format(x, y))
