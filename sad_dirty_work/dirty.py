from selenium import webdriver
from time import sleep
import json

drive = webdriver.Chrome()
# drive.get('https://icpc.baylor.edu/')
# input('Login Baylor.')

url = 'https://icpc.global/cm5-contest/contest/Taiwan-Online-2020/teams?dswid=-6322'
drive.get(url)

input('filter teams')

def select(css,element=None):
    if element != None: element.find_element_by_css_selector(css)
    return drive.find_element_by_css_selector(css)

def selectAll(tag,element=None):
    if element != None: element.find_elements_by_tag_name(tag)
    return drive.find_elements_by_tag_name(tag)

teams = []

table = drive.find_element_by_css_selector('#allTeamsForm\:allTeamsTable_data')
rows = table.find_elements_by_tag_name('tr')

for row in rows:
    tid = int(row.get_attribute('data-rk'))
    rid = row.get_attribute('data-ri')
    cols = row.find_elements_by_tag_name('td')
    if not row.text.endswith("BUTTON"): continue
    xpand,_,_,_, name,_, univ,_,_, status,_,_,_,_ = cols
    xpand.find_element_by_tag_name('div').click()
    sleep(5)
    css = '#allTeamsForm\:allTeamsTable\:%s\:membersList_list' % rid
    print(css)
    ul, cnt = None, 0
    while ul == None:
        try:
            ul = drive.find_element_by_css_selector(css)
        except:
            cnt += 1
            sleep(2)
            if cnt > 10: break
            print("retry!")
    if ul == None: 
        print(rid,'failed')
        continue
    coach = None
    contestants = list()
    others = list()
    for innerRow in selectAll('li',ul):
        spans = innerRow.find_elements_by_tag_name('span')
        a_s = innerRow.find_elements_by_tag_name('a')
        innerCols = spans[:2]+a_s+spans[2:]
        _, role, email, memberName, reg = innerCols
        email = email.get_attribute('href')[7:]
        memberName = memberName.text
        reg = reg.get_attribute('class').endswith('true')
        role = role.text.strip()
        person = {"role": role, "name": memberName, "mail": email, "OK": reg}
        if role == 'Coach':
            coach = person
        elif role == 'Contestant':
            contestants.append(person)
        else:
            others.append(person)
    xpand.find_element_by_tag_name('div').click()
    sleep(0.1)
    link = name.find_element_by_tag_name('a').get_attribute('href')
    name = name.text.strip()
    univ = univ.text.strip()
    status = status.find_element_by_tag_name('span').get_attribute('class')[6:]
    team = {"id": tid , "link": link, "name": name, "univ": univ, "status": status, "coach": coach, "contestants": contestants, "others": others}
    # if status not in ["Accepted", "Pending"]: continue
    print(team)
    teams.append(team)

with open('teams.json', 'a') as FILE:
    json.dump(teams,FILE)

drive.close()
