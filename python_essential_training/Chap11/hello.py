#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


class MyString(str):
    """Extends the built-in str class"""

    def __str__(self):
        """
            Overrides the native __str__ object function
            When a MyString object is passed to the
            print() function, it will be printed in
            reverse.
        """
        return self[::-1]


# Converts to uppercase
print("Hello, World.".upper())
# Swaps the case of each character in the string
print("Hello, World.".swapcase())
# Converts to lowercase
print("Hello, World.".lower())
# Converts to lowercase including unicode characters
print("Hello, World.".casefold())
# Capitalizes the first letter
print("hello, world.".capitalize())
# Capitalizes the first letter in each word
print("hello, world.".title())
# Multi-line string with formatting
print(
    """Hello,
World.
{}""".format(
        10
    )
)
# Setting a string literal to a var, then formatting during print
x = "Hello, World. {}"
print(x.format(42 * 7))

s = MyString("Hello, World.")
print(s)

a1 = "The quick, brown fox."
# Entirely different object than a1 because of the use of .upper()
a2 = a1.upper()
a3 = "Unique New York"
a4 = "This is " "also concatenated."
print(id(a1))
print(id(a2))
# String concatenation
print(a1 + " " + a3)
print(a4)

# String formatting
b = 42
c = b * 7
d = b * c * 1000
print("the number is {}".format(b))
print("the number is {}, {}".format(b, c))
print("the number is {first}, {second}".format(first=b, second=c))
print("the number is {1}, {0}, {1}".format(b, c))
print("the number is {2:,.3f}, {0:<050}, {1:+08}".format(b, c, d))
print("the number is {2:,}, {0}, {1}".format(b, c, d).replace(",", "."))
print(
    "the number is {0:d} as hex {0:x}, octal {0:o}, and binary {0:b}".format(b)
)
# All of the above formating can be done with f-strings as well
print(f"the number is {b}")
print(f"the number is {b}, {c}")
print(f"the number is {c}, {b}, {c}")
print(f"the number is {d:,.3f}, {b:<050}, {c:+08}")
print(f"the number is {d:,}, {b}, {c}".replace(",", "."))
print(f"number is {b:d} as hexidemical {b:x}, octal {b:o}, and binary {b:b}")
