/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscSchemeSymbol


SUMMARY:: Returns a FoscSchemeSymbol.


DESCRIPTION:: TODO


USAGE::

'''

• FoscSchemeSymbol

code::
a = FoscSchemeSymbol(\arrow);
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscSchemeSymbol : FoscScheme {
	*new { |symbol|
		^super.new((symbol ? \cross).asString).quoting_("'");
	}

	// PUBLIC /////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
 '''
	• symbol
	- Gets symbol string.

 code::
	a = FoscSchemeSymbol(\arrow);
	a.symbol;
 '''
	-------------------------------------------------------------------------------------------------------- */
	symbol {
        ^value;
	}
}