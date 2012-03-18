#!/bin/bash

############################################################
# START_IP_HEADER                                          #
#                                                          #
# Written by Jingen Ding                                   #
# Contact <dingje.gm@gmail.com> for comments & bug reports #
#                                                          #
# END_IP_HEADER                                            #
############################################################

set -e

# while [ read line ]; do
    # echo $line
# done
for line in `cat $1`; do
    echo $line
done
