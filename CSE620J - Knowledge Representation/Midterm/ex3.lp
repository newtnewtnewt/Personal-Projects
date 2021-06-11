% Question 3:
% Encode the following information about a default statement and its exceptions in ASP. 
% Make sure that your program can reason cautiously about situations when we have incomplete information 
% about who is a student or who is unemployed.
%
% Adults normally work. 
% Students are adults, but they may or may not work, so we do not want to make any kind of claims about them working or not. 
% Unemployed adults do not work. 
% Larry is unemployed.

% Note we could use the inheritance hierarchy code here, but it would be overkill

% Adults normally work. 
% The hard exception is if they are unemployed.
% The weak exception is if they are a student.
works(X) :- adult(X),  
            not -works(X),
            not ab(d_works(X)).

% The hard exception
-works(X) :- unemployed(X).

% The weak exception
ab(d_works(X)) :- student(X).

% All Students are adults
adult(X) :- student(X).

% Larry is an adult
adult(larry).
unemployed(larry).

% Billy added for testing
student(billy).

#show adult/1.
#show works/1.
#show -works/1.



            
