import json
import urllib.parse
import smtplib
from email.message import EmailMessage
from time import sleep

with open('2020topc.json') as FILE:
    teams = json.load(FILE)

for team in teams:
    msg = EmailMessage()
    msg['Subject'] = 'Account Information -- 2020 ICPC Taiwan Online Programming Contest'
    msg['From'] = 'mzshieh@nctu.edu.tw'
    coach_email = team['coach']['mail']
    contestants_email = [person['mail'] for person in team['contestants']]
    receivers = ['topc2020@icpc.tw',coach_email] + contestants_email
    msg['To'] = ', '.join(receivers)
    account = team['account']
    password = team['password']
    name = team['name']
    url_name = urllib.parse.quote(name)
    agree = urllib.parse.quote('同意')
    members = ', '.join([person['name'] for person in team['contestants']])
    text = f"""Dear Coach and Contestants,

Thank you for participating 2020 ICPC Asia Taiwan Online Programming Contest.
The contest will start at 18:00, Oct 7, 2019.
The URL of the judge system is https://j.topc2020.icpc.tw/
It will be ready for test after 12:30, Oct 6, 2020.

Here is your account information:
Team: {name}
Members: {members}
Account: {account}
Password: {password}

Also, please print and read the following document:
https://drive.google.com/file/d/1SlIVbZLt7h8Cyb-hPWQHdIY7o6A_8XSp/view
Send a photocopy with your signatures back to us via the following link:
https://docs.google.com/forms/d/e/1FAIpQLSfTArMDspFNKzeAOp1YIGR4hhPJhzXxy8pKDnQowCqdlhKEDw/viewform?entry.1008414513={url_name}&entry.787416121={account}&entry.1933828515={agree}
This is a requirement to make your score official.
Please note that you MUST accomplish uploading before the contest.

If you need any assistance, please do not hesitate to contact us.

Best regards,

Contest Organizers
2020 ICPC Asia Taiwan Online Programming Contest
"""
    msg.set_content(text)
    print(account)
    print("Sending email from", msg['From'], "to", msg['To'])
    print("Subject:",msg['Subject'])
    print(text) 
    sleep(2)
    smtpObj = smtplib.SMTP('smtp.cc.nctu.edu.tw')
    from_addr = msg['From']
    # smtpObj.sendmail(from_addr, receivers, msg)
    # smtpObj.sendmail(from_addr, ["topc2020@icpc.tw"], msg.as_string())
    smtpObj.sendmail(from_addr, receivers, msg.as_string())
    print("\nDone.\n")
