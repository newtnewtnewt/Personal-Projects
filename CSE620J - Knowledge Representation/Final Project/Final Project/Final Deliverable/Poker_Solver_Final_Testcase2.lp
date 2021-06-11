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
% rank_position_aces_high - How a particular card ranks against all others with aces being the highest value
% card - A playing card with a suit and a face
% hand/1 - Facts that cover all the types of possible hands
% hand_pos_val - A helper to ground the integers correctly 
% hand_position - Assigns a value to each hand's strength
% hand/2 - Determines if a player can make a given hand type with the available cards
% matches_face - Determines if a card matches a specified face
% matched_suit - Determines if a card matches a suit
% suit_count - The frequency of a certain suit appearing in a hand
% face_count - The frequency of a certain face appearing in a hand
% player - Someone playing Poker
% highest_hand - Determines which of two players has the higher hand
% tied_hand - Determines if two players have hands of tied value 
% break_tie_winner - If players hands are tied, breaks the tie if possible
% break_tie - Breaks the tie for all possible hands 
% matches_face - Checks if a face matches a specified desired face
% face_count - Counts the number of times a specified face appears
% matches_suit - Checks if a suit matches a specified desired suit
% suit_count - Counts the number of times a specified suit appears
% all_faces - A helper to ensure that all the atoms are faces
% all_suits - A helper to ensure that all the atoms are suits
% possible_hand - A hand that a given player is able to make with his/her cards and the community cards
% chosen_community_cards - Three cards chosen from the total of 5 potential community cards
% community_cards - Five cards available in the middle to both players
% find_high_card - Determines the highest valued card out of 5 cards 
% straight_flush_evaluation - Breaks ties for straights, flushes, and straight flushes

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
%  Output Predicate  %
%--------------------%

% game_outcome - Determines if the game results in a Player 1 win, a Player 2 win, or a tie

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

% Aces should be 14 for all non straight/flush hands. This seems redundant, but is computationally faster and necessary
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == two, POS = 2.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == three, POS = 3.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == four, POS = 4.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == five, POS = 5.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == six, POS = 6.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == seven, POS = 7.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == eight, POS = 8.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == nine, POS = 9.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == ten, POS = 10.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == jack, POS = 11.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == queen, POS = 12.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == king, POS = 13.
rank_position_aces_high(FACE, POS) :- face(FACE), FACE == ace, POS = 14.

% Predicate for describing the kinds of hands 
hand(royal_flush).
hand(straight_flush).
hand(four_of_a_kind).
hand(full_house).
hand(flush).
hand(straight).
hand(three_of_a_kind).
hand(two_pair).
hand(one_pair).
hand(high_card).

% Used for grounding
hand_pos_val(1..10).


% Assign hand rankings based on tier
hand_position(HAND, POS, PLAYER) :- HAND == royal_flush, POS = 1, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == straight_flush, POS = 2, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == four_of_a_kind, POS = 3, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == full_house, POS = 2, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == flush, POS = 5, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == straight, POS = 6, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == three_of_a_kind, POS = 7, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == two_pair, POS = 8, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == one_pair, POS = 9, player(PLAYER).
hand_position(HAND, POS, PLAYER) :- HAND == high_card, POS = 10, player(PLAYER).

% Hand position needs to be maximized
-hand_position(HAND, POS, PLAYER) :- hand(HAND), hand_pos_val(POS), hand_position(HAND2, POS2, PLAYER), hand(HAND2), POS2 < POS.

