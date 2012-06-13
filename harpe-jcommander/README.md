Harpe for JCommander
====================

[JCommander](http://jcommander.org) is a very small framework for argument
parsing based on field annotations.  It performs basic type conversion
validation to String, Integer and Long that can be extended by means of the
`IParameterValidator` interface.

To cover the gap, `org.refeed.harpe.jcommander.HarpeValidator` abstract class
implements such interface for us. To take advantage of it, just define the
`getValidation` method with the appropriate expression and use
it as the validateWith argument of @Parameter.

Example: limit the range of verbosity levels from 1 to 4
--------------------------------------------------------

Validator definition:

     public static class VerbosityValidator extends HarpeValidator {
         @Override
         protected Validation<String, Integer> getValidation() {
             return integer(between(1, 4));
         }
     }

JCommander annotated field:

     @Parameter(names = { "-log", "-verbose" },
                validateWith = VerbosityValidator.class,
                description = "Level of verbosity from 1 to 4")
     public Integer verbose = 1;

See also
--------

- [JCommander documentation on validation](http://jcommander.org/#Parameter_validation)
- [Unit tests](https://github.com/sortega/harpe/tree/master/harpe-jcommander/src/test/java/org/refeed/harpe/jcommander)