
(unless (packagep (find-package 'quicklisp))
  (load "~/quicklisp/setup.lisp"))

(unless (packagep (find-package 'cl-fad))
  (ql:quickload 'cl-fad))

(unless (packagep (find-package 'cl-ppcre))
  (ql:quickload 'cl-ppcre))

(defun reload-plugins ()
  (mapcar (lambda (plugin)
            (load plugin)) (find-plugins)))


(defun find-plugins ()
  (let ((the-files (cl-fad:list-directory "lisp/plugins" :follow-symlinks nil)))
    (mapcar (lambda (file)
              (when (cl-ppcre:scan ".lisp$" (namestring file))
                file)) the-files)))


; For running forms in swank.
(defmacro safety-dance (&rest args)
  `(ignore-errors ,args))

(defun refresh (sender)
  (load "lisp/pkg.lisp")
  (reload-plugins)
  "reloaded.")

(defun wtf (sender)
  "Yep, you can.")

(defun load-all-plugins (sender)
  (reload-plugins)
  "Ok.")



(defpackage #:tool
  (:use :cl)
  (:export #:get-all-worlds
           #:testy))

(in-package #:tool)

(defun testy (sender)
  "Reloading packages works.")

(defun get-all-worlds ()
  (let ((get-server (jstatic "getServer" "Canary")))
    (let ((get-world-manager (jstatic "getWorldManager" get-server)))
      (jcall "getAllWorlds" get-world-manager))))

