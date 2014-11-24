

(defpackage :lcraft
  (:use :common-lisp)
  (:export :boot))


(in-package #:lcraft)

(defun boot ()
  (Canary.log.info "Booting..."))

(defun banner ()
  "LambdaCraft explode-your-server edition!")


(let ((pack (find-package :lcraft)))
  (do-all-symbols (sym pack)
    (when (eql (symbol-package sym) pack)
      (export sym))))

(defun welcome (plugin engine classloader)
	(describe plugin))