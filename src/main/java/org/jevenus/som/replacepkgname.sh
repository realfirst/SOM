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

for fname in $(find . -name "*.java" -print)
do
    sed '/package org.jevenus.som/s/package org.jevenus.som/package org.jevenus.som;/g' ${fname} > ${fname}.new &&
    mv ${fname}.new ${fname}
done
