Harpe: a pluggable Java validation library
==========================================

[![Build Status](https://secure.travis-ci.org/sortega/harpe.png)](http://travis-ci.org/sortega/harpe)

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

```java
    import org.refeed.harpe.Validation;
    import static org.refeed.harpe.IntegerValidation.*;
    import static org.refeed.harpe.StringValidation.*;

    Validation<String, String> validation = trimmed(withLength(between(5, 15)));
```

Apart from the import statements and variable declaration, validator
definition is extremely compact, just `trimmed(withLength(between(5, 15)))`.
Once defined, validators can be easily used by calling `run`:

```java
    validation.run(" hi!  ");
    validation.run("very, very  long string");
```

And you will get `ValidationError` as return value with message
`"length must be at least 5"` for the former and `"length must be at most 15"`
for the latter.

```java
    validation.run("valid value  ").isValid();
    validation.run("valid value  ").getCleanValue();
```

When feeding it with valid values we get `"valid value"` which is nicely trimmed.


Examples of use
---------------

TO DO. See `test/java/org/refeed/harpe` meanwhile.

Download and install
--------------------

TO DO

Extend Harpe
------------

TO DO


License
-------

Copyleft © 2012

Distributed under the Eclipse Public License.
