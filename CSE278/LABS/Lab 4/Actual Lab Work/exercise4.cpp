// Copyright (C) 2016 raodm@miamiOH.edu
// This is the solution code from Exercise #3

#include <iostream>
#include <string>
#include <fstream>

std::string convert(const std::string& line) {
    // Format the line into a HTML fragment enclosed in 'div' tags
    return "<div class='line'>" + line + "</div>";
}

int main(int argc, char* argv[]) {
    // First print standard simple HTML header
    if(argc != 3){
        std::cout << "Specify input & output files";
        return 1;
    }
    std::ifstream input;
    std::ofstream output;
    
    input.open(argv[1]);
    output.open(argv[2]);
    
    if(!input.good() || !output.good()){
       std::cout << "Error opening input or output streams.";
    }
    
    
    output << "<!DOCTYPE html>\n<html>\n<head>\n"
              << "<link type='text/css' rel='stylesheet' "
              << "href='http://ceclnx01.cec.miamiOH.edu/~raodm/ex3/ex3.css'/>\n"
              << "</head>\n"
              << "<body>\n";
    // Read line-by-line and process it
    std::string input1;
    while (std::getline(input, input1)) {
        output << convert(input1) << std::endl;
    }
    // Wrap-up the HTML format
    output << "</body>\n</html>\n";
    return 0;
}
