
(require 'java)

(defun banner ()
  "LambdaCraft explode-your-server edition!")

(defvar *logger* nil)
(defvar *plugin* nil)
(defvar *class-loader* nil)

(defun startup (lcraft engine loader)
  (setf *plugin* lcraft)
  (setf *class-loader* loader)
  ;; (let ((logman-instance (jcall "getLogman" lcraft)))
  ;;   (setf *logger* logman-instance))

  ; The wide, wide, world of packages.
  (unless (packagep (find-package 'ql))
    (and (probe-file #P"~/quicklisp/setup.lisp")
         (load #P"~/quicklisp/setup.lisp")))
  
  (load "lisp/pkg.lisp")
  (ignore-errors
   (reload-plugins)))

;; (defun command-line (sender args)
;;   (let ((command (svref args 1)))
;;     (jcall "info" *logger* (concatenate 'string "lisp: " command))
;;     (let ((the-command (read-from-string command)))
;;       (eval (append the-command sender)))))

(defun command-line (sender args)
  (cmd-line sender args))

(defmacro cmd-line (sender args)
  `(let ((command (svref ,args 1)))
     (jcall "info" *logger* (concatenate 'string "lisp: " command))
     (let ((the-command (read-from-string command)))
       (eval (append the-command '(,sender))))))


;; (defmacro command-line (sender (&rest args))
;;   `(let* ((command (svref ,args 1))
;;           (the-command (read-from-string command)))
;;      (append the-command ,sender)))
