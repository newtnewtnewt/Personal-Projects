%%%%%%%%%%%%%%%%
%   PART   1   %
%%%%%%%%%%%%%%%%
% Start with the facts provided by the assignment
student(john).
student(mary).
student(bob).
student(rick).

course(math).
course(graphs).

took(john, math).
took(john, graphs).
took(mary, graphs).

-took(mary, math).
-took(bob, math).

% List of all Courses is complete, so no need to do any crazy recursion
took_all_classes(X) :- took(X, math), took(X, graphs).
% If a student has not taken either of the classes, they did not take all classes
-took_all_classes(X) :- -took(X, math).
-took_all_classes(X) :- -took(X, graphs).

% To graduate, an individual must be a student, there must be no strong exception
% with respect to them not graduating, and they must take all classes
can_graduate(X) :- student(X),
                   took_all_classes(X),
                   not -can_graduate(X).
% Students who cannot graduate are the same as the other, without all classes being taken
-can_graduate(X) :- student(X),
                    -took_all_classes(X),
                    not can_graduate(X).

% If there is no information regarding on taking one of the classes, the stduent needs records reviewed
% Unfortunately we cannot just 'not' the can_graduate and -can_graduate predicates because
% it is possible to know a student cannot graduate and also know that their records need reviewed
review_records(X) :- student(X), not took(X, math), not -took(X, math).
review_records(X) :- student(X), not took(X, graphs), not -took(X, graphs).


%%%%%%%%%%%%%% 
% TEST CASES % 
%%%%%%%%%%%%%%

% These are pretty self explanatory
#show can_graduate/1.
#show -can_graduate/1.
#show review_records/1.
