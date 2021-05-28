#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


class Bunny:
    def __init__(self, n):
        self._n = n

    def __repr__(self):
        return f"repr: The number of bunnies is {self._n}"

    def __str__(self):
        return f"str: The number of bunnies is {self._n}"


s = "Hello, World."
print(s)
print(repr(s))

b = Bunny(47)
print(b)
print(repr(b))
print(ascii(b))
print(chr(128406))
print(ord(chr(128406)))
print(divmod(47, 3))

x = (1, 2, 3, 4, 5)
y = list(reversed(x))
z = (0, 0, 0, 0, 0)
w = (0, 0, 1, 0, 0)
a = (6, 7, 8, 9, 10)
print(x)
print(y)
print(sum(x))
print(sum(x, 10))
print(any(z))
print(any(w))
print(all(w))
print(all(z))
print(all(x))
zipper = zip(x, a)
print(zipper)
for c, d in zipper:
    print(f"{c} - {d}")

f = ("cat", "dog", "rabbit", "velociraptor")
for i, v in enumerate(f):
    print(f"{i}: {v}")

g = 42
h = id(g)
j = isinstance(g, int)
k = isinstance(g, str)
e = type(g)

print(f'g: {g}, id of g: {h}, g is instance of int: {j}')
print(f'g is instance of str: {k}, type of g: {e}')
