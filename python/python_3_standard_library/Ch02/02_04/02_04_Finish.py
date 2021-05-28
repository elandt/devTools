# Statistics Module
import statistics
import math

agesData = [10, 13, 14, 12, 11, 10, 11, 10, 15]

print(f"Mean: {statistics.mean(agesData)}")
print(f"Mode: {statistics.mode(agesData)}")
print(f"Median: {statistics.median(agesData)}")
print(f"Sorted: {sorted(agesData)}")

print(f"Variance: {statistics.variance(agesData)}")
print(f"Standard Deviation: {statistics.stdev(agesData)}")
print(f"Standard Deviation: {math.sqrt(statistics.variance(agesData))}")
