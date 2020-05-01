#
# Example file for working with conditional statements
#


def main():
    x, y = 10, 100

    # conditional flow uses if, elif, else
    # Python does not have a switch/case control flow
    if x < y:
        st = f"x is less than y"
    elif x == y:
        st = f"x is the same as y"
    else:
        st = f"x is greater than y"

    print(st)

    # conditional statements let you use "a if C else b"
    st = f"x is less than y" if (x < y) else f"x is greater than or equal to y"

    print(st)


if __name__ == "__main__":
    main()
