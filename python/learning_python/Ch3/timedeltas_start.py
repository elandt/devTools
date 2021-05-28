#
# Example file for working with timedelta objects
#

from datetime import date, datetime, timedelta

# construct a basic timedelta and print it
print(timedelta(days=365, hours=5, minutes=1))

# print today's date
now = datetime.now()
print(f"Today is: {now}")

# print today's date one year from now
print(f"one year from now it will be: {now + timedelta(days=365)}")

# create a timedelta that uses more than one argument
print(f"2d & 3w from now it will be: {now + timedelta(days=2, weeks=3)}")

# calculate the date 1 week ago, formatted as a string
print(f"one week ago it was: {now + timedelta(weeks=-1)}")
print(f"one week ago it was: {now - timedelta(weeks=1)}")

# How many days until April Fools' Day?
today = date.today()
april_fools_day = date(today.year, 4, 1)

# use date comparison to see if April Fool's has already gone for this year
if april_fools_day < today:
    print(f"April Fool's day was {((today - april_fools_day).days)} days ago")
    # if it has, use the replace() function to get the date for next year
    april_fools_day = april_fools_day.replace(year=today.year + 1)

# Now calculate the amount of time until April Fool's Day
time_to_afd = april_fools_day - today
print(f"It's only {time_to_afd} days until April Fool's Day")
