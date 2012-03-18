#!/usr/bin/python2 -tt

import re

def csv2db():
    """
    """
    f = open('/tmp/2khxx.csv', 'rU');
    for line in f:
        match = re.search(r'"\d{3}","","","","","","","","","[A-Z]"', line)
        if match:
            print line,

    f.close()
    
def main():
    """
    """
    csv2db()

if __name__ == '__main__':
    main()
