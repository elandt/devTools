#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

# Dictionaries are MUTABLE - add, delete, or change values
# They use {} or dict() and is a sequence of key:value pairs
# Keys and Values can be any type, Keys must be imutable
# Dictionaries are indexed by their Keys


def main():
    ages = dict(bill=43, bob=24, joe=67)
    print_dict_v2(ages)
    # modifies the value with the particular key
    ages['bob'] = "Fifty-three"
    print_dict(ages)
    # adds a new value with the given key
    ages['george'] = 101
    print_keys(ages)
    animals = {'kitten': 'meow', 'puppy': 'ruff!', 'lion': 'grrr',
               'giraffe': 'I am a giraffe!', 'dragon': 'rawr'}
    print_dict(animals)
    # searches the dictionary for the given key
    print('lion' in animals)
    print('found' if 'godzilla' in animals else 'missing')
    # this will result in a KeyError if the key is not found
    # print(animals['godzilla'])
    # this will return None if the key is not found
    print(animals.get('godzilla'))
    print_values(animals)


def print_dict(o):
    for x in o:
        print(f'{x}: {o[x]}')


def print_dict_v2(o):
    for k, v in o.items():
        print(f'{k}: {v}')


def print_keys(o):
    for k in o.keys():
        print(f'Key: {k}')


def print_values(o):
    for v in o.values():
        print(f'Value: {v}')


if __name__ == '__main__':
    main()
