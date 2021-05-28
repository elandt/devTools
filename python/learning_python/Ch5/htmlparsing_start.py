#
# Example file for parsing and processing HTML
#
from html.parser import HTMLParser

metacount = 0


class MyHTMLParser(HTMLParser):
    def handle_comment(self, data):
        print(f"Encountered comment: {data}")
        pos = self.getpos()
        print(f"\tAt line: {pos[0]} position {pos[1]}")

    def handle_starttag(self, tag, attrs):
        global metacount
        if tag == "meta":
            metacount += 1
        print(f"Encountered tag: {tag}")
        pos = self.getpos()
        print(f"\tAt line: {pos[0]} position {pos[1]}")

        if attrs.__len__() > 0:
            print("\tAttributes:")
            for a in attrs:
                print(f"\t{a[0]} = {a[1]}")

    def handle_endtag(self, tag):
        print(f"Encountered tag: {tag}")
        pos = self.getpos()
        print(f"\tAt line: {pos[0]} position {pos[1]}")

    def handle_data(self, data):
        if data.isspace():
            return
        print(f"Encountered data: {data}")
        pos = self.getpos()
        print(f"\tAt line: {pos[0]} position {pos[1]}")


def main():
    # instantiate the parser and feed it some HTML
    parser = MyHTMLParser()
    f = open("learning_python/ch5/samplehtml.html")
    if f.mode == "r":
        contents = f.read()
        parser.feed(contents)
    print(f"Meta tags found: {metacount}")


if __name__ == "__main__":
    main()
