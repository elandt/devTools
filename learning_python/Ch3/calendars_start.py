#
# Example file for working with Calendars
#

# import the calendar module
import calendar
from datetime import date

# create a plain text calendar
today = date.today()
cal = calendar.TextCalendar(calendar.SUNDAY)
st = cal.formatmonth(today.year, today.month, 0, 0)
print(st)

cal = calendar.TextCalendar(calendar.MONDAY)
st = cal.formatmonth(today.year, today.month, 0, 0)
print(st)

# create an HTML formatted calendar - returns the cal as a string of html
hc = calendar.HTMLCalendar(calendar.SUNDAY)
st = hc.formatmonth(today.year, today.month)
print(st)

# loop over the days of a month
# zeroes mean that the day of the week is in an overlapping month
for i in cal.itermonthdays(today.year, today.month):
    # leading and trailing 0s in the output indicate that
    # the start or end of the month has days belonging to
    # another month
    print(i)

# The Calendar module provides useful utilities for the given locale,
# such as the names of days and months in both full and abbreviated forms
for name in calendar.month_name:
    print(name)

for name in calendar.day_name:
    print(name)

# Calculate days based on a rule: For example, consider
# a team meeting on the first Friday of every month.
# To figure out what days that would be for each month,
# we can use this script:
print("team meetings will be on: ")
for m in range(1, 13):
    cal = calendar.monthcalendar(today.year, m)
    weekone = cal[0]
    weektwo = cal[1]
    if weekone[calendar.FRIDAY] != 0:
        meetday = weekone[calendar.FRIDAY]
    else:
        meetday = weektwo[calendar.FRIDAY]

    print(f"{calendar.month_name[m]} {meetday}")
