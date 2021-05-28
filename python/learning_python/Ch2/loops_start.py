#
# Example file for working with loops
#


def main():
    x = 0

    # define a while loop
    while x < 5:
        print(x)
        x += 1

    # define a for loop
    for i in range(5, 10):
        print(i)

    # use a for loop over a collection
    days = ["Mon", "Tues", "Wed", "Thurs", "Fri"]
    for day in days:
        print(day)

    # use the break and continue statements
    for i in range(5, 10):
        if i == 9:
            # breaks out of the loop entirely
            break
        if i % 2:
            print("even")
            # skips the rest of the loop
            continue
        print(i)

    # using the enumerate() function to get index
    for i, day in enumerate(days):
        print(f"index: {i}, day: {day}")


if __name__ == "__main__":
    main()
