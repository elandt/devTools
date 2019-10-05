#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

from decimal import Decimal

x = 7
y = Decimal('3.14')
a = Decimal('.10') + Decimal('.10') + Decimal('.10') - Decimal('.30')
print(f'x is {x}, y is {y}')
print(y-y)
print(a)
print(type(x))
print(type(y))

f = y == x
print(f)
print(type(f))

x = (1, 'two', 3.0, [4, 'four'], {'five': 5})
print(x)
print(type(x))
print(id(x))
for i in x:
    print(i)
    print(type(i))
    print(id(i))
    print(f'i is a Dictionary {isinstance(i, dict)}')
# use isinstance(<object to check>,<type to check against>) for type checking
