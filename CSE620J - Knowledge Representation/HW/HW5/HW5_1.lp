% This program is built using the help of Dr. Inclezan's code
% as well as her lecture slides

%---------------------------%
% Objects + Types           %
%---------------------------%
% 
% Entities who sell things
% vendor(VENDOR).
%
% vendor(amy).
% vendor(bruce).
% vendor(carrie).
% vendor(don).

% Things that are sold
% produce(PRODUCE).
%
% produce(apples).
% produce(carrots).
% produce(lettuce).
% produce(cabbage).
% produce(pears).


%---------------------------%
% Fluent                    %
%---------------------------%

% A vendor's inventory is constantly changing
fluent(inertial, selling_produce(VENDOR, PRODUCE)) :- vendor(VENDOR), 
                                                  produce(PRODUCE).
% A customer's inventory is also constantly changing
fluent(inertial, bought_produce(CUSTOMER, PRODUCE)) :- customer(CUSTOMER), 
                                                     produce(PRODUCE).

%---------------------------%
% Actions                   %
%---------------------------%

% Vendors need to be able to buy, we assume someone can't sell to themselves
action(buy(VENDOR, CUSTOMER, PRODUCE)) :-  vendor(VENDOR), 
                                           customer(CUSTOMER), 
                                           produce(PRODUCE),
                                           VENDOR != CUSTOMER.

%---------------------------%
% Axioms                    %
%---------------------------%

%---------------------%
% Dynamic Causal Laws %
%---------------------%

% A person gains produce when they purchase it
holds(bought_produce(CUSTOMER, PRODUCE), I1) :- occurs(buy(VENDOR, CUSTOMER, PRODUCE), I),
                                           I < n, 
                                           next(I, I1).
                               
% When someone sells their produce, it is gone
-holds(selling_produce(VENDOR, PRODUCE), I1) :- occurs(buy(VENDOR, CUSTOMER, PRODUCE), I),
                                           I < n, 
                                           next(I, I1).

%--------------------------%
% State Constraints        %
%--------------------------%

% N/A, if only one good could be sold at a time this would be included here
%
% Only one good can be sold at a time
%-holds(selling_produce(VENDOR, PRODUCE1), I) :- holds(selling_produce(VENDOR, PRODUCE2), I),
%                                               produce(PRODUCE1),
%                                               step(I),
%                                               PRODUCE1 != PRODUCE2.

%--------------------------%
% Executability Conditions %
%--------------------------%

% A purchase cannot occur if the vendor is not selling it
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :- -holds(selling_produce(VENDOR, PRODUCE), I),
                                              step(I),
                                              customer(CUSTOMER),
                                              produce(PRODUCE),
                                              vendor(VENDOR).

% A vendor can only handle one person per transacation
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :-   occurs(buy(VENDOR, CUSTOMER2, PRODUCE2), I),
                                                produce(PRODUCE),
                                                customer(CUSTOMER),
                                                CUSTOMER != CUSTOMER2,
                                                step(I).
% A person cannot buy something again they already bought
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :-   occurs(buy(VENDOR, CUSTOMER2, PRODUCE), I),
                                                produce(PRODUCE),
                                                customer(CUSTOMER),
                                                holds(bought_produce(CUSTOMER, PRODUCE), I),
                                                step(I).
% A person cannot buy something from two people at once
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :-   occurs(buy(VENDOR2, CUSTOMER, PRODUCE2), I),
                                                produce(PRODUCE),
                                                customer(CUSTOMER),
                                                vendor(VENDOR),
                                                VENDOR != VENDOR2,
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


%--------------------------%
% The Actual Problem       %
%--------------------------%


% Problem 1: Sally goes to the farmers’ market 
% and buys all the lettuce that Bruce sells 
% and all the apples that Carrie sells.

% Initial Declarations

% Two Transactions Occur
#const n=2.
step(0..n).

next(I, I1) :- step(I), step(I1), I1 = I+1.

% objects
customer(sally).

vendor(bruce).
vendor(carrie).

produce(lettuce).
produce(apples).

% Initial Conditions
holds(selling_produce(bruce, lettuce), 0).
holds(selling_produce(carrie, apples), 0).
% Note that bruce and carrie can be assumed to be selling right off the bat,
% but carrie is not buying

% We do not want the closed world assumption in place here, otherwise individuals will not be seen 
% as selling items if it is not explictly declared that they aren't in future steps

% Closed World Assumption for time step 0 and 1
%-holds(F, 0) :- fluent(inertial, F), not holds(F, 0).
%-holds(F, 1) :- fluent(inertial, F), not holds(F, 1).

% Action at time step 0 and 1
occurs(buy(bruce, sally, lettuce), 0).
occurs(buy(carrie, sally, apples), 1).
#show holds/2.
#show occurs/2.
%#show -occurs/2.


