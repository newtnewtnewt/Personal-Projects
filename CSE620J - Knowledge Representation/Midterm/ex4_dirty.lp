% Question 4: 
% Consider the following dynamic domain:
% You want to keep track of students’ drafts for a term paper: 
% whether a student has written their draft or not at a certain time step, 
% and whether their draft has been reviewed by a specific student or not at a certain time step.
% 
% A student cannot review their own draft. A student cannot review a draft that has not been written yet. 
% A student cannot write their own draft and review someone else’s draft at the same time.
% Show the ASP causal laws that you would write to represent this dynamic domain. 
%
% Use the following inertial fluents in your solution:
% 
% draft_written(S) - keeps track of whether student S has written his/her draft or not
% draft_reviewed(S, SR) - indicates that S's draft was reviewed by SR
% 
% And the following actions:
%
% write_draft(S) – student S writes his/her draft
% review_draft(SR, S) - student SR reviews the draft of student S
%
% You do not need to declare these fluents and actions, nor to include the inertia axioms.


% A lot of the work here is directly moved from/uses inspiration from Dr. Inclezan's reviewing1.txt code

%---------------------------------------------%
% REMOVE THIS CODE: UNNEEDED FOR THE SOLUTION %
%---------------------------------------------%

%---------------------------%
% Fluent                    %
%---------------------------%

% Once a student writes a draft, it is finished
fluent(inertial, draft_written(S)) :- student(S).
% Once a draft is reviewed, it is finished
fluent(inertial, draft_reviewed(S, SR)) :- student(S), student(SR).


%---------------------------------------------%
% REMOVE THIS CODE: UNNEEDED FOR THE SOLUTION %
%---------------------------------------------%

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

#const n=1.
step(0..n).
next(I, I1) :- step(I), step(I1), I1 = I+1.


%---------------------------%
% Actions                   %
%---------------------------%
action(write_draft(S)) :-student(S).
action(review_draft(SR, S)) :- student(SR), student(S).

%---------------------%
% Dynamic Causal Laws %
%---------------------%

% A student's draft is written if they write the draft
holds(draft_written(S), I1) :- occurs(write_draft(S), I),
                                           I < n, 
                                           next(I, I1).

% S's draft is reviewed by SR if SR reviews S's draft 
holds(draft_reviewed(S, SR), I1) :- occurs(review_draft(SR, S), I),
                                           I < n, 
                                           next(I, I1).
%--------------------------%
% Executability Conditions %
%--------------------------%

% A student cannot write their draft while reviewing someone else's
-occurs(write_draft(S), I) :- holds(draft_reviewed(SR, S), I),
                                              step(I),
                                              student(SR),
                                              student(S).

% A student cannot review their own draft 
-occurs(review_draft(SR, S), I) :-            step(I),
                                              student(S),
                                              S == SR.

% A student's draft cannot be reviewed if they do not have one
-occurs(review_draft(SR, S), I) :-  not holds(draft_written(S), I),
                                              step(I),
                                              student(SR),
                                              student(S).
                                      
% Create the students
student(john).
student(jacob).
student(jingleheimnerschmidt).
occurs(write_draft(john), 0).
occurs(write_draft(jacob), 1).
% This does not work 
% occurs(review_draft(john, john), 1).

% This does not work
% occurs(review_draft(john, jacob), 1).

% This does not work
% occurs(review_draft(john, jingleheimnerschmidt), 1).

#show occurs/2.
%#show review_draft/2.
%#show holds/2.