# Declare a variable and initialize it
f = 0
print(f)

# re-declaring the variable works
f = "abc"
print(f)

# ERROR: variables of different types cannot be combined
# print ("string type " + 123)
print("string type " + str(123))
# this works because it's not trying to concatenate the literal int 123
print(f"string type {123}")


# Global vs. local variables in functions
def someFunction():
    # global f
    f = "def"
    print(f)


someFunction()
print(f)

# undefines f
del f
# this fails because of undifining f
# print(f)
