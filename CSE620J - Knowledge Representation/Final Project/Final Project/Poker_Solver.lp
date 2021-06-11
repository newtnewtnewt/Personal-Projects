% Project Name: ASP Poker Solver
% Programmers: John Hata and Noah Dunn
% 


%%%
% The ASP Poker Solver tool takes a set of 8 poker hands at a table and determines who has the best hand
% at each of four phases (pre-flop, flop, turn, river). The tool can determine the number of 'outs' a player
% has to make better hands compared to their current hands at each phase. In addition, this tool
% is capable of determining who wins a round of poker
%%%%%%


% A player cannot be dealt a card someone else already  has
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

% A hand contains two unique cards: Note, this includes all combinations
hand(card(S1, F1), card(S2, F2)) :- F1 != F2, suit(S1), suit(S2), 
                                    face(F1), face(F2).
hand(card(S1, F1), card(S2, F2)) :- S1 != S2, suit(S1), suit(S2), 
                                    face(F1), face(F2).

%:- hand(card(S1, F1), card(S2, F2)), hand(card(S2, F2), card(S1, F1)).

% Generate all two card hands for each player
1 { player_hand(card(S1, F1), card(S2, F2), P) : hand(card(S1, F1), card(S2, F2)) } 1 :- player(P).
% Prevent cards from showing up in multiple hands
:-  player_hand(card(S1, F1), card(S2, F2), P1), player_hand(card(S3, F3), card(S4, F4), P2),
    F1 == F3, S1 == S3, 
    P1 != P2.
:-  player_hand(card(S1, F1), card(S2, F2), P1), player_hand(card(S3, F3), card(S4, F4), P2),
    F1 == F4, S1 == S4, 
    P1 != P2.
:-  player_hand(card(S1, F1), card(S2, F2), P1), player_hand(card(S3, F3), card(S4, F4), P2),
    F2 == F3, S2 == S3, 
    P1 != P2.
:-  player_hand(card(S1, F1), card(S2, F2), P1), player_hand(card(S3, F3), card(S4, F4), P2),
    F2 == F4, S2 == S4, 
    P1 != P2.

%% Eliminate a
%:-  player_hand(card(S1, F1), card(S2, F2), P1), player_hand(card(S3, F3), card(S4, F4), P2),
%    P1 == P2, 
%    player(P1), player(P2), 
%    suit(S1), suit(S2), face(F1), face(F2),
%    suit(S3), suit(S4), face(F3), face(F4).
%
%:-  player_hand(card(S1, F1), card(S2, F2), P1), player_hand(card(S3, F3), card(S4, F4), P2),
%    F1 == F3, S1 == S3, 
%    P1 != P2, 
%    suit(S1), suit(S2), face(F1), face(F2).
%
%% Check if the suits match
%suit_match(S1, S2, S3, S4, S5) :- S1 = S2, S2 = S3, S3 = S4, S4 = S5, 
%                                  suit(S1), suit(S2), suit(S3), suit(S4), suit(S5). 
%
%% Check if the faces match
%face_match(F1, F2) :- F1 == F2, face(F1), face(F2).
%face_match(F1, F2, F3) :- F1 == F2, F2 == F3, face(F1), face(F2), face(F3).
%face_match(F1, F2, F3, F4) :- F1 == F2, F2 == F3, F3 == F4, face(F1), face(F2), face(F3), face(F4). 
%face_match(F1, F2, F3, F4, F5) :- F1 == F2, F2 == F3, F3 == F4, F4 == F5, face(F1), face(F2), face(F3), face(F4), face(F5). 
%
%% A full hand consists of 5 cards
%full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5)) :-
%           suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%           suit(S4), face(F4), suit(S5), face(F5).
%% First card can't match any other
%:- full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5)),
%   S1 == S2, F1 == F2, S1 == S3, F1 == F3, S1 == S4, F1 == F4, S1 == S5, F1 == F5.
%% Second card can't match any other
%:- full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5)),
%   S2 == S1, F2 == F1, S2 == S3, F2 == F3, S2 == S4, F2 == F4, S2 == S5, F2 == F5.
%% Third card can't match any other
%:- full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5)),
%   S3 == S1, F3 == F1, S3 == S2, F3 == F2, S3 == S4, F3 == F4, S3 == S5, F3 == F5.
%% Fourth card can't match any other
%:- full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5)),
%   S4 == S1, F4 == F1, S4 == S2, F4 == F2, S4 == S3, F4 == F3, S4 == S5, F4 == F5.
%% Fifth card can't match any other
%:- full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5)),
%   S5 == S1, F5 == F1, S5 == S2, F5 == F2, S5 == S3, F5 == F3, S5 == S4, F5 == F4.
%
%
%% Check if a face matches the face we are looking for
%contains_face(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        F1 = FACE_IN_QUESTION,
%                                        T_OR_F = true,
%                                        SUIT_IN_QUESTION = S1,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_face(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        F2 = FACE_IN_QUESTION,
%                                        T_OR_F = true,
%                                        SUIT_IN_QUESTION = S2,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_face(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        F3 = FACE_IN_QUESTION,
%                                        T_OR_F = true,
%                                        SUIT_IN_QUESTION = S3,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_face(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        F4 = FACE_IN_QUESTION,
%                                        T_OR_F = true,
%                                        SUIT_IN_QUESTION = S4,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_face(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        F5 = FACE_IN_QUESTION,
%                                        T_OR_F = true,
%                                        SUIT_IN_QUESTION = S5,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%
%% Check if a suit matches the suit we are looking for
%contains_suit(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        S1 = SUIT_IN_QUESTION,
%                                        T_OR_F = true,
%                                        FACE_IN_QUESTION = F1,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_suit(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        S2 = SUIT_IN_QUESTION,
%                                        T_OR_F = true,
%                                        FACE_IN_QUESTION = F2,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_suit(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        S3 = SUIT_IN_QUESTION,
%                                        T_OR_F = true,
%                                        FACE_IN_QUESTION = F3,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_suit(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        S4 = SUIT_IN_QUESTION,
%                                        T_OR_F = true,
%                                        FACE_IN_QUESTION = F4,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%contains_suit(T_OR_F, FACE_IN_QUESTION, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                        S5 = SUIT_IN_QUESTION,
%                                        T_OR_F = true,
%                                        FACE_IN_QUESTION = F5,
%                                        face(FACE_IN_QUESTION),
%                                        suit(SUIT_IN_QUESTION),
%                                        suit(S1), face(F1), suit(S2), face(F2), suit(S3), face(F3), 
%                                        suit(S4), face(F4), suit(S5), face(F5).
%                                                                                 
%
%% Define each of the hands
%% Ace, king, queen, jack, ten, same suit
%royal_flush(full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))) :- 
%                                                                                     suit_match(S1, S2, S3, S4, S5),
%                                                                                     face_match(F1, F2, F3, F4, F5),
%                                                                                     contains_face(T_OR_F, ace, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))),
%                                                                                     contains_face(T_OR_F, king, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))),
%                                                                                     contains_face(T_OR_F, queen, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))),
%                                                                                     contains_face(T_OR_F, jack, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))),
%                                                                                     contains_face(T_OR_F, ten, SUIT_IN_QUESTION, full_hand(card(S1, F1), card(S2, F2), card(S3, F3), card(S4, F4), card(S5, F5))),
%                                                                                     T_OR_F == true.
%
%                                                                                     
%            
%
%% We are playing 8-Handed poker
%
table(P1, P2) :- player(P1), player(P2).
% BEGIN EXECUTION BLOCK HERE
player(doyle_brunson).
%player(phil_ivey).
%% Debug Predicate
%% num_hands(NUMBER_OF_HANDS) :- NUMBER_OF_HANDS = #count{X, Y : hand(X, Y)}.
%
%%#show num_cards(NUMBER_OF_CARDS) : NUMBER_OF_CARDS = #count{S, F : card(S, F)}.
%#show royal_flush/5.
#show player_hand/3.
%%#show hand/2.










