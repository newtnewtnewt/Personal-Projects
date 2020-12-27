/* 
*  Name:  Noah Dunn
*  Instructor: Dr. Michael Zmuda
*  Class: CSE 465
*  Date:  3/5/2020
*  Assignment: Homework #03
*/

/*
* Question 1:
* 1a. Antecedents are the right-side of a statement in Prolog,
*     commonly associated with the "if" statement in a more traditional
*     imperative language. The antecedent can be one or many terms (pieces of a statement).
*	  
* 	  Consequents are the left-side of a statement in Prolog, 
*	  commonly associated with the "then" block in a more traditional
*     imperative language. The consequent is exactly one term (piece of a statement).
*
* 1b. Resolution is the principle that allows inferred propositions from given propositions.
*     For example, If I know A implies B, and B implies C, I can assert that A implies C.
*     Unification is simply the process of determining useful values for variables.
* 
* 1c. There are exactly two forms of Horn Clauses.
*     The first, headed horn clauses, have a single atomic proposition on the left.
*     The second, headless horn clauses, have no proposition on the left-side
* 
* 1d. "This is never useful or even legal in Prolog" - The Book. 
*      If K is not instantiated, the clause on the right side is undefined and the clause fail.
*      If K is instantiated, the clause fails because the left operand cannot have a value when "is" is evaluated.
*/


/*
Something that you should be aware of:

Legal comparisons: >, <, >=
Illegal: <=

So, the following are allowable:

3 > 0.
0 < 3.
3 >= 3.

But the following is not:

3 <= 5.

For this, you should:

5 >= 3.
*/

/*
The real roots of Ax^2+Bx+C=0 are returned in the list named ROOTS. If there are
two roots, they are arranged in ascending order.

Successful queries:
 quadratic(2.0,5.0,2.0,L).     % L=[-2.0,-0.5]
 quadratic(1.0,0.0,0.0,L).     % L=[0.0]
 quadratic(1.0,3.0,-4.0,L).    % L=[-4.0,1.0]

Failed queries:
 quadratic(2.0,5.0,2.0,[]). 
*/
discriminant(A, B, C, ROOTS) :- DISVALUE is B * B - 4.0 * A * C, discriminant(A, B, C, DISVALUE, ROOTS).
discriminant(_A, _B, _C, DISVALUE, ROOTS) :- DISVALUE < 0, ROOTS = [].
discriminant(_A, _B, _C, DISVALUE, ROOTS) :- DISVALUE > 0, quadratic(_A, _B, _C, ROOTS, _X, _Y).
discriminant(_A, _B, _C, _DISVALUE, ROOTS) :- quadratic(_A, _B, _C, ROOTS, _X).
quadratic(_A, _B, _C, []) :- false, !.
quadratic(A, B, C, ROOTS) :- discriminant(A, B, C, ROOTS).
quadratic(A, B, _C, ROOTS, X) :- X is (- round(B) / (2 * A)), ROOTS = [X], !.
quadratic(A, B, C, ROOTS, X, Y) :- X is (-B + sqrt(B * B - 4 * A * C) ) / (2 * A), 
Y is (-B - sqrt(B * B - 4 * A * C) ) / (2 * A), append([Y], [X], ROOTS), !.






/*
The input is 6 instaniated integer values. Query succeeds
if the volume of the first cube is > the volume of the second.

Successful queries:
 isLargerCube(2,2,2,1,3,2).
 isLargerCube(2,2,20,5,3,2).

Failed queries:
 isLargerCube(1,3,2,2,2,2).
 isLargerCube(2,2,2,2,2,2).
*/

isLargerCube(W1, L1, H1, W2, L2, H2) :- VOL1 is W1 * L1 * H1, VOL2 is W2 * L2 * H2, VOL1 > VOL2.
isLargerCube(_W1, _L1, _H1, _W2, _L2, _H2) :- false.


/*
Input is 3 instantiated integers. The 4th parameter is
bound to the clamped value. See description in previous
assignment.

Successful queries:
clamp(1, 0, 2, X).	 % X = 1
clamp(-1, 0, 2, X).	 % X = 0
clamp(10, 0, 2, X).	 % X = 2

Failed queries:
clamp(1, 0, 2, 4).
*/
clamp(V, LO, HI, VP) :- V >= LO , HI >= V, VP = V, !.
clamp(V, LO, _HI, VP) :- V < LO, VP = LO, !.
clamp(V, _LO, HI, VP) :- V > HI, VP = HI, !.
clamp(_V, _LO, _HI, _VP) :- false, !.

