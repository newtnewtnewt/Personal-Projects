
% Action Language AL:
% buy(VENDOR, CUSTOMER, PRODUCE) causes bought_produce(CUSTOMER, PRODUCE)
holds(bought_produce(CUSTOMER, PRODUCE), I1) :- occurs(buy(VENDOR, CUSTOMER, PRODUCE), I),
                                           I < n, 
                                           next(I, I1).
% Action Language AL:
% buy(VENDOR, CUSTOMER, PRODUCE) causes -selling_produce(CUSTOMER, PRODUCE)                               
-holds(selling_produce(VENDOR, PRODUCE), I1) :- occurs(buy(VENDOR, CUSTOMER, PRODUCE), I),
                                           I < n, 
                                           next(I, I1).
% Action Language AL:
% -selling_produce(CUSTOMER, PRODUCE) if selling_produce(CUSTOMER, PRODUCE), PRODUCE1 != PRODUCE2
-holds(selling_produce(VENDOR, PRODUCE1), I) :- holds(selling_produce(VENDOR, PRODUCE2), I),
                                               produce(PRODUCE1),
                                               step(I),
                                               PRODUCE1 != PRODUCE2.
% Action Language AL:
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if -selling_produce(VENDOR, PRODUCE)
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :- -holds(selling_produce(VENDOR, PRODUCE), I),
                                              step(I),
                                              customer(CUSTOMER),
                                              produce(PRODUCE),
                                              vendor(VENDOR).
% Action Language AL:                                              
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if buy(VENDOR, CUSTOMER, PRODUCE), CUSTOMER != CUSTOMER2
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :-   occurs(buy(VENDOR, CUSTOMER2, PRODUCE), I),
                                                produce(PRODUCE),
                                                customer(CUSTOMER),
                                                CUSTOMER != CUSTOMER2,
                                                step(I).
% Action Language AL:                                              
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if buy(VENDOR, CUSTOMER, PRODUCE), bought_produce(CUSTOMER, PRODUCE)
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :-   occurs(buy(VENDOR, CUSTOMER2, PRODUCE), I),
                                                produce(PRODUCE),
                                                customer(CUSTOMER),
                                                holds(bought_produce(CUSTOMER, PRODUCE), I),
                                                step(I).
% Action Language AL:                                              
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if buy(VENDOR, CUSTOMER, PRODUCE), VENDOR != VENDOR2
-occurs(buy(VENDOR, CUSTOMER, PRODUCE), I) :-   occurs(buy(VENDOR2, CUSTOMER, PRODUCE2), I),
                                                produce(PRODUCE),
                                                customer(CUSTOMER),
                                                vendor(VENDOR),
                                                VENDOR != VENDOR2,
                                                step(I).
% Full Action Language (AL) Domain:
% buy(VENDOR, CUSTOMER, PRODUCE) causes bought_produce(CUSTOMER, PRODUCE)
% buy(VENDOR, CUSTOMER, PRODUCE) causes -selling_produce(CUSTOMER, PRODUCE)
% -selling_produce(CUSTOMER, PRODUCE) if selling_produce(CUSTOMER, PRODUCE), PRODUCE1 != PRODUCE2
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if -selling_produce(VENDOR, PRODUCE)
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if buy(VENDOR, CUSTOMER, PRODUCE), CUSTOMER != CUSTOMER2
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if buy(VENDOR, CUSTOMER, PRODUCE), bought_produce(CUSTOMER, PRODUCE)
% impossible buy(VENDOR, CUSTOMER, PRODUCE) if buy(VENDOR, CUSTOMER, PRODUCE), VENDOR != VENDOR2