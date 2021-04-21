/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStemTremolo


SUMMARY:: Returns a FoscStemTremolo.


DESCRIPTION:: TODO


USAGE::

'''

• FoscStemTremolo (abjad 3.0)

Stem tremolo.


• Example 1

Sixteenth-note tremolo.

code::
a = FoscNote(60, 1/4);
m = FoscStemTremolo(16);
a.attach(m);
a.show;

img:: ![](../img/indicator-stem-tremolo-1.png)
'''

p = "%/fosc/docs/img/indicator-stem-tremolo-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Thirty-second-note tremolo.

code::
a = FoscNote(60, 1/4);
m = FoscStemTremolo(32);
a.attach(m);
a.show;

img:: ![](../img/indicator-stem-tremolo-2.png)
'''

p = "%/fosc/docs/img/indicator-stem-tremolo-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscStemTremolo : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <tremoloFlags;
    var <formatSlot='after';
    *new { |tremoloFlags=16|
        assert(
            tremoloFlags.isInteger && tremoloFlags.isPowerOfTwo && tremoloFlags >= 1,
            "FoscStemTremolo:new: tremoloFlags must be non-negative integer power of two: %."
                .format(tremoloFlags);
        );
        ^super.new.init(tremoloFlags);
    }
    init { |argTremoloFlags|
        tremoloFlags = argTremoloFlags;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tremoloFlags

    Gets tremolo flags of stem tremolo.


    • Example 1

    code::
    m = FoscStemTremolo(32);
    m.tremoloFlags;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Tweals are not implemented on stem tremolo.

    The LilyPond ':' command refuses tweaks.

    Override the LilyPond 'StemTremolo' grob instead.
    '''
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of breath mark.


    • Example 1

    code::
    m = FoscStemTremolo(32);
    m.str.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^":%".format(tremoloFlags);
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
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle;
        bundle = FoscLilypondFormatBundle();
        bundle.after.stemTremolos.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