/*
Succeeds if the all the elements of the incoming
flat list are the same value.
Successful queries:
isConstant([]).
isConstant([1,1]).
isConstant([a,a,a]).

Failed queries:
isConstant([a,a,1]).
isConstant([a,1,a]).
*/
isConstant([]) :- true, !.
isConstant([H|T]) :- isConstant(H, T).
isConstant(VAL, [H|T]) :- H = VAL, isConstant(VAL, T).
isConstant(_, []) :- true, !.


/*
First parameter is an instantiated, flat list of integers.
The second parameter is then bound to a list containing
only the positive values.

Successful queries:
positives([], L).	% L = []
positives([1], L).	% L = [1]
positives([-1], L).	% L = []
positives([-1,2,5,-9,4,0], L).	% L = [2,5,4]

Failed queries:
positives([-1], [-1]).
*/
positives([], []) :- !.
positives([H|T], LST) :- H > 0, 
positives(T, R), append([H], R, LST).
positives([_H|T], LST) :- positives(T, LST).

%positives(LST, POS) :- fail. 

/*
Succeeds if the list of integers can be cleaved into two sublists that both sum to
the same value.

Successful queries:
 splitable([1,2,3,4,10]).
 splitable([2,1,1]).
 splitable([0]).

Unsuccessful queries:
 splitable([1,4,8]).
 splitable([1,3,2]).
 splitable([2,2,1,1]).
*/
sumlist([], 0).
sumlist([H|T], TOTAL) :- sumlist(T, X), TOTAL is X + H.
splitable(LST) :- append(X, Y, LST), sumlist(X, TOTALX), sumlist(Y, TOTALY), TOTALX = TOTALY, !.
%splitable(LST) :- fail.


/*
The first parameter is an instantiate list of atoms.
The second parameters is an atom. The third parameter
is then instanitated to the original list, but with
all instances of the given value (i.e., second parameter)
removed.

Successful queries:
 removeAll([], a, []).
 removeAll([], a, L).        % L = []
 removeAll([a], a, L).       % L = []
 removeAll([a,b,c], d, L).   % L = [a,b,c]
 removeAll([a,a,c,a], a, L). % L = [c]

Unsuccessful queries:
 removeAll([], a, [a]).
*/

removeAll([], _A , []) :- !.
removeAll([H|T], A , LST) :- H \= A, 
removeAll(T, A, R), append([H], R, LST).
removeAll([_H|T], A, LST) :- removeAll(T, A, LST).


/*
Here are the parental relationships (see Google Docs file "GenerationNames"
located in the homework folder). The abbreviations used are based on females:
         D = daughther, A = aunt, N = niece, M = mother
So, ggm corresponds to great-grandmother. gn corresponds to great niece.
The following definitions are given to you for testing purposes. Your code 
should also work for other sets of people. The family tree cannot have cycles.
*/

parent(gggm, ggm).
parent(ggm, gm).
parent(gm, m).
parent(m, self).
parent(self, d).
parent(d, gd).

parent(gggm, gga). parent(gga, c12a). parent(c12a, c21a). parent(c21a, c3).
parent(ggm, ga). parent(ga, c11a). parent(c11a, c2).
parent(gm, a). parent(a, c1).

parent(m,s).
parent(s, n). parent(n, gn).
parent(c1, c11b). parent(c11b, c12b).

parent(c2, c21b). parent(c21b, c22).
parent(c3, c31). parent(c31, c32).


