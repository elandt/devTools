
import requests
import openapi

LEAGUE_LOBSTER_URL="https://scheduler.leaguelobster.com/api"
MADISON_REC_HOCKEY_ASSOCIATION_ID=942030
BEER_CAN_LEAGUE_ID=1340732
PREMIUM_DRAUGHT_LEAGUE_ID=1338774

def main():

    access_token = requests.post(url=f"{LEAGUE_LOBSTER_URL}/access-token/",data={"username":"elandt14@gmail.com", "password":""}).json()
    headers = {"Authorization": f"JWT {access_token['token']}"}

    r = requests.get(LEAGUE_LOBSTER_URL + f"/association/{MADISON_REC_HOCKEY_ASSOCIATION_ID}/leagues/", headers=headers)

    print(f"{r.text}")


if __name__ == "__main__":
    main()
