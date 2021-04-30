/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscSchemeVector


SUMMARY:: Returns a FoscSchemeVector.


DESCRIPTION:: Scheme vector

Fosc model of Scheme vector.

USAGE::

'''
Scheme vector of boolean values

code::
a = FoscSchemeVector(true, true, false)
a.format;
'''
'''
Scheme vector of symbols
code::
a = FoscSchemeVector('foo', 'bar', 'blah');
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscSchemeVector : FoscScheme {
    *new { |... args|
        ^super.new(*args).quoting_("'");
    }
}
/* ------------------------------------------------------------------------------------------------------------
• FoscSchemeVectorConstant

Fosc model of Scheme vector constant.

'''
Scheme vector of boolean values

code::
a = FoscSchemeVectorConstant(true, true, false)
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscSchemeVectorConstant : FoscScheme {
    *new { |... args|
        ^super.new(*args).quoting_("'#");
    }
}