% Determine what hands an individual makes with a given set of cards
hand(royal_flush, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), royal_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(straight_flush, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), straight_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(four_of_a_kind, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), four_of_a_kind(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(full_house, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), full_house(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(flush, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(straight, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), straight(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(three_of_a_kind, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), three_of_a_kind(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(two_pair, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), two_pair(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(one_pair, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), one_pair(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
hand(high_card, NAME) :- player(NAME), possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME), high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).

% Determine who wins each game

% If there is no tie, the player with the highest hand wins
game_outcome(P1, P2, OUTCOME) :- highest_hand(HAND1, P1, HAND2, P2, HP), OUTCOME = HP.

% If there is a tie, and both tied hands have equal value, the final result is a tie
game_outcome(P1, P2, OUTCOME) :- tied_hand(HAND1, P1, HAND2, P2), break_tie_winner(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, HAND1, P1, 
                                                                                             S6, F6, S7, F7, S9, F9, S9, F9, S10, F10, VALUE2, HAND1, P2),
                                                                                             VALUE == VALUE2, OUTCOME = tie.
% If there is a tie, and P1 has a higher valued hand, they win
game_outcome(P1, P2, OUTCOME) :- tied_hand(HAND1, P1, HAND2, P2), break_tie_winner(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, HAND1, P1, 
                                                                                             S6, F6, S7, F7, S9, F9, S9, F9, S10, F10, VALUE2, HAND1, P2),
                                                                                             VALUE > VALUE2, OUTCOME = P1.
% If there is a tie, and P2 has a higher valued hand, they win
game_outcome(P1, P2, OUTCOME) :- tied_hand(HAND1, P1, HAND2, P2), break_tie_winner(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, HAND1, P1, 
                                                                                             S6, F6, S7, F7, S9, F9, S9, F9, S10, F10, VALUE2, HAND1, P2),
                                                                                             VALUE < VALUE2, OUTCOME = P2.


% Determine the highest hand a person can make
highest_hand(HAND1, P1, HAND2, P2, HP) :- hand(HAND1, P1), hand_position(HAND1, POS1, P1), 
                                          hand(HAND2, P2), hand_position(HAND2, POS2, P2), 
                                          P1 != P2, POS1 < POS2, HP = P1.  

highest_hand(HAND1, P1, HAND2, P2, HP) :- hand(HAND1, P1), hand_position(HAND1, POS1, P1), 
                                          hand(HAND2, P2), hand_position(HAND2, POS2, P2), 
                                          P1 != P2, POS1 > POS2, HP = P1.

tied_hand(HAND1, P1, HAND2, P2) :- hand(HAND1, P1), hand_position(HAND1, POS1, P1), 
                                   hand(HAND2, P2), hand_position(HAND2, POS2, P2), 
                                   P1 != P2, POS1 == POS2.   


% If hand position is tied, evaluate the high card
break_tie_winner(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER, 
                 S6, F6, S7, F7, S9, F9, S9, F9, S10, F10, VALUE2, TIED_HAND, PLAYER2) :- break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER), 
                                                                                          break_tie(S6, F6, S7, F7, S9, F9, S9, F9, S10, F10, VALUE2, TIED_HAND, PLAYER2),
                                                                                          player(PLAYER),
                                                                                          player(PLAYER2),
                                                                                          PLAYER != PLAYER2.
% You cannot break a royal flush tie ever
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :-  possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                                TIED_HAND == royal_flush, 
                                                                                VALUE = 100. 

% Use the special straight/flush rule to evaluate ties. This rule accounts for high/low aces
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :-  possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER), 
                                                                                TIED_HAND == straight_flush,
                                                                                straight_flush_evaluation(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, TOTAL),
                                                                                VALUE = TOTAL.
% Four of a kind relies on high card
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND == four_of_a_kind, 
                                                                               face_count(F1, F2, F3, F4, F5, 4, PROMINENT_FACE),
                                                                               rank_position_aces_high(PROMINENT_FACE, FACE_VALUE),
                                                                               VALUE = FACE_VALUE. 

% Full house is evaluated by the PROMINENT_FACE (The three cards of the full house), 
% followed by the LESSER_FACE (The two cards of the full house)
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND == full_house, 
                                                                               face_count(F1, F2, F3, F4, F5, 3, PROMINENT_FACE),
                                                                               face_count(F1, F2, F3, F4, F5, 2, LESSER_FACE),
                                                                               rank_position_aces_high(PROMINENT_FACE, FACE_VALUE1),
                                                                               rank_position_aces_high(LESSER_FACE, FACE_VALUE2),
                                                                               VALUE = FACE_VALUE1 + FACE_VALUE2. 

% Flushes are treated like straight flushes
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND == flush, 
                                                                               straight_flush_evaluation(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, TOTAL),
                                                                               VALUE = TOTAL.
% Straights are treated like straight flushes
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND = straight, 
                                                                               straight_flush_evaluation(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, TOTAL),
                                                                               VALUE = TOTAL.  

% Three of a kind is evaluated by taking the value of the card that appears three times                                                   
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND == three_of_a_kind, 
                                                                               face_count(F1, F2, F3, F4, F5, 3, PROMINENT_FACE),
                                                                               rank_position_aces_high(PROMINENT_FACE, FACE_VALUE),
                                                                               VALUE = FACE_VALUE.       

% Two Pair is evaluated by taking the value of the cards in both pairs                                                   
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND == two_pair, 
                                                                               face_count(F1, F2, F3, F4, F5, 2, PROMINENT_FACE),
                                                                               face_count(F1, F2, F3, F4, F5, 2, PROMINENT_FACE2),
                                                                               PROMINENT_FACE != PROMINENT_FACE2,
                                                                               rank_position_aces_high(PROMINENT_FACE, FACE_VALUE1),
                                                                               rank_position_aces_high(PROMINENT_FACE2, FACE_VALUE2),
                                                                               VALUE = FACE_VALUE1 + FACE_VALUE2.                                                                             
% One Pair is evaluated by taking the value of the cards in the pair
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND == one_pair, 
                                                                               face_count(F1, F2, F3, F4, F5, 2, PROMINENT_FACE),
                                                                               rank_position_aces_high(PROMINENT_FACE, FACE_VALUE),
                                                                               VALUE = FACE_VALUE.     

% High card is evaluated by taking the value of the highest card  
break_tie(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, VALUE, TIED_HAND, PLAYER) :- possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, PLAYER),
                                                                               TIED_HAND = high_card, 
                                                                               find_high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, V),
                                                                               VALUE = V.                                                                                  
                                                                                
% This totals 52 cards
card(S, F) :- suit(S), face(F). 

%Generate all two cards for each player
% 2 { player_card(S, F, P) : card(S, F)} 2 :- player(P).
% This rule takes forever to run, only use if player cards are not specified 

%Prevent cards from showing up in multiple hands
:-  player_card(S1, F1, P1), player_card(S2, F2, P2),
     F1 == F2, S1 == S2, 
     P1 != P2.

% Check if a face matches the FACE we are looking for, return a 1 if it does, 0 if it doesn't
matches_face(FACE, DESIRED_FACE, RESULT) :- face(FACE), face(DESIRED_FACE), FACE == DESIRED_FACE, RESULT = 1.
matches_face(FACE, DESIRED_FACE, RESULT) :- face(FACE), face(DESIRED_FACE), FACE != DESIRED_FACE, RESULT = 0.

% Counts the number of times a FACE appears out of a standard 5 card hand (F1 - F5), and save the count in NUM_TIMES
face_count(F1, F2, F3, F4, F5, NUM_TIMES, FACE) :- all_faces(F1, F2, F3, F4, F5),
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
suit_count(S1, S2, S3, S4, S5, NUM_TIMES, SUIT) :- all_suits(S1, S2, S3, S4, S5),
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
                                                       flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).