%% --------------------------
%% Fluents
%% --------------------------
%
%% Each player is dealt two cards to form a hand
%fluent(static, hand(card(S1, F1), card(S2, F2), P)) :- suit(S1), suit(S2), face(F1), face(F2), player(P).
%
%%--------------
%% Actions
%%--------------
%action(deal(card(S1, F1), card(S2, F2), P)) :- player(P), suit(S1), suit(S2), face(F1), face(F2).
%
%% --------------------------
%% Axioms 
%% --------------------------
%
%% A player holds the hand they are dealt
%holds(hand(card(S1, F1), card(S2, F2), P)) :- occurs(deal(card(S1, F1), card(S2, F2), P), I),
%                                              I < n,
%                                              next(I, I1).
%
%% Executability conditions
%
%% The first card cannot match the first card of any other player
%-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
%                                                   player(P),
%                                                   suit(S1),
%                                                   suit(S2),
%                                                   face(F1),
%                                                   face(F2),
%                                                   S1 = S3,
%                                                   F1 = F3,
%                                                   P != P1,
%                                                   step(I).
%% The first card cannot match the second card of any other player
%-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
%                                                   player(P),
%                                                   suit(S1),
%                                                   suit(S2),
%                                                   face(F1),
%                                                   face(F2),
%                                                   S1 = S4,
%                                                   F1 = F4,
%                                                   P != P1,
%                                                   step(I).
%% The second card cannot match the first card of any other player
%-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
%                                                   player(P),
%                                                   suit(S1),
%                                                   suit(S2),
%                                                   face(F1),
%                                                   face(F2),
%                                                   S2 = S3,
%                                                   F2 = F3,
%                                                   P != P1,
%                                                   step(I).
%% The second card cannot match the second card of any other player
%-occurs(deal(card(S1, F1), card(S2, F2), P), I) :- occurs(deal(card(S3, F3), card(S4, F4), P1), I),
%                                                   player(P),
%                                                   suit(S1),
%                                                   suit(S2),
%                                                   face(F1),
%                                                   face(F2),
%                                                   S2 = S4,
%                                                   F2 = F4,
%                                                   P != P1,
%                                                   step(I).                                                 
%                                                   
%%% GENERAL INERTIA AXIOMS FOR INERTIAL FLUENTS:
%
%holds(F,I1) :- fluent(inertial,F),
%               holds(F,I),                  
%               not -holds(F,I1),
%               I < n,
%               next(I, I1).
%
%-holds(F,I1) :- fluent(inertial,F),
%		            -holds(F,I),                
%                not holds(F,I1),
%                I < n,
%                next(I, I1).
%
% Pre-game Execution Variables
% Rounds
% #const n=4. 
% change the number
% step(0..n).
% next(I, I1) :- step(I), step(I1), I1 = I+1.