#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

import sys
import os
import random
import datetime


def main():
    v = sys.version_info
    p = sys.platform
    q = os.name
    print("Python version {}.{}.{}".format(*v))
    print(f"Platform: {p}")
    print(f"OS: {q}")
    print(f'Random: {random.randint(1,1000)}')
    print(f'Current Time: {datetime.datetime.now()}')


if __name__ == "__main__":
    main()