%% A straight flush occurs when a flush (all of the same suit) and straight (cards of increasing value) occur, excluding the royal flush
straight_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- straight(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5),
                                                          not royal_flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5),
                                                          flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5).
                                                          
% Four of a kind, colloquially known as 'Quads', occurs when four of the same card appears
four_of_a_kind(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 4, FACE), 
                                                          all_suits(S1, S2, S3, S4, S5).

% A full house, colloquially known as a 'Boat', occurs when three of one card appears, and two of another card appears
full_house(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 3, FACE1),
                                                      face_count(F1, F2, F3, F4, F5, 2, FACE2),
                                                      FACE1 != FACE2,
                                                      all_suits(S1, S2, S3, S4, S5).
% A flush occurs when there are 5 of the same suit present
flush(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- all_faces(F1, F2, F3, F4, F5), 
                                                 suit_count(S1, S2, S3, S4, S5, 5, FACE).

% A straight takes place when cards are in a row based on rank. Aces are the lowest and highest card
straight(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :-  all_faces(F1, F2, F3, F4, F5), 
                                                     all_suits(S1, S2, S3, S4, S5),
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
                                                           all_suits(S1, S2, S3, S4, S5).

% Two pair, occurs when a two sets of two of the same card appear
two_pair(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- face_count(F1, F2, F3, F4, F5, 2, FACE1),
                                                    face_count(F1, F2, F3, F4, F5, 2, FACE2),
                                                    face_count(F1, F2, F3, F4, F5, 1, FACE3),
                                                    FACE1 != FACE2,
                                                    FACE2 != FACE3,
                                                    FACE1 != FACE3,
                                                    all_suits(S1, S2, S3, S4, S5).


% Pair, occurs when two of the same card appear
one_pair(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- all_suits(S1, S2, S3, S4, S5),
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
high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- all_suits(S1, S2, S3, S4, S5),
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

% Helpers to clean up syntax
all_faces(F1, F2, F3, F4, F5) :- face(F1), face(F2), face(F3), face(F4), face(F5).
all_suits(S1, S2, S3, S4, S5) :- suit(S1), suit(S2), suit(S3), suit(S4), suit(S5).


% We want to constrain all possible hands to a single answer set
% 5 Choose 3 could normally be done with Generate Tests methodology, but we need to contrain the
% Finding of a hand to multiple 
possible_hand(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, NAME) :- all_faces(F1, F2, F3, F4, F5), 
                                                               all_suits(S1, S2, S3, S4, S5), 
                                                               player(NAME),
                                                               player_card(S1, F1, NAME),
                                                               player_card(S2, F2, NAME),
                                                               chosen_community_cards(S3, F3, S4, F4, S5, F5).


% Avoiding the full generate test methodology to avoid expanding into a combinatoric performance nightmare, placing 10 rules instead (5 choose 3)
chosen_community_cards(S1, F1, S2, F2, S3, F3) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).                                        
chosen_community_cards(S1, F1, S2, F2, S4, F4) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).
chosen_community_cards(S1, F1, S2, F2, S5, F5) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).
chosen_community_cards(S1, F1, S3, F3, S4, F4) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).
chosen_community_cards(S1, F1, S3, F3, S5, F5) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).
chosen_community_cards(S1, F1, S4, F4, S5, F5) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).

