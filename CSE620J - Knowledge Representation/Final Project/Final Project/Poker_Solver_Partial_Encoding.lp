% Project Name: ASP Poker Solver
% Programmers: John Hata and Noah Dunn
% KRR methodology: Generate-Test Methodology to Solve Combinatorical Problems


%%%%%%
% The ASP Poker Solver tool takes a set of 8 poker hands at a table and determines who has the best hand
% at each of four phases (pre-flop, flop, turn, river). The tool can determine the number of 'outs' a player
% has to make better hands compared to their current hands at each phase. In addition, this tool
% is capable of determining who wins a round of poker. 
%%%%%%

%--------------------%
%  Input Predicates  %
%--------------------%

% suit - The suit value of a card (diamonds, spades, hearts, clubs)
% face - The face value of a card (two, three, four, five, six, seven, eight, nine, ten, jack, king, queen, ace)
% rank_position - How a particular card ranks against all others
% card - A playing card with a suit and a face
% matches_face - Determines if a card matches a specified face
% matched_suit - Determines if a card matches a suit
% suit_count - The frequency of a certain suit appearing in a hand
% face_count - The frequency of a certain face appearing in a hand
% player - Someone playing Poker

% Refer to hand explanations below 
%==================================
% royal_flush
% straight_flush
% four_of_a_kind
% full_house
% flush
% straight
% three_of_a_kind
% two_pair
% pair
% find_high_card
% high_card

%--------------------%
%  Output Predicates %
%--------------------%

% table - The players participating

% TBD - Predicates intend to write but haven't yet

% winner - the winning player
% loser - the losing player
% ties - players who have tied

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
% Assign a rank position for each card (Higher value means higher rank)
rank_position(FACE, POS) :- face(FACE), FACE == ace, POS = 1.
rank_position(FACE, POS) :- face(FACE), FACE == two, POS = 2.
rank_position(FACE, POS) :- face(FACE), FACE == three, POS = 3.
rank_position(FACE, POS) :- face(FACE), FACE == four, POS = 4.
rank_position(FACE, POS) :- face(FACE), FACE == five, POS = 5.
rank_position(FACE, POS) :- face(FACE), FACE == six, POS = 6.
rank_position(FACE, POS) :- face(FACE), FACE == seven, POS = 7.
rank_position(FACE, POS) :- face(FACE), FACE == eight, POS = 8.
rank_position(FACE, POS) :- face(FACE), FACE == nine, POS = 9.
rank_position(FACE, POS) :- face(FACE), FACE == ten, POS = 10.
rank_position(FACE, POS) :- face(FACE), FACE == jack, POS = 11.
rank_position(FACE, POS) :- face(FACE), FACE == queen, POS = 12.
rank_position(FACE, POS) :- face(FACE), FACE == king, POS = 13.
rank_position(FACE, POS) :- face(FACE), FACE == ace, POS = 14.


% This totals 52 cards
card(S, F) :- suit(S), face(F). 

%Generate all two cards for each player
2 { player_card(S, F, P) : card(S, F)} 2 :- player(P).

%Prevent cards from showing up in multiple hands
:-  player_card(S1, F1, P1), player_card(S2, F2, P2),
     F1 == F2, S1 == S2, 
     P1 != P2.

% Check if a face matches the FACE we are looking for, return a 1 if it does, 0 if it doesn't
matches_face(FACE, DESIRED_FACE, RESULT) :- face(FACE), face(DESIRED_FACE), FACE == DESIRED_FACE, RESULT = 1.
matches_face(FACE, DESIRED_FACE, RESULT) :- face(FACE), face(DESIRED_FACE), FACE != DESIRED_FACE, RESULT = 0.

% Counts the number of times a FACE appears out of a standard 5 card hand (F1 - F5), and save the count in NUM_TIMES
face_count(F1, F2, F3, F4, F5, NUM_TIMES, FACE) :- face(F1), face(F2), face(F3), face(F4), face(F5),
                                                              matches_face(F1, FACE, RESULT1),
                                                              matches_face(F2, FACE, RESULT2),
                                                              matches_face(F3, FACE, RESULT3),
                                                              matches_face(F4, FACE, RESULT4),
                                                              matches_face(F5, FACE, RESULT5),
                                                              NUM_TIMES = RESULT1 + RESULT2 + RESULT3 + RESULT4 + RESULT5.

% Check if a suit matches the SUIT we are looking for, return a 1 if it does, 0 if it doesn't
matches_suit(SUIT, DESIRED_SUIT, RESULT) :- suit(SUIT), suit(DESIRED_SUIT), SUIT == DESIRED_SUIT, RESULT = 1.
matches_suit(SUIT, DESIRED_SUIT, RESULT) :- suit(SUIT), suit(DESIRED_SUIT), SUIT != DESIRED_SUIT, RESULT = 0.