/*
Succeeds if P1 and P2 are Mth cousins N times removed.
M and N will be bound to integers when the query is issued.
You can add "parent" facts but you can't change those
that have been provided.

Successful queries:
 mthCousinNTimesRemoved(self, c3, 3, 0).
 mthCousinNTimesRemoved(self, c31, 3, 1).
 mthCousinNTimesRemoved(self, c32, 3, 2).

 mthCousinNTimesRemoved(self, c2, 2, 0).
 mthCousinNTimesRemoved(self, c21a, 2, 1).
 mthCousinNTimesRemoved(self, c21b, 2, 1).
 mthCousinNTimesRemoved(self, c22, 2, 2).

 mthCousinNTimesRemoved(self, c1, 1, 0).
 mthCousinNTimesRemoved(self, c11a, 1, 1).
 mthCousinNTimesRemoved(self, c11b, 1, 1).
 mthCousinNTimesRemoved(self, c12a, 1, 2).
 mthCousinNTimesRemoved(self, c12b, 1, 2).

 mthCousinNTimesRemoved(c1, c2, 2, 0).
 mthCousinNTimesRemoved(c2, c1, 2, 0).
 mthCousinNTimesRemoved(c11b, c32, 4, 1).
 mthCousinNTimesRemoved(c32, c11b, 4, 1).

Sample unsuccessful queries:
 mthCousinNTimesRemoved(self, gn, 1, 2).
 mthCousinNTimesRemoved(self, s, 1, 0).
 mthCousinNTimesRemoved(gd, ggm, 1, 1).
*/

ancestor(X, X, 0).
ancestor(X, Y, COUNT) :- parent(X, Z), ancestor(Z, Y, COUNT2), COUNT is COUNT2 + 1.

mthCousinNTimesRemoved(X, Y, M, N) :- \+ ancestor(X, Y, COUNT), \+ ancestor(Y, X, COUNT), parent(Z, Y), parent(Z2, X), Z \= Z2, mthCousinNTimesRemoved(X, Y, M, N, X).
mthCousinNTimesRemoved(X, Y, M, N, CF) :- parent(P, CF), ancestor(P, X, COUNTX), 
ancestor(P, Y, COUNTY), P \= X, P \= Y, X \= Y, COUNTX >= COUNTY, !, ME is (COUNTY - 1), NE is (COUNTX - COUNTY), M is ME, N is NE, !.
mthCousinNTimesRemoved(X, Y, M, N, CF) :- parent(P, CF),  ancestor(P, X, COUNTX), 
ancestor(P, Y, COUNTY), P \= X, P \= Y, X \= Y, COUNTX < COUNTY, !, ME is (COUNTX - 1), NE is (COUNTY - COUNTX), M is ME, N is NE, !.
mthCousinNTimesRemoved(X, Y, M, N, CF) :- parent(P, CF), mthCousinNTimesRemoved(X, Y, M, N, P).

/*
ancestor(X, X, 0).
ancestor(X, Y, COUNT) :- parent(X, Z), ancestor(Z, Y, COUNT2), COUNT is COUNT2 + 1.
mthCousinNTimesRemoved(X, Y, M, N) :- ancestor(Z, X, COUNTX), 
ancestor(Z, Y, COUNTY), Z \= X, Z \= Y, X \= Y, COUNTX >= COUNTY, ME is (COUNTY - 1), NE is (COUNTX - COUNTY), M = ME, N = NE, !.
mthCousinNTimesRemoved(X, Y, M, N) :- ancestor(Z, X, COUNTX), 
ancestor(Z, Y, COUNTY), Z \= X, Z \= Y, X \= Y, COUNTX < COUNTY, ME is (COUNTX - 1), NE is (COUNTY - COUNTX), M = ME, N = NE, !.
*/  
/*
Nim is a two-player, turn-taking, game using three piles of
objects. Each player can pick up any number of items from
exactly one pile. The game ends when all the objects are
removed; the last player to move loses the game.

Write a prolog rule to determine if the current player can
win the game, if they play the game perfectly. This Prolog
rule will succeed if a winning if it is possible. The query
will have the form: nim(P1, P2, P3), where P1, P2, and P3 are
parameters instantiated to non-negative integers. These values
correspond to the number of objects in the three piles. If a win
is possible, the rule should succeed; otherwise, it should fail.
Here are some examples:

Successful queries:
 nim(2, 0, 0).
 nim(2, 1, 0).
 nim(1, 2, 2).
 nim(2, 2, 3).

Unsuccessful queries:
 nim(1, 0, 0).
 nim(1, 1, 1).

Your code should work for starting piles containing any number of
objects; however, only piles with original sizes of 10 and fewer
will be tested.
*/

%nim(P1, P2, P3) :- fail.
