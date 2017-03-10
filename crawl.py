# https://docs.python.org/3/library/index.html
# http://www-rohan.sdsu.edu/~gawron/python_for_ss/course_core/book_draft/web/web_crawling.html
# https://breakingcode.wordpress.com/2010/06/29/google-search-python/
# https://pypi.python.org/pypi/wikipedia/
#pip install urllib3
#pip install webunit
#pip install HTMLParser
#pip install BeautifulSoup4 https://www.crummy.com/software/BeautifulSoup/bs4/doc/
#pip install wikipedia
from urllib.request import urlopen
from bs4 import BeautifulSoup
def text2mp3(para, lan):
    from gtts import gTTS
    import os
    tts = gTTS(text=para, lang=lan)
    tts.save("good.mp3")
    return
def termDef(text):
    import wikipedia
    ny = wikipedia.page(text)
    print (ny.content)
    return
def linkSearch(text):
    from google import search
    for url in search(text,lang = 'vi', stop = 20):
        print (url)
        html = str(url)
        break
        print
    return html
def getContent(link):
    page = urlopen(link)
    soup = BeautifulSoup(page, "html.parser")
    #content = soup.find_all('p')
    #
    # kill all script and style elements
    for script in soup(["script", "style"]):
        script.extract()    # rip it out
    # get text
    text = soup.get_text()
    # break into lines and remove leading and trailing space on each
    lines = (line.strip() for line in text.splitlines())
    # break multi-headlines into a line each
    chunks = (phrase.strip() for line in lines for phrase in line.split("  "))
    # drop blank lines
    text = '\n'.join(chunk for chunk in chunks if chunk)
    print(text)
    return
def termBrief(text):
    domain = text + " + wiki"
    link = linkSearch(domain)
    page = urlopen(link)
    soup = BeautifulSoup(page, "html.parser")
    return soup.select_one("#mw-content-text > p").text

#link = linkSearch("Giáo sư")
#getContent(link)
para = termBrief("Giáo sư")
lang = 'vi'
print (para)
#text2mp3(para, lang)
