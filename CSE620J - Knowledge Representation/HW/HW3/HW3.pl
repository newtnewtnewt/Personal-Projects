% The prompt is as follows
% “Tom is married to Sam and Bob is married to Mary. Married people normally like each other. However, Bob
% dislikes Mary"

% We establish the facts first
married(tom, sam).
married(bob, mary).
% Part B asks to include 
married(arnold, kate).
% Marriage goes both ways (Hopefully)
married(X, Y) :- married(Y, X).
% We have a definitive case -> married(X, Y)
% We have a strong exception -> not -likes(X, Y)
% We have a weak exception -> not ab(d_likes(X))
likes(X, Y) :- married(X, Y),
               not -likes(X, Y),
               not ab(d_likes(X)).
               
% Bob dislikes Mary
-likes(bob, mary).
% Kate's behavior is inconsistent
ab(d_likes(kate)).

#show likes/2.
#show -likes/2.

% Answer set:
% likes(tom,sam), likes(sam, tom), likes(mary,bob), -likes(bob,mary), likes(arnold, kate).