/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscTremolo


SUMMARY:: Returns a FoscTremolo.


DESCRIPTION:: TODO


USAGE::

'''

• FoscTremolo (abjad 3.0)

Tremolo (of exactly two notes).


• Example 1

With three beams by default.

code::
a = FoscChord([61, 64], 1/4);
m = FoscTremolo();
a.attach(m);
a.show;

img:: ![](../img/indicator-tremolo-1.png)
'''

p = "%/fosc/docs/img/indicator-tremolo-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

With two beams.

code::
a = FoscChord([61, 64], 1/4);
m = FoscTremolo(beamCount: 2);
a.attach(m);
a.show;

img:: ![](../img/indicator-tremolo-2.png)
'''

p = "%/fosc/docs/img/indicator-tremolo-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

With slur.

code::
a = FoscChord([61, 64], 1/4);
m = FoscTremolo(isSlurred: true);
a.attach(m);
a.show;

img:: ![](../img/indicator-tremolo-3.png)
'''

p = "%/fosc/docs/img/indicator-tremolo-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



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
    '''
    • beamCount

    Gets beam count of tremolo.


    • Example 1

    code::
    m = FoscTremolo();
    m.beamCount;


    • Example 2

    code::
    m = FoscTremolo(beamCount: 2);
    m.beamCount;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isSlurred

    Is true when tremolo is slurred.


    • Example 1

    code::
    m = FoscTremolo(isSlurred: true);
    m.isSlurred;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Tweaks are not implemented on tremolo.

    The Lilypond '\repeat tremolo' command refuses tweaks.

    Override the Lilypond 'Beam' grob instead.
    '''
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • format

    Formats stem tremolo.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of tremolo.
    '''
    -------------------------------------------------------------------------------------------------------- */
}
