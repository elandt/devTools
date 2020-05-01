#
# Example file for working with date information
#
from datetime import date, datetime


def main():
    # DATE OBJECTS
    # Get today's date from the simple today() method from the date class
    today = date.today()
    print(f"Today's date is: {today}")

    # print out the date's individual components
    print(f"Date Components: {today.day} - {today.month} - {today.year}")

    # retrieve today's weekday (0=Monday, 6=Sunday)
    print(f"Today's weekday # is: {today.weekday()}")
    days = ["Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"]
    print(f"Which is a {days[today.weekday()]}")

    # DATETIME OBJECTS
    # Get today's date from the datetime class
    today = datetime.now()
    print(f"The current date and time is {today}")

    # Get the current time
    t = datetime.time(today)
    print(f"The time is {t}")


if __name__ == "__main__":
    main()
