#
# Example file for working with functions
#

# define a basic function
def someFunc():
    x = 4
    y = 12
    print(x * y)


# function that takes arguments
def funcWithArg(x):
    print(x)


# function that returns a value
def funcWithReturn():
    return someFunc()


# function with default value for an argument
def funcWithDefaultArgValue(num, x=1):
    result = 1
    for i in range(x):
        result = result * num
    return result


# function with variable number of arguments
def funcWithVariableNumberOfArgs(*args):
    result = 0
    for i in args:
        result = result + i
    return result


someFunc()
funcWithArg(8)
funcWithReturn()
print(funcWithDefaultArgValue(8))
print(funcWithDefaultArgValue(8, 4))
print(funcWithDefaultArgValue(x=3, num=2))
print(funcWithVariableNumberOfArgs(1, 2, 3, 4))
print(funcWithVariableNumberOfArgs(3, 5, 2, 8))
