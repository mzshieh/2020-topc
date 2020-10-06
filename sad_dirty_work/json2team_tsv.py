import json

with open('2020topc.json') as FILE:
    teams = json.load(FILE)

print('File_Version',2,sep='\t')

for team in teams:
    tid = team['account'][-4:]
    xid = team['id']
    cid = 7
    name = team['name']
    inst = team['univ']
    print(tid, xid, cid, name, inst, '', 'TWN', '',sep='\t')
