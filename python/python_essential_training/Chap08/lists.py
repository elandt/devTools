#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/

# Lists are MUTABLE - add, delete, or change values
# They use [], and are ordered

# Tuples are IMMUTABLE - cannot change once created
# They use (), and are ordered


def main():
    game = ['Rock', 'Paper', 'Scissors', 'Lizard', 'Spock']
    print(game[1])
    print(game[1:3])
    print(game[1:5:2])
    # returns the index of the specified item, or ValueError if not found
    i = game.index('Lizard')
    print(game[i])
    print_list(game)
    # removes the specified item from the list, or ValueError if not found
    game.remove('Paper')
    print_list(game)
    # adds an item to the end of the list, returns None
    game.append('Computer')
    print_list(game)
    # inserts into the specified location in the list, returns None
    game.insert(3, 'Mac')
    print_list(game)
    # removes the last item in the list, and also returns it
    x = game.pop()
    print(x)
    print_list(game)
    # removes the item from specified index in the list, or IndexError if out
    # of range
    y = game.pop(3)
    print(y)
    print_list(game)
    # can also use 'del' to delete an index, or slice
    del game[1:5:2]
    print_list(game)
    # can join with a string used to join
    print(', '.join(game))
    # can get the length of the list using len(<object>)
    print(f'The list contains {len(game)} items')


def print_list(o):
    for i in o:
        print(i, end=' ', flush=True)
    print()


if __name__ == '__main__':
    main()