% Counts the number of times a SUIT appears out of a standard 5 card hand (F1 - F5), and save the count in NUM_TIMES
suit_count(S1, S2, S3, S4, S5, NUM_TIMES, SUIT) :- suit(F1), suit(F2), suit(F3), suit(F4), suit(F5),
                                                              matches_suit(S1, SUIT, RESULT1),
                                                              matches_suit(S2, SUIT, RESULT2),
                                                              matches_suit(S3, SUIT, RESULT3),
                                                              matches_suit(S4, SUIT, RESULT4),
                                                              matches_suit(S5, SUIT, RESULT5),
                                                              NUM_TIMES = RESULT1 + RESULT2 + RESULT3 + RESULT4 + RESULT5.

% A Royal Flush is a hand contianing Ace, King, Queen, Jack, and Ten, all of the same suit
royal_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 1, ace),
                                                       face_count(F1, F2, F3, F4, F5, 1, king),
                                                       face_count(F1, F2, F3, F4, F5, 1, queen),
                                                       face_count(F1, F2, F3, F4, F5, 1, jack),
                                                       face_count(F1, F2, F3, F4, F5, 1, ten),
                                                       flush(S1, S2, S3, S4, S5).

% A straight flush occurs when a flush (all of the same suit) and straight (cards of increasing value) occur, excluding the royal flush
straight_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- straight(F1, F2, F3, F4, F5),
                                                          not royal_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5),
                                                          flush(S1, S2, S3, S4, S5).
                                                          
% Four of a kind, colloquially known as 'Quads', occurs when four of the same card appears
four_of_a_kind(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 4, FACE), 
                                                          suit(S1), suit(S2), suit(S3), suit(S4), suit(S5).

% A full house, colloquially known as a 'Boat', occurs when three of one card appears, and two of another card appears
full_house(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 3, FACE1),
                                                      face_count(F1, F2, F3, F4, F5, 2, FACE2),
                                                      FACE1 != FACE2,
                                                      suit(S1), suit(S2), suit(S3), suit(S4), suit(S5).
% A flush occurs when there are 5 of the same suit present
flush(S1, S2, S3, S4, S5) :- suit_count(S1, S2, S3, S4, S5, 5, FACE).

% A straight takes place when cards are in a row based on rank. Aces are the lowest and highest card
straight(F1, F2, F3, F4, F5) :-  face(F1), face(F2), face(F3), face(F4), face(F5), 
                                 rank_position(F1, POS1), rank_position(F2, POS2), 
                                 rank_position(F3, POS3), rank_position(F4, POS4), rank_position(F5, POS5),
                                 POS5 == POS4 + 1, POS4 == POS3 + 1, POS3 == POS2 + 1, POS2 == POS1 + 1.

% Three of a kind, colloquially known as a 'Trips', occurs when three of one card appears.
three_of_a_kind(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 3, FACE1),
                                                      face_count(F1, F2, F3, F4, F5, 1, FACE2),
                                                      face_count(F1, F2, F3, F4, F5, 1, FACE3),
                                                      FACE1 != FACE2,
                                                      FACE2 != FACE3,
                                                      FACE1 != FACE3,
                                                      suit(S1), suit(S2), suit(S3), suit(S4), suit(S5).

% Two pair, occurs when a two sets of two of the same card appear
two_pair(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 2, FACE1),
                                                    face_count(F1, F2, F3, F4, F5, 2, FACE2),
                                                    face_count(F1, F2, F3, F4, F5, 1, FACE3),
                                                    FACE1 != FACE2,
                                                    FACE2 != FACE3,
                                                    FACE1 != FACE3,
                                                    suit(S1), suit(S2), suit(S3), suit(S4), suit(S5).


% Pair, occurs when two of the same card appear
pair(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- suit(S1), suit(S2), suit(S3), suit(S4), suit(S5),
                                                face_count(F1, F2, F3, F4, F5, 2, FACE1),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE2),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE3),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE4),
                                                FACE1 != FACE2,
                                                FACE2 != FACE3,
                                                FACE1 != FACE3,
                                                FACE1 != FACE4,
                                                FACE2 != FACE4,
                                                FACE3 != FACE4.

% High card occurs when a player matches no other required hand
high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- suit(S1), suit(S2), suit(S3), suit(S4), suit(S5),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE1),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE2),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE3),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE4),
                                                face_count(F1, F2, F3, F4, F5, 1, FACE5),
                                                FACE1 != FACE2,
                                                FACE2 != FACE3,
                                                FACE1 != FACE3,
                                                FACE1 != FACE4,
                                                FACE1 != FACE5,
                                                FACE2 != FACE4,
                                                FACE2 != FACE5,
                                                FACE3 != FACE4,
                                                FACE4 != FACE5.

                                  
% Calculates the highest card available in the hand of five cards
find_high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, V) :- suit(S1), suit(S2), suit(S3), suit(S4), suit(S5), rank_position(F1, POS1), rank_position(F2, POS2), 
                                rank_position(F3, POS3), rank_position(F4, POS4), rank_position(F5, POS5),
                                POS5 >= POS4, POS4 >= POS3, POS3 >= POS2, POS2 >= POS1, V = POS5.

% We are playing 2-Handed poker
table(P) :- player(P).


% BEGIN EXECUTION BLOCK HERE
player(doyle_brunson).
player(phil_ivey).

%% Debug Predicate
#show straight_flush/10.


%------------------------------%
%  POTENTIAL WORK IN PROGRESS  %                  
%------------------------------%


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