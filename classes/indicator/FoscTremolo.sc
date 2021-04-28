/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscTremolo


SUMMARY:: Returns a FoscTremolo.


DESCRIPTION:: Tremolo (of exactly two notes).


USAGE::

'''
With three beams by default.

code::
a = FoscChord([61, 64], 1/4);
m = FoscTremolo();
a.attach(m);
a.show; // ERROR: Message 'prTremoloReattackDuration' not understood.
nointerpret
'''

'''
With two beams.

code::
a = FoscChord([61, 64], 1/4);
m = FoscTremolo(beamCount: 2);
a.attach(m);
a.show; // ERROR: Message 'prTremoloReattackDuration' not understood.
nointerpret
'''

'''
With slur.

code::
a = FoscChord([61, 64], 1/4);
m = FoscTremolo(isSlurred: true);
a.attach(m);
a.show; // ERROR: Message 'prTremoloReattackDuration' not understood.
nointerpret
'''
------------------------------------------------------------------------------------------------------------ */
FoscTremolo : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <beamCount, <isSlurred;
    var <formatSlot;
    *new { |beamCount=3, isSlurred=false|
        assert(beamCount.isInteger);
        assert(beamCount >= 1);
        assert(isSlurred.isKindOf(Boolean));
        ^super.new.init(beamCount, isSlurred);
    }
    init { |argBeamCount, argIsSlurred|
        beamCount = argBeamCount;
        isSlurred = argIsSlurred;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • beamCount

    Gets beam count of tremolo.

    '''
    code::
    m = FoscTremolo();
    m.beamCount.postln;
    '''

    '''
    code::
    m = FoscTremolo(beamCount: 2);
    m.beamCount.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • isSlurred

    Is true when tremolo is slurred.

    '''
    code::
    m = FoscTremolo(isSlurred: true);
    m.isSlurred.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Tweaks are not implemented on tremolo.

    The Lilypond ``\repeat tremolo`` command refuses tweaks.

    Override the Lilypond 'Beam' grob instead.
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • format

    Formats stem tremolo.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • str

    Gets string representation of tremolo.
    -------------------------------------------------------------------------------------------------------- */
}
