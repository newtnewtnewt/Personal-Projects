%%%
% The ASP Poker Solver tool takes a set of 8 poker hands at a table and determines who has the best hand
% at each of four phases (pre-flop, flop, turn, river). The tool can determine the number of 'outs' a person
% has to make better hands compared to their current hands at each phase. In addition, this tool
% is capable of determining who wins a round of poker
%%%%%%

% --------------------------
% Fluents
% --------------------------

% Each player is dealt two cards to form a hand
fluent(static, hand(card(S1, F1), card(S2, F2), P)) :- suit(S1), suit(S2), face(F1), face(F2), person(P).

%--------------
% Actions
%--------------
action(deal(card(S1, F1), card(S2, F2), P)) :- person(P), suit(S1), suit(S2), face(F1), face(F2).

% --------------------------
% Axioms 
% --------------------------

% A person holds the hand they are dealt
holds(hand(card(S1, F1), card(S2, F2), P)) :- occurs(deal(card(S1, F1), card(S2, F2), P), I),
                                              I < n,
                                              next(I, I1).

% Executability conditions

% The first card cannot match the first card of any other person
-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
                                                   person(P),
                                                   suit(S1),
                                                   suit(S2),
                                                   face(F1),
                                                   face(F2),
                                                   S1 = S3,
                                                   F1 = F3,
                                                   P != P1,
                                                   step(I).
% The first card cannot match the second card of any other person
-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
                                                   person(P),
                                                   suit(S1),
                                                   suit(S2),
                                                   face(F1),
                                                   face(F2),
                                                   S1 = S4,
                                                   F1 = F4,
                                                   P != P1,
                                                   step(I).
% The second card cannot match the first card of any other person
-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
                                                   person(P),
                                                   suit(S1),
                                                   suit(S2),
                                                   face(F1),
                                                   face(F2),
                                                   S2 = S3,
                                                   F2 = F3,
                                                   P != P1,
                                                   step(I).
% The second card cannot match the second card of any other person
-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
                                                   person(P),
                                                   suit(S1),
                                                   suit(S2),
                                                   face(F1),
                                                   face(F2),
                                                   S2 = S4,
                                                   F2 = F4,
                                                   P != P1,
                                                   step(I).                                                 
                                                   
%% GENERAL INERTIA AXIOMS FOR INERTIAL FLUENTS:

holds(F,I1) :- fluent(inertial,F),
               holds(F,I),                  
               not -holds(F,I1),
               I < n,
               next(I, I1).

-holds(F,I1) :- fluent(inertial,F),
		            -holds(F,I),                
                not holds(F,I1),
                I < n,
                next(I, I1).


% A person cannot be dealt a card someone else already  has
% --------------------------
% Actions
% --------------------------
% action(deal()) :- .
% action(flop()) :- .
% action(turn()) :- .
% action(river()) :- .


% community_cards() :- .

%community_cards(card(S1, F1), card(S2, F2), card(S3, F3)) :-    suit(S1), suit(S2) suit(S3), face(F1), face(F2), face(F3),
%                                                                S1 != S2, F1 != F2;
%                                                                S2 != S3, F2 != F3;
%                                                                S1 != S3, F1 != F3.

deck().

% There are four suits in a deck of cards
suit(diamonds).
suit(spades).
suit(hearts).
suit(clubs).
% There are 13 faces in a deck of cards
face(two).
face(three).
face(four).
face(five).
face(six).
face(seven).
face(eight).
face(nine).
face(ten).
face(jack).
face(queen).
face(king).
face(ace).
% This totals 52 cards
card(S, F) :- suit(S), face(F). 
% A hand contains two unique cards: Note, this includes all permutations. We'll make it into combinations later
%hand(card(S1, F1), card(S2, F2)) :- not (S1 = S2, F1 = F2), suit(S1), suit(S2), 
%                                    face(F1), face(F2).
hand(card(S1, F1), card(S2, F2)) :- F1 != F2, suit(S1), suit(S2), 
                                    face(F1), face(F2).
hand(card(S1, F1), card(S2, F2)) :- S1 != S2, suit(S1), suit(S2), 
                                    face(F1), face(F2).
% We are playing 8-handed poker
table(H1, H2, H3, H4, H5, H6, H7, H8) :- hand(H1), hand(H2), hand(H3), hand(H4), 
                                         hand(H5), hand(H6), hand(H7), hand(H8).


% Pre-game Execution Variables
% Rounds
#const n=4. 
% change the number
step(0..n).
next(I, I1) :- step(I), step(I1), I1 = I+1.

% BEGIN EXECUTION BLOCK HERE
person(doyle_brunson).
person(phil_ivey).
person(daniel_negreanu).
person(phil_hellmuth).
person(tom_dwan).
person(scottie_nguyen).
person(elky).
person(doug_polk).
% Debug Predicate
num_hands(NUMBER_OF_HANDS) :- NUMBER_OF_HANDS = #count{X, Y : hand(X, Y)}.

%#show num_cards(NUMBER_OF_CARDS) : NUMBER_OF_CARDS = #count{S, F : card(S, F)}.
#show num_hands/1.
%#show hand/2.

