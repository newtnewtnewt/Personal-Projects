#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <cstring>
#include <string>
#include <iostream>

#include "mysql.h"

// ************ CSE-278     Homework  4 *******************


std::string * results;           // pointer to results of query

std::string  get_token( std::string c );
                                //  return secret token

int  connect_query(int op ,  char * pdb, char query[]  );
