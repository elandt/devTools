#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


class Animal:
    # This is an example of a base class - it's going to be inherited
    # in order to be used - this is the reason the object variables
    # do not have default values
    def __init__(self, **kwargs):
        if "type" in kwargs:
            self._type = kwargs["type"]
        if "name" in kwargs:
            self._name = kwargs["name"]
        if "sound" in kwargs:
            self._sound = kwargs["sound"]

    def type(self, t=None):
        if t:
            self._type = t
        try:
            return self._type
        except AttributeError:
            return None

    def name(self, n=None):
        if n:
            self._name = n
        try:
            return self._name
        except AttributeError:
            return None

    def sound(self, s=None):
        if s:
            self._sound = s
        try:
            return self._sound
        except AttributeError:
            return None


class Duck(Animal):
    # inherits Animal
    def __init__(self, **kwargs):
        self._type = "duck"
        # delete keyword arg for 'type' if it exists so it doesn't overwrite
        # the default
        if "type" in kwargs:
            del kwargs["type"]
        super().__init__(**kwargs)


class Kitten(Animal):
    # inherits Animal
    def __init__(self, **kwargs):
        self._type = "kitten"
        if "type" in kwargs:
            del kwargs["type"]
        super().__init__(**kwargs)

    def kill(self, target):
        print(f'{self.name()} will now kill all {target}!')


def print_animal(o):
    if not isinstance(o, Animal):
        raise TypeError("print_animal(): requires an Animal")
    print(f'The {o.type()} is named "{o.name()}" and says "{o.sound()}".')


def main():
    a0 = Kitten(name="fluffy", sound="rwar")
    a1 = Duck(name="donald", sound="quack")
    print_animal(a0)
    print_animal(a1)
    a0.kill('ducks')


if __name__ == "__main__":
    main()
