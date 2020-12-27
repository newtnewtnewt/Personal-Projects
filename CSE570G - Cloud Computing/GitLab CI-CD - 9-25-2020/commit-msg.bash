#!/bin/bash
# Name: Noah Dunn
# Instructor: Dr. Scott Campbell
# Date: 9/25/2020
# Class: CSE570J
# Assignment: commit-msg hook to validate words

INVALID_WORDS=$(cat "$1" | ispell -l | wc -l )
WORD_COUNT=$(cat "$1" | wc -w)
if [[ $INVALID_WORDS -lt 1 ]]; then
        echo "All words are valid"
        INVALID_WORDS=1
else
        echo "Invalid Number of Words: $INVALID_WORDS"
        INVALID_WORDS=0
fi
if [[ $WORD_COUNT -ge 4 ]]; then
        echo "There are 4 or more words present"
        WORD_COUNT=1
else
        echo "INVALID: Total Number of Words: $WORD_COUNT"
        WORD_COUNT=0
fi
if [[ $INVALID_WORDS && $WORD_COUNT ]]; then
        echo "Passed Word Hook"
        exit 0
else
        echo "Commit message must be at least four words spelled correc
        exit 1
fi
