import json

with open('2020topc.json') as FILE:
    teams = json.load(FILE)

print('accounts',1,sep='\t')

for team in teams:
    tid = team['account']
    name = team['name']
    pwd = team['password']
    print('team', name, tid, pwd, sep='\t')
