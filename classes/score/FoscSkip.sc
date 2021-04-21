/* ---------------------------------------------------------------------------------------------------------

TITLE:: FoscSkip


SUMMARY:: Returns a FoscSkip.


DESCRIPTION:: TODO


USAGE::

'''

• FoscSkip


• Example 1

code::
a = FoscSkip(3/16);
a.format;


• Example 2

Implicit conversion of type when another leaf is passed as initialization argument. Indicators are preserved.

code::
n = FoscNote(60, 3/16);
n.attach(FoscArticulation('>'));
a = FoscSkip(n);
a.format;


• Example 3

code::
With a multiplier;

code::
a = FoscSkip(1/8, multiplier: 5/8);
a.format;
'''
--------------------------------------------------------------------------------------------------------- */
FoscSkip : FoscLeaf {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    *new { |writtenDuration, multiplier, tag|
        var originalArgument, new;
        originalArgument = writtenDuration;
        if (originalArgument.isKindOf(FoscLeaf)) {
            writtenDuration = originalArgument.writtenDuration;
            multiplier = originalArgument.multiplier;
        };
        writtenDuration = writtenDuration ?? { FoscDuration(1, 4) };
        new = super.new(writtenDuration, multiplier, tag);
        if (originalArgument.isKindOf(FoscLeaf)) {
            new.prCopyOverrideAndSetFromLeaf(originalArgument);
        };
        ^new;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetBody

    
    code::
    FoscSkip(4/8).prGetBody;
    
    FoscSkip(5/8).prGetBody;    // unsassignable error

    code::
    a = FoscSkip(1/8, multiplier: 5/8);
    a.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetBody {
        var result;
        result = [];
        result = result.add("s%".format(this.prGetFormattedDuration));
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetCompactRepresentation

    code::
    FoscSkip(4/8).prGetCompactRepresentation;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetCompactRepresentation {
        ^"s%".format(this.prGetFormattedDuration);
    }
}
