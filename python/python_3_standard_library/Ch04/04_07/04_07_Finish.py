# HTTP Package

# https://www.googleapis.com/books/v1/volumes?q=isbn:1101904224

import urllib.request
import json
import textwrap

url_to_open = "https://www.googleapis.com/books/v1/volumes?q=isbn:1101904224"

with urllib.request.urlopen(url_to_open) as f:
    text = f.read()
    decodedtext = text.decode("utf-8")
    print(textwrap.fill(decodedtext, width=50))

print()

obj = json.loads(decodedtext)
print(obj["kind"])

print(obj["items"][0]["searchInfo"]["textSnippet"])
