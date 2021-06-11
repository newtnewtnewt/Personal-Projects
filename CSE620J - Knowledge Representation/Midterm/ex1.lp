% Question 1: 
% Consider the Answer Set Prolog program below. 
% Indicate which rules are unsafe and show how you would correct them so that they become safe 
% (i.e., what you would add to the bodies of these rules to make them safe?).

% These are simple rules, they are perfectly fine
person(george).
person(anna).

% Bad Rule: person(X).
% X is a Variable rule with no body, which is very unsafe. If the only two people 
% allowed are george and anna, we can do this
person(X) :- X == george.
person(X) :- X == anna.
% This is a simple rule, it is fine
adult(tom).

% Bad Rule: child(X) :- not adult(X).
% We need to specify what kind of variable X is, even if we don't know if they are an adult or not
child(X) :- not adult(X), person(X).

% Bad Rule: action(talks_to(X, Y)).
% We need to know what X and Y are, which can be defined in the body of the rule
action(talks_to(X, Y)) :- person(X), person(Y).
% Alternate rule: action(talks_to(X, Y)) :- person(X), person(Y), X != Y
% I am choosing to allow people to talk to themselves :)
