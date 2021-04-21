/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscHeaderBlock


SUMMARY:: Returns a FoscHeaderBlock.


DESCRIPTION:: TODO


USAGE::

'''

• FoscHeaderBlock

A LilyPond file header block.

code::
a = FoscBlock(name: 'paper');
a.leftMargin = FoscLilypondDimension(2, 'cm');
a.rightMargin = FoscLilypondDimension(2, 'cm');
a.format;

code::
\paper {
    left-margin = 2\cm
    right-margin = 2\cm
}


code::
a = FoscHeaderBlock();
a.name;
a.title_("Missa sexti tonus");
a.composer_("Josquin");
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscHeaderBlock : FoscBlock {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	*new {
		^super.new('header');
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // properties
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    composer_ { |string|
        var markup, key;
        markup = FoscMarkup(string);
        //key = thisMethod.name.asString.strip("_");
        vars['composer'] = markup;
    }
    title_ { |string|
        var markup, key;
        markup = FoscMarkup(string);
        //key = thisMethod.name.asString.strip("_");
        vars['title'] = markup;
    }
}