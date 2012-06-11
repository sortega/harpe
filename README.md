Harpe: a pluggable Java validation library
==========================================

Perseus decapitated Medusa with the mythical sword Harpe.  The same spirit of
cutting down the recurrent and tedious problems of input validation is present
in Harpe.

Harpe validators follows a simple yet flexible model: a validator is able to
take an original value of class `From` and check some properties on it.  The
result will be either a *clean* value of class `To` or a list of errors.  This
is represented with the generic interface `Validation<From,To>` (see
javadocs).

For example, one can be interested on validating a `String` value that,
ignoring whitespaces at the beginning and the end should have between 5 and 15
characters:

    import org.refeed.harpe.Validation;
    import static org.refeed.harpe.IntegerValidation.*;
    import static org.refeed.harpe.StringValidation.*;

    Validation<String, String> validation = trimmed(length(between(5, 15)));
    validation.run(" hi!  ")
    // you get "length must be at least 5"
    validation.run("very, very  long string")
    // you get "length must be at most 15"
    validation.run("valid value  ").isValid()
    // true
    validation.run("valid value  ").getCleanValue()
    // "valid value"


Examples of use
---------------

TO DO. See `test/java/org/refeed/harpe` meanwhile.

Download and install
--------------------

TO DO


License
-------

Copyleft © 2012

Distributed under the Eclipse Public License.
