class Borg:

    """ The Borg design pattern
        Borg class making class attributes global
    """

    _shared_state = {}  # Attribute dictionary

    def __init__(self) -> None:
        self.__dict__ = self._shared_state  # Make it an attribute distionary


class Singleton(Borg):  # Inherits from the Borg class
    """
        The singleton class
        This class now shares all of its attributes among its various instances
    """

    # This essentially makes the singleton objects an object-oriented
    # global variable

    def __init__(self, **kwargs) -> None:
        # Need to call Borg.__init__(self) and NOT super().__init__(self)
        Borg.__init__(self)
        # update the attribute dictionary by inserting a new key-value pair
        self._shared_state.update(kwargs)

    def __str__(self) -> str:
        # Returns the attribute dictionary for printing
        return str(self._shared_state)


# Let's create a singlteton object and add our first acornym
x = Singleton(HTTP="Hyper Test Transfer Protocol")

# Print the object
print(f"x = {x}")

# Let's create another singleton object, and if it refers to the same
# attribute dictionary by adding another acronym
y = Singleton(SNMP="Simple Network Management Protocol")

# Print the object
print(f"y = {y}")
print(f"x = {x}")
