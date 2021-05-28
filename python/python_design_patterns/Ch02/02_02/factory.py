# Factory pattern:
# A factoy is an object that specializes in the creation of other objects
# Problem: unsure about the types of objects needed
# - decisions regarding what classes to use are made at runtime
# Example scenario:
# - A pet shop initially only sold dogs, but now sells cats also


class Dog:

    """A simple dog class"""

    def __init__(self, name):
        self._name = name

    def get_name(self):
        return self._name

    def speak(self):
        return "Woof!"


class Cat:

    """A simple cat class"""

    def __init__(self, name):
        self._name = name

    def get_name(self):
        return self._name

    def speak(self):
        return "Meow!"


def get_pet(pet="dog"):

    """The factory method"""

    pets = dict(dog=Dog("Fido"), cat=Cat("Garfield"))

    return pets[pet]


def get_pet_dynamic(pet="dog", name="Fido"):

    """A more dynamic factory method"""

    pets = dict(dog=Dog(name), cat=Cat(name))

    return pets[pet]


d = get_pet("dog")
print(d.speak())

d = get_pet_dynamic("dog", "Fred")
print(f"{d.get_name()} - {d.speak()}")

c = get_pet("cat")
print(c.speak())

c = get_pet_dynamic("cat")
print(f"{c.get_name()} - {c.speak()}")

c = get_pet_dynamic("cat", "George")
print(f"{c.get_name()} - {c.speak()}")
