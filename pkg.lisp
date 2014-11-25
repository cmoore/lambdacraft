
(defun pkg-test ()
  "Yep, pkg-test.")

(defun refresh ()
  (load "lisp/pkg.lisp"))


(defun fix (&rest args)
  (let ((the-thing (jnew "java.lang.String")))
    "I made something."))
