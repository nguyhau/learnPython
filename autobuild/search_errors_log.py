#!/usr/bin/env python
import sys
import re

if __name__ == "__main__":
        for line in open(sys.argv[1]):
                line = line.strip()
                if not line:
                        continue
                parts = re.findall("ERROR: (.*)", line) + re.findall("\\(log file is located at (.*)\\)$", line)
                #parts = re.findall("Parsing", line)
                if parts:
                        print parts[0]
