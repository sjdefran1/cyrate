import requests
from bs4 import BeautifulSoup
import time
from urllib.parse import urlparse


# Tries to find /menu yelp link
# if not found it calls google(rest)
def find_menu(url: str, rest: str) -> str:
    link = try_yelp_menu(url)
    if  link == "":
        link = google(rest)
    return link
    
# returns most frequent link that appears in google search
def google(rest: str) -> str:
    # Get all links from google search using {rest}
    response = requests.get(f'https://www.google.com/search?q={rest}')
    soup = BeautifulSoup(response.text, "html.parser")
    links = soup.find_all('a')
    # empty list so we can calculate most freq
    results = []
    for a in links:
        # finds only urls that arent related to google search elements
        if '/url?q' in a['href']:
            linkHref = a['href']
            linkHref = linkHref.split('q=')[1]
            # Parses Url into object
            # can access just 'www.whateversite.com' w/ .netloc
            s = urlparse(linkHref).netloc
            results.append(s)
    mostFreq = max(set(results), key=results.count)
    return mostFreq


# returns yelp /menu link if found
def try_yelp_menu(url: str):
    # Example url =
    # 'https://www.yelp.com/biz/cornbred-ames?adjust_creative=Jzh_gvi4YouhU5-CrA6Z8A&utm_
    #           campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=Jzh_gvi4YouhU5-CrA6Z8A'
    #
    # after split 1
    # ['https://www.yelp.com/', 'cornbred-ames?adjust_creative=Jzh_gvi4YouhU5-CrA6Z8A&utm_ campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=Jzh_gvi4YouhU5-CrA6Z8A']
    # after split 2
    # ['/cornbred-ames', 'adjust_creative=Jzh_gvi4YouhU5-CrA6Z8A&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=Jzh_gvi4YouhU5-CrA6Z8A']
    # name = '/cornbred-ames'     
    name = url.split('/biz')[1]
    name = name.split('?')[0]
    search = 'https://www.yelp.com/menu' + name
    try:
        response = requests.get(search)
        # if redirected wont equal search, return empty
        if response.url != search:
            return ""
        return search
    except:
        # sleep to avoid another timeout
        # try again
        time.sleep(2)
        s = try_yelp_menu(url)
        return s
    
  