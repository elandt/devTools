import copy

# Clones objects according to a prototypical instance - useful
# when creating many identical objects individually
# Related to the Abstract Factory pattern


class Prototype:
    def __init__(self):
        self._objects = {}

    def register_object(self, name, obj):
        """Register an object"""
        self._objects[name] = obj

    def unregister_object(self, name):
        """Unregister an object"""
        del self._objects[name]

    def clone(self, name, **attr):
        """Clone a registered object and update its attributes"""
        # Store the cloned object
        obj = copy.deepcopy(self._objects.get(name))
        # Update the clone
        obj.__dict__.update(attr)
        # Return the clone
        return obj


class Car:
    def __init__(self):
        self.name = "Skylark"
        self.color = "Red"
        self.options = "Ex"

    def __str__(self):
        return f"{self.name} | {self.color} | {self.options  }"


c = Car()
prototype = Prototype()
prototype.register_object("skylark", c)

c1 = prototype.clone("skylark")
print(c1)
