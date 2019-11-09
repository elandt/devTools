# Getting more control over formatting
from datetime import datetime

now = datetime.now()

# string format time
# %<lowercase> = abreviated name i.e. Wed for Wednesday
# %<uppercase> = full name i.e Wednesday
# a/A = day of week
# b/B = month
# y/Y = year
# H = hour
# M = minute
# S = second
# p = AM/PM
print(now.strftime("%a %A %d"))

print(now.strftime("%b %B %m"))

print(now.strftime("%A %B %d"))

print(now.strftime("%H : %M : %S %p"))
print(now.strftime("%H : %M : %S %p"))

print(now.strftime("%y %Y"))
