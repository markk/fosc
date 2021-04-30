/* ---------------------------------------------------------------------------------------------------------

TITLE:: FoscMultimeasureRest


SUMMARY:: Returns a FoscMultimeasureRest.


DESCRIPTION:: A multimeasure rest.


USAGE::

'''
code::
a = FoscMultimeasureRest(3/4);
a.format;
'''

'''
code::
a = FoscMultimeasureRest(4/4, multiplier: 4);
a.show;
'''

'''
Use a 'FoscLilypondLiteral' to compress full-bar rests

N.B.: for LilyPond versions < 2.22, use `\compressFulLBarRests`.

code::
a = FoscStaff([FoscMultimeasureRest(4/4, multiplier: 4)]);
a.leafAt(0).attach(FoscLilypondLiteral("\\compressEmptyMeasures"));
a.show;
'''
--------------------------------------------------------------------------------------------------------- */
FoscMultimeasureRest : FoscLeaf {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    *new { |writtenDuration, multiplier, tag|
        writtenDuration = writtenDuration ?? { FoscDuration(1, 4) };
        ^super.new(writtenDuration, multiplier, tag);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetBody

    Gets list of string representation of body of rest. Picked up as format contribution at format-time.
    -------------------------------------------------------------------------------------------------------- */
    prGetBody {
        ^[this.prGetCompactRepresentation];
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetCompactRepresentation
    -------------------------------------------------------------------------------------------------------- */
    prGetCompactRepresentation {
        ^"R%".format(this.prGetFormattedDuration);
    }
}
