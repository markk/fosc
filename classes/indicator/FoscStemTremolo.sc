/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStemTremolo


SUMMARY:: Returns a FoscStemTremolo.


DESCRIPTION:: Stem tremolo


USAGE::

'''
Sixteenth-note tremolo.

code::
a = FoscNote(60, 1/4);
m = FoscStemTremolo(16);
a.attach(m);
a.show;
'''

'''
Thirty-second-note tremolo.

code::
a = FoscNote(60, 1/4);
m = FoscStemTremolo(32);
a.attach(m);
a.show;
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
    • tremoloFlags

    Gets tremolo flags of stem tremolo.

    '''
    code::
    m = FoscStemTremolo(32);
    m.tremoloFlags.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Tweals are not implemented on stem tremolo.

    The LilyPond ``:`` command refuses tweaks.

    Override the LilyPond 'StemTremolo' grob instead.
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • str

    Gets string representation of breath mark.

    '''
    code::
    m = FoscStemTremolo(32);
    m.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^":%".format(tremoloFlags);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle;
        bundle = FoscLilypondFormatBundle();
        bundle.after.stemTremolos.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
