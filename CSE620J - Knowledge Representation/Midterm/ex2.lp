% Question 2:
% Consider the ASP program Π shown below:
% 
% a.
% b :- not c.
% c :- not b.
% d :- c.
% 
% Is S = {a, b, d} an answer set of Π? Justify your answer by showing all the steps, 
% starting from the construction of the reduct of Π with respect to S (i.e., ΠS).


% Initial defined Answer Set
S = {a, b, d}

% Asp program Π
a.
b :- not c.
c :- not b.
d :- c.

% Calculating the Reduct of Π with respect to S
a. % Required, also in the set
b :- not c. % Nothing is known about c, so we can add b
-/- c :- not b. % We know b is in the answer set, so we CANNOT add c
-/- d :- c. % c is not in the answer set, nor have we collected it

% The reduct of Π with respect to S
S2 = {a, b}

% Compare the reduct to the answer set
S != S2 

% Try again using the Reduct as the Answer Set
S2 = {a, b}

a. % Required, also in the set
b :- not c. % Nothing is known about c, so we can add b
-/- c :- not b. % We know b is in the answer set, so we CANNOT add c
-/- d :- c. % c is not in the answer set, nor have we collected it

% The reduct of Π with respect to S2
S3 = {a, b}

% Same answer set again
S3 != S

% Conclusion
% S is not an answer set of Π, because S does not match Π's reduct


