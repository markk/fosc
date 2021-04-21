/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscBarLine


SUMMARY:: Returns a FoscBarLine.


DESCRIPTION:: TODO


USAGE::

'''

• FoscBarLine (abjad 3.0)

Bar line.


• Example 1

Attach a final bar line.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
m = FoscBarLine("|.");
a[a.lastIndex].attach(m);
a.show;

img:: ![](../img/indicator-bar-line-1.png)
'''

p = "%/fosc/docs/img/indicator-bar-line-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscBarLine : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <abbreviation;
    var <context="Staff", <formatSlot='closing';
    *new { |abbreviation|
        assert([Symbol, String].any { |type| abbreviation.isKindOf(type) });
        abbreviation = abbreviation.asString;
        ^super.new.init(abbreviation);
    }
    init { |argAbbreviation|
        abbreviation = argAbbreviation;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • abbreviation

    Gets abbreviation of bar line.

    Returns string.


    • Example 1

    code::
    m = FoscBarLine("|.");
    m.abbreviation;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • context

    Gets context.


    • Example 1

    code::
    m = FoscBarLine("|.");
    m.context;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Tweaks are not implemented on bar line.
        
    The LilyPond \bar command refuses tweaks.

    Use overrides instead.
    '''
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormat
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^"\\bar \"%\"".format(abbreviation);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle {
        var bundle;
        bundle = FoscLilypondFormatBundle();
        bundle.after.commands.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
