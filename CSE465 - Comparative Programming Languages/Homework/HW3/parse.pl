% parse.pl

noun([sun]).
noun([children]).

verb([shines]).
verb([illuminates]).

article([the]).
article([a]).

adjective([big]).
adjective([yellow]).

adverb([brightly]).

sentence(S) :- append(NP, VP, S), np(NP), vp(VP).

np([ART|NP]) :- article([ART]), np2(NP, 0).
np(NP) :- np2(NP, 0).

np2(NP2, N) :- noun(NP2), N < 11.
np2([ADJ|NP2], N) :- adjective([ADJ]), N1 is N + 1, np2(NP2, N1).

vp(VP) :- verb(VP).
vp([VERB|ADV]) :- verb([VERB]), adverb(ADV).

