#!/usr/bin/env python3
# Copyright 2009-2017 BHG http://bw.org/


class Animal:
    # this is a CLASS variable - shared by all instances of this class
    example_class_var = "some string"

    def __init__(self, **kwargs):
        # These are object variables - using the leading underscore is a
        # convention to denote a 'private' variable because python doesn't
        # not have a concept of 'private' built in like Java does
        self._type = kwargs["type"] if "type" in kwargs else "kitten"
        self._name = kwargs["name"] if "name" in kwargs else "fluffy"
        self._sound = kwargs["sound"] if "sound" in kwargs else "meow"

    # 'self' is a traditional first param for object methods - this param is
    # what binds the method to the object
    def type(self, t=None):
        if t:
            self._type = t
        return self._type

    def name(self, n=None):
        if n:
            self._name = n
        return self._name

    def sound(self, s=None):
        if s:
            self._sound = s
        return self._sound

    # This would be similar to a '.toString()' in Java
    def __str__(self):
        return f'The {self.type()} is named "{self.name()}" and says "{self.sound()}".'


def main():
    a0 = Animal(type="kitten", name="fluffy", sound="rwar")
    a1 = Animal(type="duck", name="donald", sound="quack")
    print(a0)
    print(a1)


if __name__ == "__main__":
    main()
