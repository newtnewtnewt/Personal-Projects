(load "hw2S20.scm")

(define (displayNL value)
	(DISPLAY "-->")
	(DISPLAY value)
	(NEWLINE)
	#t
)

; This file shows how your code will be invoked.
(displayNL "quadratic")
(displayNL (quadratic 1 0 0))
(displayNL (quadratic 1 3 -4))
(newline)

(displayNL "isLargerCube")
(displayNL (isLargerCube 1 3 4 2 2 2))
(displayNL (isLargerCube 2 2 2 1 3 4))
(newline)

(displayNL "clamp")
(displayNL (clamp 0.5 0.0 1.0))
(displayNL (clamp -0.5 0.0 1.0))
(displayNL (clamp 1.5 0.0 1.0))

(displayNL "isLeapYear")
(displayNL (isLeapYear 2020))
(displayNL (isLeapYear 2021))
(displayNL (isLeapYear 2100))
(displayNL (isLeapYear 2200))
(displayNL (isLeapYear 2300))
(displayNL (isLeapYear 2400))

(displayNL "positives")
(displayNL (positives '(1 2 9 -2 0 -3 4)))
(displayNL (positives '()))

(displayNL "numsInRange")
(displayNL (numsInRange '(1 2 3 4 8 10 -9 8 5) 3 8))

(displayNL "")

(displayNL "zip")
(displayNL (zip '(1 2 3) '(a b c)))  

(displayNL "transpose")
(displayNL (transpose '((1 2 3)(4 5 6)(7 8 9))))

(displayNL "flatten")
(displayNL (flatten '(1 2 3)))
(displayNL (flatten '(a (b c) ((d e) f))))

(displayNL "funcWithTailRecursion")
(displayNL (funcWithTailRecursion 10))

(displayNL "BSTContainsValue")
(displayNL (BSTContainsValue '(() 4 ()) 4))
(displayNL (BSTContainsValue '(() 4 ()) 10))
(displayNL (BSTContainsValue '((() 4 ()) 6 (((() 8 ()) 9 (() 10 ())) 12 ())) 10))
(displayNL (BSTContainsValue '((() 4 ()) 6 (((() 8 ()) 9 (() 10 ())) 12 ())) 100))
(displayNL (BSTContainsValue '((() 4 ()) 6 (((() 8 ()) 9 (() 10 ())) 12 ())) 6))

(displayNL "treeHeight")
(displayNL (treeHeight '(() 4 ())))
(displayNL (treeHeight '((() 4 ()) 6 (((() 8 ()) 9 (() 10 ())) 12 ()))))

(displayNL "addToBST")
(displayNL (addToBST '(() 8 ()) 10))
(displayNL (addToBST '(() 8 ()) 6))
(displayNL (addToBST '(() 8 (() 10())) 9))

(displayNL "isBST")
(displayNL (isBST '(() 8 ())))
(displayNL (isBST '((() 4 ()) 8 (() 10 ()))))
(displayNL (isBST '((() 4 ()) 8 (() 0 ()))))
(displayNL (isBST '(() 8 (() 10()))))
(displayNL (isBST '((() 4 ()) 6 (((() 8 ()) 9 (() 10 ())) 12 ()))))
(displayNL (isBST '((() 4 ()) 6 (((() 8 ()) 9 (() 7 ())) 120 ())))) 

(displayNL "accumulate")
(displayNL (accumulate '(1 2 3 4) (lambda (x y) (+ x y))))
(displayNL (accumulate '(1 2 3 4) (lambda (x y) (* x y))))
(displayNL (accumulate '(1) (lambda (x y) (+ x y))))
(displayNL (accumulate '(2 2 3) expt))

(define (sqr x) (* x x))

(displayNL "conditionalApplyAll")
(displayNL (conditionalApplyAll '(1 2 3 4 5 6) EVEN? sqr))

(displayNL "line")
(define L1 (eqOfLine 0.0 0.0 3.0 4.0))
(displayNL (L1 0.0))
(displayNL (L1 3.0))
(displayNL (L1 9.0))

#t
,exit
