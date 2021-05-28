# Python Logical Operators: And, Or, Not:

# What is a Boolean?
isRaining = True
isSunny = False

# Logical Operators -> Special Operators for Booleans

# AND
# true and true --> true
# false and true --> false
# true and false --> false
# false and false --> false

if isRaining and isSunny:
    print("We might see a rainbow.")

# OR
# true and true --> true
# false and true --> true
# true and false --> true
# false and false --> false

if isRaining or isSunny:
    print("It might be rainy, or it might be sunny.")

# NOT
# true --> false
# false --> true

if not isRaining:
    print("It must not be raining.")

ages = [12, 18, 19, 39, 87, 7, 2]
for age in ages:
    isAdult = age > 17
    if not isAdult:
        print(f"Being {str(age)} does not make you an adult.")
