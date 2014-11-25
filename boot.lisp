
(require 'java)

(defun banner ()
  "LambdaCraft explode-your-server edition!")

(defvar *logger* nil)

(defun startup (lcraft engine loader)
  (load "lisp/pkg.lisp")
  (let ((logman-instance (jcall "getLogman" lcraft)))
    (setf *logger* logman-instance)))

(defun command-line (sender args)
  (let ((command (svref args 1)))
    (jcall "info" *logger* (concatenate 'string "C: " command))
    (eval (read-from-string command))))