chosen_community_cards(S2, F2, S3, F3, S4, F4) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).
chosen_community_cards(S2, F2, S3, F3, S5, F5) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).
chosen_community_cards(S2, F2, S4, F4, S5, F5) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).

chosen_community_cards(S3, F3, S4, F4, S5, F5) :- community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5), all_faces(F1, F2, F3, F4, F5), all_suits(S1, S2, S3, S4, S5).


% A card cannot appear in the community cards if it's already in a player's hand
-community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- player_card(S1, F1, _), all_suits(S1, S2, S3, S4, S5), all_faces(F1, F2, F3, F4, F5).
-community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- player_card(S2, F2, _), all_suits(S1, S2, S3, S4, S5), all_faces(F1, F2, F3, F4, F5).
-community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- player_card(S3, F3, _), all_suits(S1, S2, S3, S4, S5), all_faces(F1, F2, F3, F4, F5).
-community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- player_card(S4, F4, _), all_suits(S1, S2, S3, S4, S5), all_faces(F1, F2, F3, F4, F5).
-community_cards(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5) :- player_card(S5, F5, _), all_suits(S1, S2, S3, S4, S5), all_faces(F1, F2, F3, F4, F5).

% Calculates the highest card available in the hand of five cards. Aces are 14 and 1, so we hard code their value.
find_high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, V) :- all_suits(S1, S2, S3, S4, S5), 
                                                             rank_position(F1, POS1), rank_position(F2, POS2), 
                                                             rank_position(F3, POS3), rank_position(F4, POS4), rank_position(F5, POS5),
                                                             POS5 >= POS4, POS4 >= POS3, POS3 >= POS2, POS2 >= POS1, V = POS5, face_count(F1, F2, F3, F4, F5, 0, ace).

% Hardcode the high value for an ace
find_high_card(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, V) :- all_suits(S1, S2, S3, S4, S5), face_count(F1, F2, F3, F4, F5, 1, ace), V = 14.



% Hardcode the value to avoid duplicity in rank position for Ace (Aces can be 1 or 14), only on straights, flushes, or straight flushes
straight_flush_evaluation(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, TOTAL) :- all_suits(S1, S2, S3, S4, S5), 
                                                            rank_position(F1, POS1), rank_position(F2, POS2), 
                                                            rank_position(F3, POS3), rank_position(F4, POS4), rank_position(F5, POS5),
                                                            face_count(F1, F2, F3, F4, F5, 1, king), face_count(F1, F2, F3, F4, F5, 1, ace),
                                                            TOTAL = 60.
                                                            
straight_flush_evaluation(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, TOTAL) :- all_suits(S1, S2, S3, S4, S5), 
                                                             rank_position(F1, POS1), rank_position(F2, POS2), 
                                                             rank_position(F3, POS3), rank_position(F4, POS4), rank_position(F5, POS5),
                                                             face_count(F1, F2, F3, F4, F5, 1, ace), face_count(F1, F2, F3, F4, F5, 1, two),
                                                             TOTAL = 15.      

% All other straights and flushes containing no aces
straight_flush_evaluation(S1, F1, S2, F2, S3, F3, S4, F4, S5, F5, TOTAL) :- all_suits(S1, S2, S3, S4, S5), 
                                                             rank_position(F1, POS1), rank_position(F2, POS2), 
                                                             rank_position(F3, POS3), rank_position(F4, POS4), rank_position(F5, POS5),
                                                             face_count(F1, F2, F3, F4, F5, 0, ace), 
                                                             TOTAL = POS1 + POS2 + POS3 + POS4 + POS5.                                                     
                                                                                 



% BEGIN EXECUTION BLOCK For Test Cases
% ====================================

% We are playing 2-handed poker between Daniel Negreanu and Phil Hellmuth
player(daniel_negreanu).
player(phil_hellmuth).

% Give each player a hand

% Daniel gets the two of spades and the three of clubs in his hand
player_card(spades, two, daniel_negreanu).
player_card(clubs, three, daniel_negreanu).

% Phil gets the two of hearts and the three of hearts in his hand
player_card(hearts, two, phil_hellmuth).
player_card(hearts, three, phil_hellmuth).

% Place the five and queen of diamonds, the ace and four of spades, and the six of clubs in the middle
community_cards(diamonds, five, spades, four, clubs, six, spades, ace, diamonds, queen).

% The game outcome should be a tie
% Daniel's best hand is 6-high straight
% Phil's best hand is a 6-high straight
% This yields a tie (Colloquially known as a chop)
#show game_outcome/3.