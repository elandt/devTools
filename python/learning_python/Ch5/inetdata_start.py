#
# Example file for retrieving data from the internet
#

import urllib.request


def main():
    google = "http://www.google.com"
    webUrl = urllib.request.urlopen(google)
    print(f"HTTP Status: {webUrl.getcode()}")
    data = webUrl.read()
    print(data)


if __name__ == "__main__":
    main()
