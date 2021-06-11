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

% A summary of the full AL definition is located at the bottom

%---------------------%
% Dynamic Causal Laws %
%---------------------%

% write_draft(S) causes draft_written(S)
holds(draft_written(S), I1) :- occurs(write_draft(S), I),
                                           I < n, 
                                           next(I, I1).

% review_draft(SR, S) causes draft_reviewed(S, SR)
holds(draft_reviewed(S, SR), I1) :- occurs(review_draft(SR, S), I),
                                           I < n, 
                                           next(I, I1).

%--------------------------%
% Executability Conditions %
%--------------------------%

% impossible write_draft(S) if draft_reviewed(SR, S)
-occurs(write_draft(S), I) :- holds(draft_reviewed(SR, S), I),
                                              step(I),
                                              student(SR),
                                              student(S).

% impossible review_draft(SR, S) if S == SR
-occurs(review_draft(SR, S), I) :-            step(I),
                                              student(S),
                                              S == SR.

% impossible review_draft(SR, S) if draft_written(S)
-occurs(review_draft(SR, S), I) :-  not holds(draft_written(S), I),
                                              step(I),
                                              student(SR),
                                              student(S).
% CWA 
% -holds(F, N) if not holds(F, N)
-holds(F, N) :- fluent(inertial, F), not holds(F, N), step(N).                                      

% FULL AL DESCRIPTION

% write_draft(S) causes draft_written(S)
% review_draft(SR, S) causes draft_reviewed(S, SR)
% impossible write_draft(S) if draft_reviewed(SR, S)
% impossible review_draft(SR, S) if S == SR
% impossible review_draft(SR, S) if draft_written(S)
% -holds(F, N) if not holds(F, N)