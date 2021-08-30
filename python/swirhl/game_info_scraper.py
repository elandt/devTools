from bs4 import BeautifulSoup

import requests
import re

CURRENT_TEAM = "Epucks"
WEEKLY_SCHEDULE_URL = "https://www.swirhl.com/weekly-schedule/"

def main():
    regex = re.compile(CURRENT_TEAM)
    r = requests.get(WEEKLY_SCHEDULE_URL)
    soup = BeautifulSoup(r.text, "lxml")

    game_times_and_opponents = soup.find_all(name="span", string=regex)

    if game_times_and_opponents:
        for game_and_oppenent in game_times_and_opponents:
            print(game_and_oppenent.find_previous(name="u").text)
            print(game_and_oppenent.text)
    else:
        print("No games found")

if __name__ == "__main__":
    main()
