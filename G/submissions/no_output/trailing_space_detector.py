from sys import stdin

for line in stdin:
    line = line.rstrip('\n')
    if len(line) != len(line.strip()):
        raise
