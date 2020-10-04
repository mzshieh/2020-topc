import json
from sys import stdin
from random import sample

teams = []
for line in stdin:
    teams.extend(json.loads(line))

teams = [team for team in teams if 'ACCEPTED' == team['status']]

for i, team in enumerate(teams):
    passwd = ''.join(sample('ABCDEFGHJKLMNPQRSTUVWXYZ',12))
    print('team{}'.format(i+1001), passwd, team['name'])
    team['password'] = passwd
    team['account'] = 'team{}'.format(i+1001)

print(json.dumps(teams))
