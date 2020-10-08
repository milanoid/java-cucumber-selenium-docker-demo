[![Build Status](https://travis-ci.com/milanoid/4finance-qa-assignment.svg?branch=master)](https://travis-ci.com/milanoid/4finance-qa-assignment)

# Task - Mix of API and web test

Create an acceptance test for BoardGameGeek service covering the following single scenario:

1.       Step 1 WEB Open the game collection of a user.

2.       Step 2 WEB Go to the page of one of the games (chosen at random).

3.       Step 3 API Check the information about the game - look for poll results about Language Dependence.

4.       Step 4 WEB Verify that the most voted Language Dependence level is presented on the game's page.

_Note_: Write test in human-friendly language (Cucumber/Spock) and simply runnable from command line_ 
(use Gradle as project build tool). Java/Groovy programming language should be used under the hood. Test is expected 
to run on both Windows and GNU/Linux OS. While sharing the history of your work with us is a good idea, please consider
keeping your solution private - your code shouldn't be publicly available.

_Important_: Please don't use any external libraries to handle BoardGameGeek API calls (like bgg4j). Your task is 
to test the API, not some BGG-specific library. However, you can (and are encouraged to) use a library to make REST 
calls/testing easier.