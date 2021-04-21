/* ---------------------------------------------------------------------------------------------------------

TITLE:: FoscMultimeasureRest


SUMMARY:: Returns a FoscMultimeasureRest.


DESCRIPTION:: TODO


USAGE::

'''

• FoscMultimeasureRest

A multimeasure rest.


• Example 1

code::
a = FoscMultimeasureRest(3/4);
a.format;


• Example 2

code::
a = FoscMultimeasureRest(4/4, multiplier: 4);
a.show;

img:: ![](../img/score-multimeasure-rest-1.png)
'''

p = "%/fosc/docs/img/score-multimeasure-rest-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

code::
Use a FoscLilypondLiteral to compress full-bar rests;

code::
a = FoscStaff([FoscMultimeasureRest(4/4, multiplier: 4)]);
a.leafAt(0).attach(FoscLilypondLiteral("\\compressFullBarRests"));
a.show;

img:: ![](../img/score-multimeasure-rest-2.png)
'''

p = "%/fosc/docs/img/score-multimeasure-rest-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



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
    '''
    • prGetBody

    Gets list of string representation of body of rest. Picked up as format contribution at format-time.
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetBody {
        ^[this.prGetCompactRepresentation];
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetCompactRepresentation
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetCompactRepresentation {
        ^"R%".format(this.prGetFormattedDuration);
    }
}
