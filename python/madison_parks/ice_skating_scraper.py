from bs4 import BeautifulSoup

import requests
import re

MADISON_PARKS_ICE_SKATING_URL = "https://www.cityofmadison.com/parks/iceskating"

def main():
    # regex = re.compile(CURRENT_TEAM)
    r = requests.get(MADISON_PARKS_ICE_SKATING_URL)
    soup = BeautifulSoup(r.text, "lxml")

    facilities = soup.find_all(name="div", attrs={"class": "details"})

    if facilities:
        for facility in facilities:
            print(facility.find_next(name="h2").text)
            print(facility.find_next(name="div", attrs={"class": "mainStatus"}).text.strip())
            # print(facility.find_previous(name="u").text)
            # print(facility.text)
    else:
        print("No facilities found")

if __name__ == "__main__":
    main()
