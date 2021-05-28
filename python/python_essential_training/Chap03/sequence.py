#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

print('This is a List')
# lists are mutable and use []
x = [1, 2, 3, 4, 5]
for i in x:
    print(f'i is {i}')

print('This is a Tuple')
# tuples are immutable and use ()
y = (5, 4, 3, 2, 1)
for i in y:
    print(f'i is {i}')

print('This is a Range')
# ranges are immutable - ranges are 0 indexed by default, are exclusive on the
# upper end of the range (up to, but won't include the stop value passed)
# ranges can take up to 3 params: start value, stop value, and 'step by' value
z = range(5)
for i in z:
    print(f'i is {i}')

print('This is a Dictionary')
# dictionaries are searchable lists of key:value pairs, they use {}
# dictionaries are mutable
a = {'one': 1, 'two': 2, 'three': 3, 'four': 4, 'five': 5}
# this will print only the keys
for i in a:
    print(f'i is {i}')
# this will also print only the keys
for i in a.keys():
    print(f'i is {i}')
# this will print only the values
for i in a.values():
    print(f'i is {i}')
# this will print the key:value pairs
for key, value in a.items():  # a.items() returns a 2-tuple of the key & value
    print(f'key is {key}, value is {value}')
