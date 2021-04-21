/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscSchemeVector


SUMMARY:: Returns a FoscSchemeVector.


DESCRIPTION:: TODO


USAGE::

'''

• FoscSchemeVector

Fosc model of Scheme vector.

code::
// Example 1: Scheme vector of boolean values:
a = FoscSchemeVector(true, true, false)
code::
a.format;

code::
// Example 2: Scheme vector of symbols:
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
Constant
'''
• FoscSchemeVectorConstant

Fosc model of Scheme vector constant.

code::
// Example 1: Scheme vector of boolean values:
a = FoscSchemeVectorConstant(true, true, false)
code::
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscSchemeVectorConstant : FoscScheme {
	*new { |... args|
		^super.new(*args).quoting_("'#");
	}
}