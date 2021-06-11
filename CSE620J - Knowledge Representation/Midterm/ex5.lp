% Question 5: 
% Use the Generate-Test methodology to encode the following problem in ASP:
% There are n people who must be assigned to m teams. Each person has one skill, and this information is provided to you as an input. There are some requirements about team formation:
% 
%	Each person must be assigned to exactly one team. 
%	Each team must have at least one person assigned.
% 	In each team, each skill must be covered (i.e., you do not want teams that have one or more uncovered skills).
% 
% Use the following output predicate: 
% assigned(P, T) – person P is assigned to team T


% Each team must have at least one person
1 {assigned(P, T) : team(T)} n :- person(P).
% One person can only be a part of a single team
:- assigned(P, T), assigned(P, T2), T != T2.

% Make sure all teams have someone of each skill
team_skill(S, T) :- skill(S), assigned(P, T), has_skill(P, S).
:- skill(S), team(T), not team_skill(S, T).

#const n = 4.
person(1..n).
#const m = 2.
team(1..m).


skill(swimming).
skill(running).

has_skill(1, swimming).
has_skill(2, running).
has_skill(2, swimming).
has_skill(3, running).
has_skill(4, swimming).

#show assigned/2.
