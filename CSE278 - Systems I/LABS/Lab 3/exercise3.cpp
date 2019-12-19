// Copyright (C) 2016 raodm@miamiOH.edu

#include <iostream>
#include <string>

std::string convert(const std::string& line) {
    // In the next next step of this exercise you will need to
    // format the line into a HTML fragment enclosed in 'div' tags
    return line;
}

// -------------------------------------------------------------
//   * * *   DO  NOT  MODIFY  CODE  BELOW  THIS  LINE   * * *
// -------------------------------------------------------------

int main() {
    // First print standard simple HTML header
    std::cout << "<!DOCTYPE html>\n<html>\n<head>\n"
              << "<link type='text/css' rel='stylesheet' "
              << "href='http://ceclnx01.cec.miamiOH.edu/~raodm/ex3/ex3.css'/>\n"
              << "</head>\n"
              << "<body>\n";
    // Read line-by-line and process it
    std::string input;
    while (std::getline(std::cin, input)) {
        std::cout << convert(input) << std::endl;
    }
    // Wrap-up the HTML format
    std::cout << "</body>\n</html>\n";
    return 0;
}