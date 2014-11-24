


(defun lambdacraft-banner ()
  "LambdaCraft explode-your-server edition!")

(defun inside-abcl ()
  "This is a string inside abcl.")

(defmacro eval-wrapper (&rest stuff)
  `(handler-case
       ,stuff
     (some-exception (e)
       e)))

