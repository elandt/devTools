# Chain of Responsibility pattern decouples the request and its processing
# Related to Composite


# Abstract handler
class Handler:
    """Abstract Handler"""
    def __init__(self, successor):
        # Define who is the next handler
        self._successor = successor

    def handle(self, request):
        # If handled, stop here
        handled = self._handle(request)

        # Otherwise, keep going
        if not handled:
            self._successor.handle(request)

    def _handle(self, request):
        raise NotImplementedError('Must provide implementation in subclass!')


# Inherits from the abstract handler
class ConcreteHandler1(Handler):
    """Concrete handler 1"""
    def _handle(self, request):
        # Provide a condition for handling
        if 0 < request <= 10:
            print(f"Request {request} handled in handler 1")
            # Indicates that the request has been handled
            return True


# Inherits from the abstract handler
class DefaultHandler(Handler):
    """Default handler"""

    def _handle(self, request):
        """If there is no handler available"""
        # No condition checking since this is a default handler
        print(f"End of chain, no handler for {request}")
        # Indicates that the request has been handled
        return True


# Using handlers
class Client:
    def __init__(self):
        # Create handlers and use them in a sequence you want
        # Note that the default handler has no successor
        self.handler = ConcreteHandler1(DefaultHandler(None))

    # Send your requests one at a time for handlers to handle
    def delegate(self, requests):
        for request in requests:
            self.handler.handle(request)


# Create a client
c = Client()

# Create requests
requests = [2, 5, 30]

# Send the requests
c.delegate(requests)
