#
# Example file for parsing and processing JSON
#
import urllib.request
import json


def printResults(data):
    # Use the json module to load the string data into a dictionary
    theJSON = json.loads(data)
    # print(type(theJSON))

    # now we can access the contents of the JSON like any other Python object
    if "title" in theJSON["metadata"]:
        print(theJSON["metadata"]["title"])

    # output the number of events, plus the magnitude and each event name
    count = theJSON["metadata"]["count"]
    print(f"{count} events recorded")

    # for each event, print the place where it occurred
    for quake in theJSON["features"]:
        print("----")
        # This works
        # print(quake["properties"]["place"])
        # This doesn't...why?
        # print(f"Location: {quake["properties"]["place"]}")
        # print(f"Magnitude: {quake["properties"]["mag"]}")
        # print(f"Time: {quake["properties"]["time"]}")
        location = quake["properties"]["place"]
        mag = quake["properties"]["mag"]
        time = quake["properties"]["time"]
        print(f"Location: {location}")
        print(f"Magnitude: {mag}")
        print(f"Time: {time}")
    print("----")

    # print the events that only have a magnitude greater than 4
    for quake in theJSON["features"]:
        mag = quake["properties"]["mag"]
        if mag >= 4.0:
            print("%2.1f" % mag, quake["properties"]["place"])

    # print only the events where at least 1 person reported feeling something
    for quake in theJSON["features"]:
        location = quake["properties"]["place"]
        mag = quake["properties"]["mag"]
        felt_by = quake["properties"]["felt"]
        if felt_by and felt_by > 0:
            print(f"{mag:.1f} {location} reported {felt_by} times")


def main():
    # define a variable to hold the source URL
    # In this case we'll use the free data feed from the USGS
    # This feed lists all earthquakes for the last day larger than Mag 2.5
    urlData = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson"
    # Open the URL and read the data
    webUrl = urllib.request.urlopen(urlData)
    http_status = webUrl.getcode()
    print(f"HTTP Status: {http_status}")
    if http_status == 200:
        data = webUrl.read()
        printResults(data)
    else:
        print(f"Received error ({http_status}), could not parse results")


if __name__ == "__main__":
    main()
