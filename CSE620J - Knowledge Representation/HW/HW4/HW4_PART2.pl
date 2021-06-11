%%%%%%%%%%%%%%%%
%   PART   2   %
%%%%%%%%%%%%%%%%
% The following code provided by Dr. Inclezan
% The code remains complete in example, several predicates
% I.E siblings and -member do not go used

% Establish classifications for the problem
class(animal).
class(squirrel).
class(flying_squirrel).

% Inheritance relation is 
%  animal <- squirrel <- flying_squirrel
is_subclass(squirrel, animal).
is_subclass(flying_squirrel, squirrel).

% Subclasses are used primarily to build the Member function out
subclass(C1,C2) :- is_subclass(C1,C2).

subclass(C1,C2) :- is_subclass(C1,C3),
                   subclass(C3,C2).
                   
-subclass(C1,C2) :- class(C1),
                    class(C2),
                    not subclass(C1,C2).
                    
%% Class Membership: 
member(X,C) :- is_a(X,C).
member(X,C) :- is_a(X,C0),
               subclass(C0,C).
               
siblings(C1,C2) :- is_subclass(C1,C),
                   is_subclass(C2,C),
                   C1 != C2.
-member(X,C2) :- member(X,C1),
                 siblings(C1,C2),
                 C1 != C2.
            
% Collection of Facts built into the inheritance relationship
is_a(ally_the_animal, animal).
is_a(bobby_the_squirrel, squirrel).
is_a(cedric_the_flying_squirrel, flying_squirrel).
is_a(dante_the_baby_flying_squirrel, flying_squirrel).
is_a(dante_the_baby_flying_squirrel, baby).

% Simplify class membership to the normal predicates
animal(X) :- member(X, animal).
squirrel(X) :- member(X, squirrel).
flying_squirrel(X) :- member(X, flying_squirrel).
-flying_squirrel(X) :- -member(X, flying_squirrel).
baby(X) :- member(X, baby).
-baby(X) :- -member(X, baby).

% The strong exception for flying squirrels is baby flying squirrels
ab(d_glide(X)) :- flying_squirrel(X), baby(X).


% The only thing we know can fly are flying_squirrels that we are not aware are babies
glide(X) :- flying_squirrel(X), 
            not baby(X).
% Animals do not fly, with the exception of adult baby squirrels
-glide(X) :- animal(X),
             not glide(X).

%%%%%%%%%%%%%% 
% TEST CASES % 
%%%%%%%%%%%%%%
% This should return all the creatures, since the inheritance root is at animal
#show animal/1.
% This should return everything but ally_the_animal. All the others are defined 
% as a type of squirrel
#show squirrel/1.
% This should return cedric_the_flying_squirrel and dante_the_baby_flying_squirrel
% as they are the only flying_squirrels
#show flying_squirrel/1.
% This should only return dante_the_baby_flying_squirrel, he is the only baby
#show baby/1.
% This should only return cedric_the_flying_squirrel, he is the only adult flying squirrel
#show glide/1.
% This should return the remainder of the creatures
#show -glide/1.
