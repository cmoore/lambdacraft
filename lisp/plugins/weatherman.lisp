
(defpackage #:weatherman
  (:use :cl :tool :core)
  (:export #:test))

(in-package :weatherman)

(defun test (sender)
  (let ((x (tool:get-all-worlds)))
    (when x
      "Yes!")))


;; (defun on-server-tick-hook (hook)
;;   (let ((worlds ())))
;;   )

